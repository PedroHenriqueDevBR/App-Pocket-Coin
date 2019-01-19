package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.app.Activity;

import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoDeUsuarioException;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class UsuarioController extends Activity {

    private BoxStore boxStore;
    private Box<Usuario> usuarioBox;

    public UsuarioController(BoxStore boxStore) {
        // ObjectBox
        this.boxStore   = boxStore;
        usuarioBox      = boxStore.boxFor(Usuario.class);
    }

    public void cadastrarNovoUsuario(String nome, String login, String senha, String repeteSenha) throws CadastroInvalidoDeUsuarioException {
        if (dadosDoCadastroEstaoValidos(nome, login, senha, repeteSenha)){
            Usuario usuario = new Usuario(nome, login, senha);
            usuarioBox.put(usuario);
        }
    }

    private boolean dadosDoCadastroEstaoValidos(String nome, String login, String senha, String repeteSenha) throws CadastroInvalidoDeUsuarioException {
        if (nome.length() == 0){
            throw new CadastroInvalidoDeUsuarioException("Preencha o campo nome");
        } else if (login.length() == 0){
            throw new CadastroInvalidoDeUsuarioException("Preencha o campo login");
        } else if (senha.length() == 0){
            throw new CadastroInvalidoDeUsuarioException("Preencha o campo senha");
        } else if (repeteSenha.length() == 0){
            throw new CadastroInvalidoDeUsuarioException("Preencha o campo repetir senha");
        } else {
            if (!senha.equals(repeteSenha)){
                throw new CadastroInvalidoDeUsuarioException("Senhas diferentes, tente novamente");
            }
        }
        return true;
    }
}
