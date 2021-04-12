package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.UserNotFoundException;
import org.univaq.oop.business.UserService;
import org.univaq.oop.domain.Role;
import org.univaq.oop.domain.User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileUtenteServiceImpl implements UserService {

    String userFileName;

    public FileUtenteServiceImpl(String userFileName) {
        this.userFileName = userFileName;
    }

    @Override
    public User authenticate(String username, String password) throws UserNotFoundException, BusinessException {
        try {
            FileData fileData = Utility.readAllRows(userFileName);
            for (String[] colonne : fileData.getRighe()) {
                if (colonne[3].equals(username) && colonne[4].equals(password)) {
                    User utente = new User();
                    // colonna[3] identifica il ruolo
                    switch (colonne[5]) {
                        case "ADMIN":
                            utente.setRole(Role.ADMIN);
                            break;
                        case "PHARMACIST":
                            utente.setRole(Role.PHARMACIST);
                            break;
                        case "PATIENT":
                            utente.setRole(Role.PATIENT);
                            break;
                        case "DOCTOR":
                            utente.setRole(Role.DOCTOR);
                            break;
                        default:
                            break;
                    }
                    if (utente != null) {
                        utente.setId((long) Integer.parseInt(colonne[0]));
                        utente.setUsername(username);
                        utente.setPassword(password);
                        utente.setName(colonne[1]);
                        utente.setSurname(colonne[2]);
                        utente.setFiscalCode(colonne[6]);


                    } else {
                        throw new BusinessException("Errore nella lettura del file");
                    }

                    return utente;
                }
            }
            throw new UserNotFoundException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }

    }


    @Override
    public List<User> userslist() {
        return null;
    }

    @Override
    public void addUser(User user) throws BusinessException {

        try{
            FileData fileData = Utility.readAllRows(userFileName);
            try (PrintWriter writer = new PrintWriter(new File(userFileName))){
                long contatore = fileData.getContatore();
                writer.println((contatore + 1));
                for (String[] righe : fileData.getRighe()) {
                    writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
                }
                StringBuilder row = new StringBuilder();
                row.append(contatore);
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(user.getName());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(user.getSurname());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(user.getUsername());
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(user.getPassword());
                row.append(Utility.SEPARATORE_COLONNA);
                if(user.getRole().equals(Role.ADMIN)) row.append("ADMIN");
                if(user.getRole().equals(Role.PHARMACIST)) row.append("PHARMACIST");
                if(user.getRole().equals(Role.PATIENT)) row.append("PATIENT");
                if(user.getRole().equals(Role.DOCTOR)) row.append("DOCTOR");
                row.append(Utility.SEPARATORE_COLONNA);
                row.append(user.getFiscalCode());
                row.append(Utility.SEPARATORE_COLONNA);



                writer.println(row.toString());
            } //chiude try interno
        }catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }


    }

    @Override
    public List<String> findAllPatients() throws BusinessException {
        return null;
    }

    @Override
    public User findPatientByFiscalCode(String fiscalCode) throws BusinessException {
        return null;
    }

    @Override
    public User findPatientById(int id) throws BusinessException {
        return null;
    }
}
