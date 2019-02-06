package com.droppages.pedrohenrique.pocketcoin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.model.Movimentacao;

import java.util.List;

public class MovimentacaoAdapter extends RecyclerView.Adapter<MovimentacaoAdapter.ViewHolder> {
    private List<Movimentacao> movimentacoes;
    private Context context;

    public MovimentacaoAdapter(List<Movimentacao> movimentacoes, Context context) {
        this.movimentacoes = movimentacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_movimentacao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movimentacao movimentacao = movimentacoes.get(position);

        String natureza     = movimentacao.selecionarNatureza().getNome();
        String valor        = "R$ " + Double.toString(movimentacao.getValor());
        String descricao    = movimentacao.getDescricao();
        String carteira     = movimentacao.selecionarCarteira().getNome();
        String categoria    = movimentacao.selecionarCategoria().getNome();
        String data         = movimentacao.getData();

        holder.txtValor.setText(valor);
        holder.txtDescricao.setText(descricao);
        holder.txtCarteira.setText(carteira);
        holder.txtCategoria.setText(categoria);
        holder.txtData.setText(data);

        // Seta a cor da movimentacoa
        if (natureza.equals("Crédito")){
            holder.txtNatureza.setText("R");
            holder.txtNatureza.setBackgroundResource(R.drawable.bg_circle_receita);
            holder.txtCor.setBackgroundColor(Color.rgb(76,175,80));
        } else {
            holder.txtNatureza.setText("D");
            holder.txtNatureza.setBackgroundResource(R.drawable.bg_circle_despesa);
            holder.txtCor.setBackgroundColor(Color.rgb(244,67,54));
        }
    }

    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtValor, txtData, txtDescricao, txtNatureza, txtCarteira, txtCategoria, txtCor;
        private ImageView imageMenu;

        ViewHolder(View itemView) {
            super(itemView);
            txtNatureza     = itemView.findViewById(R.id.txt_natureza_movimentacao);
            txtValor        = itemView.findViewById(R.id.txt_valor_movimentacao);
            txtData         = itemView.findViewById(R.id.txt_data_movimentacao);
            txtDescricao    = itemView.findViewById(R.id.txt_descricao_movimentacao);
            txtCarteira     = itemView.findViewById(R.id.txt_carteira_movimentacao);
            txtCategoria    = itemView.findViewById(R.id.txt_categoria_movimentacao);
            txtCor          = itemView.findViewById(R.id.txt_cor_movimentacao);
            imageMenu       = itemView.findViewById(R.id.image_menu_movimentacao);
        }
    }
}
