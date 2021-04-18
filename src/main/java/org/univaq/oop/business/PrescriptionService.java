package org.univaq.oop.business;


import org.univaq.oop.domain.Prescription;

import java.util.List;

public interface PrescriptionService {
    List<Prescription> findAllPrescrizioni() throws BusinessException;

    List<Prescription> findToEvadePrescriptions() throws BusinessException;

    List<Prescription> findPrescrizioniByPatient(int id) throws BusinessException;

    List<Prescription> findPrescrizioniByMedico(int id) throws BusinessException;

    Prescription createPrescrizione(Prescription prescrizione) throws BusinessException;

    void updatePrescrizione(Prescription prescrizione) throws BusinessException;

    void evadiPrescrizione(Prescription prescrizione);

    void deletePrescrizione(int codice);


    //Prescription findPrescrizioneByNumeroEMedico(int num, int id) throws BusinessException, PrescrizioneNonTrovata;

    //Prescription findPrescrizioneByNumeroEPaziente(int num, int id) throws BusinessException, PrescrizioneNonTrovata;


}
