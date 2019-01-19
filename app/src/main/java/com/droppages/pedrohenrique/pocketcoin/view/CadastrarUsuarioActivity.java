package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CadastrarUsuarioActivity extends AppCompatActivity {
    public static final String  LOGIN = "login";
    EditText                    txtNome, txtLogin, txtSenha, txtRepeteSenha;
    BoxStore                    boxStore;
    Box<Usuario>                usuarioBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();
        usuarioBox  = boxStore.boxFor(Usuario.class);

        // bind
        txtNome         = findViewById(R.id.edit_nome);
        txtLogin        = findViewById(R.id.edit_login);
        txtSenha        = findViewById(R.id.edit_senha);
        txtRepeteSenha  = findViewById(R.id.edit_repetir_senha);
    }


    public void cadastrarNovoUsuario(View view){
        String nome         = txtNome.getText().toString().trim();
        String login        = txtLogin.getText().toString().trim();
        String senha        = txtSenha.getText().toString().trim();
        String repeteSenha  = txtRepeteSenha.getText().toString().trim();

        if (dadosValidos(nome, login, senha, repeteSenha)) {
            Usuario usuario = new Usuario(nome, login, senha);
            usuarioBox.put(usuario);
            mostrarMensagem("Usuário cadastrado com sucesso!");
            finalizarCadastroERetornarLogin(login);
        }
    }


    private boolean dadosValidos(String nome, String login, String senha, String repeteSenha){
        if (nome.length() == 0){
            mostrarMensagem("Preencha o campo nome.");
            return false;
        } else if (login.length() == 0){
            mostrarMensagem("Preencha o campo login.");
            return false;
        } else if (senha.length() == 0){
            mostrarMensagem("Preencha o campo senha.");
            return false;
        } else if (repeteSenha.length() == 0){
            mostrarMensagem("Preencha o campo repetir senha.");
            return false;
        } else {
            if (!senha.equals(repeteSenha)){
                mostrarMensagem("Senhas diferentes, tente novamente");
                limparCampoDeSenha();
                return false;
            }
        }
        return true;
    }


    private void finalizarCadastroERetornarLogin(String login) {
        Intent intent = new Intent();
        intent.putExtra(LOGIN, login);
        setResult(RESULT_OK, intent);
        this.finish();
    }


    private void limparCampoDeSenha(){
        txtSenha.setText("");
        txtRepeteSenha.setText("");
    }


    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
