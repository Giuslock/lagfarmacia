package org.univaq.oop.business.impl.jdbc.migrations;
import java.util.ArrayList;
import java.util.List;

public class PrescrizioneTable extends Migration {

    public PrescrizioneTable() {
        super("prescrizione");
    }

    @Override
    protected List<String> tableColumns() {
        List<String> columns = new ArrayList<>();

        columns.add("id bigint auto_increment primary key");
        columns.add("f_evaso boolean default false");
        columns.add("descrizione varchar(255) not null");
        columns.add("medico_id bigint (255) not null");
        columns.add("utente_id bigint (255) not null");
        columns.add("foreign key (medico_id) references utente (id)");
        columns.add("foreign key (utente_id) references utente (id)");
        columns.add("state varchar(25)");

        return columns;
    }
}
