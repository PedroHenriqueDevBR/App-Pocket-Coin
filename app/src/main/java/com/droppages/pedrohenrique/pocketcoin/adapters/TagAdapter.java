package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.TagController;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;

import java.util.List;

import io.objectbox.BoxStore;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    Context context;
    List<Tag> lista;
    TagController controller;

    public TagAdapter(Context context, List<Tag> lista, BoxStore boxStore, SharedPreferences preferences) {
        this.context = context;
        this.lista = lista;
        controller = new TagController(boxStore, preferences);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_tag, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Tag tag = lista.get(i);

        String id = Long.toString(tag.id);
        String nome = tag.getNome();

        viewHolder.txtId.setText(id);
        viewHolder.txtNome.setText(nome);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txt_id_da_tag);
            txtNome = itemView.findViewById(R.id.txt_nome_da_tag);

            deletarTag();
        }

        private void deletarTag(){
            itemView.setOnClickListener((c) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Atenção");
                builder.setMessage("Deseja deletar a tag selecionada?");
                builder.setPositiveButton("Sim, eu quero deletar", (a, b) -> {
                    long id = Long.parseLong(txtId.getText().toString());
                    controller.deletarTag(id);
                });
                builder.setNeutralButton("Cancelar", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
    }
}
