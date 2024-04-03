/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.lucas.tasksapp.main;

import java.util.Scanner;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Tarefa;
/**
 *
 * @author llsfj
 */
public class Programa {

   public static void main(String[] args) throws SQLException{

       int opcao;
       Scanner leitorTerminal = new Scanner(System.in);
       
       do {           
           System.out.println(""" 
                              1 - Cadastrar tarefa
                              2 - Editar tarefa
                              3 - Excluir tarefa
                              4 - Listar tarefas
                              0 - Finalizar programa
                              Digite o número correspondente da opção desejada :
                              """);
           
           opcao = leitorTerminal.nextInt();
           leitorTerminal.nextLine();
           
           switch (opcao) {
               case 0:
                   break;
               case 1:
                   cadastrarTarefa(leitorTerminal);
                   break;
               case 2:
                   editarTarefa(leitorTerminal);
                   break;
               case 3:
                   excluirTarefa(leitorTerminal);
                   break;
               case 4:
                   listarTarefas();
               default:
                   System.out.print("Digite uma opção válida: ");
           }
       } while (opcao != 0);	
   }
   
   public static void cadastrarTarefa(Scanner leitor) throws SQLException {
       // Coleta as informações a serem cadastradas
       
       System.out.println("==== CADASTRAR TAREFA ====");
       System.out.print("Informe a tarefa: ");
       String descricao = leitor.nextLine();
       System.out.print("Informe a prioridade da tarefa: ");
       int prioridade = leitor.nextInt();
       leitor.nextLine();
       
       // Começa a conexão com o banco de dados
       
       String dbURL = "jdbc:mysql://localhost:3306/tasksdb";
       String username = "root";
       String password = "";
       
       Connection conn = DriverManager.getConnection(dbURL, username, password);
       
       Tarefa tarefa;
       try {
           // "Tenta" criar uma nova tarefa, com informalções passando por nossos tratamentos definidos.
           tarefa = new Tarefa(descricao, prioridade);
           
            if(conn != null) {
                // Se a conexão for bem sucedida e o objeto tarefa for criado, insere no banco de dados.
           String sql = "insert into tarefas (tarefa, prioridade) values (?,?)";
           PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           
           preparedStatement.setString(1, tarefa.getDescricao());
           preparedStatement.setInt(2, tarefa.getPrioridade());
           
           preparedStatement.executeUpdate();
           
           ResultSet resultados = preparedStatement.getGeneratedKeys();
           int id = 0;
           
           // Recebe o id auto_increment do banco de dados e cadastra ele no nosso objeto tarefa, o que nos é disponibilizado fazer
           // ao criarmos os diferentes tipos de construtor, um deles permitindo a criação parcial dos atributos e inserindo mais
           // no decorrer do cadastro
           
           if(resultados.next()) {
               tarefa.setId(resultados.getInt(1));
           }
           
           resultados.close();
           preparedStatement.close();
           conn.close();
           
           System.out.println("Tarefa cadastrada com o id : " + tarefa.getId());
           
       } else {
           System.out.println("Não conectado");
       }
       } catch (Exception ex) {
           Logger.getLogger(Programa.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   }
   public static void editarTarefa(Scanner leitor) throws SQLException {
      
   }
   
   public static void excluirTarefa(Scanner leitor) throws SQLException {
       
   }

   public static void listarTarefas() throws SQLException {
       
       String dbURL = "jdbc:mysql://localhost:3306/tasksdb";
       String username = "root";
       String password = "";
       
       Connection conn = DriverManager.getConnection(dbURL, username, password);
       
       if(conn != null) {
           String sql = "SELECT * FROM tarefas";
           PreparedStatement preparedStatement = conn.prepareStatement(sql);
           
           ResultSet result = preparedStatement.executeQuery();
           
           while(result.next()) {
               System.out.println(result.getString(2));
               System.out.println(result.getInt(3));
           }
           
           result.close();
           preparedStatement.close();
           conn.close();
       }
   }
} 
