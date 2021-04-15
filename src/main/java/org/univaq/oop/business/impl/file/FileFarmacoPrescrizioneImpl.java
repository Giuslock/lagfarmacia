package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FileFarmacoPrescrizioneImpl implements FarmacoPrescrizioneService {

    private String farmacoPrescrizioneFileName;
    private String farmacoFileName;
    private String prescrizioneFileName;
    private MedicineService medicineService;


    public FileFarmacoPrescrizioneImpl(String farmacoPrescrizioneFileName, String farmacoFileName, String prescrizioneFileName) {
        this.farmacoPrescrizioneFileName = farmacoPrescrizioneFileName;
        this.farmacoFileName = farmacoFileName;
        this.prescrizioneFileName = prescrizioneFileName;
    }

    @Override
    public Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) throws BusinessException {
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
    public void evadePrescription(int id) throws BusinessException {
        try {
            FileData fileData = Utility.readAllRows(farmacoPrescrizioneFileName);
            FileData fileData2 = Utility.readAllRows(farmacoFileName);
            FileData fileData3 = Utility.readAllRows(prescrizioneFileName);

            Cavallo:
            for (String[] colonne : fileData.getRighe()) {
                if (id == Integer.parseInt(colonne[2])){
                    for (String[] colonne2 : fileData2.getRighe()) {
                        if (Integer.parseInt(colonne[1]) == Integer.parseInt(colonne2[0])) {
                            Medicine modifica = new Medicine();
                            modifica.setId(Long.parseLong(colonne2[0]));
                            modifica.setName(colonne2[1]);
                            modifica.setDescription((colonne2[2]));
                            modifica.setMinimum(Integer.parseInt(colonne2[3]));
                            int sum = 0;
                            sum = Integer.parseInt(colonne2[4]) - Integer.parseInt(colonne[3]);
                            if (sum < 0) {
                                System.out.println("non abbastanza farmaci");
                                break Cavallo;
                            }
                            modifica.setQuantity(sum);
//                            try (PrintWriter writer = new PrintWriter(farmacoFileName)) {
//                                writer.println(fileData2.getContatore());
//                                  for (String[] righe : fileData2.getRighe()) {
//                                    if (Long.parseLong(righe[0]) == modifica.getId())
//                                    {
//                                        StringBuilder row = new StringBuilder();
//                                        row.append(modifica.getId());
//                                        row.append(Utility.SEPARATORE_COLONNA);
//                                        row.append(modifica.getName());
//                                        row.append(Utility.SEPARATORE_COLONNA);
//                                        row.append(modifica.getDescription());
//                                        row.append(Utility.SEPARATORE_COLONNA);
//                                        row.append(modifica.getMinimum());
//                                        row.append(Utility.SEPARATORE_COLONNA);
//                                        row.append(modifica.getQuantity());
//                                        writer.println(row);
//                                    } else {
//                                        writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
//                                    }
//                                }
//                            }
                        }
                    }}
//                    for (String[] colonne3 : fileData3.getRighe()){
//                     DA IMPLEMENTARE UPDATE PRESCRIZIONE
//                    }


            }
        }
          catch (IOException e) {
            e.printStackTrace();

        }



    }
}
