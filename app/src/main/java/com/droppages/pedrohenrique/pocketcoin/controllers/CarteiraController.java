package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.content.SharedPreferences;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeCarteiraException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.relation.ToOne;

public class CarteiraController {

    private Box<Carteira>       carteiraBox;
    private Box<Usuario>        usuarioBox;
    private Sessao              sessao;


    public CarteiraController(BoxStore boxStore, SharedPreferences preferences) {
        carteiraBox = boxStore.boxFor(Carteira.class);
        usuarioBox  = boxStore.boxFor(Usuario.class);
        this.sessao = new Sessao(preferences);
    }


    public void cadastrarNovaCarteira(String nome, float saldo, NaturezaDaAcao natureza) throws DadoInvalidoNoCadastroDeCarteiraException {
        if (dadosValidosParaCadastro(nome, saldo)){
            Carteira carteira = new Carteira(nome, saldo);
            carteira.natureza.setTarget(natureza);
            Usuario usuarioLogado = selecionarUsuarioLogado();
            usuarioLogado.carteiras.add(carteira);
            usuarioBox.put(usuarioLogado);
        }
    }


    public List<Carteira> selecionarTodasAsCarteirasDoUsuarioLogado(){
        Usuario usuarioLogado = selecionarUsuarioLogado();
        return usuarioLogado.carteiras;
    }


    /*
    * Métodos de apoio aos métodos públicos
    */


    private boolean dadosValidosParaCadastro(String nome, float saldo) throws DadoInvalidoNoCadastroDeCarteiraException {
        if (nome.length() == 0) {
            throw new DadoInvalidoNoCadastroDeCarteiraException("Digite o nome da carteira.");
        } else if (saldo < 0){
            throw new DadoInvalidoNoCadastroDeCarteiraException("O saldo inicial nao pode ser negativo.");
        }
        return true;
    }


    private Usuario selecionarUsuarioLogado() {
        long idDoUsuarioLogado = Long.parseLong(sessao.recuperarDadosDaSessao(Sessao.USUARIO_ID));
        return usuarioBox.get(idDoUsuarioLogado);
    }
}
