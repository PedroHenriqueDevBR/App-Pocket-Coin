package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.view.EditarTagActivity;
import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private Context context;
    private List<Tag> lista;

    public TagAdapter(Context context, List<Tag> lista) {
        this.context = context;
        this.lista = lista;
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


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtId;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txt_id_da_tag);
            txtNome = itemView.findViewById(R.id.txt_nome_da_tag);

            escutarAcao();
        }

        private void escutarAcao(){
            itemView.setOnClickListener((c) -> {
                // ID
                int posicao = getAdapterPosition();
                long id = lista.get(posicao).id;

                Intent intent = new Intent(context.getApplicationContext(), EditarTagActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            });
        }
    }
}
