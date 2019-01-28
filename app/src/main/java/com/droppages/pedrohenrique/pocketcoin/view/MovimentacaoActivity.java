package com.droppages.pedrohenrique.pocketcoin.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeMovimentacaoException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.objectbox.BoxStore;

public class MovimentacaoActivity extends AppCompatActivity {
    private EditText                txtValor, txtData, txtDescricao;
    private Spinner                 spnCategoria, spnCarteira, spnTag;
    private CheckBox                checkConcluido;
    private MovimentacaoController  controller;
    private List<Long>              tagIndice, carteiraIndice, categoriaIndice;
    private Long                    idNaturezaRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao);

        // Recuperando Intent
        Intent intent = getIntent();
        idNaturezaRecebida = intent.getLongExtra("idNatureza", 0);

        // Bind
        txtValor            = findViewById(R.id.edit_valor);
        txtData             = findViewById(R.id.edit_data);
        txtDescricao        = findViewById(R.id.edit_descricao);
        spnCategoria        = findViewById(R.id.spn_categoria);
        spnCarteira         = findViewById(R.id.spn_carteira);
        spnTag              = findViewById(R.id.spn_tag);
        checkConcluido      = findViewById(R.id.check_concluido);

        // ObjectBox
        BoxStore boxStore   = ((App) getApplication()).getBoxStore();

        // Controller e Instancia
        controller          = new MovimentacaoController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
        tagIndice           = new ArrayList<>();
        carteiraIndice      = new ArrayList<>();
        categoriaIndice     = new ArrayList<>();

        // Metodos
        preencherSpninnersComDados();
        preencherTxtDataComDataAtual();
        txtData.setOnClickListener(a -> abrirDatePicker());
    }

    private void preencherSpninnersComDados(){
        ArrayAdapter<String> adapter;
        // Spinner Tag
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selecionarListaDeTags());
        spnTag.setAdapter(adapter);
        // Spinner Categoria
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selecionarListaDeCategoria());
        spnCategoria.setAdapter(adapter);
        // Spinner Carteira
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selecionarListaDeCarteira());
        spnCarteira.setAdapter(adapter);
    }

    public void cadastrarNovaMovimentacao(View view){
        String valor        = txtValor.getText().toString().trim();
        String data         = txtData.getText().toString().trim();
        String descricao    = txtDescricao.getText().toString().trim();
        long idCategoria    = selecionarIdDoSpinnerCategoria();
        long idCarteira     = selecionarIdDoSpinnerCarteira();
        long idTag          = selecionarIdDoSpinnerTag();
        boolean concluido   = checkConcluido.isChecked();

        try {
            controller.cadastrarNovaMovimentacao(valor, data, descricao, idCategoria, idCarteira, idTag, idNaturezaRecebida, concluido);
            mostrarMensagem("Movimentação cadastrada com sucesso");
            this.finish();
        } catch (DadoInvalidoNoCadastroDeMovimentacaoException e){
            mostrarMensagem(e.getMensagem());
        } catch (Exception e) {
            Log.e("cadastroMovimentacao", e.getMessage());
        }
    }

    private void abrirDatePicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Layout
        View date_picker_layout = View.inflate(this, R.layout.date_picker, null);

        // Builder
        builder.setView(date_picker_layout);
        builder.setNeutralButton("Cancelar", null);
        builder.setCancelable(false);
        builder.setPositiveButton("Concluído", (a, b) -> {
            DatePicker picker = date_picker_layout.findViewById(R.id.date_picker_dialog);
            String dia = Integer.toString(picker.getDayOfMonth());
            String mes = Integer.toString(picker.getMonth() + 1); // Soma mais um para que o mês venha no formato correto
            String ano = Integer.toString(picker.getYear());
            if (dia.length() == 1) { dia = "0"+dia; }
            if (mes.length() == 1){ mes = "0"+mes; }
            String dataFormatada = dia + "/" + mes + "/" + ano;
            txtData.setText(dataFormatada);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private List<String> selecionarListaDeTags() {
        List<String> resultado = new ArrayList<>();

        for (Map<Long, String> map: controller.selecionarTodasAsTagsComoDicionario()){
            String chaveNaoFormatada = map.keySet().toString().replace("[", "").replace("]", "");
            Long chaveFormatada = Long.parseLong(chaveNaoFormatada);
            tagIndice.add(chaveFormatada);
            resultado.add(map.get(chaveFormatada));
        }
        return resultado;
    }

    private List<String> selecionarListaDeCarteira() {
        List<String> resultado = new ArrayList<>();

        for (Map<Long, String> map: controller.selecionarTodasAsCarteirasComoDicionario()){
            String chaveNaoFormatada = map.keySet().toString().replace("[", "").replace("]", "");
            Long chaveFormatada = Long.parseLong(chaveNaoFormatada);
            carteiraIndice.add(chaveFormatada);
            resultado.add(map.get(chaveFormatada));
        }
        return resultado;
    }

    private List<String> selecionarListaDeCategoria() {
        List<String> resultado = new ArrayList<>();

        for (Map<Long, String> map: controller.selecionarTodasAsCategoriasComoDicionario(idNaturezaRecebida)){
            String chaveNaoFormatada = map.keySet().toString().replace("[", "").replace("]", "");
            Long chaveFormatada = Long.parseLong(chaveNaoFormatada);
            categoriaIndice.add(chaveFormatada);
            resultado.add(map.get(chaveFormatada));
        }
        return resultado;
    }

    private void preencherTxtDataComDataAtual() {
        String formatoDaData = "dd/MM/yyyy";
        DateFormat format = new SimpleDateFormat(formatoDaData);
        Date date = new Date();
        txtData.setText(format.format(date));
    }

    private long selecionarIdDoSpinnerCategoria(){
        return categoriaIndice.get(spnCategoria.getSelectedItemPosition());
    }

    private long selecionarIdDoSpinnerCarteira(){
        return carteiraIndice.get(spnCarteira.getSelectedItemPosition());
    }

    private long selecionarIdDoSpinnerTag(){
        return tagIndice.get(spnTag.getSelectedItemPosition());
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
