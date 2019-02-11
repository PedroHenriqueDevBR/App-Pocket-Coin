package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.UsuarioController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;

import java.util.List;

import io.objectbox.BoxStore;

public class LoginActivity extends AppCompatActivity {
    // Constantes
    private static final int            REQUEST_CODE = 4678;
    // variáveis globais
    private EditText                    txtLogin, txtSenha;
    private BoxStore                    boxStore;
    private UsuarioController           controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind
        boxStore        = ((App)getApplication()).getBoxStore();
        txtLogin        = findViewById(R.id.edit_login);
        txtSenha        = findViewById(R.id.edit_senha);
        controller      = new UsuarioController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));

        // Instalação inicial
        controller.primeiraInstalacaoDoApp();

        irParaMainActivityCasoUsuarioLogado();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            String login = data.getStringExtra(CadastrarUsuarioActivity.LOGIN);
            setarLogin(login);
        }
    }

    public void AbrirActivityCadastrarUsuario(View v){
        startActivityForResult(new Intent(this, CadastrarUsuarioActivity.class), REQUEST_CODE);
    }

    public void entrar(View v){
        String login = txtLogin.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();

        try {
            controller.login(login, senha);
            limparCampos();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } catch (CadastroInvalidoException e){
            mostrarMensagem(e.getMensagem());
        } catch (Exception e) {
            Log.e("Erronologin", e.getMessage());
        }
    }

    private void irParaMainActivityCasoUsuarioLogado() {
        List<String> dadosDoUsuarioLogado = controller.pegarDadosDoUsuarioLogado();
        if (!dadosDoUsuarioLogado.get(0).equals(Sessao.DEFAULT)){
            mostrarMensagem("Seja bem vindo de volta " + dadosDoUsuarioLogado.get(1));
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void setarLogin(String login){
        txtLogin.setText(login);
    }

    private void limparCampos(){
        txtLogin.setText("");
        txtSenha.setText("");
    }
}
