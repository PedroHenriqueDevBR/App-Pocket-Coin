package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class DadoInvalidoNoCadastroDeUsuarioException extends Exception {

    private String mensagem;

    public DadoInvalidoNoCadastroDeUsuarioException(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
