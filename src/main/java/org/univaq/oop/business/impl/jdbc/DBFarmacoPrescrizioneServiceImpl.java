package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoNonTrovato;
import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DBFarmacoPrescrizioneServiceImpl implements FarmacoPrescrizioneService {

    private static final String PRESCRIZIONE_WITH_FARMACI = "select * from farmaco_prescrizione where prescrizione_id=?";
    private static final String SELECT_FARMACO = "select * from farmaco where id=?";
    private static final String DELETE_FARMACO_FROM_PRESCRIZIONE = "delete from farmaco_prescrizione where farmaco_id=? and prescrizione_id=?";
    private static final String INSERT_FARMACO_IN_PRESCRIZIONE = "insert into farmaco_prescrizione(farmaco_id, prescrizione_id, quantity) values (?, ?, ?)";
    private static final String UPDATE_FARMACO_QUANTITY_IN_FARMACO_PRESCRIZIONE = "update farmaco_prescrizione set quantity=? where prescrizione_id=? and farmaco_id=?";


    @Override
    public Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) throws BusinessException {
        Map<Medicine, Integer> farmaciMap = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement(PRESCRIZIONE_WITH_FARMACI);
            statement.setInt(1, prescriptionId.intValue());
            ResultSet resultSet = statement.executeQuery();

            // ResultSet fields -> ["id", "farmaco_id", "prescrizione_id", "quantity"]
            while(resultSet.next()) {
//                Medicine f = this.medicineService.findMedicineById(resultSet.getInt("farmaco_id"));
                Medicine f = new Medicine();
                try (Connection connection2 = DatabaseConnection.getConnection()) {
                    PreparedStatement statement2 = connection2.prepareStatement(SELECT_FARMACO);
                    statement2.setLong(1,resultSet.getLong(2));
                    ResultSet rs = statement2.executeQuery();
                    if(rs.next()){
                        f.setId(rs.getLong("id"));
                        f.setName(rs.getString("nome"));
                        f.setDescription(rs.getString("descrizione"));
                        f.setQuantity(rs.getInt("quantity"));
                        f.setMinimum(rs.getInt("q_min"));
                        f.setOutOfStock();
                        f.setStatoFarmaco();
                    }
                    else{
                        throw new FarmacoNonTrovato();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                farmaciMap.put(f, resultSet.getInt("quantity"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return farmaciMap;
    }

    @Override
    public List<MedicinePrescription> mapToFarmacoPrescrizione(Map<Medicine, Integer> mappaFarmaci) {
        return mappaFarmaci.entrySet()
                .stream()
                .map(entry -> new MedicinePrescription(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getValue())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFarmacoFromPrescrizione(Long id, Long prescrizione_id) {
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_FARMACO_FROM_PRESCRIZIONE);
            statement.setInt(1, id.intValue());
            statement.setInt(2, prescrizione_id.intValue());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insertFarmacoInPrescrizione(Long farmacoId, Long prescrizioneId, int quantity) {
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_FARMACO_IN_PRESCRIZIONE);
            statement.setInt(1, farmacoId.intValue());
            statement.setInt(2, prescrizioneId.intValue());
            statement.setInt(3, quantity);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateFarmacoQuantityInFarmacoPrescrizione(Long farmacoId, Long prescrizioneId, int newQuantity) {
        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_FARMACO_QUANTITY_IN_FARMACO_PRESCRIZIONE);
            statement.setInt(1, newQuantity);
            statement.setInt(2, prescrizioneId.intValue());
            statement.setInt(3, farmacoId.intValue());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public MedicinePrescription farmacoSingoloInFarmacoPrescrizione(Medicine f) {
        return new MedicinePrescription(f.getId(), f.getName(), 1);
    }
}
