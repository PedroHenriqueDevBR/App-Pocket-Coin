package com.droppages.pedrohenrique.pocketcoin.view;


import android.content.Context;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.model.Movimentacao;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;


public class HomeFragment extends Fragment {
    private BoxStore boxStore;
    private MovimentacaoController controller;

    public HomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Bind
        boxStore = ((App)getActivity().getApplication()).getBoxStore();
        controller = new MovimentacaoController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));
        return view;
    }

    private void mostrarListaDeMovimentacoes(){
        List<Movimentacao> movimentacoes = controller.selecionarTodasMovimentacoesDoUsuario();
    }

}
