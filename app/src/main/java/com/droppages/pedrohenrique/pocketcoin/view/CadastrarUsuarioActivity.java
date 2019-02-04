package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.UsuarioController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;

import io.objectbox.BoxStore;

public class CadastrarUsuarioActivity extends AppCompatActivity {
    // Constantes
    public static final String  LOGIN = "login";
    // Variáveis
    private EditText            txtNome, txtLogin, txtSenha, txtRepeteSenha;
    private BoxStore            boxStore;
    private UsuarioController   controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        // ObjectBox
        boxStore    = ((App)getApplication()).getBoxStore();

        // bind
        txtNome         = findViewById(R.id.edit_nome);
        txtLogin        = findViewById(R.id.edit_login);
        txtSenha        = findViewById(R.id.edit_senha);
        txtRepeteSenha  = findViewById(R.id.edit_repetir_senha);

        // Inicializa Controller
        controller = new UsuarioController(boxStore);
    }

    public void cadastrarNovoUsuario(View view){
        String nome         = txtNome.getText().toString().trim();
        String login        = txtLogin.getText().toString().trim();
        String senha        = txtSenha.getText().toString().trim();
        String repeteSenha  = txtRepeteSenha.getText().toString().trim();

        try {
            controller.cadastrarNovoUsuario(nome, login, senha, repeteSenha);
            mostrarMensagem("Usuário cadastrado com sucesso!");
            finalizarCadastroERetornarLogin(login);
        } catch (CadastroInvalidoException e){
            mostrarMensagem(e.getMensagem());
        } catch (Exception e) {
            System.out.println(" Erro no cadastro: " + e.getMessage());
        }

    }

    private void finalizarCadastroERetornarLogin(String login) {
        Intent intent = new Intent();
        intent.putExtra(LOGIN, login);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
