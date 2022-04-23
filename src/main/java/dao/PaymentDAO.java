package dao;

import entity.Payment;
import entity.PaymentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public void savePayment(Payment payment) {
        List<Long> longStream = new ArrayList<>(this.findAllPaymentIds());
        try (Connection connection = ConnectionPool.createConnection()) {
            String sql;
            if (longStream.stream().anyMatch(id -> id.equals(payment.getId()))) {
                sql = "update user_payment " +
                        "set " +
                        "payment_status = ?," +
                        "change_date = ? " +
                        "where " +
                        "user_payment_id = ?;";
                assert connection != null;
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, payment.getPaymentStatus().toString());
                statement.setLong(2, payment.getDateChange());
                statement.setLong(3, payment.getId());

                statement.executeUpdate();
            } else {
                sql = "insert into user_payment" +
                        "(template_name, card_number, payment_sum, payment_status, creation_date, change_date) " +
                        "values (?,?,?,?,?,?);";
                assert connection != null;
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, payment.getTemplateName());
                statement.setLong(2, payment.getCardNumber());
                statement.setLong(3, payment.getPaymentSum());
                statement.setString(4, payment.getPaymentStatus().toString());
                statement.setLong(5, payment.getDateCreation());
                statement.setLong(6, payment.getDateChange());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<Payment> findPaymentWithNewStatus() {
        List<Payment> paymentList = new ArrayList<>();

        try (Connection connection = ConnectionPool.createConnection()) {
            String sql = "SELECT * from user_payment " +
                    "where " +
                    "payment_status = ?;";

            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, PaymentStatus.NEW.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getLong("user_payment_id"));
                payment.setTemplateName(resultSet.getString("template_name"));
                payment.setCardNumber(resultSet.getLong("card_number"));
                payment.setPaymentSum(resultSet.getLong("payment_sum"));
                payment.setPaymentStatus(PaymentStatus.setPaymentStatus(resultSet.getString("payment_status")));
                payment.setDateCreation(resultSet.getLong("creation_date"));
                payment.setDateChange(resultSet.getLong("change_date"));

                paymentList.add(payment);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return paymentList;
    }

    public List<Long> findAllPaymentIds() {
        List<Long> paymentList = new ArrayList<>();
        try (Connection connection = ConnectionPool.createConnection()) {

            String sql = "SELECT user_payment_id from user_payment;";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                paymentList.add(resultSet.getLong("user_payment_id"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return paymentList;
    }

    public void saveListToDB(List<Payment> paymentList) {
        try (Connection connection = ConnectionPool.createConnection()) {
            String sql = "update user_payment " +
                    "set " +
                    "payment_status = ?," +
                    "change_date = ? " +
                    "where " +
                    "user_payment_id = ?;";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Payment payment : paymentList) {
                statement.setString(1, payment.getPaymentStatus().toString());
                statement.setLong(2, payment.getDateChange());
                statement.setLong(3, payment.getId());

                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
