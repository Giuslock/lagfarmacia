package org.univaq.oop.business;


import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.util.List;

public interface MedicineService {

    List<Medicine> findAllFarmaci() throws BusinessException;

    void addFarmaco(Medicine farmaco) throws BusinessException;

    void updateFarmaco(Medicine farmaco) throws BusinessException;

    Medicine findMedicineById(Integer codice) throws BusinessException;

    void deleteFarmaco(Long codice);

    void aggiornaQtaFarmaco(Prescription prescrizione) throws BusinessException;//, NonCiSonoAbbastanzaFarmaci, FarmacoNonTrovato;

    List<String> findAllFarmaciByNome() throws BusinessException;

    Medicine findFarmacoByName(String string) throws BusinessException;

    List<Medicine> findFarmaciInEsaurimento() throws BusinessException;
}
