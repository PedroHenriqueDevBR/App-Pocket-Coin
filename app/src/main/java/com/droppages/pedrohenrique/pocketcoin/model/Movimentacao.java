package com.droppages.pedrohenrique.pocketcoin.model;

import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Movimentacao {
    @Id
    public  long                    id;
    private double                  valor;
    private boolean                 concuildo;
    private String                  data;
    private String                  descricao;
    private ToMany<Tag>             tag;
    private ToOne<Categoria>        categoria;
    private ToOne<Carteira>         carteira;
    private ToOne<NaturezaDaAcao>   natureza;


    public Movimentacao(){}


    public Movimentacao(long id, double valor, String data, String descricao, boolean concuildo) {
        this.id = id;
        this.valor = valor;
        this.concuildo = concuildo;
        this.data = data;
        this.descricao = descricao;
    }


    public Movimentacao(double valor, String data, String descricao, boolean concuildo) {
        this.valor = valor;
        this.concuildo = concuildo;
        this.data = data;
        this.descricao = descricao;
    }


    public double getValor() {
        return valor;
    }


    public void setValor(double valor) {
        this.valor = valor;
    }


    public void concluirMovimentacao() {
        this.concuildo = true;
    }


    public boolean isConcuildo() {
        return concuildo;
    }


    public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public NaturezaDaAcao selecionarNatureza() {
        return natureza.getTarget();
    }


    public void configurarNatureza(NaturezaDaAcao natureza) {
        this.natureza.setTarget(natureza);
    }


    public Categoria selecionarCategoria() {
        return categoria.getTarget();
    }


    public void configurarCategoria(Categoria categoria) {
        this.categoria.setTarget(categoria);
    }


    public Carteira selecionarCarteira() {
        return carteira.getTarget();
    }


    public void configurarCarteira(Carteira carteira) {
        this.carteira.setTarget(carteira);
    }


    public List<Tag> selecionarListaDeTag() {
        return tag;
    }


    public void adicionarTag(Tag tag) {
        this.tag.add(tag);
    }


    /*
     * Métodos abaixo foram solicitados pelo objectBox
     * */
    public void setConcuildo(boolean concuildo) {
        this.concuildo = concuildo;
    }


    public ToMany<Tag> getTag() {
        return tag;
    }


    public void setTag(ToMany<Tag> tag) {
        this.tag = tag;
    }


    public ToOne<Categoria> getCategoria() {
        return categoria;
    }


    public void setCategoria(ToOne<Categoria> categoria) {
        this.categoria = categoria;
    }


    public ToOne<Carteira> getCarteira() {
        return carteira;
    }


    public void setCarteira(ToOne<Carteira> carteira) {
        this.carteira = carteira;
    }


    public ToOne<NaturezaDaAcao> getNatureza() {
        return natureza;
    }


    public void setNatureza(ToOne<NaturezaDaAcao> natureza) {
        this.natureza = natureza;
    }
}