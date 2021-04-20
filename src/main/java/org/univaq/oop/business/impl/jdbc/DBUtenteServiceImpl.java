package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.UtenteNonTrovato;
import org.univaq.oop.business.UtenteService;
import org.univaq.oop.domain.Ruolo;
import org.univaq.oop.domain.Utente;

import java.sql.*;
import java.util.List;

public class DBUtenteServiceImpl implements UtenteService {


    private static final String SELECT_ALL = "select * from utente";
    private static final String SELECT_FROM_UTENTE_WHERE_ID = "select * from utente where id=?";
    private static final String SELEZIONA_UTENTE_TRAMITE_FISCALCODE = "select * from utente where codicefiscale=?";
    private static final String DELETE_FROM_UTENTE_WHERE_ID = "delete from utente where id=?";
    private static final String AGGIUNGI_UTENTE = "insert into utente ( nome,cognome,username,password_,ruolo,codicefiscale) values  (?,?,?,?,?,?) ;";
    private static final String SELEZIONA_UTENTE_DA_USERNAME_E_PASSWORD = "select * from utente where username=? and password_=?";

    @Override
    public Utente autenticazione(String username, String password) throws BusinessException {
        Utente utente = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELEZIONA_UTENTE_DA_USERNAME_E_PASSWORD);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utente = mapTo(resultSet);
            } else {
                throw new UtenteNonTrovato();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return utente;
    }

    @Override
    public List<Utente> userslist() {
        return null;
    }

    @Override
    public void aggiungiUtente(Utente utente) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(AGGIUNGI_UTENTE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, utente.getNome());
            statement.setString(2, utente.getCognome());
            statement.setString(3, utente.getUsername());
            statement.setString(4, utente.getPassword());
            statement.setString(5, utente.getRuolo().toString());
            statement.setString(6, utente.getCodiceFiscale());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    public List<String> findAllPatients() throws BusinessException {
        return null;
    }

    @Override
    public Utente trovaPazienteDaCodiceFiscale(String fiscalCode) throws BusinessException {
        Utente utente = new Utente();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELEZIONA_UTENTE_TRAMITE_FISCALCODE);
            statement.setString(1, fiscalCode);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                utente.setId(rs.getLong("id"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password_"));
                utente.setCodiceFiscale(rs.getString("codicefiscale"));
            } else {
                throw new UtenteNonTrovato();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return utente;
    }

    @Override
    public Utente findPatientById(int id) throws BusinessException {
        return null;
    }

    protected Utente mapTo(ResultSet resultSet) {
        Utente utente = null;
        try {
            utente = new Utente(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    Ruolo.valueOf(resultSet.getString(6).toUpperCase()),
                    resultSet.getString(7)
            );
        } catch (SQLException throwables) {
            System.err.println("ERROR WHILE CONVERTING SQL TO USER OBJECT");
            throwables.printStackTrace();
        }
        return utente;
    }

}
