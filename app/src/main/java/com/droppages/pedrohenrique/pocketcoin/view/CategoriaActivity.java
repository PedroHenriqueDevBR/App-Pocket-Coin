package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.adapters.CategoriaAdapter;
import com.droppages.pedrohenrique.pocketcoin.controllers.CategoriaController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CategoriaActivity extends AppCompatActivity {
    private BoxStore                boxStore;
    private Box<NaturezaDaAcao>     naturezaBox;
    private CategoriaController     categoriaController;
    private RecyclerView            rvCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Bind
        rvCategoria = findViewById(R.id.rv_lista_de_categoria);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        naturezaBox = boxStore.boxFor(NaturezaDaAcao.class);
        categoriaController = new CategoriaController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarCategoriasCadastradas();
    }

    private void mostrarCategoriasCadastradas(){
        List<Categoria> categorias = categoriaController.selecionarTodasAsCategoriasDoUsuarioLogado();
        CategoriaAdapter adapter = new CategoriaAdapter(this, categorias);
        rvCategoria.setAdapter(adapter);
        rvCategoria.setLayoutManager(new LinearLayoutManager(this));
    }

    public void cadastrarCategoria(View view){
        View            dialogCategoria = View.inflate(this, R.layout.dialog_cadastrar_categoria, null);
        List<Long>      idDoSpinner     = new ArrayList<>();
        List<String>    nomeCategoria   = new ArrayList<>();
        EditText        txtNome         = dialogCategoria.findViewById(R.id.edit_nome);
        Spinner         spnNatureza     = dialogCategoria.findViewById(R.id.spn_natureza);

        // Preenche o spinner e a lista de id's
        for (NaturezaDaAcao natureza: categoriaController.selecionarTodasAsNaturezas()) {
            idDoSpinner.add(natureza.id);
            nomeCategoria.add(natureza.getNome());
        }

        // Preenche o spinner com dados
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nomeCategoria);
        spnNatureza.setAdapter(adapter);

        // Criação do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogCategoria);
        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String nome = txtNome.getText().toString().trim();
                    long id = idDoSpinner.get(spnNatureza.getSelectedItemPosition());
                    categoriaController.cadastrarNovaCategoria(nome, id);
                    mostrarMensagem("Categoria cadastrada com sucesso!");
                    onResume();
                } catch (CadastroInvalidoException e){
                    mostrarMensagem(e.getMensagem());
                } catch (Exception e) {
                    System.out.println("Erro no cadastro: " + e.getMessage());
                }
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
