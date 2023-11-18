package personaladdressbooksystem;

import personaladdressbooksystem.service.PersonalAddressBookService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalAddressBookSystemClient extends JFrame {
    private JTextField nameField, addressField, phoneField;
    private JButton addButton, updateButton, deleteButton;
    private JTable resultTable;
    private JScrollPane scrollPane;

    private PersonalAddressBookService personalAddressBookService;

    public PersonalAddressBookSystemClient() {
        setTitle("个人通讯录系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 创建文本框、按钮和结果区域
        nameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(20);

        addButton = new JButton("添加");
        updateButton = new JButton("修改");
        deleteButton = new JButton("删除");

        // 创建表格
        resultTable = new JTable();
        scrollPane = new JScrollPane(resultTable);

        // 创建按钮监听器
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {
                    addContact();
                } else if (e.getSource() == updateButton) {
                    updateContact();
                } else if (e.getSource() == deleteButton) {
                    deleteContact();
                }
            }
        };

        // 添加按钮监听器
        addButton.addActionListener(buttonListener);
        updateButton.addActionListener(buttonListener);
        deleteButton.addActionListener(buttonListener);

        // 创建顶部面板并添加组件
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("姓名:"));
        topPanel.add(nameField);
        topPanel.add(new JLabel("住址:"));
        topPanel.add(addressField);
        topPanel.add(new JLabel("电话:"));
        topPanel.add(phoneField);

        // 创建底部面板并添加按钮
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);

        // 添加面板和结果区域到主窗口
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 创建AddressBookService实例
        personalAddressBookService = new PersonalAddressBookService();

        // 查询并显示联系人信息
        queryContacts();

        pack();
        setVisible(true);
    }

    private void queryContacts() {
        DefaultTableModel tableModel = personalAddressBookService.queryContacts();
        if (tableModel == null) {
            return;
        }
        resultTable.setModel(tableModel);

        // 添加表格监听器，以便在单击表格时自动填充文本框
        resultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int row = resultTable.getSelectedRow();
                if(row != -1) {
                    String name = resultTable.getValueAt(row, 0).toString();
                    String address = resultTable.getValueAt(row, 1).toString();
                    String phone = resultTable.getValueAt(row, 2).toString();
                    nameField.setText(name);
                    addressField.setText(address);
                    phoneField.setText(phone);
                }
            }
        });
    }

    private void addContact() {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        personalAddressBookService.addContact(name, address, phone);
        queryContacts();
        clearFields();
    }

    private void updateContact() {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        personalAddressBookService.updateContact(name, address, phone);
        queryContacts();
        clearFields();
    }

    private void deleteContact() {
        String name = nameField.getText();
        personalAddressBookService.deleteContact(name);
        queryContacts();
        clearFields();
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PersonalAddressBookSystemClient();
            }
        });
    }
}
