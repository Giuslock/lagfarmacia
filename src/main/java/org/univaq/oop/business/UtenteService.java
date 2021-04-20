package org.univaq.oop.business;

import org.univaq.oop.domain.Utente;

import java.util.List;

public interface UtenteService {

    Utente autenticazione(String username, String password) throws BusinessException;

    List<Utente> userslist();

    void aggiungiUtente(Utente utente) throws BusinessException;

    List<String> findAllPatients() throws BusinessException;

    Utente trovaPazienteDaCodiceFiscale(String fiscalCode) throws BusinessException;

    Utente findPatientById(int id) throws BusinessException;
}
