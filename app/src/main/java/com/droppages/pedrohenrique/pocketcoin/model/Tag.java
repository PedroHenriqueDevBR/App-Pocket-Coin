package com.droppages.pedrohenrique.pocketcoin.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Tag {
    @Id
    public long id;
    private String nome;

    public Tag(){}

    public Tag(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
