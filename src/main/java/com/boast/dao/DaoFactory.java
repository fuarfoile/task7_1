package com.boast.dao;

import com.boast.transferobject.Department;
import com.boast.transferobject.Task;
import com.boast.transferobject.Employee;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoFactory {

    /** Возвращает подключение к базе данных */
    public Connection getConnection() throws SQLException;

    /** Возвращает объект для управления персистентным состоянием объекта Task */
    public GenericDao<Task> getTaskDao(Connection connection);

    /** Возвращает объект для управления персистентным состоянием объекта Employee */
    public GenericDao<Employee> getEmployeeDao(Connection connection);

    /** Возвращает объект для управления персистентным состоянием объекта Department */
    public GenericDao<Department> getDepartmentDao(Connection connection);
}