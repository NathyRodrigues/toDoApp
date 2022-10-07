package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

public class TaskController {
    
    public void save (Task task) {
        
        String sql = "INSERT INTO tasks"
               + "(idProject, name, description, status, notes, deadline, "
               + "completed, createdAt, updatedAt)"
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setByte(4, task.getStatus());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statement.setBoolean(7, task.isCompleted());
            statement.setDate(8, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(9, new java.sql.Date(task.getUpdatedAt().getTime()));
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException ("Erro ao salvar tarefa"+ ex.getMessage(), ex); 
        } finally {        
         try {
             if (statement !=null) {
             statement.close();        
            }
         if (connection != null) {
             connection.close();
        }
        } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
    }
    }
      
        public void update (Task task) {
        String sql = "UPDATE tasks SET " 
                + "idProject = ?," + "name =?, description =?, notes =?, "
                + "deadline =?,completed =?," + "createdAt =?," + "updatedAt =? "
                + "WHERE id = ?";
            
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString (2, task.getName());
            statement.setString (3, task.getDescription ());
            statement.setBoolean (4, task.isIsCompleted());
            statement.setString (5, task.getNotes());
            statement.setDate (6, new Date (task.getDeadline().getTime()));
            statement.setDate (7, new Date (task.getCreatedAt().getTime()));
            statement.setDate (8, new Date (task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            statement.setByte(10, task.getStatus());
            statement.execute ();   
            
        } catch (SQLException ex) {
      throw new RuntimeException ("Erro ao atualizar tarefa" + ex.getMessage(), ex); 
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }
    }
        
        
    public void getAll (){
        
        String sql = "SELECT * FROM tasks";
        List <Task> tasks = new ArrayList <>();
                
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
       
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement (sql);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Task task = new Task ();
                task.setId(resultSet.getInt ("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setStatus(resultSet.getByte("status"));
                task.setNotes (resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("creaatedAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            throw new RuntimeException ("Erro ao buscar as tarefas", ex);
        }
    }
}
    