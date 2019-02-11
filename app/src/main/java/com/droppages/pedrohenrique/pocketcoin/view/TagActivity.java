package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.adapters.TagAdapter;
import com.droppages.pedrohenrique.pocketcoin.controllers.TagController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;

import java.util.List;

import io.objectbox.BoxStore;

public class TagActivity extends AppCompatActivity {
    private BoxStore        boxStore;
    private EditText        txtNome;
    private TagController   controller;
    private RecyclerView    rvTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        // Bind
        txtNome     = findViewById(R.id.edit_nome);
        rvTags      = findViewById(R.id.rv_lista_de_tag);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        controller  = new TagController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarTagsCadastradas();
    }

    private void mostrarTagsCadastradas(){
        List<Tag> tags = controller.selecionarTodasAsTagsDoUsuarioLogado();
        TagAdapter adapter = new TagAdapter(this, tags);
        rvTags.setAdapter(adapter);
        rvTags.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    public void cadastrarTag(View v){
        View            dialogCategoria = View.inflate(this, R.layout.dialog_cadastrar_tag, null);
        EditText        txtNome         = dialogCategoria.findViewById(R.id.edit_nome);

        // Criação do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogCategoria);
        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String nome = txtNome.getText().toString().trim();
                    controller.cadastrarTag(nome);
                    mostrarMensagem("Tag cadastrada com sucesso!");
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
