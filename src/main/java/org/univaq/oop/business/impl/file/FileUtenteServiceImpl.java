package org.univaq.oop.business.impl.file;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.UserNotFoundException;
import org.univaq.oop.business.UserService;
import org.univaq.oop.domain.Role;
import org.univaq.oop.domain.User;

import java.io.IOException;
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
                    User utente = null;
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
                        utente.setUsername(username); //colonne[1]
                        utente.setPassword(password); //colonne[2]
                        //in colonne[3] c'è il ruolo
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
