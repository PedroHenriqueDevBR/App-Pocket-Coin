package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.view.EditarCategoriaActivity;
import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private Context context;
    private List<Categoria> lista;

    public CategoriaAdapter(Context context, List<Categoria> lista) {
        this.context = context;
        this.lista = lista;
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
        String nome = categoria.getNome();
        viewHolder.txtNome.setText(nome);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNome;
        private ImageView imageAcao;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txt_nome_da_categoria);
            imageAcao = itemView.findViewById(R.id.image_acao);
            escutarAcao();
        }

        private void escutarAcao(){
            itemView.setOnLongClickListener(v -> {
                // ID
                int posicao = getAdapterPosition();
                long id = lista.get(posicao).id;

                Intent intent = new Intent(context.getApplicationContext(), EditarCategoriaActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
                return false;
            });
        }
    }
}
