/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lucas.taskapp.model;

import lucas.taskapp.exceptions.InvalidIdAttributionException;
import java.lang.Exception;

/**
 *
 * @author llsfj
 */
public class Tarefa {

    private int id;
    private String descricao;
    private int prioridade;

    // Não recebe nenhum parâmetro, situação de criar uma nova tarefa e aos poucos ir setando os dados dela
    public Tarefa() {
        this.id = 0; // Indicando que essa tarefa ainda não recebeu um id do db;
    }

    public Tarefa(String descricao, int prioridade) throws Exception {
        this.id = 0;
        setDescricao(descricao);
        setPrioridade(prioridade);
    }

    //Vai ser utilizado quando formos recuperar as informações do banco de dados, porque ele ja tem o id definido lá.
    public Tarefa(int id, String descricao, int prioridade) throws Exception {
        this(descricao, prioridade);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        // Utilizando a classe setId para fazer o tratamento de alguns erros.

        if (id < 0) {
            throw new Exception("O ID deve ser um inteiro maior que 0!");
        }

        if (this.id != 0) {
            // Criando uma Exception personalizada.
            throw new InvalidIdAttributionException("O Id da tarefa não pode ser alterado.");
        }
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) throws Exception {
        if (descricao != null && descricao.length() > 0 && descricao.length() <= 200) {
            this.descricao = descricao;
        } else {
            throw new Exception("A descrição da tarefa não pode ter mais de 200 caracteres e nem ser vazia.");
        }
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) throws Exception {
        if (prioridade >= 1 && prioridade <= 5) {
            this.prioridade = prioridade;
        } else {
            throw new Exception("A prioridade da tarefa deve ser um inteiro entre 1 e 5");
        }
    }

    public String getNomePrioridade() {
        return switch (this.prioridade) {
            case 1 ->
                "Prioridade mínima";
            case 2 ->
                "Prioridade baixa";
            case 3 ->
                "Prioridade média";
            case 4 ->
                "Prioridade alta";
            case 5 ->
                "Prioridade altissima";
            default ->
                null;
        };
    }

    public boolean isValid() {
        //Método utilitário pra saber se o objeto está em situação de ser armazenado na base.
        if (this.descricao != null && this.prioridade >= 1 && this.prioridade <= 5) {
            return true;
        } else {
            return false;
        }
    }
}
