package com.droppages.pedrohenrique.pocketcoin.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.objectbox.BoxStore;

public class TransferenciaActivity extends AppCompatActivity {

    private Spinner                 spinnerOrigem, spinnerDestino;
    private EditText                txtValor;
    private MovimentacaoController  movimentacaoController;
    private List<Long>              carteiraIndice;
    private BoxStore                boxStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);

        // Bind
        spinnerOrigem       = findViewById(R.id.spn_origem);
        spinnerDestino      = findViewById(R.id.spn_destino);
        txtValor            = findViewById(R.id.txt_valor);
        carteiraIndice      = new ArrayList<>();
        boxStore            = ((App)getApplication()).getBoxStore();
        movimentacaoController = new MovimentacaoController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));

        // Métodos
        preencherSpinnerComDados();
    }

    private void preencherSpinnerComDados(){
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, selecionarListaDeCarteira());
        spinnerOrigem.setAdapter(adapter);
        spinnerDestino.setAdapter(adapter);
    }

    private List<String> selecionarListaDeCarteira() {
        List<String> resultado = new ArrayList<>();

        for (Map<Long, String> map: movimentacaoController.selecionarTodasAsCarteirasComoDicionario()){
            String chaveNaoFormatada = map.keySet().toString().replace("[", "").replace("]", "");
            Long chaveFormatada = Long.parseLong(chaveNaoFormatada);
            carteiraIndice.add(chaveFormatada);
            resultado.add(map.get(chaveFormatada));
        }
        return resultado;
    }

    public void transferir(View view){
        long idOrigem   = carteiraIndice.get(spinnerOrigem.getSelectedItemPosition());
        long idDestino  = carteiraIndice.get(spinnerDestino.getSelectedItemPosition());
        float valor;

        if (idOrigem == idDestino){
            mostrarMensagem("Impossível transferir dinheiro de uma carteira para ela mesma");
        } else if (txtValor.getText().toString().trim().length() == 0) {
            mostrarMensagem("Digite o valor da tranferência.");
        } else {
            valor = Float.parseFloat(txtValor.getText().toString().trim());
            movimentacaoController.transferir(idOrigem, idDestino, valor);
            mostrarMensagem("Transferência realizada com sucesso!");
            this.finish();
        }
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
