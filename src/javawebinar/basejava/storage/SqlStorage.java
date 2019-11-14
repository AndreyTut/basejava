package javawebinar.basejava.storage;

import javawebinar.basejava.exception.NotExistStorageException;
import javawebinar.basejava.model.*;
import javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("deleting all resumes");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        LOG.info("update " + resume);
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(resume, connection);
            deleteSections(resume, connection);
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("save " + resume);
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
            }
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, resume);
                }
            }
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    addSection(resultSet, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });

    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume r ORDER BY r.full_name, r.uuid")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    map.put(uuid, new Resume(uuid, resultSet.getString("full_name")));
                }
            }
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = map.get(resultSet.getString("resume_uuid"));
                    addContact(resultSet, resume);
                }
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM section")){
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    Resume resume = map.get(resultSet.getString("resume_uuid"));
                    addSection(resultSet, resume);
                }
            }
            return new ArrayList<>(map.values());
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

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
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

    private void deleteContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            statement.setString(1, resume.getUuid());
            statement.execute();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value.trim());
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO section (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                AbstractSection as = entry.getValue();
                String section = as instanceof ListSection ? String.join("\n", ((ListSection) as).getItems()) :
                        ((TextSection) as).getContent();
                statement.setString(1, entry.getKey().toString());
                statement.setString(2, section);
                statement.setString(3, resume.getUuid());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void addSection(ResultSet resultSet, Resume resume) throws SQLException {
        String sectionContext = resultSet.getString("value");
        if (sectionContext != null) {
            AbstractSection section;
            String[] sectionInArray = sectionContext.split("\n");
            sectionInArray[sectionInArray.length - 1] = sectionInArray[sectionInArray.length - 1].trim();
            if (sectionInArray.length == 1) {
                section = new TextSection(sectionInArray[0]);
            } else {
                section = new ListSection(Arrays.asList(sectionInArray));
            }
            resume.addSection(SectionType.valueOf(resultSet.getString("type").trim()), section);
        }
    }

    private void deleteSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            statement.setString(1, resume.getUuid());
            statement.execute();
        }
    }
}
