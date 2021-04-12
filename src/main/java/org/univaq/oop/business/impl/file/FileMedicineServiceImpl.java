package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileMedicineServiceImpl implements MedicineService {

    private String farmacoFileName;


    public FileMedicineServiceImpl(String farmacoFileName) {
        this.farmacoFileName = farmacoFileName;
    }

    @Override
    public List<Medicine> findAllFarmaci() throws BusinessException {
        List<Medicine> result = new ArrayList<>();
        try{
            FileData fileData = Utility.readAllRows(farmacoFileName);
            for(String[] colonne : fileData.getRighe()){
                Medicine farmaco = new Medicine();
                farmaco.setId((long) Integer.parseInt(colonne[0]));
                farmaco.setName(colonne[1]);
                farmaco.setDescription(colonne[2]);
                farmaco.setMinimum(Integer.parseInt(colonne[3]));
                farmaco.setQuantity(Integer.parseInt(colonne[4]));
                farmaco.setOutOfStock();
                farmaco.setStatoFarmaco();
                result.add(farmaco);

            }
        } catch (IOException e){
            e.printStackTrace();
            throw new BusinessException(e);
        }

        return result;
    }

    @Override
    public void addFarmaco(Medicine farmaco) throws BusinessException {

    }

    @Override
    public void updateFarmaco(Medicine farmaco) throws BusinessException {

    }

    @Override
    public Medicine findFarmacoByCodice(Integer codice) throws BusinessException {
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
