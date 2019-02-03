package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.CarteiraController;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeCarteiraException;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;
import com.droppages.pedrohenrique.pocketcoin.model.Log;

import java.util.List;

import io.objectbox.BoxStore;

public class CarteiraAdapter extends RecyclerView.Adapter<CarteiraAdapter.ViewHolder> {

    Context context;
    List<Carteira> lista;
    CarteiraController controller;
    BoxStore boxStore;

    public CarteiraAdapter(Context context, List<Carteira> lista, BoxStore boxStore, SharedPreferences preferences){
        this.context = context;
        this.lista = lista;
        this.boxStore = boxStore;
        controller = new CarteiraController(boxStore, preferences);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_carteira, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Carteira carteira = lista.get(i);

        String id = Long.toString(carteira.id);
        String nome = carteira.getNome();
        String valor = Float.toString(carteira.getSaldo());

        viewHolder.txtId.setText(id);
        viewHolder.txtNome.setText(nome);
        viewHolder.txtValor.setText(valor);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtValor, txtId;
        ImageView imageAcao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId                  = itemView.findViewById(R.id.txt_id_da_carteira);
            txtNome                = itemView.findViewById(R.id.txt_nome_da_carteira);
            txtValor               = itemView.findViewById(R.id.txt_valor_da_carteira);
            imageAcao = itemView.findViewById(R.id.image_acao);

            executarAcao();
        }

        private void executarAcao(){
            imageAcao.setOnClickListener(c -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Opções");
                builder.setIcon(R.drawable.ic_bank);
                builder.setMessage("Escolha uma ação, para cancelar clique fora da caixa.");
                // Editar dados
                builder.setPositiveButton("Editar", (a, b) -> {
                    long id = Long.parseLong(txtId.getText().toString().trim());
                    editarCarteira(id);
                });

                builder.setNegativeButton("Deletar", (a, b) -> {
                    long id = Long.parseLong(txtId.getText().toString().trim());
                    deletarCarteira(id);
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Opções");
                    builder.setIcon(R.drawable.ic_bank);
                    builder.setMessage("Escolha uma ação, para cancelar clique fora da caixa.");
                    // Editar dados
                    builder.setPositiveButton("Editar", (a, b) -> {
                        long id = Long.parseLong(txtId.getText().toString().trim());
                        editarCarteira(id);
                    });

                    builder.setNegativeButton("Deletar", (a, b) -> {
                        long id = Long.parseLong(txtId.getText().toString().trim());
                        deletarCarteira(id);
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            });
        }

        private void editarCarteira(long id){
            Carteira carteira = controller.selecionarCarteiraPeloId(id);
            String nome = carteira.getNome();
            String valor = Float.toString(carteira.getSaldo());

            // Layout
            View view = View.inflate(context, R.layout.dialog_cadastrar_carteira, null);
            EditText editarNome     = view.findViewById(R.id.edit_nome);
            EditText editarValor    = view.findViewById(R.id.edit_valor);

            editarNome.setText(nome);
            editarValor.setText(valor);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(view);

            builder.setPositiveButton("Salvar", (a, b) -> {
                try {
                    carteira.setNome(editarNome.getText().toString().trim());
                    carteira.setSaldo(Float.parseFloat(txtValor.getText().toString().trim()));
                    controller.atualizarCarteira(carteira);
                } catch (DadoInvalidoNoCadastroDeCarteiraException e) {
                    Toast.makeText(context, e.getMensagem(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    System.out.println("Erro: " + e.getMessage());
                }
            });

            builder.setNeutralButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private void deletarCarteira(long id){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.ic_alerta);
            builder.setTitle("Atenção");
            builder.setMessage("Deseja deletar a carteira selecionada?");

            builder.setPositiveButton("Sim, eu quero deletar", (a, b) -> {
                controller.deletarCarteira(id);
            });
            builder.setNeutralButton("Cancelar", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
