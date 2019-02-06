package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.R;

import java.util.List;

public class DespesaPorCategoriaAdapter extends RecyclerView.Adapter<DespesaPorCategoriaAdapter.ViewHolder> {
    private List<CategoriaComValor> listaDeCategoria;
    private Context context;

    public DespesaPorCategoriaAdapter(List<CategoriaComValor> listaDeCategoria, Context context){
        this.listaDeCategoria = listaDeCategoria;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_despesa_por_categoria, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CategoriaComValor linha = listaDeCategoria.get(i);

        String categoria    = linha.categoria;
        String valor        = Float.toString(linha.valor);

        viewHolder.txtCategoria.setText(categoria);
        viewHolder.txtValor.setText(valor);
    }

    @Override
    public int getItemCount() {
        return listaDeCategoria.size();
    }

    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoria, txtValor;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoria    = itemView.findViewById(R.id.text_view_nome_categoria);
            txtValor        = itemView.findViewById(R.id.text_view_valor_categoria);
        }
    }

    public static class CategoriaComValor {
        public String categoria;
        public float valor;

        public CategoriaComValor(){}
    }

}
