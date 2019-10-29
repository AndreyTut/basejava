package javawebinar.basejava.storage;

import javawebinar.basejava.exception.ExistStorageException;
import javawebinar.basejava.exception.NotExistStorageException;
import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.model.Resume;
import javawebinar.basejava.sql.ConnectionFactory;
import javawebinar.basejava.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        helper = new SqlHelper();
    }

    @Override
    public void clear() {
        helper.executeStatement(connectionFactory, "DELETE FROM resume", statement -> {
            statement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        delete(resume.getUuid());
        save(resume);
    }

    @Override
    public void save(Resume resume) {
        try {
            helper.executeStatement(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", statement -> {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
                return null;
            });
        } catch (StorageException e) {
            if (e.getMessage().contains("duplicate key value")) {
                throw new ExistStorageException(resume.getUuid());
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid =?", statement -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        helper.executeStatement(connectionFactory, "DELETE FROM resume WHERE uuid=?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            statement.execute();
            return null;
        });

    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.executeStatement(connectionFactory, "SELECT * FROM resume ORDER BY full_name, uuid", statement -> {
            ResultSet resultSet = statement.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Resume(resultSet.getString(1).trim(), resultSet.getString(2).trim()));
            }
            return list;
        });
    }

    @Override
    public int size() {
        return helper.executeStatement(connectionFactory, "SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }
}
