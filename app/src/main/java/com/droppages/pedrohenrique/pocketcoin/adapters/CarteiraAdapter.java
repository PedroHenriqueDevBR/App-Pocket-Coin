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

import com.droppages.pedrohenrique.pocketcoin.view.EditarCarteiraActivity;
import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;

import java.util.List;

public class CarteiraAdapter extends RecyclerView.Adapter<CarteiraAdapter.ViewHolder> {

    private Context context;
    private List<Carteira> lista;

    public CarteiraAdapter(Context context, List<Carteira> lista){
        this.context = context;
        this.lista = lista;
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

        String nome = carteira.getNome();
        String valor = Float.toString(carteira.getSaldo());

        viewHolder.txtNome.setText(nome);
        viewHolder.txtValor.setText(valor);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNome, txtValor;
        private ImageView imageAcao;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome                = itemView.findViewById(R.id.txt_nome_da_carteira);
            txtValor               = itemView.findViewById(R.id.txt_valor_da_carteira);
            imageAcao              = itemView.findViewById(R.id.image_acao);

            escutarAcao();
        }

        // Listener
        private void escutarAcao(){
            itemView.setOnClickListener(c -> {
                // ID
                int posicao = getAdapterPosition();
                long id = lista.get(posicao).id;

                Intent intent = new Intent(context.getApplicationContext(), EditarCarteiraActivity.class);
                intent.putExtra("id", id);

                context.startActivity(intent);
            });
        }
    }

}
