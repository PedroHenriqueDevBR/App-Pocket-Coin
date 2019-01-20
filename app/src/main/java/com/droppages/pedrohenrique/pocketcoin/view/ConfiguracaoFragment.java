package com.droppages.pedrohenrique.pocketcoin.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.droppages.pedrohenrique.pocketcoin.R;


public class ConfiguracaoFragment extends Fragment {
    Button btnGerenciarTag, btnGerenciarCategoria, btnGerenciarCarteira;

    public ConfiguracaoFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracao, container, false);

        // Bind
        btnGerenciarTag         = view.findViewById(R.id.btn_gerenciar_tag);
        btnGerenciarCarteira    = view.findViewById(R.id.btn_gerenciar_carteira);
        btnGerenciarCategoria   = view.findViewById(R.id.btn_gerenciar_categoria);

        // Métodos
        btnGerenciarTag.setOnClickListener(arg -> abrirActivityTag());
        btnGerenciarCarteira.setOnClickListener(arg -> abrirActivityCarteiras());
        btnGerenciarCategoria.setOnClickListener(arg -> abrirActivityCategoria());

        return view;
    }

    private void abrirActivityCarteiras(){
        startActivity(new Intent(this.getActivity(), CarteiraActivity.class));
    }

    private void abrirActivityCategoria(){
        startActivity(new Intent(getActivity(), CategoriaActivity.class));
    }

    private void abrirActivityTag(){
        startActivity(new Intent(getActivity(), TagActivity.class));
    }
}
