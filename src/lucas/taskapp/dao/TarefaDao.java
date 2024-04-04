/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lucas.taskapp.dao;

import lucas.taskapp.model.Tarefa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author llsfj
 */
public class TarefaDao {

    // Método para lidar com o contato com o banco de dados.
    public void cadastrar(Tarefa tarefa) throws SQLException, Exception {
        if (tarefa != null && tarefa.isValid() && tarefa.getId() == 0) {

            Connection conexao = ConnectionFactory.getConnection();

            if (conexao != null) {
                // Se a conexão for bem sucedida e o objeto tarefa for criado, insere no banco de dados.
                String sql = "insert into tarefas (tarefa, prioridade) values (?,?)";
                PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, tarefa.getDescricao());
                preparedStatement.setInt(2, tarefa.getPrioridade());

                preparedStatement.executeUpdate();

                ResultSet resultados = preparedStatement.getGeneratedKeys();
                int id = 0;

                // Recebe o id auto_increment do banco de dados e cadastra ele no nosso objeto tarefa, o que nos é disponibilizado fazer
                // ao criarmos os diferentes tipos de construtor, um deles permitindo a criação parcial dos atributos e inserindo mais
                // no decorrer do cadastro
                if (resultados.next()) {
                    tarefa.setId(resultados.getInt(1));
                }

                resultados.close();
                preparedStatement.close();
                conexao.close();
            }
        }
    }

    public static Tarefa buscar(int id) throws SQLException, Exception {
        if (id > 0) {
            Connection conexao = ConnectionFactory.getConnection();

            if (conexao != null) {
                String sql = "SELECT * FROM tarefas WHERE id = ?";
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setInt(1, id);

                ResultSet resultado = preparedStatement.executeQuery();

                if (resultado.next()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setId(resultado.getInt("id"));
                    tarefa.setDescricao(resultado.getString("tarefa"));
                    tarefa.setPrioridade(resultado.getInt("prioridade"));
                }
            }
            return null;
        }
    }

    public List<Tarefa> buscarTodas() throws SQLException, Exception {
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM tarefas";

        PreparedStatement preparedStatement = conexao.prepareStatement(sql);

        ResultSet resultado = preparedStatement.executeQuery();

        List<Tarefa> tarefas = new ArrayList<>();

        while (resultado.next()) {
            Tarefa tarefa = new Tarefa();
            tarefa.setId(resultado.getInt("id"));
            tarefa.setDescricao(resultado.getString("tarefa"));
            tarefa.setPrioridade(resultado.getInt("prioridade"));
            tarefas.add(tarefa);
        }

        resultado.close();
        preparedStatement.close();
        conexao.close();

        return tarefas;
    }

    public void atualizar(Tarefa tarefa) throws SQLException {
        if (tarefa != null && tarefa.isValid() && tarefa.getId() > 0) {
            Connection conexao = ConnectionFactory.getConnection();

            String sqlUpdate = "UPDATE tarefas SET tarefa=?, prioridade=? WHERE id = ?";

            PreparedStatement preparedStatement = conexao.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, tarefa.getDescricao());
            preparedStatement.setInt(2, tarefa.getPrioridade());
            preparedStatement.setInt(3, tarefa.getId());

            preparedStatement.executeQuery();

            preparedStatement.close();
            conexao.close();
        }
    }
}
