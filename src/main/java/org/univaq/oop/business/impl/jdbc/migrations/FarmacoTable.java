package org.univaq.oop.business.impl.jdbc.migrations;

import java.util.ArrayList;
import java.util.List;

public class FarmacoTable extends Migration {

    public FarmacoTable() {
        super("farmaco");
    }

    @Override
    protected List<String> tableColumns() {
        List<String> columns = new ArrayList<>();

        columns.add("id bigint auto_increment primary key");
        columns.add("nome varchar(60) not null");
        columns.add("descrizione varchar(255) not null");
        columns.add("minimo tinyint default 5");
        columns.add("quantita smallint");
        columns.add("in_esaurimento tinyint(1) generated always as (minimo > quantita) stored"); // mysql generated columns


        return columns;
    }
}
