package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.MainActivity;
import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.model.Configuracao;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class LoginActivity extends AppCompatActivity {
    // variáveis globais
    private static final int    REQUEST_CODE = 4678;
    private static final String SESSAO_USUARIO = "SessaoUsuario";
    SharedPreferences           sessao;
    EditText                    txtLogin, txtSenha;
    Button                      btnEntrar, btnCadastrar;
    BoxStore                    boxStore;
    Box<Usuario>                usuarioBox;
    Box<Configuracao>           configuracaoBox;
    Box<NaturezaDaAcao>         naturezaBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ObjectBox
        boxStore        = ((App)getApplication()).getBoxStore();
        usuarioBox      = boxStore.boxFor(Usuario.class);
        configuracaoBox = boxStore.boxFor(Configuracao.class);
        naturezaBox = boxStore.boxFor(NaturezaDaAcao.class);

        // bind
        txtLogin        = findViewById(R.id.edit_login);
        txtSenha        = findViewById(R.id.edit_senha);
        btnEntrar       = findViewById(R.id.btn_entrar);
        btnCadastrar    = findViewById(R.id.btn_cadastrar);

        // Sessao
        sessao = getSharedPreferences(SESSAO_USUARIO, Context.MODE_PRIVATE);

        // Instalação inicial
        primeiraInstalacaoDoApp();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            String login = data.getStringExtra(CadastrarUsuarioActivity.LOGIN);
            setarLogin(login);
        }
    }


    private void primeiraInstalacaoDoApp() {
        List<Configuracao> configuracoes = configuracaoBox.getAll();
        if (configuracoes.size() == 0) {
            configuracaoBox.put(new Configuracao("Instalado", "1"));
            naturezaBox.put(new NaturezaDaAcao("Crédito"));
            naturezaBox.put(new NaturezaDaAcao("Débito"));
        }
    }


    public void cadastrarUsuario(View v){
        startActivityForResult(new Intent(this, CadastrarUsuarioActivity.class), REQUEST_CODE);
    }


    public void entrar(View v){
        String login = txtLogin.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();

        if (dadosValidos(login, senha)){
            long idDoUsuario = usuarioExistente(login, senha);
            if (idDoUsuario != -1){
                mostrarMensagem("ID: " + idDoUsuario);
                limparCampos();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                mostrarMensagem("Dados inválidos, tente novamente ou cadastre-se!");
                txtSenha.setText("");
            }
        }
    }


    private boolean dadosValidos(String login, String senha){
        if (login.length() == 0){
            mostrarMensagem("Preencha o campo login");
            return false;
        } else if (senha.length() == 0){
            mostrarMensagem("Preencha o campo senha");
            return false;
        }
        return true;
    }


    private long usuarioExistente(String login, String senha){
        for (Usuario usuario: usuarioBox.getAll()){
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)){
                return usuario.id;
            }
        }
        return -1;
    }


    private void adicionarDadosaASessao(String chave, String valor){
        if (chave.length() > 0 && valor.length() > 0){
            SharedPreferences.Editor session = sessao.edit();
            session.putString(chave, valor);
            session.apply();
        }
    }


    private String recuperarDadosDaSessao(String chave){
        String valor = sessao.getString(chave, "Nada encontrado");
        return valor;
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
