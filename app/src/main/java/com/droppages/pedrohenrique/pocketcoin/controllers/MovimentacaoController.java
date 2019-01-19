package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.app.Activity;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.model.Configuracao;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MovimentacaoController {
    private Sessao sessao;
    private BoxStore boxStore;
    private Box<Usuario> usuarioBox;
    private Box<Configuracao>   configuracaoBox;
    private Box<NaturezaDaAcao> naturezaBox;

}
