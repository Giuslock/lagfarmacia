package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilePrescriptionServiceImpl implements PrescriptionService {

    private String prescrizioniFileName;

    public FilePrescriptionServiceImpl(String prescrizioniFileName) {
        this.prescrizioniFileName = prescrizioniFileName;
    }

    @Override
    public List<Prescription> findAllPrescrizioni() throws BusinessException {
        List<Prescription> result = new ArrayList<>();
        try{
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for(String[] colonne : fileData.getRighe()){
                Prescription prescrizione = new Prescription();
                prescrizione.setId((long) Integer.parseInt(colonne[0]));
                prescrizione.setEvaded(false);
                prescrizione.setDescription(colonne[2]);
                prescrizione.setDoctorId(Integer.parseInt(colonne[3]));
                prescrizione.setUserId(Integer.parseInt(colonne[4]));
                result.add(prescrizione);
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return result;
    }

    @Override
    public List<Prescription> findPrescrizioniByPatient(int id) throws BusinessException {

        List<Prescription> result = new ArrayList<>();
        try{
            FileData fileData = Utility.readAllRows(prescrizioniFileName);
            for(String[] colonne : fileData.getRighe()){
                if(Integer.parseInt(colonne[4]) == id) {
                    Prescription prescrizione = new Prescription();
                    prescrizione.setId(Long.parseLong(colonne[0]));
                    prescrizione.setDoctorId(Integer.parseInt(colonne[3]));
                   ;
                    result.add(prescrizione);
                }
            }
        } catch (IOException e){
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

    }

    @Override
    public void updatePrescrizione(Prescription prescrizione) throws BusinessException {

    }

    @Override
    public void evadiPrescrizione(Prescription prescrizione) {

    }

    @Override
    public void deletePrescrizione(int codice) {

    }


}
