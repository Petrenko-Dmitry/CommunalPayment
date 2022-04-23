package dao;

import entity.Template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TemplateDAO {

    public void saveTemplate(Template template) {
        try (Connection connection = ConnectionPool.createConnection()) {

            String sql = "insert into user_template" +
                    "(template_name, iban, payment_purpose, user_address_id) " +
                    "values " +
                    "(?,?,?,?);";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, template.getTemplateName());
            statement.setString(2, template.getIban());
            statement.setString(3, template.getPaymentPurpose());
            statement.setLong(4, template.getUserAddressId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
