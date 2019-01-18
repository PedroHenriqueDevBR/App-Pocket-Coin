package com.droppages.pedrohenrique.pocketcoin.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Log {
    @Id
    public long id;
    public ToMany<Movimentacao> movimentacoes;

    public Log(){}

}
