package com.droppages.pedrohenrique.pocketcoin.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
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
    EditText                        txtNome, txtValor;
    Spinner                         spnNatureza;

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

        Carteira carteira = new Carteira(nome, valor);
        carteira.natureza.setTarget(natureza);

        carteiraBox.put(carteira);
    }

    public void MostrarCarteirasCadastradas(View view){
        List<Carteira> carteiras = carteiraBox.getAll();
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
