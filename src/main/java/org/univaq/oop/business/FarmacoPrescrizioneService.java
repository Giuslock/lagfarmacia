package org.univaq.oop.business;

import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.FarmacoPrescrizione;

import java.util.List;
import java.util.Map;

public interface FarmacoPrescrizioneService {

    Map<Farmaco, Integer> ottieniFarmaciDallaPrescrizione(Long prescriptionId) throws BusinessException;

    List<FarmacoPrescrizione> mappaFarmacoPrescrizione(Map<Farmaco, Integer> mappaFarmaci);

    void eliminaFarmacoDallaPrescrizione(Long id, Long prescrizione_id);

    void inserisciFarmacoNellaPrescrizione(Long farmacoId, Long prescrizioneId, int quantity);

    void aggiornaQuantitaFarmacoInFarmacoPrescrizione(Long farmacoId, Long prescrizioneId, int newQuantity);

    FarmacoPrescrizione farmacoSingoloInFarmacoPrescrizione(Farmaco f);

}
