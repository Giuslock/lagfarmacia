package org.univaq.oop.business;

import org.univaq.oop.business.impl.file.FileLagBusinessFactoryImpl;

public abstract class LagBusinessFactory {

    private static LagBusinessFactory factory = new FileLagBusinessFactoryImpl();

    public static LagBusinessFactory getInstance() {
        return factory;
    }

    public abstract UserService getUtenteService();
    public abstract PrescriptionService getPrescrizioneService();
    public abstract MedicineService getFarmacoService();
    public abstract FarmacoPrescrizioneService getFarmacoPrescrizioneService();


}
