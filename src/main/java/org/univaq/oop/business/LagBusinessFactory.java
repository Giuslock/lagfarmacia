package org.univaq.oop.business;

import org.univaq.oop.business.impl.file.FileLagBusinessFactoryImpl;
import org.univaq.oop.business.impl.jdbc.DBLagBusinessFactoryImpl;

public abstract class LagBusinessFactory {

//    private static final LagBusinessFactory factory = new FileLagBusinessFactoryImpl();
    private static final LagBusinessFactory factory = new DBLagBusinessFactoryImpl();

    public static LagBusinessFactory getInstance() {
        return factory;
    }

    public abstract UserService getUtenteService();

    public abstract PrescriptionService getPrescrizioneService();

    public abstract MedicineService getFarmacoService();

    public abstract FarmacoPrescrizioneService getFarmacoPrescrizioneService();


}
