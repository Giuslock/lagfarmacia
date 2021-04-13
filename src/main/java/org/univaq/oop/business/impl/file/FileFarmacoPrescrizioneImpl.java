package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileFarmacoPrescrizioneImpl implements FarmacoPrescrizioneService {

    private String farmacoPrescrizioneFileName;
    private String farmacoFileName;

    public FileFarmacoPrescrizioneImpl(String farmacoPrescrizioneFileName, String farmacoFileName) {
        this.farmacoPrescrizioneFileName = farmacoPrescrizioneFileName;
        this.farmacoFileName = farmacoFileName;
    }

    @Override
    public Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) {
        Map<Medicine,Integer> result = new HashMap<>();
        try{

            FileData fileData = Utility.readAllRows(farmacoPrescrizioneFileName);
            FileData fileData2 = Utility.readAllRows(farmacoFileName);

            for(String[] colonne : fileData.getRighe()){
                if(Long.parseLong(colonne[2]) == prescriptionId){
                    for (String[] colonne2 : fileData2.getRighe()){
                        if (Integer.parseInt(colonne[1]) == Integer.parseInt(colonne2[0])){
                            Medicine farmacoresult = new Medicine();
                            farmacoresult.setId((long) Integer.parseInt(colonne2[0]));
                            farmacoresult.setName(colonne2[1]);
                            farmacoresult.setDescription(colonne2[2]);
                            farmacoresult.setMinimum(Integer.parseInt(colonne2[3]));
                            farmacoresult.setQuantity(Integer.parseInt(colonne2[4]));
                            farmacoresult.setOutOfStock();
                            farmacoresult.setStatoFarmaco();
                            result.put(farmacoresult,Integer.parseInt(colonne[3]));
                        }
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    return result;}

    @Override
    public void evadePrescription(Prescription prescription) {

    }
}
