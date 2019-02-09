package com.droppages.pedrohenrique.pocketcoin.model;

import java.util.List;

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
    private ToMany<Movimentacao>    movimentacoes;
    private ToMany<Configuracao>    configuracoes;
    private ToMany<Carteira>        carteiras;
    private ToMany<Categoria>       categorias;
    private ToMany<Tag>             tags;


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


    public List<Movimentacao> selecionarMovimentacoes() {
        return movimentacoes;
    }


    public void adicionarMovimentacao(Movimentacao movimentacao) {
        this.movimentacoes.add(movimentacao);
    }


    public List<Configuracao> selecionarListaDeConfiguracoes() {
        return configuracoes;
    }


    public void adicionarConfiguracao(Configuracao configuracao) {
        this.configuracoes.add(configuracao);
    }


    public List<Carteira> selecionarListaDeCarteiras() {
        return carteiras;
    }


    public void adicionarCarteira(Carteira carteira) {
        this.carteiras.add(carteira);
    }


    public List<Categoria> selecionarListaDeCategorias() {
        return categorias;
    }


    public void adicionarCategoria(Categoria categoria) {
        this.categorias.add(categoria);
    }


    public List<Tag> selecionarListaDeTags() {
        return tags;
    }


    public void adicionarTag(Tag tag) {
        this.tags.add(tag);
    }


    public ToMany<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    /*
    * Métodos abaixo foram solicitados pelo objectBox
    * */
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

}
