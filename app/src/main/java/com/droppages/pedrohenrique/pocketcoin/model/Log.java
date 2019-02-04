package com.droppages.pedrohenrique.pocketcoin.model;

import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Log {
    @Id
    public long id;
    private ToMany<Movimentacao> movimentacoes;


    public Log(){}


    public List<Movimentacao> selecionarListaDeMovimentacao() {
        return movimentacoes;
    }


    public void adicionarNovaMovimentacao(Movimentacao movimentacao) {
        this.movimentacoes.add(movimentacao);
    }


    public ToMany<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }


    public void setMovimentacoes(ToMany<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}
