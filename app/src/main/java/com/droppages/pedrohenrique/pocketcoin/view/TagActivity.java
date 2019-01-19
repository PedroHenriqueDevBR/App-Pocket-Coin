package com.droppages.pedrohenrique.pocketcoin.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class TagActivity extends AppCompatActivity {
    private BoxStore    boxStore;
    private Box<Tag>    tagBox;
    EditText            txtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        // Bind
        txtNome     = findViewById(R.id.edit_nome);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        tagBox      = boxStore.boxFor(Tag.class);
    }


    public void cadastrarNovaTag(View view){
        String nome = txtNome.getText().toString().trim();
        Tag tag = new Tag(nome);
        tagBox.put(tag);
        mostrarMensagem("Tag cadastrada com sucesso!");
        limparCampos();
    }

    public void mostrarTagsCadastradas(View view){
        List<Tag> tags = tagBox.getAll();
        mostrarMensagem("" + tags.size());
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void limparCampos(){
        txtNome.setText("");
    }
}
