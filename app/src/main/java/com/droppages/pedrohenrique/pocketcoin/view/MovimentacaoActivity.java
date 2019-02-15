package com.droppages.pedrohenrique.pocketcoin.view;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.CarteiraController;
import com.droppages.pedrohenrique.pocketcoin.controllers.CategoriaController;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.controllers.TagController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.CadastroInvalidoException;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.objectbox.BoxStore;

public class MovimentacaoActivity extends AppCompatActivity {
    private EditText                txtValor, txtData, txtDescricao;
    private TextView                txtDescricaoMovimentacao;
    private Spinner                 spnCategoria, spnCarteira, spnTag;
    private MovimentacaoController  movimentacaoController;
    private TagController           tagController;
    private CarteiraController      carteiraController;
    private CategoriaController     categoriaController;
    private List<Long>              tagIndice, carteiraIndice, categoriaIndice;
    private Long                    idNaturezaRecebida;
    private ImageView               imgCadastrarTag, imgCadastrarCategoria, imgCadastrarCarteira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao);

        // Recuperando Intent
        Intent intent = getIntent();
        idNaturezaRecebida = intent.getLongExtra("idNatureza", 0);

        // Bind
        spnTag                      = findViewById(R.id.spn_tag);
        txtData                     = findViewById(R.id.edit_data);
        txtValor                    = findViewById(R.id.edit_valor);
        spnCarteira                 = findViewById(R.id.spn_carteira);
        spnCategoria                = findViewById(R.id.spn_categoria);
        txtDescricao                = findViewById(R.id.edit_descricao);
        imgCadastrarTag             = findViewById(R.id.image_add_tag);
        imgCadastrarCarteira        = findViewById(R.id.image_add_carteira);
        imgCadastrarCategoria       = findViewById(R.id.image_add_categoria);
        txtDescricaoMovimentacao    = findViewById(R.id.txt_descricao_movimentacao);

        // ObjectBox
        BoxStore boxStore       = ((App) getApplication()).getBoxStore();

        // Controller e Instancia
        tagIndice               = new ArrayList<>();
        tagController           = new TagController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
        carteiraIndice          = new ArrayList<>();
        categoriaIndice         = new ArrayList<>();
        carteiraController      = new CarteiraController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
        categoriaController     = new CategoriaController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));
        movimentacaoController  = new MovimentacaoController(boxStore, getSharedPreferences(Sessao.SESSAO_USUARIO, MODE_PRIVATE));

        // Metodos
        setaCorDoTopo();
        preencherTxtDataComDataAtual();
        txtData.setOnClickListener(a -> abrirDatePicker());
        imgCadastrarTag.setOnClickListener(a -> cadastrarTag());
        imgCadastrarCarteira.setOnClickListener(a -> cadastrarCarteira());
        imgCadastrarCategoria.setOnClickListener(a -> cadastrarCategoria());
    }

    @Override
    protected void onResume() {
        preencherSpninnersComDados();
        super.onResume();
    }

    private void setaCorDoTopo() {
        if (idNaturezaRecebida == 1){
            txtDescricaoMovimentacao.setBackgroundColor(Color.rgb(76,175,80));
        } else {
            txtDescricaoMovimentacao.setBackgroundColor(Color.rgb(244,67,54));
        }
    }

    private void preencherSpninnersComDados(){
        ArrayAdapter<String> adapter;
        // Spinner Tag
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selecionarListaDeTags());
        spnTag.setAdapter(adapter);
        // Spinner Categoria
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selecionarListaDeCategoria());
        spnCategoria.setAdapter(adapter);
        // Spinner Carteira
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selecionarListaDeCarteira());
        spnCarteira.setAdapter(adapter);
    }

    public void cadastrarNovaMovimentacao(View view){
        String valor        = txtValor.getText().toString().trim();
        String data         = txtData.getText().toString().trim();
        String descricao    = selecionarDescricao(txtDescricao.getText().toString().trim());
        long idCategoria    = selecionarIdDoSpinnerCategoria();
        long idCarteira     = selecionarIdDoSpinnerCarteira();
        long idTag          = selecionarIdDoSpinnerTag();

        try {
            movimentacaoController.cadastrarNovaMovimentacao(valor, data, descricao, idCategoria, idCarteira, idTag, idNaturezaRecebida, true);
            mostrarMensagem("Movimentação cadastrada com sucesso");
            this.finish();
        } catch (CadastroInvalidoException e){
            mostrarMensagem(e.getMensagem());
        } catch (Exception e) {
            Log.e("cadastroMovimentacao", e.getMessage());
        }
    }

    private String selecionarDescricao(String descricao){
        if (descricao.length() == 0) {
            descricao = "Sem descrição";
        }
        return descricao;
    }

    private void abrirDatePicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Layout
        View date_picker_layout = View.inflate(this, R.layout.dialog_date_picker, null);

        // Builder
        builder.setView(date_picker_layout);
        builder.setNeutralButton("Cancelar", null);
        builder.setCancelable(false);
        builder.setPositiveButton("Concluído", (a, b) -> {
            DatePicker picker = date_picker_layout.findViewById(R.id.date_picker_dialog);
            String dia = Integer.toString(picker.getDayOfMonth());
            String mes = Integer.toString(picker.getMonth() + 1); // Soma mais um para que o mês venha no formato correto
            String ano = Integer.toString(picker.getYear());
            if (dia.length() == 1) { dia = "0"+dia; }
            if (mes.length() == 1){ mes = "0"+mes; }
            String dataFormatada = dia + "/" + mes + "/" + ano;
            txtData.setText(dataFormatada);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private List<String> selecionarListaDeTags() {
        List<String> resultado = new ArrayList<>();

        for (Map<Long, String> map: movimentacaoController.selecionarTodasAsTagsComoDicionario()){
            String chaveNaoFormatada = map.keySet().toString().replace("[", "").replace("]", "");
            Long chaveFormatada = Long.parseLong(chaveNaoFormatada);
            tagIndice.add(chaveFormatada);
            resultado.add(map.get(chaveFormatada));
        }
        return resultado;
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

    private List<String> selecionarListaDeCategoria() {
        List<String> resultado = new ArrayList<>();

        for (Map<Long, String> map: movimentacaoController.selecionarTodasAsCategoriasComoDicionario(idNaturezaRecebida)){
            String chaveNaoFormatada = map.keySet().toString().replace("[", "").replace("]", "");
            Long chaveFormatada = Long.parseLong(chaveNaoFormatada);
            categoriaIndice.add(chaveFormatada);
            resultado.add(map.get(chaveFormatada));
        }
        return resultado;
    }

    private void preencherTxtDataComDataAtual() {
        String formatoDaData = "dd/MM/yyyy";
        DateFormat format = new SimpleDateFormat(formatoDaData);
        Date date = new Date();
        txtData.setText(format.format(date));
    }

    private void cadastrarTag(){
        View dialogCategoria = View.inflate(this, R.layout.dialog_cadastrar_tag, null);
        EditText txtNome = dialogCategoria.findViewById(R.id.edit_nome);

        // Criação do AlertDialog
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setView(dialogCategoria);
        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String nome = txtNome.getText().toString().trim();
                    tagController.cadastrarTag(nome);
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
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void cadastrarCategoria(){
        View            dialogCategoria = View.inflate(this, R.layout.dialog_cadastrar_categoria, null);
        List<Long>      idDoSpinner     = new ArrayList<>();
        List<String>    nomeCategoria   = new ArrayList<>();
        EditText        txtNome         = dialogCategoria.findViewById(R.id.edit_nome);
        Spinner         spnNatureza     = dialogCategoria.findViewById(R.id.spn_natureza);

        // Preenche o spinner e a lista de id's
        for (NaturezaDaAcao natureza: categoriaController.selecionarTodasAsNaturezas()) {
            idDoSpinner.add(natureza.id);
            nomeCategoria.add(natureza.getNome());
        }

        // Preenche o spinner com dados
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nomeCategoria);
        spnNatureza.setAdapter(adapter);

        // Criação do AlertDialog
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setView(dialogCategoria);
        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String nome = txtNome.getText().toString().trim();
                    long id = idDoSpinner.get(spnNatureza.getSelectedItemPosition());
                    categoriaController.cadastrarNovaCategoria(nome, id);
                    mostrarMensagem("Categoria cadastrada com sucesso!");
                    onResume();
                } catch (CadastroInvalidoException e){
                    mostrarMensagem(e.getMensagem());
                } catch (Exception e) {
                    System.out.println("Erro no cadastro: " + e.getMessage());
                }
            }
        });
        builder.setNeutralButton("Cancelar", null);
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void cadastrarCarteira(){
        // Layout
        View dialogCadastrarCarteira = View.inflate(this, R.layout.dialog_cadastrar_carteira, null);

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setView(dialogCadastrarCarteira);
        builder.setPositiveButton("Cadastrar", (a, b) -> {
            EditText txtNome = dialogCadastrarCarteira.findViewById(R.id.edit_nome);
            EditText txtValor = dialogCadastrarCarteira.findViewById(R.id.edit_valor);

            String nome = txtNome.getText().toString().trim();
            float valor = 0f;

            if (!txtValor.getText().toString().trim().isEmpty()) {
                valor = Float.parseFloat(txtValor.getText().toString().trim());
            }

            try {
                carteiraController.cadastrarNovaCarteira(nome, valor);
                mostrarMensagem("Carteira adicionada com sucesso");
                onResume();
            } catch (CadastroInvalidoException e){
                mostrarMensagem(e.getMensagem());
            }
        });
        builder.setNeutralButton("Cancelar", null);

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private long selecionarIdDoSpinnerCategoria(){
        return categoriaIndice.get(spnCategoria.getSelectedItemPosition());
    }

    private long selecionarIdDoSpinnerCarteira(){
        return carteiraIndice.get(spnCarteira.getSelectedItemPosition());
    }

    private long selecionarIdDoSpinnerTag(){
        return tagIndice.get(spnTag.getSelectedItemPosition());
    }

    private void mostrarMensagem(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
