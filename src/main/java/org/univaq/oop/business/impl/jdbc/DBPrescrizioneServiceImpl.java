package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.domain.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBPrescrizioneServiceImpl implements PrescriptionService {
    private static final String GET_USER_PRESCRIZIONI = "select * from prescrizione where utente_id=?";
    private static final String GET_MEDICO_PRESCRIZIONI = "select * from prescrizione where medico_id=? and f_evaso=false";
    private static final String SELECT_ALL = "select * from prescrizione where f_evaso=false";
    private static final String PRESCRIZIONE_WITH_FARMACI = "select * from farmaco_prescrizione where prescrizione_id=?";
    private static final String SELECT_FROM_PRESCRIZIONE_WHERE_ID = "select * from prescrizione where id=?";
    private static final String CREATE_PRESCRIZIONE ="insert into prescrizione (descrizione, medico_id, utente_id) values (?,?,?)";
    private static final String UPDATE_PRESCRIZIONE ="update prescrizione set f_evaso=true where id=?";
    private static final String DELETE_PRESCRIZIONE = "delete from prescrizione where id=?";
    private static final String DELETE_FARMACO_FROM_PRESCRIZIONE = "delete from farmaco_prescrizione where farmaco_id=? and prescrizione_id=?";
    private static final String INSERT_FARMACO_IN_PRESCRIZIONE = "insert into farmaco_prescrizione(farmaco_id, prescrizione_id, quantity) values (?, ?, ?)";
    private static final String UPDATE_FARMACO_QUANTITY_IN_FARMACO_PRESCRIZIONE = "update farmaco_prescrizione set quantity=? where prescrizione_id=? and farmaco_id=?";

    @Override
    public List<Prescription> findAllPrescrizioni() throws BusinessException {
        return null;
    }

    @Override
    public List<Prescription> findToEvadePrescriptions() throws BusinessException {
        return null;
    }

    @Override
    public List<Prescription> findPrescrizioniByPatient(int id) throws BusinessException {
        List<Prescription> prescrizioni = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_PRESCRIZIONI);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                prescrizioni.add(mapTo(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return prescrizioni;
    }

    @Override
    public List<Prescription> findPrescrizioniByMedico(int id) throws BusinessException {
        return null;
    }

    @Override
    public Prescription createPrescrizione(Prescription prescrizione) throws BusinessException {
        return null;
    }

    @Override
    public void updatePrescrizione(Prescription prescrizione) throws BusinessException {

    }

    @Override
    public void evadiPrescrizione(Prescription prescrizione) {

    }

    @Override
    public void deletePrescrizione(int codice) {

    }

    protected Prescription mapTo(ResultSet resultSet) {
        Prescription prescrizione = null;
        try {
            prescrizione = new Prescription(
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
