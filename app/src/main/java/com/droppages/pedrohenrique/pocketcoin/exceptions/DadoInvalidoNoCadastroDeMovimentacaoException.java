package com.droppages.pedrohenrique.pocketcoin.exceptions;

public class DadoInvalidoNoCadastroDeMovimentacaoException extends Exception {
    private String mensagem;

    public DadoInvalidoNoCadastroDeMovimentacaoException(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
