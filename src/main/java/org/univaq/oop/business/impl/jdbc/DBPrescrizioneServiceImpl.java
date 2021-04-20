package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.PrescrizioneNonTrovata;
import org.univaq.oop.business.PrescrizioneService;
import org.univaq.oop.domain.Prescrizione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPrescrizioneServiceImpl implements PrescrizioneService {
    private static final String GET_USER_PRESCRIZIONI = "select * from prescrizione where utente_id=?";
    private static final String GET_MEDICO_PRESCRIZIONI = "select * from prescrizione where medico_id=? and f_evaso=false";
    private static final String GET_PRESCRIZIONI_DA_EVADERE = "select * from prescrizione where f_evaso=false";
    private static final String SELECT_ALL = "select * from prescrizione";
    private static final String PRESCRIZIONE_WITH_FARMACI = "select * from farmaco_prescrizione where prescrizione_id=?";
    private static final String SELECT_FROM_PRESCRIZIONE_WHERE_ID = "select * from prescrizione where id=?";
    private static final String CREATE_PRESCRIZIONE = "insert into prescrizione (descrizione, medico_id, utente_id) values (?,?,?)";
    private static final String UPDATE_PRESCRIZIONE = "update prescrizione set f_evaso=true where id=?";
    private static final String DELETE_PRESCRIZIONE = "delete from prescrizione where id=?";
    private static final String DELETE_FARMACO_FROM_PRESCRIZIONE = "delete from farmaco_prescrizione where farmaco_id=? and prescrizione_id=?";
    private static final String INSERT_FARMACO_IN_PRESCRIZIONE = "insert into farmaco_prescrizione(farmaco_id, prescrizione_id, quantity) values (?, ?, ?)";
    private static final String UPDATE_FARMACO_QUANTITY_IN_FARMACO_PRESCRIZIONE = "update farmaco_prescrizione set quantity=? where prescrizione_id=? and farmaco_id=?";

    @Override
    public List<Prescrizione> trovaTuttePrescrizioni() throws BusinessException {
        List<Prescrizione> prescrizioni = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }

        } catch (SQLException throwables) {
            throw new PrescrizioneNonTrovata();
        }
        return prescrizioni;
    }

    @Override
    public List<Prescrizione> trovaPrescrizioniDaEvadere() throws BusinessException {
        List<Prescrizione> prescrizioni = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_PRESCRIZIONI_DA_EVADERE);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return prescrizioni;
    }

    @Override
    public List<Prescrizione> trovaPrescrizioniDalPaziente(int id) throws BusinessException {
        List<Prescrizione> prescrizioni = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_PRESCRIZIONI);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }
            for (Prescrizione p:prescrizioni) {
                p.setStato();

            }//setto lo stato per ogni elemento della lista delle prescrizioni

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return prescrizioni;
    }

    @Override
    public List<Prescrizione> trovaPrescrizioniByMedicoDaEvadere(int id) throws BusinessException {
        List<Prescrizione> prescrizioni = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_MEDICO_PRESCRIZIONI);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }
            for (Prescrizione p:prescrizioni) {
                p.setStato();

            }//setto lo stato per ogni elemento della lista delle prescrizioni

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return prescrizioni;

    }

    @Override
    public Prescrizione creaPrescrizione(Prescrizione prescrizione) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_PRESCRIZIONE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, prescrizione.getDescrizione());
            statement.setInt(2, prescrizione.getCodiceDottore());
            statement.setInt(3, prescrizione.getCodicePaziente());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                prescrizione.setId(rs.getLong(1));
            }

        } catch (SQLException throwables) {
            throw new PrescrizioneNonTrovata();
        }

        return prescrizione;
    }

    @Override
    public void aggiornaPrescrizione(Prescrizione prescrizione) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRESCRIZIONE);
            statement.setInt(1, prescrizione.getId().intValue());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new PrescrizioneNonTrovata();
        }
    }

    @Override
    public void evadiPrescrizione(Prescrizione prescrizione) {

    }

    @Override
    public void deletePrescrizione(int codice) {

    }

    protected Prescrizione mapTo(ResultSet resultSet) {
        Prescrizione prescrizione = null;
        try {
            prescrizione = new Prescrizione(
                    resultSet.getLong("id"),
                    resultSet.getBoolean("f_evaso"),
                    resultSet.getString("descrizione"),
                    resultSet.getInt("medico_id"),
                    resultSet.getInt("utente_id")

            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return prescrizione;
    }

}
