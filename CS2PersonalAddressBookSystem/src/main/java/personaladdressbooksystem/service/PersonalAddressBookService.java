package personaladdressbooksystem.service;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PersonalAddressBookService {
    private PersonalAddressBookDao personalAddressBookDao;

    public PersonalAddressBookService() {
        personalAddressBookDao = new PersonalAddressBookDao();
        personalAddressBookDao.connectToDatabase();
    }

    public DefaultTableModel queryContacts() {
        try {
            ResultSet resultSet = personalAddressBookDao.query();
            // 检查结果集是否为空
            if (!resultSet.next()) {
                return new DefaultTableModel(); // 创建一个空的表格模型
            }

            resultSet.beforeFirst(); // 将结果集指针重置到第一行

            // 将查询结果放入表格中
            return buildTableModel(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addContact(String name, String address, String phone) {
        personalAddressBookDao.add(name, address, phone);
    }

    public void updateContact(String name, String address, String phone) {
        personalAddressBookDao.update(name, address, phone);
    }

    public void deleteContact(String name) {
        personalAddressBookDao.delete(name);
    }

    // 将ResultSet转换为TableModel
    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // 获取列数
        int columnCount = metaData.getColumnCount();

        // 获取列名
        String[] columnNames = new String[columnCount];
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columnNames[columnIndex - 1] = metaData.getColumnLabel(columnIndex);
        }

        // 将查询结果放入Object数组中
        Object[][] data = new Object[0][columnCount];
        int rowCount = 0;
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                row[columnIndex - 1] = rs.getObject(columnIndex);
            }
            data = addRowToMatrix(data, row);
            rowCount++;
        }

        // 创建TableModel并返回
        return new DefaultTableModel(data, columnNames);
    }

    // 将一个行数组添加到矩阵中
    private Object[][] addRowToMatrix(Object[][] matrix, Object[] row) {
        if (matrix == null || matrix.length == 0) {
            return new Object[][] {row};
        }

        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        if (columnCount == 0) {
            columnCount = row.length;
        }

        Object[][] result = new Object[rowCount + 1][columnCount];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            result[rowIndex] = matrix[rowIndex];
        }

        result[rowCount] = row;

        return result;
    }


}

