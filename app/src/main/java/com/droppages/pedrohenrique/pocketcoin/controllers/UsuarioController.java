package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.app.Activity;
import android.content.Context;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoDeUsuarioException;
import com.droppages.pedrohenrique.pocketcoin.model.Configuracao;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class UsuarioController extends Activity {

    private Sessao              sessao;
    private BoxStore            boxStore;
    private Box<Usuario>        usuarioBox;
    private Box<Configuracao>   configuracaoBox;
    private Box<NaturezaDaAcao> naturezaBox;


    public UsuarioController(BoxStore boxStore) {
        this.boxStore   = boxStore;
        configuracaoBox = boxStore.boxFor(Configuracao.class);
        naturezaBox     = boxStore.boxFor(NaturezaDaAcao.class);
        usuarioBox      = boxStore.boxFor(Usuario.class);
    }

    public void cadastrarNovoUsuario(String nome, String login, String senha, String repeteSenha) throws DadoInvalidoDeUsuarioException {
        if (dadosValidosParaCadastro(nome, login, senha, repeteSenha)){
            Usuario usuario = new Usuario(nome, login, senha);
            usuarioBox.put(usuario);
        }
    }

    private boolean dadosValidosParaCadastro(String nome, String login, String senha, String repeteSenha) throws DadoInvalidoDeUsuarioException {
        if (nome.length() == 0){
            throw new DadoInvalidoDeUsuarioException("Preencha o campo nome");
        } else if (login.length() == 0){
            throw new DadoInvalidoDeUsuarioException("Preencha o campo login");
        } else if (senha.length() == 0){
            throw new DadoInvalidoDeUsuarioException("Preencha o campo senha");
        } else if (repeteSenha.length() == 0){
            throw new DadoInvalidoDeUsuarioException("Preencha o campo repetir senha");
        } else {
            if (!senha.equals(repeteSenha)){
                throw new DadoInvalidoDeUsuarioException("Senhas diferentes, tente novamente");
            }
        }
        return true;
    }

    public long login(String login, String senha) throws DadoInvalidoDeUsuarioException {
        if (dadosValidosParaLogin(login, senha)){
            long idUsuarioLogado = usuarioExistente(login, senha);
            if (idUsuarioLogado != -1) {
                return idUsuarioLogado;
            }
        }
        throw new DadoInvalidoDeUsuarioException("Usuário não cadastrado");
    }

    private boolean dadosValidosParaLogin(String login, String senha) throws DadoInvalidoDeUsuarioException {
        if (login.length() == 0){
            throw new DadoInvalidoDeUsuarioException("Preencha o campo login");
        } else if (senha.length() == 0){
            throw new DadoInvalidoDeUsuarioException("Preencha o campo senha");
        }
        return true;
    }

    private long usuarioExistente(String login, String senha){
        for (Usuario usuario: usuarioBox.getAll()){
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)){
                return usuario.id;
            }
        }
        return -1;
    }

    public void primeiraInstalacaoDoApp() {
        List<Configuracao> configuracoes = configuracaoBox.getAll();
        if (configuracoes.size() == 0) {
            configuracaoBox.put(new Configuracao("Instalado", "1"));
            naturezaBox.put(new NaturezaDaAcao("Crédito"));
            naturezaBox.put(new NaturezaDaAcao("Débito"));
        }
    }
}
