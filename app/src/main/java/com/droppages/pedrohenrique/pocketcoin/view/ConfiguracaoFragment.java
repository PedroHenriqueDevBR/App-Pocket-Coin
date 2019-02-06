package com.droppages.pedrohenrique.pocketcoin.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;


public class ConfiguracaoFragment extends Fragment {
    // private Button btnGerenciarTag, btnGerenciarCategoria, btnGerenciarCarteira, btnEncerrarSessao;
    private CardView cardCarteira, cardCategoria, cardTag, cardEncerrarSessao;
    private Sessao sessao;

    public ConfiguracaoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracao, container, false);

        // Bind
        cardCarteira        = view.findViewById(R.id.card_abrir_carteira);
        cardCategoria       = view.findViewById(R.id.card_abrir_categoria);
        cardTag             = view.findViewById(R.id.card_abrir_tag);
        cardEncerrarSessao  = view.findViewById(R.id.card_encerrar_sessao);
        sessao              = new Sessao(getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, getActivity().MODE_PRIVATE));

        // Métodos
        abrirActivityCarteiras();
        abrirActivityCategoria();
        limparUsuarioDaSessao();
        abrirActivityTag();

        return view;
    }

    private void abrirActivityCarteiras(){
        cardCarteira.setOnClickListener(c -> startActivity(new Intent(this.getActivity(), CarteiraActivity.class)));
    }

    private void abrirActivityCategoria(){
        cardCategoria.setOnClickListener(c -> startActivity(new Intent(getActivity(), CategoriaActivity.class)));
    }

    private void abrirActivityTag(){
        cardTag.setOnClickListener(c -> startActivity(new Intent(getActivity(), TagActivity.class)));
    }

    public void limparUsuarioDaSessao(){
        cardEncerrarSessao.setOnClickListener(c -> {
            sessao.deletarDadosDaSessao(Sessao.USUARIO_ID);
            sessao.deletarDadosDaSessao(Sessao.USUARIO_NOME);
            sessao.deletarDadosDaSessao(Sessao.USUARIO_LOGIN);
            sessao.deletarDadosDaSessao(Sessao.USUARIO_SENHA);
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
    }
}
