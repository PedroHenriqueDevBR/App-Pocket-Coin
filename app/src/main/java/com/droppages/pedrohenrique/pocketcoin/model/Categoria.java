package com.droppages.pedrohenrique.pocketcoin.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Categoria {
    @Id
    public long id;
    private String nome;
    public ToOne<NaturezaDaAcao> natureza;

    public Categoria(){}

    public Categoria(String tipo, String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
