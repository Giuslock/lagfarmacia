package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.business.UserService;

public class FileLagBusinessFactoryImpl extends LagBusinessFactory {

    @Override
    public UserService getUtenteService() {
        return null;
    }

    @Override
    public PrescriptionService getPrescrizioneService() {
        return null;
    }

    @Override
    public MedicineService getFarmacoService() {
        return null;
    }
}
