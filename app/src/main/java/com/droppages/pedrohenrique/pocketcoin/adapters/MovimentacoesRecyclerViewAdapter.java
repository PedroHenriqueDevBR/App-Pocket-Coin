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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_movimentacoes, parent, false);

        ViewHolder  viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Movimentacao movimentacao = movimentacoes.get(position);

        viewHolder.txtValor.setText((int) movimentacao.getValor());
        viewHolder.txtData.setText((movimentacao.getData()));
        viewHolder.txtDescricao.setText((movimentacao.getDescricao()));
        viewHolder.txtConcluido.setText("Concluido");

//        setupClick(holder, aluno);
//
//        setupLongClick(holder, aluno);

    }

    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtValor, txtData, txtDescricao, txtConcluido;

        public ViewHolder(View itemView) {
            super(itemView);

            txtValor = itemView.findViewById(R.id.text_view_valor);
            txtData =  itemView.findViewById(R.id.text_view_data);
            txtDescricao =  itemView.findViewById(R.id.text_view_descricao);
        }
    }
}
