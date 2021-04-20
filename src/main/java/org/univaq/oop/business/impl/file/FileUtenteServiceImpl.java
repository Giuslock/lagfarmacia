package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.CodiceFiscaleException;
import org.univaq.oop.business.UtenteNonTrovatoException;
import org.univaq.oop.business.UtenteService;
import org.univaq.oop.domain.Ruolo;
import org.univaq.oop.domain.Utente;

import java.io.IOException;
import java.io.PrintWriter;

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
                    switch (colonne[5]) {
                        case "AMMINISTRATORE":
                            utente.setRuolo(Ruolo.AMMINISTRATORE);
                            break;
                        case "FARMACISTA":
                            utente.setRuolo(Ruolo.FARMACISTA);
                            break;
                        case "PAZIENTE":
                            utente.setRuolo(Ruolo.PAZIENTE);
                            break;
                        case "DOTTORE":
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


                    }
                    return utente;
                }
            }
            throw new UtenteNonTrovatoException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }

    }


    @Override
    public void aggiungiUtente(Utente utente) throws BusinessException {

        try {
            FileData fileData = Utility.readAllRows(userFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (colonne[6].equals(utente.getCodiceFiscale())) {
                    throw new CodiceFiscaleException();
                }
            }
        } catch (IOException e) {
          e.printStackTrace();
        }


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
                if (utente.getRuolo().equals(Ruolo.AMMINISTRATORE)) row.append("AMMINISTRATORE");
                if (utente.getRuolo().equals(Ruolo.FARMACISTA)) row.append("FARMACISTA");
                if (utente.getRuolo().equals(Ruolo.PAZIENTE)) row.append("PAZIENTE");
                if (utente.getRuolo().equals(Ruolo.DOTTORE)) row.append("DOTTORE");
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(utente.getCodiceFiscale());
                writer.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Utente trovaPazienteDaCodiceFiscale(String codiceFiscale) throws BusinessException {
        Utente utente = new Utente();
        try {
            FileData fileData = Utility.readAllRows(userFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (colonne[6].equals(codiceFiscale)) {

                    utente.setId((long) Integer.parseInt(colonne[0]));
                    utente.setNome(colonne[1]);
                    utente.setCognome(colonne[2]);
                    utente.setUsername(colonne[3]);
                    utente.setPassword(colonne[4]);
                    utente.setRuolo(Ruolo.valueOf(colonne[5]));
                    utente.setCodiceFiscale(colonne[6]);
                    return utente;
                }
            } throw new UtenteNonTrovatoException();

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException();
        }
    }

}
