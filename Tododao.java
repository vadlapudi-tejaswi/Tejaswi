
package com.example.dao;
import com.example.model.Todo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/todo_tasks";
    private String jdbcUsername = "root";   
    private String jdbcPassword = "root"; 
 
    private static final String INSERT_TODO_SQL =
            "INSERT INTO todo_items (todo_title, todo_desc, todo_datetime,todo_targetdate, todo_status) VALUES (?,?, ?, ?, ?)";
    
    private static final String SELECT_TODO_BY_ID =
            "SELECT id, todo_title, todo_desc, todo_datetime, todo_status FROM todo_items WHERE id = ?";
    
    private static final String SELECT_ALL_TODOS =
            "SELECT id, todo_title, todo_desc, todo_datetime, todo_status FROM todo_items";
    
    private static final String DELETE_TODO_SQL =
            "DELETE FROM todo_items WHERE id = ?";
    
    private static final String UPDATE_TODO_SQL =
            "UPDATE todo_items SET todo_title = ?, todo_desc = ?, todo_datetime = ?, todo_status = ? WHERE id = ?";


    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


    public void insertTodo(Todo todo) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_TODO_SQL)) {

            ps.setString(1, todo.getTodoTitle());
            ps.setString(2, todo.getTodoDesc());
            ps.setString(3, todo.getTodoDateTime() 
            		);
            ps.setString(4, todo.getTodoStatus());

            ps.executeUpdate();
            System.out.println("Todo inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Todo selectTodo(int id) {
        Todo todo = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_TODO_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setTodoTitle(rs.getString("todo_title"));
                todo.setTodoDesc(rs.getString("todo_desc"));
                todo.setTodoDateTime(rs.getString("todo_datetime"));
                todo.setTodoStatus(rs.getString("todo_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todo;
    }


    public List<Todo> selectAllTodos() {
        List<Todo> todos = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_TODOS)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setTodoTitle(rs.getString("todo_title"));
                todo.setTodoDesc(rs.getString("todo_desc"));
                todo.setTodoDateTime(rs.getString("todo_datetime"));
                todo.setTodoStatus(rs.getString("todo_status"));
                todos.add(todo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todos;
    }


    public boolean updateTodo(Todo todo) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_TODO_SQL)) {

            ps.setString(1, todo.getTodoTitle());
            ps.setString(2, todo.getTodoDesc());
            ps.setString(3, todo.getTodoDateTime());
            ps.setString(4, todo.getTodoStatus());
            ps.setInt(5, todo.getId());

            rowUpdated = ps.executeUpdate() > 0;
            if (rowUpdated) {
                System.out.println(" Todo updated successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }
    
    
    
    
    public boolean deleteTodo(int id) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_TODO_SQL)) {

            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
            if (rowDeleted) {
                System.out.println(" Todo deleted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
