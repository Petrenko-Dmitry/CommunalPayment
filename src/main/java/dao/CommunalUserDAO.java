package dao;

import entity.CommunalUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommunalUserDAO {

    public void saveUser(CommunalUser user) {
        try (Connection connection = ConnectionPool.createConnection()) {
            String sql = "insert into communal_user" +
                    "(fio, email, phone_number) " +
                    "values " +
                    "(?,?,?); ";

            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFio());
            statement.setString(2, user.getEmail());
            statement.setLong(3, user.getPhoneNumber());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
