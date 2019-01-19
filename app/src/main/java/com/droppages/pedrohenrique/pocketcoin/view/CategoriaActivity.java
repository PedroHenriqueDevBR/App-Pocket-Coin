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
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CategoriaActivity extends AppCompatActivity {
    private BoxStore                boxStore;
    private Box<Categoria>          categoriaBox;
    private Box<NaturezaDaAcao>     naturezaBox;
    EditText                        txtNome;
    Spinner                         spnNatureza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Bind
        spnNatureza = findViewById(R.id.spn_natureza);
        txtNome     = findViewById(R.id.edit_nome);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        categoriaBox = boxStore.boxFor(Categoria.class);
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

    public void cadastrarCategoria(View view){
        String nome             = txtNome.getText().toString().trim();
        NaturezaDaAcao natureza = naturezaBox.get(pegaElementoDoSpinner());

        Categoria categoria = new Categoria(nome);
        categoria.natureza.setTarget(natureza);

        categoriaBox.put(categoria);

        mostrarMensagem("Categoria cadastrada");
    }

    public void MostrarCategoriaCadastradas(View view){
        List<Categoria> categorias = categoriaBox.getAll();
        mostrarMensagem("" + categorias.size());
    }

    private long pegaElementoDoSpinner(){
        String valor[] = spnNatureza.getSelectedItem().toString().trim().split(" ");
        return Long.parseLong(valor[0]);
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void limparCampos(){
        txtNome.setText("");
    }
}
