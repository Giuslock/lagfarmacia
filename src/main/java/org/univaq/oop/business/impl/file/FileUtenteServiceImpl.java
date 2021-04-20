package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.UtenteNonTrovato;
import org.univaq.oop.business.UtenteService;
import org.univaq.oop.domain.Ruolo;
import org.univaq.oop.domain.Utente;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileUtenteServiceImpl implements UtenteService {

    String userFileName;

    public FileUtenteServiceImpl(String userFileName) {
        this.userFileName = userFileName;
    }

    @Override
    public Utente autenticazione(String username, String password) throws BusinessException {
        try {
            FileData fileData = Utility.readAllRows(userFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (colonne[3].equals(username) && colonne[4].equals(password)) {
                    Utente utente = new Utente();
                    // colonna[3] identifica il ruolo
                    switch (colonne[5]) {
                        case "ADMIN":
                            utente.setRuolo(Ruolo.AMMINISTRATORE);
                            break;
                        case "PHARMACIST":
                            utente.setRuolo(Ruolo.FARMACISTA);
                            break;
                        case "PATIENT":
                            utente.setRuolo(Ruolo.PAZIENTE);
                            break;
                        case "DOCTOR":
                            utente.setRuolo(Ruolo.DOTTORE);
                            break;
                        default:
                            break;
                    }
                    if (utente != null) {
                        utente.setId((long) Integer.parseInt(colonne[0]));
                        utente.setUsername(username);
                        utente.setPassword(password);
                        utente.setNome(colonne[1]);
                        utente.setCognome(colonne[2]);
                        utente.setCodiceFiscale(colonne[6]);


                    } else {
                        throw new BusinessException("Errore nella lettura del file");
                    }

                    return utente;
                }
            }
            throw new UtenteNonTrovato();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }

    }


    @Override
    public List<Utente> userslist() {
        return null;
    }

    @Override
    public void aggiungiUtente(Utente utente) throws BusinessException {

        try {
            FileData fileData = Utility.readAllRows(userFileName);
            try (PrintWriter writer = new PrintWriter(userFileName)) {
                long contatore = fileData.getContatore();
                writer.println((contatore + 1));
                for (String[] righe : fileData.getRighe()) {
                    writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
                }
                StringBuilder row = new StringBuilder();
                row.append(contatore);
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(utente.getNome());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(utente.getCognome());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(utente.getUsername());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(utente.getPassword());
                row.append(Utility.SEPARATORE_COLONNA);
                if (utente.getRuolo().equals(Ruolo.AMMINISTRATORE)) row.append("ADMIN");
                if (utente.getRuolo().equals(Ruolo.FARMACISTA)) row.append("PHARMACIST");
                if (utente.getRuolo().equals(Ruolo.PAZIENTE)) row.append("PATIENT");
                if (utente.getRuolo().equals(Ruolo.DOTTORE)) row.append("DOCTOR");
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(utente.getCodiceFiscale());
                writer.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }

    }

    @Override
    public List<String> findAllPatients() throws BusinessException {
        return null;
    }

    @Override
    public Utente trovaPazienteDaCodiceFiscale(String fiscalCode) throws BusinessException {

        try {
            FileData fileData = Utility.readAllRows(userFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (colonne[6].equals(fiscalCode)) {
                    Utente utente = new Utente();
                    utente.setId((long) Integer.parseInt(colonne[0]));
                    utente.setNome(colonne[1]);
                    utente.setCognome(colonne[2]);
                    utente.setUsername(colonne[3]);
                    utente.setPassword(colonne[4]);
                    utente.setRuolo(Ruolo.valueOf(colonne[5]));
                    utente.setCodiceFiscale(colonne[6]);
                    return utente;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        throw new UtenteNonTrovato();
    }

    @Override
    public Utente findPatientById(int id) throws BusinessException {
        return null;
    }
}
