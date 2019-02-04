package com.droppages.pedrohenrique.pocketcoin.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.droppages.pedrohenrique.pocketcoin.R;
import com.droppages.pedrohenrique.pocketcoin.adapters.MovimentacaoAdapter;
import com.droppages.pedrohenrique.pocketcoin.controllers.MovimentacaoController;
import com.droppages.pedrohenrique.pocketcoin.dal.App;
import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.model.Movimentacao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.objectbox.BoxStore;


public class LogFragment extends Fragment {
    private BoxStore boxStore;
    private MovimentacaoController controller;
    private RecyclerView listaDeMovimentacoes;
    private Spinner spnDatas;

    public LogFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);;

        // Bind
        spnDatas                = view.findViewById(R.id.spn_datas);
        boxStore                = ((App)getActivity().getApplication()).getBoxStore();
        controller              = new MovimentacaoController(boxStore, getActivity().getSharedPreferences(Sessao.SESSAO_USUARIO, Context.MODE_PRIVATE));
        listaDeMovimentacoes    = view.findViewById(R.id.recycler_movimentacoes);

        return view;
    }

    @Override
    public void onResume() {
        mostrarListaDeMovimentacoesDoMesAtual();
        preecherSpinnerComDatas();
        alterarDataDasMovimentacoes();
        super.onResume();
    }

    private void alterarDataDasMovimentacoes(){
        spnDatas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object objeto = spnDatas.getItemAtPosition(position);
                String data = objeto.toString();

                if (data.equals("Mês atual")){ mostrarListaDeMovimentacoesDoMesAtual(); }
                else { mostrarListaDeMovimentacoesDoMesSelecionado(data); }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void mostrarListaDeMovimentacoesDoMesAtual(){
        List<Movimentacao> movimentacoes = ordenarListaNaOrdemDecrescente(controller.selecionarTodasAsMovimentacoesDoUsuarioNoMesAtual());
        MovimentacaoAdapter adapter = new MovimentacaoAdapter(movimentacoes, getActivity().getApplicationContext());
        listaDeMovimentacoes.setAdapter(adapter);
        listaDeMovimentacoes.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private void mostrarListaDeMovimentacoesDoMesSelecionado(String data){
        List<Movimentacao> movimentacoes = ordenarListaNaOrdemDecrescente(controller.selecionarTodasAsMovimentacoesDoUsuarioNoMesEAnoSelecionado(data));
        MovimentacaoAdapter adapter = new MovimentacaoAdapter(movimentacoes, getActivity().getApplicationContext());
        listaDeMovimentacoes.setAdapter(adapter);
        listaDeMovimentacoes.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private List<Movimentacao> ordenarListaNaOrdemDecrescente(List<Movimentacao> movimentacoes){
        Collections.sort(movimentacoes, new Comparator<Movimentacao>() {
            @Override
            public int compare(Movimentacao o1, Movimentacao o2) {
                if (o1.id < o2.id){
                    return 1;
                } else if (o1.id > o2.id) {
                    return -1;
                }
                return 0;
            }
        });
        return movimentacoes;
    }

    private List<String> ordenarDataPorAno(List<String> lista){
        Collections.sort(lista, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 != "Mês atual"  && o2 != "Mês atual"){
                    int anoUm = Integer.parseInt(o1.split("/")[1]);
                    int anoDois = Integer.parseInt(o2.split("/")[1]);

                    if (anoUm > anoDois) {
                        return 1;
                    } else if (anoUm < anoDois) {
                        return -1;
                    }
                }
                return 0;
            }
        });
        return lista;
    }

    private List<String> ordenarDataPorMes(List<String> lista){
        Collections.sort(lista, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 != "Mês atual"  && o2 != "Mês atual"){
                    int anoUm = Integer.parseInt(o1.split("/")[0]);
                    int anoDois = Integer.parseInt(o2.split("/")[0]);

                    if (anoUm > anoDois) {
                        return 1;
                    } else if (anoUm < anoDois) {
                        return -1;
                    }
                }
                return 0;
            }
        });
        return lista;
    }

    private void preecherSpinnerComDatas(){
        List<String> listaDeDatas = controller.selecionarTodasAsDatasCadastradas();
        ordenarDataPorMes(listaDeDatas);
        ordenarDataPorAno(listaDeDatas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaDeDatas);
        spnDatas.setAdapter(adapter);
    }

    private void mostrarMensage(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
