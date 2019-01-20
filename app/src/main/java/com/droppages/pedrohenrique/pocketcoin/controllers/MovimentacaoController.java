package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.content.SharedPreferences;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeMovimentacaoException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.Movimentacao;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MovimentacaoController {
    private Box<Movimentacao>   movimentacaoBox;
    private Box<Tag>            tagBox;
    private Box<Categoria>      categoriaBox;
    private Box<Carteira>       carteiraBox;
    private Box<NaturezaDaAcao> naturezaBox;
    private Box<Usuario>        usuarioBox;
    private Sessao              sessao;

    public MovimentacaoController(BoxStore boxStore, SharedPreferences preferences) {
        this.tagBox          = boxStore.boxFor(Tag.class);
        this.categoriaBox    = boxStore.boxFor(Categoria.class);
        this.carteiraBox     = boxStore.boxFor(Carteira.class);
        this.naturezaBox     = boxStore.boxFor(NaturezaDaAcao.class);
        this.movimentacaoBox = boxStore.boxFor(Movimentacao.class);
        this.usuarioBox      = boxStore.boxFor(Usuario.class);
        this.sessao          = new Sessao(preferences);
    }

    public boolean cadastrarNovaMovimentacao(String valor, String data, String descricao, long idCategoria, long idCarteira, long idTag, long idNatureza, boolean concluido) throws DadoInvalidoNoCadastroDeMovimentacaoException {
        if (dadosValidosParaCadastro(valor, data, descricao)){
            double valorMovimentacao = Double.parseDouble(valor);
            Tag tag = selecionarTagPorId(idTag);
            Carteira carteira = selecionarCarteiraPorId(idCarteira);
            Categoria categoria = selecionarCategoriaPorId(idCategoria);
            NaturezaDaAcao natureza = selecionarNaturezaPorId(idNatureza);

            Movimentacao movimentacao = new Movimentacao(valorMovimentacao, data, descricao, concluido);
            movimentacao.carteira.setTarget(carteira);
            movimentacao.categoria.setTarget(categoria);
            movimentacao.tag.add(tag);
            movimentacao.natureza.setTarget(natureza);

            long idDoUsuarioLogado = Long.parseLong(sessao.recuperarDadosDaSessao(Sessao.USUARIO_ID));
            Usuario usuarioLogado = usuarioBox.get(idDoUsuarioLogado);
            usuarioLogado.movimentacoes.add(movimentacao);

            return true;
        }
        return false;
    }

    private boolean dadosValidosParaCadastro(String valor, String data, String descricao) throws DadoInvalidoNoCadastroDeMovimentacaoException {
        if (valor.length() == 0){
            throw new DadoInvalidoNoCadastroDeMovimentacaoException("Preencha o campo valor");
        } else if (data.length() == 0){
            throw new DadoInvalidoNoCadastroDeMovimentacaoException("Preencha o campo data");
        } else if (descricao.length() == 0){
            throw new DadoInvalidoNoCadastroDeMovimentacaoException("Preencha o campo descrição");
        }
        return true;
    }

    private Tag selecionarTagPorId(long id){
        return tagBox.get(id);
    }

    private Categoria selecionarCategoriaPorId(long id){
        return categoriaBox.get(id);
    }

    private Carteira selecionarCarteiraPorId(long id){
        return carteiraBox.get(id);
    }

    private NaturezaDaAcao selecionarNaturezaPorId(long id){
        return naturezaBox.get(id);
    }

    public List<String> selecionarTodasAsTagsComoList(){
        List<String> lista = new ArrayList();
        for (Tag tag: tagBox.getAll()){
            lista.add(tag.id + " - " + tag.getNome());
        }
        return lista;
    }

    public List<String> selecionarTodasAsCategoriasComoList(){
        List<String> lista = new ArrayList();
        for (Categoria categoria: categoriaBox.getAll()){
            lista.add(categoria.id + " - " + categoria.getNome());
        }
        return lista;
    }

    public List<String> selecionarTodasAsCarteirasComoList(){
        List<String> lista = new ArrayList();
        for (Carteira carteira: carteiraBox.getAll()){
            lista.add(carteira.id + " - " + carteira.getNome());
        }
        return lista;
    }

    public List<String> selecionarTodasAsNaturezasComoList(){
        List<String> lista = new ArrayList();
        for (NaturezaDaAcao natureza: naturezaBox.getAll()){
            lista.add(natureza.id + " - " + natureza.getNome());
        }
        return lista;
    }

}
