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
    public ToOne<NaturezaDaAcao> natureza;

    public Carteira(){}

    public Carteira(String nome, float saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    // Atenção para esta parte
    // Precisa-se criar retornos personalizados
    // Catch
    public boolean depositar(float valor){
        if (valor > 0){
            this.saldo += valor;
            return true;
        }
        return false;
    }

    // Atenção para esta parte
    // Precisa-se criar retornos personalizados
    // Catch
    public boolean sacar(float valor){
        if (valor > 0 && haSaldoSuficiente(valor)){
            this.saldo -= valor;
        }
        return false;
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
