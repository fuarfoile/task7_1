package com.boast.dao;

import com.boast.transferobject.Department;
import com.boast.transferobject.Task;
import com.boast.transferobject.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySqlDaoFactory implements DaoFactory {

    private String driver = "com.mysql.jdbc.Driver";//Имя драйвера

    public MySqlDaoFactory() {
        try {
            Class.forName(driver);//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url      = resource.getString("url");        //URL адрес
        String user     = resource.getString("user");       //Логин пользователя
        String password = resource.getString("password");   //Пароль пользователя

        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public GenericDao<Task> getTaskDao(Connection connection) {
        return new MySqlTaskDao(connection);
    }

    @Override
    public GenericDao<Employee> getEmployeeDao(Connection connection) {
        return new MySqlEmployeeDao(connection);
    }

    @Override
    public GenericDao<Department> getDepartmentDao(Connection connection) {
        return new MySqlDepartmentDao(connection);
    }
}