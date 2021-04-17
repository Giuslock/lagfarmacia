package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.domain.Prescription;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FilePrescriptionServiceImpl implements PrescriptionService {

    private final String prescrizioniFileName;

    public FilePrescriptionServiceImpl(String prescrizioniFileName) {
        this.prescrizioniFileName = prescrizioniFileName;
    }

    @Override
    public List<Prescription> findAllPrescrizioni() throws BusinessException {
        List<Prescription> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for (String[] colonne : fileData.getRighe()) {
                Prescription prescrizione = new Prescription();
                prescrizione.setId((long) Integer.parseInt(colonne[0]));
                prescrizione.setEvaded(Boolean.parseBoolean(colonne[1]));
                prescrizione.setDescription(colonne[2]);
                prescrizione.setDoctorId(Integer.parseInt(colonne[3]));
                prescrizione.setUserId(Integer.parseInt(colonne[4]));
                result.add(prescrizione);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return result;
    }

    @Override
    public List<Prescription> findToEvadePrescriptions() throws BusinessException {
        List<Prescription> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for (String[] colonne : fileData.getRighe()) {
                Prescription prescrizione = new Prescription();
                if (!Boolean.parseBoolean(colonne[1])) {
                    prescrizione.setId((long) Integer.parseInt(colonne[0]));
                    prescrizione.setEvaded(Boolean.parseBoolean(colonne[1]));
                    prescrizione.setDescription(colonne[2]);
                    prescrizione.setDoctorId(Integer.parseInt(colonne[3]));
                    prescrizione.setUserId(Integer.parseInt(colonne[4]));
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
    public List<Prescription> findPrescrizioniByPatient(int id) throws BusinessException {

        List<Prescription> result = new ArrayList<>();
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (Integer.parseInt(colonne[4]) == id) {
                    Prescription prescrizione = new Prescription();
                    prescrizione.setId((long) Integer.parseInt(colonne[0]));
                    prescrizione.setEvaded(Boolean.parseBoolean(colonne[1]));
                    prescrizione.setDescription(colonne[2]);
                    prescrizione.setDoctorId(Integer.parseInt(colonne[3]));
                    prescrizione.setUserId(Integer.parseInt(colonne[4]));
                    prescrizione.setState();
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
    public List<Prescription> findPrescrizioniByMedico(int id) throws BusinessException {
        return null;
    }

    @Override
    public void addPrescrizione(Prescription prescrizione) throws BusinessException {

        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            try (PrintWriter writer = new PrintWriter(prescrizioniFileName)) {
                long counter = fileData.getContatore();
                writer.println(counter + 1);
                for (String[] righe : fileData.getRighe()) {
                    writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
                }
                StringBuilder row = new StringBuilder();
                row.append(prescrizione.getId());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizione.isEvaded());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizione.getDescription());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizione.getDoctorId());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(prescrizione.getUserId());
                writer.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);

        }

    }

    @Override
    public void updatePrescrizione(Prescription prescrizione) throws BusinessException {
        try {
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            try (PrintWriter writer = new PrintWriter(prescrizioniFileName)) {
                writer.println(fileData.getContatore());
                for (String[] righe : fileData.getRighe()) {
                    if (Long.parseLong(righe[0]) == prescrizione.getId()) {
                        StringBuilder row = new StringBuilder();
                        row.append(prescrizione.getId());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizione.isEvaded());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizione.getDescription());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizione.getDoctorId());
                        row.append(Utility.SEPARATORE_COLONNA);
                        row.append(prescrizione.getUserId());
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
    public void evadiPrescrizione(Prescription prescrizione) {

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
