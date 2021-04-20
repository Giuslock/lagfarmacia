package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.*;

import java.io.File;

public class FileLagBusinessFactoryImpl extends LagBusinessFactory {

    private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "dati";
    private static final String UTENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "utenti.txt";
    private static final String FARMACI_FILE_NAME = REPOSITORY_BASE + File.separator + "farmaci.txt";
    private static final String PRESCRIZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "prescrizioni.txt";
    private static final String FARMACI_PRESCRIZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "farmacoprescrizione.txt";
    private final UtenteService utenteService;
    private final FarmacoService farmacoService;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;

    public FileLagBusinessFactoryImpl() {
        utenteService = new FileUtenteServiceImpl(UTENTI_FILE_NAME);
        farmacoService = new FileFarmacoServiceImpl(FARMACI_FILE_NAME,FARMACI_PRESCRIZIONI_FILE_NAME);
        prescrizioneService = new FilePrescrizioneServiceImpl(PRESCRIZIONI_FILE_NAME);
        farmacoPrescrizioneService = new FileFarmacoPrescrizioneImpl(FARMACI_PRESCRIZIONI_FILE_NAME, FARMACI_FILE_NAME, PRESCRIZIONI_FILE_NAME);


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