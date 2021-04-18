package org.univaq.oop.business.impl.jdbc.migrations;

import java.util.ArrayList;
import java.util.List;

public class FarmacoPrescrizioneTable extends Migration {

    public FarmacoPrescrizioneTable() {
        super("farmaco_prescrizione");
    }

    @Override
    protected List<String> tableColumns() {
        List<String> columns = new ArrayList<>();

        columns.add("id bigint auto_increment primary key");
        columns.add("farmaco_id bigint not null");
        columns.add("prescrizione_id bigint not null");
        columns.add("quantity smallint default 1");
        columns.add("foreign key (farmaco_id) references farmaco (id)");
        columns.add("foreign key (prescrizione_id) references prescrizione (id) on delete cascade");

        return columns;
    }


}
