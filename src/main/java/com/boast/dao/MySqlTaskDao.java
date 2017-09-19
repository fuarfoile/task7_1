package com.boast.dao;

import com.boast.transferobject.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MySqlTaskDao implements TaskDao {
    private final Connection connection;

    Logger logger;

    public MySqlTaskDao(Connection connection) {
        this.connection = connection;
        logger = Logger.getLogger("logger");
    }

    @Override
    public boolean create(Task task)  throws SQLException {
        String sql = "INSERT INTO task7.tasks" +
                " VALUES (" + task.getId() +
                ", '" + task.getDescription() +
                "', " + task.getEmployeesNumber() + ");";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public Task getById(int id) throws SQLException {
        String sql = "SELECT * FROM task7.tasks WHERE number = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, id);

        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            return null;
        }
        Task g = new Task();
        g.setId(rs.getInt("number"));
        g.setDescription(rs.getString("description"));
        g.setEmployeesNumber(rs.getInt("employees number"));
        return g;
    }

    @Override
    public boolean update(Task task) throws SQLException{
        String sql = "UPDATE task7.tasks SET description = '" + task.getDescription() +
                "', `employees number` = '" + task.getEmployeesNumber() +
                "' WHERE number = " + task.getId() + ";";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public boolean delete(Task task) throws SQLException{
        String sql = "DELETE FROM task7.tasks" +
                " WHERE number = " + task.getId() + ";";
        Statement stm = connection.createStatement();
        return stm.executeUpdate(sql) > 0;
    }

    @Override
    public List<Task> getAll() throws SQLException {
        String sql = "SELECT * FROM task7.tasks;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<Task> list = new ArrayList<>();
        while (rs.next()) {
            Task g = new Task();
            g.setId(rs.getInt("number"));
            g.setDescription(rs.getString("description"));
            g.setEmployeesNumber(rs.getInt("employees number"));
            list.add(g);
        }
        return list;
    }
}