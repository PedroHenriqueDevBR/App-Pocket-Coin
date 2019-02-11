package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.CarteiraController;
import com.droppages.pedrohenrique.pocketcoin.controllers.CategoriaController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;

import io.objectbox.BoxStore;

public class EditarCategoriaActivity extends AppCompatActivity {
    private long                idDaCategoria;
    private CategoriaController controller;
    private BoxStore            boxStore;
    private TextView            txtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria);

        Intent intent = getIntent();
        idDaCategoria = intent.getLongExtra("id", 0);

        //Bind
        txtNome     = findViewById(R.id.edit_nome);

        boxStore = ((App)getApplication()).getBoxStore();
        controller = new CategoriaController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));

        setarDados();
    }

    private void setarDados(){
        Categoria categoria = controller.selecionarCategoriaPeloId(idDaCategoria);
        txtNome.setText(categoria.getNome());
    }

    public void deletarCarteira(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Você realmente deseja deletar esta categoria?");
        builder.setPositiveButton("Sim", (a, b) -> {
            controller.deletarCategoria(idDaCategoria);
            this.finish();
        });
        builder.setNeutralButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void atualizarDados(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Você realmente deseja editar esta categoria?");

        builder.setPositiveButton("Sim", (a, b) -> {
            String nome = txtNome.getText().toString().trim();

            if (dadosValidos()){
                Categoria categoria = controller.selecionarCategoriaPeloId(idDaCategoria);
                categoria.setNome(nome);
                controller.atualizarCategoria(categoria);
                this.finish();
            }

        });

        builder.setNeutralButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean dadosValidos(){
        String nome = txtNome.getText().toString().trim();

        if (nome.isEmpty()){
            mostraMensagem("Preencha o campo nome");
            return false;
        }
        return true;
    }

    private void mostraMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
