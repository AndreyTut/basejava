package javawebinar.basejava.storage;

import javawebinar.basejava.Config;
import javawebinar.basejava.exception.ExistStorageException;
import javawebinar.basejava.exception.NotExistStorageException;
import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.model.Resume;
import javawebinar.basejava.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        helper.executeStatement("DELETE FROM resume", statement -> {
            statement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        helper.executeStatement("UPDATE resume SET full_name=? WHERE uuid=?", statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            statement.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
            helper.executeStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", statement -> {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
                return null;
            });
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeStatement("SELECT * FROM resume r WHERE r.uuid =?", statement -> {
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
        helper.executeStatement("DELETE FROM resume WHERE uuid=?", statement -> {
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
        return helper.executeStatement("SELECT * FROM resume ORDER BY full_name, uuid", statement -> {
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
        return helper.executeStatement("SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }
}
