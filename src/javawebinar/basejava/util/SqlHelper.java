package javawebinar.basejava.util;

import javawebinar.basejava.exception.ExistStorageException;
import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeStatement(String request, StatementProcessor<T> statementProcessor) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            return statementProcessor.processStatement(preparedStatement);
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value")) {
                throw new ExistStorageException("uuid");
            }
            throw new StorageException(e);
        }
    }

    public interface StatementProcessor<T> {
        T processStatement(PreparedStatement statement) throws SQLException;
    }
}
