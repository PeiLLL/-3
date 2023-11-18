package personaladdressbooksystem.service;

import java.sql.*;

public class PersonalAddressBookDao {
    private Connection connection;
    private Statement statement;

    public void connectToDatabase() {
        try {
            // 连接数据库
            connection = DriverManager.getConnection("jdbc:mysql://localhost/shiyan3", "root", "root");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query() {
        try {
            return statement.executeQuery("SELECT * FROM shiyan3");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(String name,String address,String phone) {
        try {
            statement.executeUpdate("INSERT INTO shiyan3 (name, address, phone) VALUES ('" + name + "', '" + address + "', '" + phone + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String name,String address,String phone) {
        try {
            statement.executeUpdate("UPDATE shiyan3 SET address='" + address + "',phone='" + phone + "' WHERE name='" + name + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String name) {
        try {
            statement.executeUpdate("DELETE FROM shiyan3 WHERE name='" + name + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}