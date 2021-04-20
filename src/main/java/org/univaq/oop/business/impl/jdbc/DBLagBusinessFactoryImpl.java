package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.*;

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
}
