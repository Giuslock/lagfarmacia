package org.univaq.oop.business.impl.jdbc.migrations;

import java.util.ArrayList;
import java.util.List;

public class UtenteTable extends Migration {

    public UtenteTable() {
        super("utente");
    }


    @Override
    protected List<String> tableColumns() {
        List<String> columns = new ArrayList<>();

        columns.add("id bigint auto_increment primary key");
        columns.add("username varchar(25) not null unique");
        columns.add("password_ varchar(45) not null");
        columns.add("nome varchar(15) not null");
        columns.add("cognome varchar(20)  not null");
        columns.add("role varchar(30) default \"PATIENT\"");
        columns.add("codicefiscale varchar(25) not null unique");

        return columns;
    }
}
