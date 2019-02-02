package com.droppages.pedrohenrique.pocketcoin.view;

import android.se.omapi.Session;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.adapters.CarteiraAdapter;
import com.droppages.pedrohenrique.pocketcoin.controllers.CarteiraController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeCarteiraException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CarteiraActivity extends AppCompatActivity {
    private BoxStore                boxStore;
    private Box<Carteira>           carteiraBox;
    private Box<NaturezaDaAcao>     naturezaBox;
    private CarteiraController      controller;
    private RecyclerView            rvCarteiras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        // Bind
        rvCarteiras = findViewById(R.id.rv_lista_de_carteiras);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        carteiraBox = boxStore.boxFor(Carteira.class);
        naturezaBox = boxStore.boxFor(NaturezaDaAcao.class);
        controller  = new CarteiraController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
    }

    @Override
    protected void onResume() {
        super.onResume();

        listarCarteirasCadastradas();
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
            float valor = Float.parseFloat(txtValor.getText().toString().trim());

            try {
                controller.cadastrarNovaCarteira(nome, valor);
                mostrarMensagem("Carteira adicionada com sucesso");
                onResume();
            } catch (DadoInvalidoNoCadastroDeCarteiraException e){
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
