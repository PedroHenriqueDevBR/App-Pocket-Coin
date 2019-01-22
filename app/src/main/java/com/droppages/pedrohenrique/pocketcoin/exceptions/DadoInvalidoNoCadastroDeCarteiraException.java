package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class DadoInvalidoNoCadastroDeCarteiraException extends Exception {
    private String mensagem;

    public DadoInvalidoNoCadastroDeCarteiraException(String msg){
        this.mensagem = msg;
    }

    public String getMensagem() {
        return mensagem;
    }
}
