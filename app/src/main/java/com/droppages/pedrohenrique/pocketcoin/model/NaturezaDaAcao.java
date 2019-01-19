package com.droppages.pedrohenrique.pocketcoin.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class NaturezaDaAcao {
    @Id
    public long id;
    private String nome;

    public NaturezaDaAcao(){}

    public NaturezaDaAcao(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
