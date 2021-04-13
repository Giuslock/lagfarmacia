package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;


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
        try {
            FileData fileData = Utility.readAllRows(farmacoFileName);
            for (String[] colonne : fileData.getRighe()) {
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
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }

        return result;
    }

    @Override
    public void addFarmaco(Medicine medicine) throws BusinessException {
        try {
            FileData fileData = Utility.readAllRows(farmacoFileName);
            try (PrintWriter writer = new PrintWriter(new File(farmacoFileName))) {
                long counter = fileData.getContatore();
                writer.println((counter + 1));
                for (String[] righe : fileData.getRighe()) {
                    writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
                }
                StringBuilder row = new StringBuilder();
                row.append(counter);
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(medicine.getName());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(medicine.getDescription());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(medicine.getMinimum());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(medicine.getQuantity());
                writer.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);

        }


    }

    @Override
    public void updateFarmaco(Medicine medicine) throws BusinessException {
        //System.out.println("cavallo");
        try {
            FileData fileData = Utility.readAllRows(farmacoFileName);
            try (PrintWriter writer = new PrintWriter(new File(farmacoFileName))) {
                writer.println(fileData.getContatore());
                for (String[] righe : fileData.getRighe()) {
                    if (Long.parseLong(righe[0]) == medicine.getId())
                    {
                        StringBuilder row = new StringBuilder();
                        row.append(medicine.getId());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(medicine.getName());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(medicine.getDescription());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(medicine.getQuantity());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(medicine.getMinimum());
                        row.append(Utility.SEPARATORE_COLONNA);
                        writer.println(row);
                    } else {
                        writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
    }

    @Override
    public Medicine findFarmacoByCodice(Integer codice) throws BusinessException {
        return null;
    }

    @Override
    public void deleteFarmaco(Long codice) {
        try {
        FileData fileData = Utility.readAllRows(farmacoFileName);
        try (PrintWriter writer = new PrintWriter(new File(farmacoFileName))) {
            //decremento contatore e scrivo il nuovo numero
            writer.println(fileData.getContatore()-1);
            //uso una variabile booleana per sapere se ho trovato la riga da eliminare e poi scrivo le righe con il numero aggiornato
            boolean trovato = false;
            for (String[] colonne : fileData.getRighe()) {

                if (colonne[0].equals(String.valueOf(codice))) {
                    trovato = true;
                    continue;
                }
                if (trovato) {
                    colonne[0] = Integer.toString(Integer.parseInt(colonne[0]) - 1);
                }
                writer.println(String.join(Utility.SEPARATORE_COLONNA, colonne));
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

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
                if (farmaco.getMedicineStatus().equals("RUNNING OUT"))
                    result.add(farmaco);
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return result;
    }
}








   // @Override
/*    public Medicine findMedicinebyid(Long id) throws BusinessException {

        return null;
    }

    @Override
    public void deleteFarmaco(Long ) {

    }

    @Override
    public void aggiornaQtaFarmaco(Prescription prescription) throws BusinessException {

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
    }*/

