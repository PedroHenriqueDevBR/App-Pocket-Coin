package com.droppages.pedrohenrique.pocketcoin.model;

import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Categoria {
    @Id
    public long id;
    private String nome;
    private ToOne<NaturezaDaAcao> natureza;


    public Categoria(){}


    public Categoria(String nome) {
        this.nome = nome;
    }


    public Categoria(String nome, NaturezaDaAcao natureza) {
        this.nome = nome;
        this.natureza.setTarget(natureza);
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public NaturezaDaAcao selecionarNatureza() {
        return natureza.getTarget();
    }


    public void configurarNatureza(NaturezaDaAcao natureza) {
        this.natureza.setTarget(natureza);
    }


    /*
     * Métodos abaixo foram solicitados pelo objectBox
     * */
    public ToOne<NaturezaDaAcao> getNatureza() {
        return natureza;
    }


    public void setNatureza(ToOne<NaturezaDaAcao> natureza) {
        this.natureza = natureza;
    }

}
