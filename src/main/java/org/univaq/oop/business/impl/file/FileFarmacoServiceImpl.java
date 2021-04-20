package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoInPrescrizioneException;
import org.univaq.oop.business.FarmacoService;
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.Prescrizione;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileFarmacoServiceImpl implements FarmacoService {

    private final String farmacoFileName;
    private final String farmacoPrescrizioneFileName;


    public FileFarmacoServiceImpl(String farmacoFileName, String farmacoPrescrizioneFileName) {
        this.farmacoFileName = farmacoFileName;
        this.farmacoPrescrizioneFileName = farmacoPrescrizioneFileName;
    }

    @Override
    public List<Farmaco> trovaTuttiFarmaci() throws BusinessException {
        List<Farmaco> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(farmacoFileName);
            for (String[] colonne : fileData.getRighe()) {
                Farmaco farmaco = new Farmaco();
                farmaco.setId((long) Integer.parseInt(colonne[0]));
                farmaco.setNome(colonne[1]);
                farmaco.setDescrizione(colonne[2]);
                farmaco.setMinimo(Integer.parseInt(colonne[3]));
                farmaco.setQuantita(Integer.parseInt(colonne[4]));
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
    public void aggiungiFarmaco(Farmaco farmaco) throws BusinessException {
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
                row.append(farmaco.getNome());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(farmaco.getDescrizione());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(farmaco.getMinimo());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(farmaco.getQuantita());
                writer.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);

        }


    }

    @Override
    public void aggiornaFarmaco(Farmaco farmaco) throws BusinessException {
        try {
            FileData fileData = Utility.readAllRows(farmacoFileName);
            try (PrintWriter writer = new PrintWriter(farmacoFileName)) {
                writer.println(fileData.getContatore());
                for (String[] righe : fileData.getRighe()) {
                    if (Long.parseLong(righe[0]) == farmaco.getId()) {
                        StringBuilder row = new StringBuilder();
                        row.append(farmaco.getId());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(farmaco.getNome());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(farmaco.getDescrizione());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(farmaco.getMinimo());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(farmaco.getQuantita());
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
    public Farmaco trovaFarmacoDaId(Integer codice) throws BusinessException {
        Farmaco far = new Farmaco();
        try {
            FileData fileData = Utility.readAllRows(farmacoFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (Integer.parseInt(colonne[0]) == codice) {
                    far.setId((long) Integer.parseInt(colonne[0]));
                    far.setNome(colonne[1]);
                    far.setDescrizione(colonne[2]);
                    far.setMinimo(Integer.parseInt(colonne[3]));
                    far.setQuantita(Integer.parseInt(colonne[4]));
                    far.setOutOfStock();
                    far.setStatoFarmaco();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return far;
    }


    @Override
    public void eliminaFarmaco(Long codice) throws FarmacoInPrescrizioneException {

        FileData prescrizioni = null;
        try {
            prescrizioni = Utility.readAllRows(farmacoPrescrizioneFileName);
            for (String[] colonne : prescrizioni.getRighe()) {
                if(Long.parseLong(colonne[1]) == codice) {
                    throw new FarmacoInPrescrizioneException();} }
        } catch (IOException e) {
            e.printStackTrace();
        }

            try {
            FileData fileData = Utility.readAllRows(farmacoFileName);
            try (PrintWriter writer = new PrintWriter(farmacoFileName)) {
                writer.println(fileData.getContatore() - 1);
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
    public void aggiornaQtaFarmaco(Prescrizione prescrizione) throws BusinessException {

    }

    @Override
    public List<String> findAllFarmaciByNome() throws BusinessException {
        return null;
    }

    @Override
    public Farmaco findFarmacoByName(String string) throws BusinessException {
        return null;
    }

    @Override
    public List<Farmaco> trovaFarmaciInEsaurimento() throws BusinessException {
        List<Farmaco> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(farmacoFileName);
            for (String[] colonne : fileData.getRighe()) {
                Farmaco farmaco = new Farmaco();
                farmaco.setId((long) Integer.parseInt(colonne[0]));
                farmaco.setNome(colonne[1]);
                farmaco.setDescrizione(colonne[2]);
                farmaco.setMinimo(Integer.parseInt(colonne[3]));
                farmaco.setQuantita(Integer.parseInt(colonne[4]));
                farmaco.setOutOfStock();
                farmaco.setStatoFarmaco();
                if (farmaco.getStatoFarmaco().equals("RUNNING OUT"))
                    result.add(farmaco);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return result;
    }
}

