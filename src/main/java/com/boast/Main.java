package com.boast;

import com.boast.dao.DaoFactory;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.Employee;
import com.boast.transferobject.Task;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class Main {
    public static void main(String[] args) {

        DaoFactory daoFactory = new MySqlDaoFactory();
        try (Connection connection = daoFactory.getConnection()) {

            connection.setAutoCommit(false);
            Savepoint svpt = connection.setSavepoint("NewEmp");

            System.out.println("Получить список всех сотрудников:");
            System.out.println(daoFactory.getEmployeeDao(connection).getAll());

            System.out.println("Получить список всех заданий:");
            System.out.println(daoFactory.getTaskDao(connection).getAll());

            System.out.println("Получить список сотрудников указанного отдела:");
            System.out.println(daoFactory.getDepartmentDao(connection).getById(5).employees);

            System.out.println("Получить для указанного сотрудника список его заданий:");
            System.out.println(daoFactory.getEmployeeDao(connection).getById(7).tasks);

            System.out.println("Добавить задание для некоторого сотрудника:");
            Employee employee1 = daoFactory.getEmployeeDao(connection).getById(7);
            Task newTask = new Task();
            newTask.setDescription("New test task");
            employee1.tasks.add(newTask);
            System.out.println(employee1.tasks);
            daoFactory.getEmployeeDao(connection).update(employee1);

            System.out.println("Удалить сотрудника:");
            Employee employee2 = daoFactory.getEmployeeDao(connection).getById(0);
            daoFactory.getEmployeeDao(connection).delete(employee2);
            System.out.println(daoFactory.getEmployeeDao(connection).getAll());

            connection.rollback(svpt);
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
