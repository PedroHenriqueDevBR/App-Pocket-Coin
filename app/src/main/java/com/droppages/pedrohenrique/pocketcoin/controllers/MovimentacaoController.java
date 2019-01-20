package com.droppages.pedrohenrique.pocketcoin.controllers;

import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeMovimentacaoException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.Movimentacao;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;

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

    /* Movimentacao
    private double                  valor;
    private boolean                 concuildo;
    private String                  data;
    private String                  descricao;
    public  ToMany<Tag>             tag;
    public  ToOne<Categoria>        categoria;
    public  ToOne<Carteira>         carteira;
    public  ToOne<NaturezaDaAcao>   natureza;
    */

    /* MovimentacaoActivity
    txtValor        = findViewById(R.id.edit_valor);
    txtData         = findViewById(R.id.edit_data);
    txtDescricao    = findViewById(R.id.edit_descricao);
    spnCategoria    = findViewById(R.id.spn_categoria);
    spnCarteira     = findViewById(R.id.spn_carteira);
    spnTag          = findViewById(R.id.spn_tag);
    spnNatureza     = findViewById(R.id.spn_natureza);
    checkConcluido  = findViewById(R.id.check_concluido);
    */

    public MovimentacaoController(BoxStore boxStore) {
        this.tagBox          = boxStore.boxFor(Tag.class);
        this.categoriaBox    = boxStore.boxFor(Categoria.class);
        this.carteiraBox     = boxStore.boxFor(Carteira.class);
        this.naturezaBox     = boxStore.boxFor(NaturezaDaAcao.class);
        this.movimentacaoBox = boxStore.boxFor(Movimentacao.class);
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

            movimentacaoBox.put(movimentacao);
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
