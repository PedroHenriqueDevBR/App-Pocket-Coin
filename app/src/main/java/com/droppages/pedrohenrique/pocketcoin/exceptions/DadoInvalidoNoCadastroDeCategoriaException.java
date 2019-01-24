package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class DadoInvalidoNoCadastroDeCategoriaException extends Exception {
    private String mensagem;

    public DadoInvalidoNoCadastroDeCategoriaException(String msg){
        this.mensagem = msg;
    }

    public String getMensagem() {
        return mensagem;
    }
}
