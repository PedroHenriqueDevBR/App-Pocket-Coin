package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.model.Movimentacao;

import java.util.List;

public class MovimentacoesRecyclerViewAdapter extends RecyclerView.Adapter<MovimentacoesRecyclerViewAdapter.ViewHolder> {
    List<Movimentacao> movimentacoes;
    Context context;

    public MovimentacoesRecyclerViewAdapter(List<Movimentacao> movimentacoes, Context context) {
        this.movimentacoes = movimentacoes;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtValor, txtData, txtDescricao, txtConcluido;

        public ViewHolder(View itemView) {
            super(itemView);
            txtValor     = itemView.findViewById(R.id.text_view_valor);
            txtData      = itemView.findViewById(R.id.text_view_data);
            txtDescricao = itemView.findViewById(R.id.text_view_descricao);
            txtConcluido = itemView.findViewById(R.id.text_view_concluido);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_movimentacoes, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movimentacao movimentacao = movimentacoes.get(position);

        String valor        = Double.toString(movimentacao.getValor());
        String data         = movimentacao.getData();
        String descricao    = movimentacao.getDescricao();
        String finalizado;
        if (movimentacao.isConcuildo()){ finalizado = "Sim"; }
        else { finalizado = "Nao"; }

        holder.txtValor.setText(valor);
        holder.txtData.setText(data);
        holder.txtDescricao.setText(descricao);
        holder.txtConcluido.setText(finalizado);
    }

    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }
}
