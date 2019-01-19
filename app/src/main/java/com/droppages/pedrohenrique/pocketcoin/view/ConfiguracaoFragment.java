package com.droppages.pedrohenrique.pocketcoin.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droppages.pedrohenrique.pocketcoin.R;


public class ConfiguracaoFragment extends Fragment {


    public ConfiguracaoFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracao, container, false);
        return view;
    }

}
