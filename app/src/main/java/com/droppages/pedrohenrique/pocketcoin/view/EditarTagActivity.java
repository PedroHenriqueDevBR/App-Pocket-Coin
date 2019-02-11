package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.CategoriaController;
import com.droppages.pedrohenrique.pocketcoin.controllers.TagController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;

import io.objectbox.BoxStore;

public class EditarTagActivity extends AppCompatActivity {
    private long                idDaTag;
    private TagController       controller;
    private BoxStore            boxStore;
    private TextView            txtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tag);

        Intent intent = getIntent();
        idDaTag = intent.getLongExtra("id", 0);

        //Bind
        txtNome     = findViewById(R.id.edit_nome);

        boxStore = ((App)getApplication()).getBoxStore();
        controller = new TagController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));

        setarDados();
    }

    private void setarDados(){
        Tag tag = controller.selecionarTagPeloId(idDaTag);
        txtNome.setText(tag.getNome());
    }

    public void deletarCarteira(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Você realmente deseja deletar esta tag?");
        builder.setPositiveButton("Sim", (a, b) -> {
            controller.deletarTag(idDaTag);
            this.finish();
        });
        builder.setNeutralButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void atualizarDados(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Você realmente deseja editar esta tag?");

        builder.setPositiveButton("Sim", (a, b) -> {
            String nome = txtNome.getText().toString().trim();

            if (dadosValidos()){
                Tag tag = controller.selecionarTagPeloId(idDaTag);
                tag.setNome(nome);
                controller.atualizarTag(tag);
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
