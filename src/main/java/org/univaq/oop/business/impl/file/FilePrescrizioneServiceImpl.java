package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.PrescrizioneService;
import org.univaq.oop.domain.Prescrizione;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FilePrescrizioneServiceImpl implements PrescrizioneService {

    private final String prescrizioniFileName;

    public FilePrescrizioneServiceImpl(String prescrizioniFileName) {
        this.prescrizioniFileName = prescrizioniFileName;
    }

    @Override
    public List<Prescrizione> trovaTuttePrescrizioni() throws BusinessException {
        List<Prescrizione> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for (String[] colonne : fileData.getRighe()) {
                Prescrizione prescrizione = new Prescrizione();
                prescrizione.setId((long) Integer.parseInt(colonne[0]));
                prescrizione.setEvasa(Boolean.parseBoolean(colonne[1]));
                prescrizione.setDescrizione(colonne[2]);
                prescrizione.setCodiceDottore(Integer.parseInt(colonne[3]));
                prescrizione.setCodicePaziente(Integer.parseInt(colonne[4]));
                result.add(prescrizione);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return result;
    }

    @Override
    public List<Prescrizione> trovaPrescrizioniDaEvadere() throws BusinessException {
        List<Prescrizione> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for (String[] colonne : fileData.getRighe()) {
                Prescrizione prescrizione = new Prescrizione();
                if (!Boolean.parseBoolean(colonne[1])) {
                    prescrizione.setId((long) Integer.parseInt(colonne[0]));
                    prescrizione.setEvasa(Boolean.parseBoolean(colonne[1]));
                    prescrizione.setDescrizione(colonne[2]);
                    prescrizione.setCodiceDottore(Integer.parseInt(colonne[3]));
                    prescrizione.setCodicePaziente(Integer.parseInt(colonne[4]));
                    result.add(prescrizione);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return result;
    }


    @Override
    public List<Prescrizione> trovaPrescrizioniDalPaziente(int id) throws BusinessException {

        List<Prescrizione> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (Integer.parseInt(colonne[4]) == id) {
                    Prescrizione prescrizione = new Prescrizione();
                    prescrizione.setId((long) Integer.parseInt(colonne[0]));
                    prescrizione.setEvasa(Boolean.parseBoolean(colonne[1]));
                    prescrizione.setDescrizione(colonne[2]);
                    prescrizione.setCodiceDottore(Integer.parseInt(colonne[3]));
                    prescrizione.setCodicePaziente(Integer.parseInt(colonne[4]));
                    prescrizione.setStato();
                    result.add(prescrizione);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return result;

    }

    @Override
    public List<Prescrizione> trovaPrescrizioniByMedicoDaEvadere(int id) throws BusinessException { List<Prescrizione> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (Integer.parseInt(colonne[3]) == id && colonne[1]=="false") {
                    Prescrizione prescrizione = new Prescrizione();
                    prescrizione.setId((long) Integer.parseInt(colonne[0]));
                    prescrizione.setEvasa(Boolean.parseBoolean(colonne[1]));
                    prescrizione.setDescrizione(colonne[2]);
                    prescrizione.setCodiceDottore(Integer.parseInt(colonne[3]));
                    prescrizione.setCodicePaziente(Integer.parseInt(colonne[4]));
                    prescrizione.setStato();
                    result.add(prescrizione);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return result;


    }

    @Override
    public Prescrizione creaPrescrizione(Prescrizione prescrizione) throws BusinessException {

        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            try (PrintWriter writer = new PrintWriter(prescrizioniFileName)) {
                long counter = fileData.getContatore();
                writer.println(counter + 1);
                for (String[] righe : fileData.getRighe()) {
                    writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
                }
                StringBuilder row = new StringBuilder();
                prescrizione.setId(counter);
                row.append(counter);
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizione.attualmenteEvasa());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizione.getDescrizione());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizione.getCodiceDottore());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizione.getCodicePaziente());
                writer.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);

        }

        return prescrizione;
    }

    @Override
    public void aggiornaPrescrizione(Prescrizione prescrizione) throws BusinessException {
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            try (PrintWriter writer = new PrintWriter(prescrizioniFileName)) {
                writer.println(fileData.getContatore());
                for (String[] righe : fileData.getRighe()) {
                    if (Long.parseLong(righe[0]) == prescrizione.getId()) {
                        StringBuilder row = new StringBuilder();
                        row.append(prescrizione.getId());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizione.attualmenteEvasa());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizione.getDescrizione());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizione.getCodiceDottore());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizione.getCodicePaziente());
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
    public void evadiPrescrizione(Prescrizione prescrizione) {

    }

    @Override
    public void deletePrescrizione(int codice) {

        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            try (PrintWriter writer = new PrintWriter(prescrizioniFileName)) {
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


}
