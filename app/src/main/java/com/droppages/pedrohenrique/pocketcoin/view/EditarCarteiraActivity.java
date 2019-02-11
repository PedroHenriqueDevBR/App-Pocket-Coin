package com.droppages.pedrohenrique.pocketcoin.view;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.CarteiraController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;

import io.objectbox.BoxStore;

public class EditarCarteiraActivity extends AppCompatActivity {
    private long                idDaCarteira;
    private CarteiraController  controller;
    private BoxStore            boxStore;
    private TextView            txtNome, txtValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_carteira);

        Intent intent = getIntent();
        idDaCarteira = intent.getLongExtra("id", 0);

        //Bind
        txtNome     = findViewById(R.id.edit_nome);
        txtValor    = findViewById(R.id.edit_valor);

        boxStore = ((App)getApplication()).getBoxStore();
        controller = new CarteiraController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));

        setarDados();
    }

    private void setarDados(){
        Carteira carteira = controller.selecionarCarteiraPeloId(idDaCarteira);
        txtNome.setText(carteira.getNome());
        txtValor.setText(Float.toString(carteira.getSaldo()));
    }

    public void deletarCarteira(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Você realmente deseja deletar esta carteira?");
        builder.setPositiveButton("Sim", (a, b) -> {
            controller.deletarCarteira(idDaCarteira);
            this.finish();
        });
        builder.setNeutralButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void atualizarDados(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Você realmente deseja editar esta carteira?");

        builder.setPositiveButton("Sim", (a, b) -> {
            String nome = txtNome.getText().toString().trim();
            String valor = txtValor.getText().toString().trim();

            if (dadosValidos()){
                Carteira carteira = controller.selecionarCarteiraPeloId(idDaCarteira);
                carteira.setNome(nome);
                carteira.setSaldo(Float.parseFloat(valor));
                controller.atualizarCarteira(carteira);
                this.finish();
            }

        });

        builder.setNeutralButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean dadosValidos(){
        String nome = txtNome.getText().toString().trim();
        String valor = txtValor.getText().toString().trim();

        if (nome.isEmpty()){
            mostraMensagem("Preencha o campo nome");
            return false;
        } else if (valor.isEmpty()) {
            mostraMensagem("Preencha o campo valor");
            return false;
        }
        return true;
    }

    private void mostraMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
