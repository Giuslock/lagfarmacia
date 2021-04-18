package org.univaq.oop.business;

import org.univaq.oop.domain.User;

import java.util.List;

public interface UserService {

    User authenticate(String username, String password) throws BusinessException;

    List<User> userslist();

    void addUser(User user) throws BusinessException;

    List<String> findAllPatients() throws BusinessException;

    User findPatientByFiscalCode(String fiscalCode) throws BusinessException;

    User findPatientById(int id) throws BusinessException;
}
