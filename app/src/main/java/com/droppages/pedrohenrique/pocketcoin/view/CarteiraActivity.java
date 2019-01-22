package com.droppages.pedrohenrique.pocketcoin.view;

import android.se.omapi.Session;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.CarteiraController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeCarteiraException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CarteiraActivity extends AppCompatActivity {
    private BoxStore                boxStore;
    private Box<Carteira>           carteiraBox;
    private Box<NaturezaDaAcao>     naturezaBox;
    private EditText                txtNome, txtValor;
    private Spinner                 spnNatureza;
    private CarteiraController      controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        // Bind
        spnNatureza = findViewById(R.id.spn_natureza);
        txtNome     = findViewById(R.id.edit_nome);
        txtValor    = findViewById(R.id.edit_valor);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        carteiraBox = boxStore.boxFor(Carteira.class);
        naturezaBox = boxStore.boxFor(NaturezaDaAcao.class);
        controller  = new CarteiraController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));

        // Spliner
        preencherSpinerComNaturezaDaAcao();
    }

    private void preencherSpinerComNaturezaDaAcao(){
        List<NaturezaDaAcao> lista = naturezaBox.getAll();

        List<String> naturezas = new ArrayList();
        for (NaturezaDaAcao n: lista) {
            naturezas.add(n.id + " - " + n.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, naturezas);
        spnNatureza.setAdapter(adapter);
    }

    public void cadastrarCarteira(View view){
        String nome = txtNome.getText().toString().trim();
        float valor = Float.parseFloat(txtValor.getText().toString().trim());
        NaturezaDaAcao natureza = naturezaBox.get(pegaElementoDoSpinner());

        try {
            controller.cadastrarNovaCarteira(nome, valor, natureza);
            mostrarMensagem("Carteira adicionada com sucesso");
        } catch (DadoInvalidoNoCadastroDeCarteiraException e){
            mostrarMensagem(e.getMensagem());
        }
    }

    public void MostrarQuantidadeDeCarteirasCadastradas(View view){
        List<Carteira> carteiras = controller.selecionarTodasAsCarteirasDoUsuarioLogado();
        mostrarMensagem("" + carteiras.size());
    }

    private long pegaElementoDoSpinner(){
        String valor[] = spnNatureza.getSelectedItem().toString().trim().split(" ");
        return Long.parseLong(valor[0]);
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
