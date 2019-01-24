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
    public  ToMany<Movimentacao>    movimentacoes;
    public  ToMany<Configuracao>    configuracoes;
    public  ToMany<Carteira>        carteiras;
    public  ToMany<Categoria>       categorias;
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

    public ToMany<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(ToMany<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public ToMany<Configuracao> getConfiguracoes() {
        return configuracoes;
    }

    public void setConfiguracoes(ToMany<Configuracao> configuracoes) {
        this.configuracoes = configuracoes;
    }

    public ToMany<Carteira> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(ToMany<Carteira> carteiras) {
        this.carteiras = carteiras;
    }

    public ToMany<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(ToMany<Categoria> categorias) {
        this.categorias = categorias;
    }

    public ToMany<Tag> getTags() {
        return tags;
    }

    public void setTags(ToMany<Tag> tags) {
        this.tags = tags;
    }

    public ToMany<Log> getLog() {
        return log;
    }

    public void setLog(ToMany<Log> log) {
        this.log = log;
    }
}
