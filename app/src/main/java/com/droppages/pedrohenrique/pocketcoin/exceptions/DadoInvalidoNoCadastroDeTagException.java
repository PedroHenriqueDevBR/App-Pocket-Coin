package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class DadoInvalidoNoCadastroDeTagException extends Exception {

    private String mensagem;

    public DadoInvalidoNoCadastroDeTagException(String msg){
        this.mensagem = msg;
    }

    public String getMensagem() {
        return mensagem;
    }

}
