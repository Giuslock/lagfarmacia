package org.univaq.oop.business.impl.file;

import com.mysql.cj.jdbc.result.UpdatableResultSet;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.business.UserService;

import java.io.File;

public class FileLagBusinessFactoryImpl extends LagBusinessFactory{

    private UserService utenteService;
    private MedicineService farmacoService;
    private PrescriptionService prescrizioneService;

    private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "dati";
    private static final String UTENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "utenti.txt";
    private static final String FARMACI_FILE_NAME = REPOSITORY_BASE + File.separator + "farmaci.txt";
    private static final String PRESCRIZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "prescrizioni.txt";

    public FileLagBusinessFactoryImpl() {
        utenteService = new FileUtenteServiceImpl(UTENTI_FILE_NAME);
        farmacoService = new FileMedicineServiceImpl(FARMACI_FILE_NAME);
        prescrizioneService = new FilePrescriptionServiceImpl(PRESCRIZIONI_FILE_NAME);
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
}