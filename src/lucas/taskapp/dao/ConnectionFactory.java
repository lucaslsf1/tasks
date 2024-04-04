/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lucas.taskapp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


/**
 *
 * @author llsfj
 */
public class ConnectionFactory {
    
    public static Connection getConnection() throws SQLException {
       String dbURL = "jdbc:mysql://localhost:3306/tasksdb";
       String username = "root";
       String password = "";
       
       Connection conexao = DriverManager.getConnection(dbURL, username, password);
       
       return conexao;
    }
}
