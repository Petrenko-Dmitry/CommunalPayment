package dao;

import java.sql.*;
import java.util.*;


public class CreateTable {

    public void createTable() {
        this.createTableCommunalUserIfNotExist();
        this.createTableUserAddressIfNotExist();
        this.createTableUserTemplateIfNotExist();
        this.createTableUserPaymentIfNotExist();
    }

    private void createTableCommunalUserIfNotExist() {
        if (!Objects.requireNonNull(this.checkForTables()).contains("communal_user")) {
            try (Statement statement = Objects.requireNonNull(ConnectionPool.createConnection()).createStatement()) {
                String sql = "create table communal_user" +
                        "(" +
                        "fio varchar," +
                        "email varchar primary key," +
                        "phone_number bigint" +
                        ");";
                statement.executeUpdate(sql);

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void createTableUserAddressIfNotExist() {
        if (!Objects.requireNonNull(this.checkForTables()).contains("user_address")) {
            try (Statement statement = Objects.requireNonNull(ConnectionPool.createConnection()).createStatement()) {
                String sql = "create sequence user_address_serial;" +
                        "alter sequence user_address_serial start 1000001;" +
                        "alter sequence user_address_serial restart 1000001;" +
                        "alter sequence user_address_serial MINVALUE 1000001;" +
                        "create table user_address" +
                        "(" +
                        "user_address_id bigint NOT NULL DEFAULT nextval('user_address_serial') primary key," +
                        "address varchar," +
                        "user_email varchar," +
                        "foreign key(user_email) references communal_user (email) on delete cascade" +
                        ");";

                statement.executeUpdate(sql);

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void createTableUserTemplateIfNotExist() {
        if (!Objects.requireNonNull(this.checkForTables()).contains("user_template")) {
            try (Statement statement = Objects.requireNonNull(ConnectionPool.createConnection()).createStatement()) {
                String sql = "create sequence user_template_serial;" +
                        "alter sequence user_template_serial start 1000001;" +
                        "alter sequence user_template_serial restart 1000001;" +
                        "alter sequence user_template_serial MINVALUE 1000001;" +
                        "create table user_template" +
                        "(" +
                        "user_template_id bigint NOT NULL DEFAULT nextval('user_template_serial') primary key," +
                        "template_name varchar," +
                        "iban varchar," +
                        "payment_purpose varchar," +
                        "user_address_id bigint," +
                        "foreign key(user_address_id) references user_address (user_address_id) on delete cascade" +
                        ");";

                statement.executeUpdate(sql);

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void createTableUserPaymentIfNotExist() {
        if (!Objects.requireNonNull(this.checkForTables()).contains("user_payment")) {
            try (Statement statement = Objects.requireNonNull(ConnectionPool.createConnection()).createStatement()) {
                String sql = "create sequence user_payment_serial;" +
                        "alter sequence user_payment_serial start 1000001;" +
                        "alter sequence user_payment_serial restart 1000001;" +
                        "alter sequence user_payment_serial MINVALUE 1000001;" +
                        "create table user_payment" +
                        "(" +
                        "user_payment_id bigint NOT NULL DEFAULT nextval('user_payment_serial')," +
                        "template_name varchar," +
                        "card_number bigint," +
                        "payment_sum bigint," +
                        "payment_status varchar(7)," +
                        "creation_date bigint," +
                        "change_date bigint" +
                        ");";

                statement.executeUpdate(sql);

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    private List<String> checkForTables() {
        List<String> tableNames = new ArrayList<>();
        try (Connection connection = ConnectionPool.createConnection()) {
            String sql = "SELECT * FROM pg_catalog.pg_tables;";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("tablename"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return tableNames;
    }
}

