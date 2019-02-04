package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class CadastroInvalidoException extends Exception {

    private String mensagem;

    public CadastroInvalidoException(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
