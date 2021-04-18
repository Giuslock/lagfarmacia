package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.domain.Prescription;

import java.util.List;

public class DBPrescrizioneServiceImpl implements PrescriptionService {
    @Override
    public List<Prescription> findAllPrescrizioni() throws BusinessException {
        return null;
    }

    @Override
    public List<Prescription> findToEvadePrescriptions() throws BusinessException {
        return null;
    }

    @Override
    public List<Prescription> findPrescrizioniByPatient(int id) throws BusinessException {
        return null;
    }

    @Override
    public List<Prescription> findPrescrizioniByMedico(int id) throws BusinessException {
        return null;
    }

    @Override
    public Prescription createPrescrizione(Prescription prescrizione) throws BusinessException {
        return null;
    }

    @Override
    public void updatePrescrizione(Prescription prescrizione) throws BusinessException {

    }

    @Override
    public void evadiPrescrizione(Prescription prescrizione) {

    }

    @Override
    public void deletePrescrizione(int codice) {

    }
}
