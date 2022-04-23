package dao;

import entity.UserAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAddressDAO {

    public void saveAddress(UserAddress address) {
        try (Connection connection = ConnectionPool.createConnection()) {

            String sql = "insert into user_address" +
                    "(address, user_email) " +
                    "values " +
                    "(?,?);";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, address.getAddress());
            statement.setString(2, address.getUsersEmail());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
