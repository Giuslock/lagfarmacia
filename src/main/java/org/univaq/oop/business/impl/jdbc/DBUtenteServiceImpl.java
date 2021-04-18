package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.UserService;
import org.univaq.oop.domain.Role;
import org.univaq.oop.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DBUtenteServiceImpl implements UserService {

    private static final String SELECT_ALL = "select * from utente";
    private static final String SELECT_FROM_UTENTE_WHERE_ID = "select * from utente where id=?";
    private static final String DELETE_FROM_UTENTE_WHERE_ID = "delete from utente where id=?";
    private static final String CREATE_UTENTE = "insert into utente ( nome,cognome,username,password_,ruolo) values  (?,?,?,?,?) ;";
    private static final String UTENTE_WHERE_USERNAME_AND_PASSWORD = "select * from utente where username=? and password_=?";

    @Override
    public User authenticate(String username, String password) throws BusinessException {
        User user = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UTENTE_WHERE_USERNAME_AND_PASSWORD);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = mapTo(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
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

    protected User mapTo(ResultSet resultSet) {
        User user = null;
        try {
            user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    Role.valueOf(resultSet.getString(6).toUpperCase()),
                    resultSet.getString(7)
            );
        } catch (SQLException throwables) {
            System.err.println("ERROR WHILE CONVERTING SQL TO USER OBJECT");
            throwables.printStackTrace();
        }
        return user;
    }

}
