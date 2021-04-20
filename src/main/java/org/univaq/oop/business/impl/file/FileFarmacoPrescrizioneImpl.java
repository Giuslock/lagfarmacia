package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.FarmacoPrescrizione;

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
    public Map<Farmaco, Integer> ottieniFarmaciDallaPrescrizione(Long prescriptionId) throws BusinessException {

        Map<Farmaco, Integer> result = new HashMap<>();
        try {

            FileData fileData = Utility.readAllRows(farmacoPrescrizioneFileName);
            FileData fileData2 = Utility.readAllRows(farmacoFileName);

            for (String[] colonne : fileData.getRighe()) {
                if (Long.parseLong(colonne[2]) == prescriptionId) {
                    for (String[] colonne2 : fileData2.getRighe()) {
                        if (Integer.parseInt(colonne[1]) == Integer.parseInt(colonne2[0])) {
                            Farmaco farmacoresult = new Farmaco();
                            farmacoresult.setId((long) Integer.parseInt(colonne2[0]));
                            farmacoresult.setNome(colonne2[1]);
                            farmacoresult.setDescrizione(colonne2[2]);
                            farmacoresult.setMinimo(Integer.parseInt(colonne2[3]));
                            farmacoresult.setQuantita(Integer.parseInt(colonne2[4]));
                            farmacoresult.setInEsaurimento();
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
    public List<FarmacoPrescrizione> mappaFarmacoPrescrizione(Map<Farmaco, Integer> mappaFarmaci) {
        return mappaFarmaci.entrySet()
                .stream()
                .map(entry -> new FarmacoPrescrizione(
                        entry.getKey().getId(),
                        entry.getKey().getNome(),
                        entry.getValue())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void eliminaFarmacoDallaPrescrizione(Long id, Long prescrizione_id) {

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
    public void inserisciFarmacoNellaPrescrizione(Long farmacoId, Long prescrizioneId, int quantity) {
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

    @Override
    public void aggiornaQuantitaFarmacoInFarmacoPrescrizione(Long farmacoId, Long prescrizioneId, int newQuantity) {
        try {
            FileData fileData = Utility.readAllRows(farmacoPrescrizioneFileName);
            try (PrintWriter writer = new PrintWriter(farmacoPrescrizioneFileName)) {
                writer.println(fileData.getContatore());
                for (String[] righe : fileData.getRighe()) {
                    if (Long.parseLong(righe[1]) == farmacoId && Long.parseLong(righe[2]) == prescrizioneId) {
                        StringBuilder row = new StringBuilder();
                        String temp = righe[0];
                        row.append(temp);
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(farmacoId);
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizioneId);
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(newQuantity);
                        writer.println(row);
                    } else {
                        writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public FarmacoPrescrizione farmacoSingoloInFarmacoPrescrizione(Farmaco f) {
        return new FarmacoPrescrizione(f.getId(), f.getNome(), 1);
    }


}
