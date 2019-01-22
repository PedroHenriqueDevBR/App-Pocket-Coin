package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class DadoInvalidoNoCadastroDeCarteira extends Exception {
    private String mensagem;

    public DadoInvalidoNoCadastroDeCarteira(String msg){
        this.mensagem = msg;
    }

    public String getMensagem() {
        return mensagem;
    }
}
