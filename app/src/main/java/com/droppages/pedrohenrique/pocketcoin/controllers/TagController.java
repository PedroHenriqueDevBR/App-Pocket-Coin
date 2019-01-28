package com.droppages.pedrohenrique.pocketcoin.controllers;

import android.content.SharedPreferences;

import com.droppages.pedrohenrique.pocketcoin.dal.Sessao;
import com.droppages.pedrohenrique.pocketcoin.exceptions.DadoInvalidoNoCadastroDeTagException;
import com.droppages.pedrohenrique.pocketcoin.model.Categoria;
import com.droppages.pedrohenrique.pocketcoin.model.NaturezaDaAcao;
import com.droppages.pedrohenrique.pocketcoin.model.Tag;
import com.droppages.pedrohenrique.pocketcoin.model.Usuario;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class TagController {

    private Box<Tag> tagBox;
    private Box<Usuario> usuarioBox;
    private Sessao sessao;


    public TagController(BoxStore boxStore, SharedPreferences preferences) {
        tagBox = boxStore.boxFor(Tag.class);
        usuarioBox  = boxStore.boxFor(Usuario.class);
        this.sessao = new Sessao(preferences);
    }


    public void cadastrarTag(String nome) throws DadoInvalidoNoCadastroDeTagException {
        if (dadosValidosParaCadastro(nome)){
            Tag tag = new Tag(nome);
            Usuario usuarioLogado = selecionarUsuarioLogado();
            usuarioLogado.tags.add(tag);
            usuarioBox.put(usuarioLogado);
        }
    }


    public List<Tag> selecionarTodasAsTagsDoUsuarioLogado(){
        Usuario usuarioLogado = selecionarUsuarioLogado();
        return usuarioLogado.getTags();
    }


    /*
     * Métodos de apoio aos métodos públicos
     */


    private boolean dadosValidosParaCadastro(String nome) throws DadoInvalidoNoCadastroDeTagException {
        if (nome.length() == 0) {
            throw new DadoInvalidoNoCadastroDeTagException("Digite o nome da tag.");
        } else if (tagJaCadastrado(nome)) {
            throw new DadoInvalidoNoCadastroDeTagException("Tag já cadastrada, altere o nome da tag e tente novamente.");
        }
        return true;
    }


    private boolean tagJaCadastrado(String nome){
        for (Tag tag: tagBox.getAll()){
            if (tag.getNome().toLowerCase().equals(nome.toLowerCase())) { return true; }
        }
        return false;
    }


    private Usuario selecionarUsuarioLogado() {
        long idDoUsuarioLogado = Long.parseLong(sessao.recuperarDadosDaSessao(Sessao.USUARIO_ID));
        return usuarioBox.get(idDoUsuarioLogado);
    }

}
