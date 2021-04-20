package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.*;
import org.univaq.oop.business.impl.jdbc.migrations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBLagBusinessFactoryImpl extends LagBusinessFactory {
    private final UtenteService utenteService;
    private final FarmacoService farmacoService;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;

    public DBLagBusinessFactoryImpl() {
        this.utenteService = new DBUtenteServiceImpl();
        this.farmacoService = new DBFarmacoServiceImpl();
        this.prescrizioneService = new DBPrescrizioneServiceImpl();
        this.farmacoPrescrizioneService = new DBFarmacoPrescrizioneServiceImpl();
        //initDB();

    }

    @Override
    public UtenteService getUtenteService() {
        return utenteService;
    }

    @Override
    public PrescrizioneService getPrescrizioneService() {
        return prescrizioneService;
    }

    @Override
    public FarmacoService getFarmacoService() {
        return farmacoService;
    }

    @Override
    public FarmacoPrescrizioneService getFarmacoPrescrizioneService() {
        return farmacoPrescrizioneService;
    }

    private static void initDB() {
        // L'ORDINE E' IMPORTANTE!!!
        List<Migration> migrationList = new ArrayList<>();
        migrationList.add(new FarmacoPrescrizioneTable());
        migrationList.add(new FarmacoTable());
        migrationList.add(new PrescrizioneTable());
        migrationList.add(new UtenteTable());

        migrationList.forEach(Migration::down);

        Collections.reverse(migrationList);
        migrationList.forEach(Migration::up);
    }

}
