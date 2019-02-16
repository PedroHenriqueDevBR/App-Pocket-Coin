package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.content.SharedPreferences;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public void cadastrarNovaMovimentacao(String valor, String data, String descricao, long idCategoria, long idCarteira, long idTag, long idNatureza, boolean concluido) throws CadastroInvalidoException {
        if (dadosValidosParaCadastro(valor, data, descricao)){
            // Prepara os dados
            double valorMovimentacao = Double.parseDouble(valor);
            Tag tag = selecionarTagaAPartirDoId(idTag);
            Carteira carteira = selecionarCarteiraaAPartirDoId(idCarteira);
            Categoria categoria = selecionarCategoriaaAPartirDoId(idCategoria);
            NaturezaDaAcao natureza = selecionarNaturezaAPartirDoId(idNatureza);

            // Configura a movimentação para cadstrar
            Movimentacao movimentacao = new Movimentacao(valorMovimentacao, data, descricao, concluido);
            movimentacao.configurarCarteira(carteira);
            movimentacao.configurarCategoria(categoria);
            movimentacao.adicionarTag(tag);
            movimentacao.configurarNatureza(natureza);

            // Adiciona nova movimentação
            Usuario usuarioLogado = selecionarUsuarioLogado();
            usuarioLogado.adicionarMovimentacao(movimentacao);

            // Deposita ou saca o valor na carteira
            if (idNatureza == 1){ carteira.depositar(Float.parseFloat(valor)); } else { carteira.sacar(Float.parseFloat(valor)); }

            // Efetua o cadastro da movimentação
            carteiraBox.put(carteira);
            usuarioBox.put(usuarioLogado);
        }
    }


    public void transferir(long idOrigem, long idDestino, float valor){
        Carteira origem = carteiraBox.get(idOrigem);
        Carteira destino = carteiraBox.get(idDestino);
        origem.transferir(destino, valor);
        carteiraBox.put(origem);
        carteiraBox.put(destino);
    }


    public List<Movimentacao> selecionarTodasAsMovimentacoesDoUsuarioNoMesAtual(){
        String dataAtual = dataAtual();
        int mesAtual = selecionarDadoAPartirDeUmaData(dataAtual, "mes");
        int anoAtual = selecionarDadoAPartirDeUmaData(dataAtual, "ano");
        return selecionarTodasAsMovimentacoesAPartirDoMesEAno(mesAtual, anoAtual);
    }


    public List<String> selecionarTodasAsDatasCadastradas(){
        List<String> datas = new ArrayList<>();
        datas.add("Mês atual");

        for (Movimentacao movimentacao: selecionarUsuarioLogado().selecionarMovimentacoes()){
            String data[] = movimentacao.getData().split("/");
            String dataComparacao = data[1] + "/" + data[2];

            if (!verificaSeDataEstaNaLista(datas, dataComparacao)){
                datas.add(dataComparacao);
            }
        }

        return datas;
    }


    public List<Movimentacao> selecionarTodasAsMovimentacoesDoUsuarioNoMesEAnoSelecionado(String mesAno){
        List<Movimentacao> movimentacoes = new ArrayList<>();

        for (Movimentacao movimentacao: selecionarUsuarioLogado().selecionarMovimentacoes()){
            String data[] = movimentacao.getData().split("/");
            String dataComparacao = data[1] + "/" + data[2];

            if (dataComparacao.equals(mesAno)){
                movimentacoes.add(movimentacao);
            }
        }

        return movimentacoes;
    }


    public List<Map<Long, String>> selecionarTodasAsTagsComoDicionario(){
        List<Map<Long, String>> mapList = new ArrayList<>();
        Usuario usuario = selecionarUsuarioLogado();
        for (Tag tag: usuario.selecionarListaDeTags()){
            Map<Long, String> map = new HashMap<Long, String>();
            map.put(tag.id, tag.getNome());
            mapList.add(map);
        }
        return mapList;
    }


    public List<Map<Long, String>> selecionarTodasAsCategoriasComoDicionario(Long idNatureza){
        List<Map<Long, String>> mapList = new ArrayList<>();
        Usuario usuario = selecionarUsuarioLogado();
        NaturezaDaAcao natureza = naturezaBox.get(idNatureza);

        for (Categoria categoria: usuario.selecionarListaDeCategorias()){
            if (categoria.selecionarNatureza().getNome().equals(natureza.getNome())) {
                Map<Long, String> map = new HashMap<Long, String>();
                map.put(categoria.id, categoria.getNome());
                mapList.add(map);
            }
        }
        return mapList;
    }


    public List<Map<Long, String>> selecionarTodasAsCarteirasComoDicionario(){
        List<Map<Long, String>> mapList = new ArrayList<>();
        Usuario usuario = selecionarUsuarioLogado();
        for (Carteira carteira: usuario.selecionarListaDeCarteiras()){
            Map<Long, String> map = new HashMap<Long, String>();
            map.put(carteira.id, carteira.getNome());
            mapList.add(map);
        }
        return mapList;
    }


    public float calcularSaldoTotalDeTodasAsCarteiras(){
        float total = 0f;

        Usuario usuario = selecionarUsuarioLogado();

        for (Carteira carteira: usuario.selecionarListaDeCarteiras()){
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
            if (movimentacao.selecionarNatureza().getNome().equals(natureza)){
                total += movimentacao.getValor();
            }
        }

        return total;
    }


    public double calcularValorGastoNoMesPorCategoriaSelecionada(Categoria categoria){
        double resultado = 0f;

        String dataAtual = dataAtual();
        int mesAtual = selecionarDadoAPartirDeUmaData(dataAtual, "mes");
        int anoAtual = selecionarDadoAPartirDeUmaData(dataAtual, "ano");

        for (Movimentacao movimentacao: selecionarTodasAsMovimentacoesAPartirDoMesEAno(mesAtual, anoAtual)){
            if (movimentacao.selecionarCategoria().id == categoria.id){
                resultado += movimentacao.getValor();
            }
        }
        return resultado;
    }


    /*
    * Métodos de apoio aos métodos públicos
    */


    private boolean dadosValidosParaCadastro(String valor, String data, String descricao) throws CadastroInvalidoException {
        if (valor.length() == 0){
            throw new CadastroInvalidoException("Preencha o campo valor");
        } else if (data.length() == 0){
            throw new CadastroInvalidoException("Preencha o campo data");
        } else if (descricao.length() == 0){
            throw new CadastroInvalidoException("Preencha o campo descrição");
        } else if (Float.parseFloat(valor) < 0){
            throw new CadastroInvalidoException("O valor não pode ser inferior a zero");
        }
        return true;
    }


    private Usuario selecionarUsuarioLogado() {
        long idDoUsuarioLogado = Long.parseLong(sessao.recuperarDadosDaSessao(Sessao.USUARIO_ID));
        return usuarioBox.get(idDoUsuarioLogado);
    }


    private List<Movimentacao> selecionarTodasAsMovimentacoesAPartirDoMesEAno(int mes, int ano){
        List<Movimentacao> movimentacoes = new ArrayList<>();
        for (Movimentacao movimentacao: selecionarUsuarioLogado().selecionarMovimentacoes()){
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


    private boolean verificaSeDataEstaNaLista(List<String> lista, String data){
        for (String dado: lista){
            if (dado.equals(data)){
                return true;
            }
        }
        return false;
    }


    private List<Movimentacao> selecionarTodasAsMovimentacoesComDespesa(){
        List<Movimentacao> movimentacoes = movimentacaoBox.getAll();
        List<Movimentacao> resultado = new ArrayList<>();
        NaturezaDaAcao natureza = naturezaBox.get(2);

        for (Movimentacao movimentacao: movimentacoes){
            if (movimentacao.selecionarNatureza().id == natureza.id){
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
