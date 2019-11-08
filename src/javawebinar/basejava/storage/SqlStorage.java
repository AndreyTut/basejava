package javawebinar.basejava.storage;

import javawebinar.basejava.exception.NotExistStorageException;
import javawebinar.basejava.model.ContactType;
import javawebinar.basejava.model.Resume;
import javawebinar.basejava.sql.SqlExecutor;
import javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            executeStatement(connection, "UPDATE resume SET full_name=? WHERE uuid=?",
                    resume.getFullName(), resume.getUuid(), st -> {
                        if (st.executeUpdate() == 0) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                        return null;
                    });
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
                statement.setString(1, resume.getUuid());
                statement.execute();
            }
            storeContacts(resume, connection);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            executeStatement(connection, "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                    resume.getUuid(), resume.getFullName(), PreparedStatement::execute);
            storeContacts(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        if (value != null) {
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            r.addContact(type, value.trim());
                        }
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", statement -> {
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
        return sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid " +
                "ORDER BY r.full_name, r.uuid", statement -> {
            ResultSet resultSet = statement.executeQuery();
            List<Resume> list = new ArrayList<>();
            Resume currentResume = null;
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid").trim();
                String fullName = resultSet.getString("full_name").trim();
                String type = resultSet.getString("type");
                if (type != null) {
                    String value = resultSet.getString("value").trim();
                    if (currentResume == null) {
                        currentResume = new Resume(uuid, fullName);
                        currentResume.addContact(ContactType.valueOf(type), value);
                    } else {
                        if (uuid.equals(currentResume.getUuid())) {
                            currentResume.addContact(ContactType.valueOf(type), value);
                        } else {
                            list.add(currentResume);
                            currentResume = new Resume(uuid, fullName);
                            currentResume.addContact(ContactType.valueOf(type), value);
                        }
                    }
                } else {
                    if (currentResume != null) {
                        list.add(currentResume);
                    }
                    currentResume = new Resume(uuid, fullName);
                }
            }
            if (currentResume != null) {
                list.add(currentResume);
            }
            return list;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    private void storeContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                statement.setString(1, entry.getKey().toString());
                statement.setString(2, entry.getValue());
                statement.setString(3, resume.getUuid());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void executeStatement(Connection connection, String sql, String param1, String param2, SqlExecutor executor) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, param1);
            statement.setString(2, param2);
            executor.execute(statement);
        }

    }
}
