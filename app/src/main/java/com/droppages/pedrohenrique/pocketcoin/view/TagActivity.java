package com.droppages.pedrohenrique.pocketcoin.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.TagController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeTagException;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;

import java.util.List;

import io.objectbox.BoxStore;

public class TagActivity extends AppCompatActivity {
    private BoxStore        boxStore;
    private EditText        txtNome;
    private TagController   controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        // Bind
        txtNome     = findViewById(R.id.edit_nome);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        controller  = new TagController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
    }


    public void cadastrarNovaTag(View view){
        try {
            String nome = txtNome.getText().toString().trim();
            controller.cadastrarTag(nome);
            mostrarMensagem("Tag cadastrada com sucesso");
            limparCampos();
        } catch (DadoInvalidoNoCadastroDeTagException e) {
            mostrarMensagem(e.getMensagem());
        } catch (Exception e) {
            Log.e("TagCadastro", e.getMessage());
        }
    }

    public void mostrarTagsCadastradas(View view){
        List<Tag> tags = controller.selecionarTodasAsTagsDoUsuarioLogado();
        mostrarMensagem("" + tags.size());
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void limparCampos(){
        txtNome.setText("");
    }
}
