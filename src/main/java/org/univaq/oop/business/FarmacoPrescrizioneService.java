package org.univaq.oop.business;

import org.univaq.oop.domain.Medicine;

import java.util.Map;

public interface FarmacoPrescrizioneService {

    Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) throws BusinessException;

    void evadePrescription(int id) throws BusinessException;

}
