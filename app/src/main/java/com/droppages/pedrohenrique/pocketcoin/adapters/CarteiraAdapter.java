package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.model.Carteira;

import java.util.List;

public class CarteiraAdapter extends RecyclerView.Adapter<CarteiraAdapter.ViewHolder> {

    Context context;
    List<Carteira> lista;

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

        viewHolder.nome.setText(nome);
        viewHolder.valor.setText(valor);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, valor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome                = itemView.findViewById(R.id.txt_nome_da_carteira);
            valor               = itemView.findViewById(R.id.txt_valor_da_carteira);
        }
    }

}
