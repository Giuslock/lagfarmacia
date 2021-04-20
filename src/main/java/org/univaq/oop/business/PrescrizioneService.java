package org.univaq.oop.business;


import org.univaq.oop.domain.Prescrizione;

import java.util.List;

public interface PrescrizioneService {
    List<Prescrizione> trovaTuttePrescrizioni() throws BusinessException;

    List<Prescrizione> trovaPrescrizioniDaEvadere() throws BusinessException;

    List<Prescrizione> trovaPrescrizioniDalPaziente(int id) throws BusinessException;

    List<Prescrizione> trovaPrescrizioniByMedicoDaEvadere(int id) throws BusinessException;

    Prescrizione creaPrescrizione(Prescrizione prescrizione) throws BusinessException;

    void aggiornaPrescrizione(Prescrizione prescrizione) throws BusinessException;

    void evadiPrescrizione(Prescrizione prescrizione);

    void deletePrescrizione(int codice);


    //Prescription findPrescrizioneByNumeroEMedico(int num, int id) throws BusinessException, PrescrizioneNonTrovata;

    //Prescription findPrescrizioneByNumeroEPaziente(int num, int id) throws BusinessException, PrescrizioneNonTrovata;


}
