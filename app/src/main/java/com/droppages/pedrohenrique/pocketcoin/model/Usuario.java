package com.droppages.pedrohenrique.pocketcoin.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Usuario {
    @Id
    public  long id;
    private String                  nome;
    private String                  login;
    private String                  senha;
    public  ToMany<Configuracoes>   configuracoes;
    public  ToMany<Carteira>        carteiras;
    public  ToMany<Categoria>       categorias;
    public  ToMany<Movimentacao>    movimentacoes;
    public  ToMany<Tag>             tags;
    public  ToMany<Log>             log;

    public Usuario(){}

    public Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
