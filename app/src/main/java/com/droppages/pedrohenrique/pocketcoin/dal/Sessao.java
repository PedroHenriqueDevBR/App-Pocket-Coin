package com.droppages.pedrohenrique.pocketcoin.dal;

import android.content.Context;
import android.content.SharedPreferences;

public class Sessao {
    public static final String SESSAO_USUARIO = "sessao_usuario";
    public static final String USUARIO_ID = "usuario_id";
    private             SharedPreferences sessao;

    // Construtor
    public Sessao(SharedPreferences sessao){
        this.sessao = sessao;
    }


    public void adicionarDadosASessao(String chave, String valor){
        if (chave.length() > 0 && valor.length() > 0){
            SharedPreferences.Editor session = sessao.edit();
            session.putString(chave, valor);
            session.apply();
        }
    }


    public String recuperarDadosDaSessao(String chave){
        String valor = sessao.getString(chave, "Nada encontrado");
        return valor;
    }

}
