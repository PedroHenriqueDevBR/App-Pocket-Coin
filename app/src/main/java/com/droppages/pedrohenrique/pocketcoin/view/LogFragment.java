package com.droppages.pedrohenrique.pocketcoin.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.adapters.MovimentacoesRecyclerViewAdapter;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.model.Movimentacao;

import java.util.List;

import io.objectbox.BoxStore;


public class LogFragment extends Fragment {
    private BoxStore boxStore;
    private MovimentacaoController movimentacaoControllercontroller;
    private RecyclerView listaDeMovimentacoes;

    public LogFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);;

        // Bind
        boxStore = ((App)getActivity().getApplication()).getBoxStore();
        movimentacaoControllercontroller = new MovimentacaoController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));
        listaDeMovimentacoes = view.findViewById(R.id.recycler_movimentacoes);

        mostrarListaDeMovimentacoes();

        return view;
    }

    @Override
    public void onResume() {
        mostrarListaDeMovimentacoes();
        super.onResume();
    }

    private void mostrarListaDeMovimentacoes(){
        List<Movimentacao> movimentacoes = movimentacaoControllercontroller.selecionarTodasMovimentacoesDoUsuario();
        mostrarMensage("Numero de elementos: " + movimentacoes.size());
        MovimentacoesRecyclerViewAdapter adapter = new MovimentacoesRecyclerViewAdapter(movimentacoes, getActivity().getApplicationContext());
        listaDeMovimentacoes.setAdapter(adapter);
        listaDeMovimentacoes.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private void mostrarMensage(String msg){
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
