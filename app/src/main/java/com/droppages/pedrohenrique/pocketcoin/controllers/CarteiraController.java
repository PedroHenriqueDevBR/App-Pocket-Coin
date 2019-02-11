package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.content.SharedPreferences;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CarteiraController {

    private Box<Carteira>       carteiraBox;
    private Box<Usuario>        usuarioBox;
    private Sessao              sessao;


    public CarteiraController(BoxStore boxStore, SharedPreferences preferences) {
        carteiraBox = boxStore.boxFor(Carteira.class);
        usuarioBox  = boxStore.boxFor(Usuario.class);
        this.sessao = new Sessao(preferences);
    }


    public void cadastrarNovaCarteira(String nome, float saldo) throws CadastroInvalidoException {
        if (dadosValidosParaCadastro(nome, saldo)){
            Carteira carteira = new Carteira(nome, saldo);
            Usuario usuarioLogado = selecionarUsuarioLogado();
            usuarioLogado.adicionarCarteira(carteira);
            usuarioBox.put(usuarioLogado);
        }
    }


    public List<Carteira> selecionarTodasAsCarteirasDoUsuarioLogado(){
        Usuario usuarioLogado = selecionarUsuarioLogado();
        return usuarioLogado.selecionarListaDeCarteiras();
    }


    public Carteira selecionarCarteiraPeloId(long id){
        return carteiraBox.get(id);
    }


    public void atualizarCarteira(Carteira carteira) {
        carteiraBox.put(carteira);
    }


    public void deletarCarteira(long id){
        carteiraBox.remove(id);
    }


    /*
    * Métodos de apoio aos métodos públicos
    */


    private boolean dadosValidosParaCadastro(String nome, float saldo) throws CadastroInvalidoException {
        if (nome.length() == 0) {
            throw new CadastroInvalidoException("Digite o nome da carteira.");
        } else if (carteiraJaCadastrada(nome)) {
            throw new CadastroInvalidoException("Carteira já cadastrada, altere o campo nome e tente novamente.");
        } else if (saldo < 0){
            throw new CadastroInvalidoException("O saldo inicial nao pode ser negativo.");
        }
        return true;
    }


    private boolean carteiraJaCadastrada(String nome){
        for (Carteira carteira: selecionarUsuarioLogado().selecionarListaDeCarteiras()){
            if (carteira.getNome().toLowerCase().equals(nome.toLowerCase())) { return true; }
        }
        return false;
    }


    private Usuario selecionarUsuarioLogado() {
        long idDoUsuarioLogado = Long.parseLong(sessao.recuperarDadosDaSessao(Sessao.USUARIO_ID));
        return usuarioBox.get(idDoUsuarioLogado);
    }
}
