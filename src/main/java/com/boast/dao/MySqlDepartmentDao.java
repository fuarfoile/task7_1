package com.boast.dao;

import com.boast.transferobject.Department;
import com.boast.transferobject.Employee;
import com.boast.transferobject.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDepartmentDao implements DepartmentDao {
    private final Connection connection;

    public MySqlDepartmentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Department department)  throws SQLException {
        String sql = "INSERT INTO task7.departments" +
                " VALUES (" + department.getId() +
                ", '" + department.getName() +
                "', '" + department.getPhoneNumber() + "');";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public Department getById(int id) throws SQLException {
        String sql = "SELECT * FROM task7.departments WHERE number = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, id);

        ResultSet rs = stm.executeQuery();
        rs.next();
        Department g = new Department();
        g.setId(rs.getInt("number"));
        g.setName(rs.getString("name"));
        g.setPhoneNumber(rs.getString("phone number"));

        String sq2 = "SELECT * FROM task7.employees WHERE `department number` = " + g.getId() + ";";
        PreparedStatement stm2 = connection.prepareStatement(sq2);

        ResultSet rs2 = stm2.executeQuery();
        g.employees = new ArrayList<>();
        while (rs2.next()) {
            Employee employee = new Employee();
            employee.setId(rs2.getInt("number"));
            employee.setName(rs2.getString("first name"));
            employee.setSurname(rs2.getString("second name"));
            employee.setPosition(rs2.getString("position"));
            employee.setDepartmentNumber(rs2.getInt("department number"));
            g.employees.add(employee);
        }

        return g;
    }

    @Override
    public boolean update(Department department) throws SQLException{
        String sql = "UPDATE task7.departments SET `name` = '" + department.getName() +
                "', `phone number` = '" + department.getPhoneNumber() +
                "' WHERE number = " + department.getId() + ";";
        Statement stm = connection.createStatement();

        MySqlEmployeeDao mySqlEmployeeDao = new MySqlEmployeeDao(connection);
        for (Employee employee : department.employees) {
            employee.setDepartmentNumber(department.getId());
            if (mySqlEmployeeDao.getById(employee.getId()) != null) {
                mySqlEmployeeDao.update(employee);
            } else {
                mySqlEmployeeDao.create(employee);
            }
        }

        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public boolean delete(Department department) throws SQLException{
        MySqlEmployeeDao mySqlEmployeeDao = new MySqlEmployeeDao(connection);
        List<Employee> employeeListList = mySqlEmployeeDao.getAll();

        for (Employee employee : employeeListList) {
            if (employee.getDepartmentNumber() == department.getId()) {
                mySqlEmployeeDao.delete(employee);
            }
        }

        String sql = "DELETE FROM task7.departments" +
                " WHERE number = " + department.getId() + ";";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public List<Department> getAll() throws SQLException {
        String sql = "SELECT * FROM task7.departments;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<Department> list = new ArrayList<>();
        while (rs.next()) {
            Department g = getById(rs.getInt("number"));
            list.add(g);
        }
        return list;
    }
}