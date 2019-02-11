package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.content.SharedPreferences;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CategoriaController {
    public final static String CREDITO = "credito";
    public final static String DEBITO = "debito";
    private Box<Categoria>      categoriaBox;
    private Box<Usuario>        usuarioBox;
    private Box<NaturezaDaAcao> naturezaBox;
    private Sessao              sessao;


    public CategoriaController(BoxStore boxStore, SharedPreferences preferences) {
        categoriaBox = boxStore.boxFor(Categoria.class);
        usuarioBox  = boxStore.boxFor(Usuario.class);
        naturezaBox = boxStore.boxFor(NaturezaDaAcao.class);
        this.sessao = new Sessao(preferences);
    }


    public void cadastrarNovaCategoria(String nome, NaturezaDaAcao natureza) throws CadastroInvalidoException {
        if (dadosValidosParaCadastro(nome)){
            Categoria categoria = new Categoria(nome);
            categoria.configurarNatureza(natureza);
            Usuario usuarioLogado = selecionarUsuarioLogado();
            usuarioLogado.adicionarCategoria(categoria);
            usuarioBox.put(usuarioLogado);
        }
    }


    public void cadastrarNovaCategoria(String nome, long idNatureza) throws CadastroInvalidoException {
        if (dadosValidosParaCadastro(nome)){
            Categoria categoria = new Categoria(nome);
            NaturezaDaAcao natureza = naturezaBox.get(idNatureza);
            categoria.configurarNatureza(natureza);
            Usuario usuarioLogado = selecionarUsuarioLogado();
            usuarioLogado.adicionarCategoria(categoria);
            usuarioBox.put(usuarioLogado);
        }
    }


    public List<NaturezaDaAcao> selecionarTodasAsNaturezas(){
        return naturezaBox.getAll();
    }


    public List<Categoria> selecionarTodasAsCategoriasDoUsuarioLogado(){
        Usuario usuarioLogado = selecionarUsuarioLogado();
        return usuarioLogado.selecionarListaDeCategorias();
    }


    public List<Categoria> selecionarTodasAsCategoriasDespesaDoUsuarioLogado(){
        Usuario usuarioLogado = selecionarUsuarioLogado();
        List<Categoria> categorias = new ArrayList<>();

        for (Categoria categoria: usuarioLogado.selecionarListaDeCategorias()){
            if (categoria.selecionarNatureza().getNome().equals("Débito")){
                categorias.add(categoria);
            }
        }

        return categorias;
    }


    public Categoria selecionarCategoriaPeloId(long id){
        return categoriaBox.get(id);
    }


    public void deletarCategoria(long id){
        categoriaBox.remove(id);
    }


    public void atualizarCategoria(Categoria categoria) {
        categoriaBox.put(categoria);
    }


    /*
     * Métodos de apoio aos métodos públicos
     */


    private boolean dadosValidosParaCadastro(String nome) throws CadastroInvalidoException {
        if (nome.length() == 0) {
            throw new CadastroInvalidoException("Digite o nome da categoria.");
        } else if (categoriaJaCadastrada(nome)) {
            throw new CadastroInvalidoException("Categoria já cadastrada, altere o nome da categoria e tente novamente.");
        }
        return true;
    }


    private boolean categoriaJaCadastrada(String nome){
        for (Categoria categoria: selecionarUsuarioLogado().selecionarListaDeCategorias()){
            if (categoria.getNome().toLowerCase().equals(nome.toLowerCase())) { return true; }
        }
        return false;
    }


    private Usuario selecionarUsuarioLogado() {
        long idDoUsuarioLogado = Long.parseLong(sessao.recuperarDadosDaSessao(Sessao.USUARIO_ID));
        return usuarioBox.get(idDoUsuarioLogado);
    }

}
