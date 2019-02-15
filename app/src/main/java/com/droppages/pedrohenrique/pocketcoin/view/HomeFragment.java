package com.droppages.pedrohenrique.pocketcoin.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.adapters.DespesaPorCategoriaAdapter;
import com.droppages.pedrohenrique.pocketcoin.controllers.CarteiraController;
import com.droppages.pedrohenrique.pocketcoin.controllers.CategoriaController;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.controllers.TagController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.objectbox.BoxStore;


public class HomeFragment extends Fragment {
    private MovimentacaoController  movimentacaoController;
    private CategoriaController     categoriaController;
    private CarteiraController      carteiraController;
    private TagController           tagController;
    private TextView                txtSaldoAtual, txtReceitaGeral, txtDespesaGeral, txtReceitaMensal, txtDespesaMensal, txtEconomia;
    private RecyclerView            recyclerView;
    private FabSpeedDial            fabSpeedDial;
    private PieChart                chartCategoria;
    private BarChart                chartComparacao;
    private ProgressBar             progressBarEconomiaMensal;
    private LinearLayout            lytMostraSaldo, lytGastoPorCategoria;


    public HomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Bind
        txtSaldoAtual               = view.findViewById(R.id.text_view_saldo_atual);
        txtReceitaGeral             = view.findViewById(R.id.text_view_receita_geral);
        txtDespesaGeral             = view.findViewById(R.id.text_view_despesa_geral);
        txtReceitaMensal            = view.findViewById(R.id.text_view_receita_mensal);
        txtDespesaMensal            = view.findViewById(R.id.text_view_despesa_mensal);
        txtEconomia                 = view.findViewById(R.id.text_view_economia);
        recyclerView                = view.findViewById(R.id.lista_gasto_por_categoria);
        fabSpeedDial                = view.findViewById(R.id.fab_speed_dial);
        chartCategoria              = view.findViewById(R.id.chart_despesa_por_categoria);
        chartComparacao             = view.findViewById(R.id.chart_comparacao);
        progressBarEconomiaMensal   = view.findViewById(R.id.progress_bar_economia_mensal);
        lytMostraSaldo              = view.findViewById(R.id.layout_mostra_saldo);
        lytGastoPorCategoria        = view.findViewById(R.id.layout_gasto_por_categoria);
        BoxStore boxStore           = ((App) getActivity().getApplication()).getBoxStore();
        movimentacaoController      = new MovimentacaoController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));
        categoriaController         = new CategoriaController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));
        carteiraController          = new CarteiraController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));
        tagController               = new TagController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));

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
        preencherGraficoComparacao();
        setarEconomiaMensal();
        configuraMenu();
        abrirActivityCarteira();
        abrirActivityCategoria();
    }

    private void configuraMenu() {
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
    }

    private void preencherGraficoCategoria(List<DespesaPorCategoriaAdapter.CategoriaComValor> lista){
        chartCategoria.setRotationEnabled(true);
        chartCategoria.setUsePercentValues(true);

        chartCategoria.getDescription().setEnabled(false);
        chartCategoria.setExtraOffsets(5, 10, 5, 5);
        chartCategoria.setHoleColor(Color.WHITE);
        chartCategoria.setDrawHoleEnabled(true);

        chartCategoria.setCenterTextColor(Color.BLACK);
        chartCategoria.setCenterTextSize(10);
        chartCategoria.setHoleRadius(50f);
        chartCategoria.setTransparentCircleAlpha(1);
        chartCategoria.setDrawEntryLabels(true);
        chartCategoria.setEntryLabelTextSize(10);
        chartCategoria.animateY(1500, Easing.EasingOption.EaseInOutCubic);

        dadosParaOGraficoDeCategoria(lista);
    }

    private void dadosParaOGraficoDeCategoria(List<DespesaPorCategoriaAdapter.CategoriaComValor> lista) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        boolean haDadosCadastrados = false;

        for(int i = 0; i < lista.size(); i++){
            if (lista.get(i).valor > 0) {
                yEntrys.add(new PieEntry(lista.get(i).valor, lista.get(i).categoria));
                haDadosCadastrados = true;
            }
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(10);
        pieData.setValueTextColor(Color.WHITE);
        chartCategoria.setData(pieData);
        chartCategoria.invalidate();

        if (!haDadosCadastrados){ chartCategoria.setVisibility(View.GONE); }
        else {chartCategoria.setVisibility(View.VISIBLE);}
    }

    private void preencherGraficoComparacao(){
        float receita = movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Crédito");
        float despesa = movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Débito");

        Description description = new Description();
        description.setText("");

        chartComparacao.setDescription(description);
        chartComparacao.setDrawBarShadow(false);
        chartComparacao.setDrawValueAboveBar(true);
        chartComparacao.setPinchZoom(true);
        chartComparacao.setDrawGridBackground(true);
        chartComparacao.setDrawValueAboveBar(true);
        chartComparacao.setFitBars(true);

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(0, 0f));
        entries.add(new BarEntry(1, receita));
        entries.add(new BarEntry(2, despesa));
        entries.add(new BarEntry(3, 0f));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(255,255,255));
        colors.add(Color.rgb(76,175,80));
        colors.add(Color.rgb(244,67,54));
        colors.add(Color.rgb(255,255,255));

        BarDataSet dataSet = new BarDataSet(entries, "Dados");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(0);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        chartComparacao.setData(data);
    }

    private void setarEconomiaMensal() {
        int receita = (int) movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Crédito");
        int despesa = (int) movimentacaoController.calcularMovimentacaoTotalDoMesEAnoAtualAPartirDaNatureza("Débito");
        int max, min;

        if (despesa > receita) {
            max = despesa;
            min = receita;
            // 244,67,54
            // 76,175,80
            progressBarEconomiaMensal.getProgressDrawable().setColorFilter(
                    Color.rgb(244,67,54), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            max = receita;
            min = despesa;
            progressBarEconomiaMensal.getProgressDrawable().setColorFilter(
                    Color.rgb(76,175,80), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        progressBarEconomiaMensal.setMax(max);
        progressBarEconomiaMensal.setProgress(min);
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

        // Preenche o grafico
        preencherGraficoCategoria(listaCategoriaComValor);
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
        int carteiraSize    = carteiraController.selecionarTodasAsCarteirasDoUsuarioLogado().size();
        int categoriaSize   = categoriaController.selecionarTodasAsCategoriasDoUsuarioLogado().size();
        int tagSize         = tagController.selecionarTodasAsTagsDoUsuarioLogado().size();


        if (carteiraSize > 0 && categoriaSize > 0 && tagSize > 0) {
            Intent intent;
            if (codigo == 1) { // Nova receita
                intent = new Intent(getActivity(), MovimentacaoActivity.class);
                intent.putExtra("idNatureza", 1L);
            } else if (codigo == 2) { // Nova despesa
                intent = new Intent(getActivity(), MovimentacaoActivity.class);
                intent.putExtra("idNatureza", 2L);
            } else { // Nova transferência
                intent = new Intent(getActivity(), TransferenciaActivity.class);
            }
            startActivity(intent);

        } else {
            String mensagem = "Você deve cadastrar pelo menos uma";
            if (carteiraSize == 0){ mensagem += ", carteira"; }
            if (categoriaSize == 0){ mensagem += ", categoria"; }
            if (tagSize == 0){ mensagem += ", tag"; }
            mensagem += ".";
            mostrarMensagem(mensagem);
        }
    }

    private int quantidadeDeErros(int a, int b, int c){
        int resultado = 0;
        if (a > 0) { resultado++; }
        if (b > 0) { resultado++; }
        if (c > 0) { resultado++; }
        return resultado;
    }

    private void abrirActivityCarteira(){
        lytMostraSaldo.setOnClickListener(c -> {
            startActivity(new Intent(getActivity(), CarteiraActivity.class));
        });
    }

    private void abrirActivityCategoria(){
        lytGastoPorCategoria.setOnClickListener(c -> {
            startActivity(new Intent(getActivity(), CategoriaActivity.class));
        });
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

}
