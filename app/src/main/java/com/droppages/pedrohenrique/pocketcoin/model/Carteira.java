package com.droppages.pedrohenrique.pocketcoin.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Carteira {
    @Id
    public long id;
    private String nome;
    private float saldo;

    public Carteira(){}

    public Carteira(String nome, float saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    public boolean depositar(float valor){
        if (valor > 0){
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public boolean sacar(float valor){
        saldo -= valor;
        return false;
    }

    public void transferir(Carteira destino, float valor){
        destino.depositar(valor);
        this.sacar(valor);
    }

    private boolean haSaldoSuficiente(float valor){
        return saldo - valor > 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

}
