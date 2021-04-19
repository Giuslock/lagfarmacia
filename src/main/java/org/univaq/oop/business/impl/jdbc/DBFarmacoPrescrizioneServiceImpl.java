package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBFarmacoPrescrizioneServiceImpl implements FarmacoPrescrizioneService {

    private static final String PRESCRIZIONE_WITH_FARMACI = "select * from farmaco_prescrizione where prescrizione_id=?";
    private MedicineService medicineService;

    @Override
    public Map<Medicine, Integer> getMedicineFromPrescription(Long prescriptionId) throws BusinessException {
        Map<Medicine, Integer> farmaciMap = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement(PRESCRIZIONE_WITH_FARMACI);
            statement.setInt(1, prescriptionId.intValue());
            ResultSet resultSet = statement.executeQuery();

            // ResultSet fields -> ["id", "farmaco_id", "prescrizione_id", "quantity"]
            while(resultSet.next()) {
                Medicine f = this.medicineService.findMedicineById(resultSet.getInt("farmaco_id"));
                farmaciMap.put(f, resultSet.getInt("quantity"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return farmaciMap;
    }

    @Override
    public List<MedicinePrescription> mapToFarmacoPrescrizione(Map<Medicine, Integer> mappaFarmaci) {
        return null;
    }

    @Override
    public void deleteFarmacoFromPrescrizione(Long id, Long prescrizione_id) {

    }

    @Override
    public void insertFarmacoInPrescrizione(Long farmacoId, Long prescrizioneId, int quantity) {

    }

    @Override
    public void updateFarmacoQuantityInFarmacoPrescrizione(Long farmacoId, Long prescrizioneId, int newQuantity) {

    }

    @Override
    public MedicinePrescription farmacoSingoloInFarmacoPrescrizione(Medicine f) {
        return null;
    }
}
