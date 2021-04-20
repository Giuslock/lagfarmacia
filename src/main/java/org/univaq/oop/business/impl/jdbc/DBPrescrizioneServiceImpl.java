package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.DatabaseException;
import org.univaq.oop.business.PrescrizioneNonTrovataException;
import org.univaq.oop.business.PrescrizioneService;
import org.univaq.oop.domain.Prescrizione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPrescrizioneServiceImpl implements PrescrizioneService {
    private static final String TROVA_PRESCRIZIONI_DA_ID_UTENTE = "select * from prescrizione where utente_id=?";
    private static final String TROVA_PRESCRIZIONI_PER_MEDICO = "select * from prescrizione where medico_id=? and evasa=false";
    private static final String TROVA_PRESCRIZIONI_DA_EVADERE = "select * from prescrizione where evasa=false";
    private static final String SELEZIONA_TUTTE_LE_PRESCRIZIONI = "select * from prescrizione";
    private static final String PRESCRIZIONE_WITH_FARMACI = "select * from farmaco_prescrizione where prescrizione_id=?";
    private static final String SELECT_FROM_PRESCRIZIONE_WHERE_ID = "select * from prescrizione where id=?";
    private static final String AGGIUNGI_PRESCRIZIONE = "insert into prescrizione (descrizione, medico_id, utente_id) values (?,?,?)";
    private static final String AGGIORNA_PRESCRIZIONE = "update prescrizione set evasa=true where id=?";
    private static final String DELETE_PRESCRIZIONE = "delete from prescrizione where id=?";
    private static final String DELETE_FARMACO_FROM_PRESCRIZIONE = "delete from farmaco_prescrizione where farmaco_id=? and prescrizione_id=?";
    private static final String INSERT_FARMACO_IN_PRESCRIZIONE = "insert into farmaco_prescrizione(farmaco_id, prescrizione_id, quantity) values (?, ?, ?)";
    private static final String UPDATE_FARMACO_QUANTITY_IN_FARMACO_PRESCRIZIONE = "update farmaco_prescrizione set quantity=? where prescrizione_id=? and farmaco_id=?";

    @Override
    public List<Prescrizione> trovaTuttePrescrizioni() throws BusinessException {
        List<Prescrizione> prescrizioni = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELEZIONA_TUTTE_LE_PRESCRIZIONI);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }

        } catch (SQLException throwables) {
            throw new PrescrizioneNonTrovataException();
        }
        return prescrizioni;
    }

    @Override
    public List<Prescrizione> trovaPrescrizioniDaEvadere() throws BusinessException {
        List<Prescrizione> prescrizioni = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(TROVA_PRESCRIZIONI_DA_EVADERE);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }

        } catch (SQLException throwables) {
            throw new DatabaseException();
        }
        return prescrizioni;
    }

    @Override
    public List<Prescrizione> trovaPrescrizioniDalPaziente(int id) throws BusinessException {
        List<Prescrizione> prescrizioni = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(TROVA_PRESCRIZIONI_DA_ID_UTENTE);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }
            for (Prescrizione p:prescrizioni) {
                p.setStato();

            }//setto lo stato per ogni elemento della lista delle prescrizioni

        } catch (SQLException throwables) {
            throw new DatabaseException();
        }

        return prescrizioni;
    }

    @Override
    public List<Prescrizione> trovaPrescrizioniByMedicoDaEvadere(int id) throws BusinessException {
        List<Prescrizione> prescrizioni = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(TROVA_PRESCRIZIONI_PER_MEDICO);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }
            for (Prescrizione p:prescrizioni) {
                p.setStato();

            }//setto lo stato per ogni elemento della lista delle prescrizioni

        } catch (SQLException throwables) {
            throw new DatabaseException();
        }

        return prescrizioni;

    }

    @Override
    public Prescrizione creaPrescrizione(Prescrizione prescrizione) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(AGGIUNGI_PRESCRIZIONE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, prescrizione.getDescrizione());
            statement.setInt(2, prescrizione.getCodiceDottore());
            statement.setInt(3, prescrizione.getCodicePaziente());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                prescrizione.setId(rs.getLong(1));
            }

        } catch (SQLException throwables) {
            throw new DatabaseException();
        }

        return prescrizione;
    }

    @Override
    public void aggiornaPrescrizione(Prescrizione prescrizione) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(AGGIORNA_PRESCRIZIONE);
            statement.setInt(1, prescrizione.getId().intValue());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DatabaseException();
        }
    }

    protected Prescrizione mapTo(ResultSet resultSet) {
        Prescrizione prescrizione = null;
        try {
            prescrizione = new Prescrizione(
                    resultSet.getLong("id"),
                    resultSet.getBoolean("evasa"),
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
