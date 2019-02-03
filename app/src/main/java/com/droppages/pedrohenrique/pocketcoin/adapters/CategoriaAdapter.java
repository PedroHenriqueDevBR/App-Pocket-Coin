package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.CategoriaController;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;

import java.util.List;

import io.objectbox.BoxStore;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    Context context;
    List<Categoria> lista;
    CategoriaController controller;

    public CategoriaAdapter(Context context, List<Categoria> lista, BoxStore boxStore, SharedPreferences preferences) {
        this.context = context;
        this.lista = lista;
        this.controller = new CategoriaController(boxStore, preferences);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_categoria, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Categoria categoria = lista.get(i);
        String id = Long.toString(categoria.id);
        String nome = categoria.getNome();

        viewHolder.txtId.setText(id);
        viewHolder.txtNome.setText(nome);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtId;
        ImageView imageAcao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txt_id_da_categoria);
            txtNome = itemView.findViewById(R.id.txt_nome_da_categoria);
            imageAcao = itemView.findViewById(R.id.image_acao);

            escolherAcao();
        }

        private void escolherAcao(){
            itemView.setOnLongClickListener(v -> {
                deletarCategoria();
                return false;
            });

            imageAcao.setOnClickListener(c -> deletarCategoria());
        }

        private void deletarCategoria(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.ic_alerta);
            builder.setTitle("Atenção");
            builder.setMessage("Deseja deletar categoria?");
            builder.setPositiveButton("Sim, eu quero deletar", (a, b) -> {
                long id = Long.parseLong(txtId.getText().toString().trim());
                controller.deletarCategoria(id);
            });
            builder.setNeutralButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
