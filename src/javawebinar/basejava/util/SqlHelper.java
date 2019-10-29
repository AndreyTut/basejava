package javawebinar.basejava.util;

import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public <T> T executeStatement(ConnectionFactory connectionFactory, String request, StatementProcessor<T> statementProcessor) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            return statementProcessor.processStatement(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface StatementProcessor<T> {
        T processStatement(PreparedStatement statement) throws SQLException;
    }
}
