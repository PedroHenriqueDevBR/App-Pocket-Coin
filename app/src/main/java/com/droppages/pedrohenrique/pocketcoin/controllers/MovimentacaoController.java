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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.relation.ToMany;

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


    public void cadastrarNovaMovimentacao(String valor, String data, String descricao, long idCategoria, long idCarteira, long idTag, long idNatureza, boolean concluido) throws DadoInvalidoNoCadastroDeMovimentacaoException {
        if (dadosValidosParaCadastro(valor, data, descricao)){
            // Prepara os dados
            double valorMovimentacao = Double.parseDouble(valor);
            Tag tag = selecionarTagaAPartirDoId(idTag);
            Carteira carteira = selecionarCarteiraaAPartirDoId(idCarteira);
            Categoria categoria = selecionarCategoriaaAPartirDoId(idCategoria);
            NaturezaDaAcao natureza = selecionarNaturezaAPartirDoId(idNatureza);

            // Configura a movimentação para cadstrar
            Movimentacao movimentacao = new Movimentacao(valorMovimentacao, data, descricao, concluido);
            movimentacao.carteira.setTarget(carteira);
            movimentacao.categoria.setTarget(categoria);
            movimentacao.tag.add(tag);
            movimentacao.natureza.setTarget(natureza);

            // Adiciona nova movimentação
            Usuario usuarioLogado = selecionarUsuarioLogado();
            usuarioLogado.movimentacoes.add(movimentacao);

            // Deposita valor na carteira
            carteira.depositar(Float.parseFloat(valor));
            carteiraBox.put(carteira);

            // Efetua o cadastro da movimentação
            usuarioBox.put(usuarioLogado);
        }
    }


    public List<Movimentacao> selecionarTodasMovimentacoesDoUsuario(){
        Usuario usuarioLogado = selecionarUsuarioLogado();
        return usuarioLogado.movimentacoes;
    }


    public List<String> selecionarTodasAsTagsComoList(){
        List<String> lista = new ArrayList<>();
        Usuario usuario = selecionarUsuarioLogado();
        for (Tag tag: usuario.getTags()){
            lista.add(tag.id + " - " + tag.getNome());
        }
        return lista;
    }


    public List<String> selecionarTodasAsCategoriasComoList(){
        List<String> lista = new ArrayList<>();
        Usuario usuario = selecionarUsuarioLogado();
        for (Categoria categoria: usuario.getCategorias()){
            lista.add(categoria.id + " - " + categoria.getNome());
        }
        return lista;
    }


    public List<String> selecionarTodasAsCarteirasComoList(){
        List<String> lista = new ArrayList<>();
        Usuario usuario = selecionarUsuarioLogado();
        for (Carteira carteira: usuario.getCarteiras()){
            lista.add(carteira.id + " - " + carteira.getNome());
        }
        return lista;
    }


    public List<String> selecionarTodasAsNaturezasComoList(){
        List<String> lista = new ArrayList<>();
        for (NaturezaDaAcao natureza: naturezaBox.getAll()){
            lista.add(natureza.id + " - " + natureza.getNome());
        }
        return lista;
    }


    public float calcularSaldoTotalDeTodasAsCarteiras(){
        float total = 0f;

        Usuario usuario = selecionarUsuarioLogado();

        for (Carteira carteira: usuario.getCarteiras()){
            total += carteira.getSaldo();
        }
        return total;
    }


    public float calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza(String natureza){
        String dataAtual = dataAtual();
        int mesAtual = selecionarDadoAPartirDeUmaData(dataAtual, "mes");
        int anoAtual = selecionarDadoAPartirDeUmaData(dataAtual, "ano");
        float total = 0f;

        for (Movimentacao movimentacao: selecionarTodasAsMovimentacoesAPartirDoMesEAno(mesAtual, anoAtual)){
            if (movimentacao.getNatureza().getTarget().getNome().equals(natureza)){
                total += movimentacao.getValor();
            }
        }

        return total;
    }


    public double calcularValorGastoNoMesPorCategoriaSelecionada(Categoria categoria){
        List<Movimentacao> movimentacoes = selecionarTodasAsMovimentacoesComDespesa();
        double resultado = 0f;

        for (Movimentacao movimentacao: movimentacoes){
            if (movimentacao.getCategoria().getTarget().id == categoria.id){
                resultado += movimentacao.getValor();
            }
        }
        return resultado;
    }


    /*
    * Métodos de apoio aos métodos públicos
    */


    private boolean dadosValidosParaCadastro(String valor, String data, String descricao) throws DadoInvalidoNoCadastroDeMovimentacaoException {
        if (valor.length() == 0){
            throw new DadoInvalidoNoCadastroDeMovimentacaoException("Preencha o campo valor");
        } else if (data.length() == 0){
            throw new DadoInvalidoNoCadastroDeMovimentacaoException("Preencha o campo data");
        } else if (descricao.length() == 0){
            throw new DadoInvalidoNoCadastroDeMovimentacaoException("Preencha o campo descrição");
        } else if (Float.parseFloat(valor) < 0){
            throw new DadoInvalidoNoCadastroDeMovimentacaoException("O valor não pode ser inferior a zero");
        }
        return true;
    }


    private Usuario selecionarUsuarioLogado() {
        long idDoUsuarioLogado = Long.parseLong(sessao.recuperarDadosDaSessao(Sessao.USUARIO_ID));
        return usuarioBox.get(idDoUsuarioLogado);
    }


    private List<Movimentacao> selecionarTodasAsMovimentacoesAPartirDoMesEAno(int mes, int ano){
        List<Movimentacao> movimentacoes = new ArrayList<>();
        for (Movimentacao movimentacao: movimentacaoBox.getAll()){
            int mesDaMovimentacao = selecionarDadoAPartirDeUmaData(movimentacao.getData(), "mes");
            int anoDaMovimentacao = selecionarDadoAPartirDeUmaData(movimentacao.getData(), "ano");

            if (mes == mesDaMovimentacao && ano == anoDaMovimentacao){
                movimentacoes.add(movimentacao);
            }
        }
        return movimentacoes;
    }


    private Tag selecionarTagaAPartirDoId(long id){
        return tagBox.get(id);
    }


    private Categoria selecionarCategoriaaAPartirDoId(long id){
        return categoriaBox.get(id);
    }


    private Carteira selecionarCarteiraaAPartirDoId(long id){
        return carteiraBox.get(id);
    }


    private NaturezaDaAcao selecionarNaturezaAPartirDoId(long id){
        return naturezaBox.get(id);
    }


    private String dataAtual(){
        String formatoDaData = "dd/MM/yyyy";
        DateFormat format = new SimpleDateFormat(formatoDaData);
        Date date = new Date();
        return format.format(date);
    }


    private int selecionarDadoAPartirDeUmaData(String data, String dado){
        String dataSeparadaPorBarra[] = data.split("/");
        int resultado;

        switch (dado) {
            case "mes":
                resultado = Integer.parseInt(dataSeparadaPorBarra[1]);
                break;
            case "ano":
                resultado = Integer.parseInt(dataSeparadaPorBarra[2]);
                break;
            default:
                resultado = Integer.parseInt(dataSeparadaPorBarra[0]);
                break;
        }
        return resultado;
    }


    private List<Movimentacao> selecionarTodasAsMovimentacoesComDespesa(){
        List<Movimentacao> movimentacoes = movimentacaoBox.getAll();
        List<Movimentacao> resultado = new ArrayList<>();
        NaturezaDaAcao natureza = naturezaBox.get(2);

        for (Movimentacao movimentacao: movimentacoes){
            if (movimentacao.getNatureza().getTarget().id == natureza.id){
                String[] dataDaMovimentacao = movimentacao.getData().split("/");
                int mes = Integer.parseInt(dataDaMovimentacao[1]);
                int ano = Integer.parseInt(dataDaMovimentacao[2]);
                int mesAtual = selecionarDadoAPartirDeUmaData(dataAtual(), "mes");
                int anoAtual = selecionarDadoAPartirDeUmaData(dataAtual(), "ano");

                if (mes == mesAtual && ano == anoAtual){
                    resultado.add(movimentacao);
                }
            }
        }

        return resultado;
    }

}
