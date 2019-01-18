package com.droppages.pedrohenrique.pocketcoin.dal;

import android.app.Application;

import com.droppages.pedrohenrique.pocketcoin.model.MyObjectBox;

import io.objectbox.BoxStore;

public class App extends Application {

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        inicialize();
        super.onCreate();
    }

    public void inicialize(){
        boxStore = MyObjectBox.builder().androidContext(this).build();
    }

    public BoxStore getBoxStore(){
        return this.boxStore;
    }
}
