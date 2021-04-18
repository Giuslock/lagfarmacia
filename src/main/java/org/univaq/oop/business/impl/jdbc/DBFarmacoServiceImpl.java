package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.util.List;

public class DBFarmacoServiceImpl implements MedicineService {
    @Override
    public List<Medicine> findAllFarmaci() throws BusinessException {
        return null;
    }

    @Override
    public void addFarmaco(Medicine farmaco) throws BusinessException {

    }

    @Override
    public void updateFarmaco(Medicine farmaco) throws BusinessException {

    }

    @Override
    public Medicine findMedicineById(Integer codice) throws BusinessException {
        return null;
    }

    @Override
    public void deleteFarmaco(Long codice) {

    }

    @Override
    public void aggiornaQtaFarmaco(Prescription prescrizione) throws BusinessException {

    }

    @Override
    public List<String> findAllFarmaciByNome() throws BusinessException {
        return null;
    }

    @Override
    public Medicine findFarmacoByName(String string) throws BusinessException {
        return null;
    }

    @Override
    public List<Medicine> findFarmaciInEsaurimento() throws BusinessException {
        return null;
    }
}
