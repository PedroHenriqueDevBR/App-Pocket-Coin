package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class DadoInvalidoDeUsuarioException extends Exception {

    private String mensagem;

    public DadoInvalidoDeUsuarioException(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
