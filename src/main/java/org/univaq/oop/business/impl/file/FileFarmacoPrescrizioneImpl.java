package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileFarmacoPrescrizioneImpl implements FarmacoPrescrizioneService {


    private final String farmacoPrescrizioneFileName;
    private final String farmacoFileName;
    private final String prescrizioneFileName;


    public FileFarmacoPrescrizioneImpl(String farmacoPrescrizioneFileName, String farmacoFileName, String prescrizioneFileName) {
        this.farmacoPrescrizioneFileName = farmacoPrescrizioneFileName;
        this.farmacoFileName = farmacoFileName;
        this.prescrizioneFileName = prescrizioneFileName;
    }

    @Override
    public Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) throws BusinessException {

        Map<Medicine, Integer> result = new HashMap<>();
        try {

            FileData fileData = Utility.readAllRows(farmacoPrescrizioneFileName);
            FileData fileData2 = Utility.readAllRows(farmacoFileName);

            for (String[] colonne : fileData.getRighe()) {
                if (Long.parseLong(colonne[2]) == prescriptionId) {
                    for (String[] colonne2 : fileData2.getRighe()) {
                        if (Integer.parseInt(colonne[1]) == Integer.parseInt(colonne2[0])) {
                            Medicine farmacoresult = new Medicine();
                            farmacoresult.setId((long) Integer.parseInt(colonne2[0]));
                            farmacoresult.setName(colonne2[1]);
                            farmacoresult.setDescription(colonne2[2]);
                            farmacoresult.setMinimum(Integer.parseInt(colonne2[3]));
                            farmacoresult.setQuantity(Integer.parseInt(colonne2[4]));
                            farmacoresult.setOutOfStock();
                            farmacoresult.setStatoFarmaco();
                            result.put(farmacoresult, Integer.parseInt(colonne[3]));
                        }
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<MedicinePrescription> mapToFarmacoPrescrizione(Map<Medicine, Integer> mappaFarmaci) {
        return mappaFarmaci.entrySet()
                .stream()
                .map(entry -> new MedicinePrescription(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getValue())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFarmacoFromPrescrizione(Long id, Long prescrizione_id) {

        try {
            FileData fileData = Utility.readAllRows(farmacoPrescrizioneFileName);
            try (PrintWriter writer = new PrintWriter(farmacoPrescrizioneFileName)) {
                writer.println(fileData.getContatore() - 1);
                boolean trovato = false;
                for (String[] colonne : fileData.getRighe()) {

                    if (colonne[0].equals(String.valueOf(id)) && colonne[2].equals(String.valueOf(prescrizione_id))) {
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
    public void insertFarmacoInPrescrizione(Long farmacoId, Long prescrizioneId, int quantity) {
        try {
            FileData fileData = Utility.readAllRows(farmacoPrescrizioneFileName);
            try (PrintWriter writer = new PrintWriter(farmacoPrescrizioneFileName)) {
                long counter = fileData.getContatore();
                writer.println(counter + 1);
                for (String[] righe : fileData.getRighe()) {
                    writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
                }
                StringBuilder row = new StringBuilder();
                row.append(counter);
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(farmacoId);
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizioneId);
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(quantity);
                writer.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
