package com.droppages.pedrohenrique.pocketcoin.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;


public class ConfiguracaoFragment extends Fragment {
    private Button btnGerenciarTag, btnGerenciarCategoria, btnGerenciarCarteira, btnEncerrarSessao;
    private Sessao sessao;

    public ConfiguracaoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracao, container, false);

        // Bind
        btnGerenciarTag         = view.findViewById(R.id.btn_gerenciar_tag);
        btnEncerrarSessao       = view.findViewById(R.id.btn_encerrar_sessao);
        btnGerenciarCarteira    = view.findViewById(R.id.btn_gerenciar_carteira);
        btnGerenciarCategoria   = view.findViewById(R.id.btn_gerenciar_categoria);
        sessao                  = new Sessao(getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, getActivity().MODE_PRIVATE));

        // Métodos
        btnGerenciarTag.setOnClickListener(arg -> abrirActivityTag());
        btnGerenciarCarteira.setOnClickListener(arg -> abrirActivityCarteiras());
        btnGerenciarCategoria.setOnClickListener(arg -> abrirActivityCategoria());
        btnEncerrarSessao.setOnClickListener(arg -> limparUsuarioDaSessao());

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

    private void limparUsuarioDaSessao(){
        sessao.deletarDadosDaSessao(Sessao.USUARIO_ID);
        sessao.deletarDadosDaSessao(Sessao.USUARIO_NOME);
        sessao.deletarDadosDaSessao(Sessao.USUARIO_LOGIN);
        sessao.deletarDadosDaSessao(Sessao.USUARIO_SENHA);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
