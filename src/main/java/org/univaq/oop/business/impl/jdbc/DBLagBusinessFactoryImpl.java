package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.*;

public class DBLagBusinessFactoryImpl extends LagBusinessFactory {
    private final UserService utenteService;
    private final MedicineService farmacoService;
    private final PrescriptionService prescrizioneService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;

    public DBLagBusinessFactoryImpl() {
        this.utenteService = new DBUtenteServiceImpl();
        this.farmacoService = new DBFarmacoServiceImpl();
        this.prescrizioneService = new DBPrescrizioneServiceImpl();
        this.farmacoPrescrizioneService = new DBFarmacoPrescrizioneServiceImpl();
    }

    @Override
    public UserService getUtenteService() {
        return utenteService;
    }

    @Override
    public PrescriptionService getPrescrizioneService() {
        return prescrizioneService;
    }

    @Override
    public MedicineService getFarmacoService() {
        return farmacoService;
    }

    @Override
    public FarmacoPrescrizioneService getFarmacoPrescrizioneService() {
        return farmacoPrescrizioneService;
    }
}
