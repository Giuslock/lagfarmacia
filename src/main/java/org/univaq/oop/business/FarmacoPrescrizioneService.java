package org.univaq.oop.business;

import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.util.Map;

public interface FarmacoPrescrizioneService {

    Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId);

    void evadePrescription(Prescription prescription);

}
