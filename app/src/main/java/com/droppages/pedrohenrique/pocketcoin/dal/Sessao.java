package com.droppages.pedrohenrique.pocketcoin.dal;

import android.content.Context;
import android.content.SharedPreferences;

public class Sessao {
    public static final String SESSAO_USUARIO = "sessao_usuario";
    public static final String USUARIO_ID = "usuario_id";
    public static final String USUARIO_NOME = "usuario_nome";
    public static final String USUARIO_LOGIN = "usuario_login";
    public static final String USUARIO_SENHA = "usuario_senha";
    public static final String DEFAULT = "nada_encontrado";
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
        String valor = sessao.getString(chave, DEFAULT);
        return valor;
    }

    public void deletarDadosDaSessao(String chave){
        SharedPreferences.Editor editor = sessao.edit();
        editor.remove(chave);
        editor.apply();
    }

}
