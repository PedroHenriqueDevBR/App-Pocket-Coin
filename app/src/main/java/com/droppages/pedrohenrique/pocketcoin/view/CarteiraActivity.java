package com.droppages.pedrohenrique.pocketcoin.view;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.adapters.CarteiraAdapter;
import com.droppages.pedrohenrique.pocketcoin.controllers.CarteiraController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.BoxStore;

public class CarteiraActivity extends AppCompatActivity {
    private BoxStore                boxStore;
    private CarteiraController      controller;
    private RecyclerView            rvCarteiras;
    private BarChart                chartCarteiras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        // Bind
        rvCarteiras     = findViewById(R.id.rv_lista_de_carteiras);
        chartCarteiras  = findViewById(R.id.chart_carteiras_cadastradas);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        controller  = new CarteiraController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarCarteirasCadastradas();
        mostrarGraficoDeCarteirasCadastradas();
    }

    private void mostrarGraficoDeCarteirasCadastradas(){
        Description description = new Description();
        description.setText("");

        chartCarteiras.setDescription(description);
        chartCarteiras.setDrawBarShadow(false);
        chartCarteiras.setDrawValueAboveBar(true);
        chartCarteiras.setPinchZoom(true);
        chartCarteiras.setDrawGridBackground(true);
        chartCarteiras.setDrawValueAboveBar(true);
        chartCarteiras.setFitBars(true);

        List<BarEntry> entries = new ArrayList<>();


        List<Carteira> carteiras = controller.selecionarTodasAsCarteirasDoUsuarioLogado();
        for (int i = 0; i < carteiras.size(); i++){
            entries.add(new BarEntry(i, carteiras.get(i).getSaldo()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Dados");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(10);

        BarData data = new BarData(dataSet);

        chartCarteiras.animateY(2500, Easing.EasingOption.EaseInOutCubic);
        chartCarteiras.animateX(2500, Easing.EasingOption.EaseInOutCubic);
        chartCarteiras.setData(data);
    }

    private void listarCarteirasCadastradas(){
        List<Carteira> carteiras = controller.selecionarTodasAsCarteirasDoUsuarioLogado();
        CarteiraAdapter adapter = new CarteiraAdapter(this, carteiras);
        rvCarteiras.setAdapter(adapter);
        rvCarteiras.setLayoutManager(new LinearLayoutManager(this));
    }

    public void abrirDialogParaCadastroDeCarteira(View v){
        // Layout
        View dialogCadastrarCarteira = View.inflate(this, R.layout.dialog_cadastrar_carteira, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogCadastrarCarteira);
        builder.setPositiveButton("Cadastrar", (a, b) -> {
            EditText txtNome = dialogCadastrarCarteira.findViewById(R.id.edit_nome);
            EditText txtValor = dialogCadastrarCarteira.findViewById(R.id.edit_valor);

            String nome = txtNome.getText().toString().trim();
            float valor = 0f;

            if (!txtValor.getText().toString().trim().isEmpty()) {
                valor = Float.parseFloat(txtValor.getText().toString().trim());
            }

            try {
                controller.cadastrarNovaCarteira(nome, valor);
                mostrarMensagem("Carteira adicionada com sucesso");
                onResume();
            } catch (CadastroInvalidoException e){
                mostrarMensagem(e.getMensagem());
            }
        });
        builder.setNeutralButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
