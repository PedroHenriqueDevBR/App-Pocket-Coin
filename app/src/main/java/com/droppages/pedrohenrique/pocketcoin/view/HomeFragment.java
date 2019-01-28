package com.droppages.pedrohenrique.pocketcoin.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.adapters.DespesaPorCategoriaAdapter;
import com.droppages.pedrohenrique.pocketcoin.controllers.CategoriaController;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;

import java.util.ArrayList;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.objectbox.BoxStore;


public class HomeFragment extends Fragment {
    private MovimentacaoController  movimentacaoController;
    private CategoriaController     categoriaController;
    private TextView                txtSaldoAtual, txtReceitaGeral, txtDespesaGeral, txtReceitaMensal, txtDespesaMensal, txtEconomia;
    private RecyclerView            recyclerView;
    private FabSpeedDial            fabSpeedDial;

    public HomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Bind
        txtSaldoAtual           = view.findViewById(R.id.text_view_saldo_atual);
        txtReceitaGeral         = view.findViewById(R.id.text_view_receita_geral);
        txtDespesaGeral         = view.findViewById(R.id.text_view_despesa_geral);
        txtReceitaMensal        = view.findViewById(R.id.text_view_receita_mensal);
        txtDespesaMensal        = view.findViewById(R.id.text_view_despesa_mensal);
        txtEconomia             = view.findViewById(R.id.text_view_economia);
        recyclerView            = view.findViewById(R.id.lista_gasto_por_categoria);
        fabSpeedDial            = view.findViewById(R.id.fab_speed_dial);
        BoxStore boxStore       = ((App) getActivity().getApplication()).getBoxStore();
        movimentacaoController  = new MovimentacaoController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));
        categoriaController     = new CategoriaController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));

        // Metodos
        setarTodosOsDados();

        // Float Action Button
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_nova_entrada) {
                    novaAcaoAbrirActivity(1);
                } else if (menuItem.getItemId() == R.id.nav_nova_despesa) {
                    novaAcaoAbrirActivity(2);
                } else {
                    novaAcaoAbrirActivity(3);
                }
                return true;
            }

            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) { return true; }

            @Override
            public void onMenuClosed() {}
        });

        // Retorna a view preenchida com os dados
        return view;
    }

    @Override
    public void onResume() {
        setarTodosOsDados();
        super.onResume();
    }

    private void setarTodosOsDados() {
        setarSaldoTotal();
        setarVisaoGeral();
        setarBalancoMensal();
        setarDespesasPorCategoria();
        calcularEconomiaMesal();
    }

    private void setarSaldoTotal() {
        String valor = Float.toString(movimentacaoController.calcularSaldoTotalDeTodasAsCarteiras());
        txtSaldoAtual.setText(valor);
    }

    private void setarVisaoGeral() {
        String receita = Float.toString(movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Crédito"));
        String despesa = Float.toString(movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Débito"));
        txtReceitaGeral.setText("R$ " + receita);
        txtDespesaGeral.setText("R$ " + despesa);
    }

    private void setarBalancoMensal() {
        String receita = Float.toString(movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Crédito"));
        String despesa = Float.toString(movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Débito"));
        txtReceitaMensal.setText("R$ " + receita);
        txtDespesaMensal.setText("R$ " + despesa);
    }

    private void setarDespesasPorCategoria() {
        List<Categoria> categorias = categoriaController.selecionarTodasAsCategoriasDespesaDoUsuarioLogado();
        List<DespesaPorCategoriaAdapter.CategoriaComValor> listaCategoriaComValor = new ArrayList<>();

        for (Categoria categoria : categorias){
            DespesaPorCategoriaAdapter.CategoriaComValor categoriaComValor = new DespesaPorCategoriaAdapter.CategoriaComValor();
            categoriaComValor.categoria = categoria.getNome();
            categoriaComValor.valor = (float) movimentacaoController.calcularValorGastoNoMesPorCategoriaSelecionada(categoria);
            listaCategoriaComValor.add(categoriaComValor);
        }

        DespesaPorCategoriaAdapter adapter = new DespesaPorCategoriaAdapter(listaCategoriaComValor, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void calcularEconomiaMesal(){
        float despesa   = movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Débito");
        float receita   = movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Crédito");
        int difereca    = (int) (receita - despesa);
        int resultado   = (int) ((despesa * 100) / receita);
        int economia    = 100 - resultado;
        String msg;

        if (receita == 0 && despesa == 0){
            msg = "No mês atual, nenhuma movimentação foi realizada.";
        } else if (difereca == 0) {
            msg = "Neste mês sua economia foi zero. Seu gasto mensal é equivalente a entrada de dinheiro.";
        } else if (difereca > 0) {
            msg = "Parabéns, sua economia no mês atual é "+ economia +"%, sua economia líquida é igual a R$ "+ difereca;
        } else {
            economia *= -1;
            difereca *= -1;
            msg = "No mês atual a sua despesa superou a sua receita em R$" + difereca + ", a sua despesa superou a receita em " + economia + "% cuidado para não perder o controle das suas contas";
        }
        txtEconomia.setText(msg);
    }

    private void novaAcaoAbrirActivity(int codigo){
        Intent intent;
        if (codigo == 1) { // Nova receita
            intent = new Intent(getActivity(), MovimentacaoActivity.class);
            intent.putExtra("idNatureza", 1L);
        } else if (codigo == 2) { // Nova despesa
            intent = new Intent(getActivity(), MovimentacaoActivity.class);
            intent.putExtra("idNatureza", 2L);
        } else { // Nova transferência
            intent = new Intent(getActivity(), MovimentacaoActivity.class);
            intent.putExtra("idNatureza", 1L);
        }
        startActivity(intent);
    }

}
