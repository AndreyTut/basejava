package javawebinar.basejava.storage;

import javawebinar.basejava.exception.NotExistStorageException;
import javawebinar.basejava.model.Resume;
import javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        helper.execute("UPDATE resume SET full_name=? WHERE uuid=?", statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", statement -> {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute("SELECT * FROM resume r WHERE r.uuid =?", statement -> {
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
        helper.execute("DELETE FROM resume WHERE uuid=?", statement -> {
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
        return helper.execute("SELECT * FROM resume ORDER BY full_name, uuid", statement -> {
            ResultSet resultSet = statement.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Resume(resultSet.getString(1), resultSet.getString(2)));
            }
            return list;
        });
    }

    @Override
    public int size() {
        return helper.execute("SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }
}
