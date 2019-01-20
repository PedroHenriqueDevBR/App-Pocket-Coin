package com.droppages.pedrohenrique.pocketcoin.model;

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
    public  ToMany<Tag>             tag;
    public  ToOne<Categoria>        categoria;
    public  ToOne<Carteira>         carteira;
    public  ToOne<NaturezaDaAcao>   natureza;

    public Movimentacao(){}

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

    public boolean isConcuildo() {
        return concuildo;
    }

    public void setConcuildo(boolean concuildo) {
        this.concuildo = concuildo;
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
}
