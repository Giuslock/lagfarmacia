package org.univaq.oop.business;

import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface FarmacoPrescrizioneService {

    Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) throws BusinessException;

    List<MedicinePrescription> mapToFarmacoPrescrizione(Map<Medicine, Integer> mappaFarmaci);

    void deleteFarmacoFromPrescrizione(Long id, Long prescrizione_id);

    void insertFarmacoInPrescrizione(Long farmacoId, Long prescrizioneId, int quantity);

    void updateFarmacoQuantityInFarmacoPrescrizione(Long farmacoId, Long prescrizioneId, int newQuantity);

    MedicinePrescription farmacoSingoloInFarmacoPrescrizione(Medicine f);

}
