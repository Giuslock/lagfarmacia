package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.util.List;
import java.util.Map;

public class FilePrescriptionServiceImpl implements PrescriptionService {

    private String prescrizioniFileName;

    public FilePrescriptionServiceImpl(String prescrizioniFileName) {
        this.prescrizioniFileName = prescrizioniFileName;
    }

    @Override
    public List<Prescription> findAllPrescrizioni() throws BusinessException {
        return null;
    }

    @Override
    public List<Prescription> findPrescrizioniByPaziente(int id) throws BusinessException {
        return null;
    }

    @Override
    public List<Prescription> findPrescrizioniByMedico(int id) throws BusinessException {
        return null;
    }

    @Override
    public void addPrescrizione(Prescription prescrizione) throws BusinessException {

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

    @Override
    public Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) {
        return null;
    }
}
