package com.droppages.pedrohenrique.pocketcoin.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeMovimentacaoException;

import io.objectbox.BoxStore;

public class MovimentacaoActivity extends AppCompatActivity {
    private EditText                txtValor, txtData, txtDescricao;
    private Spinner                 spnCategoria, spnCarteira, spnTag, spnNatureza;
    private CheckBox                checkConcluido;
    private BoxStore                boxStore;
    private MovimentacaoController  controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao);

        // Bind
        txtValor        = findViewById(R.id.edit_valor);
        txtData         = findViewById(R.id.edit_data);
        txtDescricao    = findViewById(R.id.edit_descricao);
        spnCategoria    = findViewById(R.id.spn_categoria);
        spnCarteira     = findViewById(R.id.spn_carteira);
        spnTag          = findViewById(R.id.spn_tag);
        spnNatureza     = findViewById(R.id.spn_acao);
        checkConcluido  = findViewById(R.id.check_concluido);

        // ObjectBox
        boxStore = ((App)getApplication()).getBoxStore();

        // Controller
        controller = new MovimentacaoController(boxStore);

        preencherSpninnersComDados();
    }

    private void preencherSpninnersComDados(){
        ArrayAdapter<String> adapter;
        // Spinner Tag
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, controller.selecionarTodasAsTagsComoList());
        spnTag.setAdapter(adapter);
        // Spinner Categoria
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, controller.selecionarTodasAsCategoriasComoList());
        spnCategoria.setAdapter(adapter);
        // Spinner Carteira
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, controller.selecionarTodasAsCarteirasComoList());
        spnCarteira.setAdapter(adapter);
        // Spinner Tag
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, controller.selecionarTodasAsNaturezasComoList());
        spnNatureza.setAdapter(adapter);
    }

    public void cadastrarNovaMovimentacao(View view){
        String valor        = txtValor.getText().toString().trim();
        String data         = txtData.getText().toString().trim();
        String descricao    = txtDescricao.getText().toString().trim();
        long idCategoria    = selecionarIdDoSpinnerCategoria();
        long idCarteira     = selecionarIdDoSpinnerCarteira();
        long idTag          = selecionarIdDoSpinnerTag();
        long idNatureza     = selecionarIdDoSpinnerNatureza();
        boolean concluido;

        if (checkConcluido.isChecked()){
            concluido = true;
        } else {
            concluido = false;
        }

        try {
            controller.cadastrarNovaMovimentacao(valor, data, descricao, idCategoria, idCarteira, idTag, idNatureza, concluido);
            mostrarMEnsagem("Movimentação cadastrada com sucesso");
        } catch (DadoInvalidoNoCadastroDeMovimentacaoException e){
            mostrarMEnsagem(e.getMensagem());
        } catch (Exception e) {
            Log.e("cadastroMovimentacao", e.getMessage());
        }
    }

    private long selecionarIdDoSpinnerCategoria(){
        String valor[] = spnCategoria.getSelectedItem().toString().trim().split(" ");
        return Long.parseLong(valor[0]);
    }

    private long selecionarIdDoSpinnerCarteira(){
        String valor[] = spnCarteira.getSelectedItem().toString().trim().split(" ");
        return Long.parseLong(valor[0]);
    }

    private long selecionarIdDoSpinnerTag(){
        String valor[] = spnTag.getSelectedItem().toString().trim().split(" ");
        return Long.parseLong(valor[0]);
    }

    private long selecionarIdDoSpinnerNatureza(){
        String valor[] = spnNatureza.getSelectedItem().toString().trim().split(" ");
        return Long.parseLong(valor[0]);
    }

    private void mostrarMEnsagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
