package org.univaq.oop.business;

import org.univaq.oop.business.impl.file.FileLagBusinessFactoryImpl;
import org.univaq.oop.business.impl.jdbc.DBLagBusinessFactoryImpl;

public abstract class LagBusinessFactory {

    //private static final LagBusinessFactory factory = new FileLagBusinessFactoryImpl();
    private static final LagBusinessFactory factory = new DBLagBusinessFactoryImpl();


    public static LagBusinessFactory getInstance() {
        return factory;
    }

    public abstract UtenteService getUtenteService();

    public abstract PrescrizioneService getPrescrizioneService();

    public abstract FarmacoService getFarmacoService();

    public abstract FarmacoPrescrizioneService getFarmacoPrescrizioneService();


}
