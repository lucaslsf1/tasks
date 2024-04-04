/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lucas.taskapp.main;

import java.util.Scanner;
import java.sql.*;
import java.util.List;
import lucas.taskapp.dao.TarefaDao;
import lucas.taskapp.model.Tarefa;

/**
 *
 * @author llsfj
 */
public class Programa {

    public static void main(String[] args) throws SQLException {

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

        Tarefa tarefa;
        try {
            // "Tenta" criar uma nova tarefa, com informalções passando por nossos tratamentos definidos.
            tarefa = new Tarefa(descricao, prioridade);

            // Passa para o tarefaDao para cadastrar no banco de dados
            TarefaDao tarefaDao = new TarefaDao();
            tarefaDao.cadastrar(tarefa);

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar tarefa: " + e.getMessage());
        }

    }

    public static void editarTarefa(Scanner leitor) throws SQLException {
        System.out.println("==== Editar tarefa ====\n");
        System.out.print("Informe o ID da tarefa que deseja editar: ");
        int id = leitor.nextInt();
        leitor.nextLine();

        try {
            // Retornamos a busca pelo id que vai devolver uma tarefa.
            TarefaDao tarefaDao = new TarefaDao();
            Tarefa tarefa = tarefaDao.buscar(id);

            if (tarefa != null) {
                System.out.println("Descrição atual: " + tarefa.getDescricao() + ".\nDigite a nova descrição: ");
                String tarefaTemporaria = leitor.nextLine();

                if (!tarefaTemporaria.equals("")) {
                    // Atualizando a descrição da tarefa se não for vazio.
                    tarefa.setDescricao(tarefaTemporaria);
                }

                System.out.println("\nPrioridade atual: " + tarefa.getPrioridade() + "\nDigite a nova prioridade: ");
                // Atualizando a prioridade da tarefa.
                tarefa.setPrioridade(leitor.nextInt());
                leitor.nextLine();

                tarefaDao.atualizar(tarefa);

                System.out.println("\nTarefa atualizada com sucesso.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }

    public static void excluirTarefa(Scanner leitor) throws SQLException {

    }

    public static void listarTarefas() throws SQLException {

        System.out.println("===== Tarefas =====");
        TarefaDao tarefaDao = new TarefaDao();
        List<Tarefa> tarefas;

        try {
            tarefas = tarefaDao.buscarTodas();

            if (tarefas != null && !tarefas.isEmpty()) {
                for (Tarefa tarefa : tarefas) {
                    System.out.println(tarefa.getId());
                    System.out.println("\t| " + tarefa.getDescricao());
                    System.out.println("\t| " + tarefa.getPrioridade());
                }
            } else {
                System.out.println("Não existem tarefas cadastradas.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar tarefas: " + e.getMessage());
        }
    }
}
