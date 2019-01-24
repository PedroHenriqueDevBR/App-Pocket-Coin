package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.content.SharedPreferences;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeCategoriaException;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CategoriaController {

    private Box<Categoria> categoriaBox;
    private Box<Usuario>        usuarioBox;
    private Sessao sessao;


    public CategoriaController(BoxStore boxStore, SharedPreferences preferences) {
        categoriaBox = boxStore.boxFor(Categoria.class);
        usuarioBox  = boxStore.boxFor(Usuario.class);
        this.sessao = new Sessao(preferences);
    }


    public void cadastrarNovaCategoria(String nome, NaturezaDaAcao natureza) throws DadoInvalidoNoCadastroDeCategoriaException {
        if (dadosValidosParaCadastro(nome)){
            Categoria categoria = new Categoria(nome);
            categoria.setNatureza(natureza);
            Usuario usuarioLogado = selecionarUsuarioLogado();
            usuarioLogado.categorias.add(categoria);
            usuarioBox.put(usuarioLogado);
        }
    }


    public List<Categoria> selecionarTodasAsCategoriasDoUsuarioLogado(){
        Usuario usuarioLogado = selecionarUsuarioLogado();
        return usuarioLogado.getCategorias();
    }


    /*
     * Métodos de apoio aos métodos públicos
     */


    private boolean dadosValidosParaCadastro(String nome) throws DadoInvalidoNoCadastroDeCategoriaException {
        if (nome.length() == 0) {
            throw new DadoInvalidoNoCadastroDeCategoriaException("Digite o nome da categoria.");
        }
        return true;
    }


    private Usuario selecionarUsuarioLogado() {
        long idDoUsuarioLogado = Long.parseLong(sessao.recuperarDadosDaSessao(Sessao.USUARIO_ID));
        return usuarioBox.get(idDoUsuarioLogado);
    }

}
