package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;

import java.util.List;
import java.util.Map;

public class DBFarmacoPrescrizioneServiceImpl implements FarmacoPrescrizioneService {
    @Override
    public Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) throws BusinessException {
        return null;
    }

    @Override
    public List<MedicinePrescription> mapToFarmacoPrescrizione(Map<Medicine, Integer> mappaFarmaci) {
        return null;
    }

    @Override
    public void deleteFarmacoFromPrescrizione(Long id, Long prescrizione_id) {

    }

    @Override
    public void insertFarmacoInPrescrizione(Long farmacoId, Long prescrizioneId, int quantity) {

    }

    @Override
    public void updateFarmacoQuantityInFarmacoPrescrizione(Long farmacoId, Long prescrizioneId, int newQuantity) {

    }

    @Override
    public MedicinePrescription farmacoSingoloInFarmacoPrescrizione(Medicine f) {
        return null;
    }
}
