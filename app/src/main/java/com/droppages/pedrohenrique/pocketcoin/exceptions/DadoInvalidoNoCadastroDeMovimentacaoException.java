package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class DadoInvalidoDeMovimentacaoException extends Exception {
    private String mensagem;

    public DadoInvalidoDeMovimentacaoException(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
