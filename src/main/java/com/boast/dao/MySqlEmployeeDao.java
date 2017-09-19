package com.boast.dao;

import com.boast.transferobject.Employee;
import com.boast.transferobject.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MySqlEmployeeDao implements EmployeeDao {
    private final Connection connection;

    Logger logger;

    public MySqlEmployeeDao(Connection connection) {
        this.connection = connection;
        logger = Logger.getLogger("logger");
    }

    @Override
    public boolean create(Employee employee)  throws SQLException {
        String sql = "INSERT INTO task7.employees" +
                " VALUES (" + employee.getId() +
                ", '" + employee.getSurname() +
                "', '" + employee.getName() +
                "', '" + employee.getPosition() +
                "', " + employee.getDepartmentNumber() + ");";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public Employee getById(int id) throws SQLException {
        String sql = "SELECT * FROM task7.employees WHERE number = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, id);

        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            return null;
        }
        Employee g = new Employee();
        g.setId(rs.getInt("number"));
        g.setName(rs.getString("first name"));
        g.setSurname(rs.getString("second name"));
        g.setPosition(rs.getString("position"));
        g.setDepartmentNumber(rs.getInt("department number"));

        String sq2 = "SELECT * FROM task7.tasks WHERE `employees number` = " + rs.getInt("number") + ";";
        PreparedStatement stm2 = connection.prepareStatement(sq2);

        ResultSet rs2 = stm2.executeQuery();
        g.tasks = new ArrayList<>();
        while (rs2.next()) {
            Task task = new Task();
            task.setId(rs2.getInt("number"));
            task.setDescription(rs2.getString("description"));
            task.setEmployeesNumber(rs2.getInt("employees number"));
            g.tasks.add(task);
        }

        return g;
    }

    @Override
    public boolean update(Employee employee) throws SQLException{
        String sql = "UPDATE task7.employees SET `first name` = '" + employee.getName() +
                "', `second name` = '" + employee.getSurname() +
                "', `department number` = '" + employee.getDepartmentNumber() +
                "', `position` = '" + employee.getPosition() +
                "' WHERE number = " + employee.getId() + ";";
        Statement stm = connection.createStatement();

        MySqlTaskDao mySqlTaskDao = new MySqlTaskDao(connection);
        for (Task task : employee.tasks) {
            task.setEmployeesNumber(employee.getId());
            if (mySqlTaskDao.getById(task.getId()) != null) {
                mySqlTaskDao.update(task);
            } else {
                mySqlTaskDao.create(task);
            }
        }

        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public boolean delete(Employee employee) throws SQLException{
        String sq2 = "DELETE FROM task7.tasks" +
                " WHERE `employees number` = " + employee.getId() + ";";
        Statement stm = connection.createStatement();
        stm.executeUpdate(sq2);

        String sql = "DELETE FROM task7.employees" +
                " WHERE number = " + employee.getId() + ";";
        stm = connection.createStatement();
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM task7.employees;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<Employee> list = new ArrayList<>();
        while (rs.next()) {
            Employee g = getById(rs.getInt("number"));
            list.add(g);
        }
        return list;
    }
}