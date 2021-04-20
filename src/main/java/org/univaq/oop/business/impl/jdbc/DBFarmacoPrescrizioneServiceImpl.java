package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoNonTrovato;
import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.FarmacoPrescrizione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DBFarmacoPrescrizioneServiceImpl implements FarmacoPrescrizioneService {

    private static final String PRESCRIZIONE_CON_FARMACI = "select * from farmaco_prescrizione where prescrizione_id=?";
    private static final String SELEZIONA_FARMACO_PER_ID = "select * from farmaco where id=?";
    private static final String ELIMINA_FARMACO_DALLA_PRESCRIZIONE = "delete from farmaco_prescrizione where farmaco_id=? and prescrizione_id=?";
    private static final String INSERISCI_FARMACO_IN_PRESCRIZIONE = "insert into farmaco_prescrizione(farmaco_id, prescrizione_id, quantity) values (?, ?, ?)";
    private static final String AGGIORNA_QUANTITA_FARMACO_IN_FARMACO_PRESCRIZIONE = "update farmaco_prescrizione set quantity=? where prescrizione_id=? and farmaco_id=?";


    @Override
    public Map<Farmaco, Integer> ottieniFarmaciDallaPrescrizione(Long prescriptionId) throws BusinessException {
        Map<Farmaco, Integer> farmaciMap = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(PRESCRIZIONE_CON_FARMACI);
            statement.setInt(1, prescriptionId.intValue());
            ResultSet resultSet = statement.executeQuery();

            // ResultSet fields -> ["id", "farmaco_id", "prescrizione_id", "quantity"]
            while (resultSet.next()) {
//                Medicine f = this.medicineService.findMedicineById(resultSet.getInt("farmaco_id"));
                Farmaco f = new Farmaco();
                try (Connection connection2 = DatabaseConnection.getConnection()) {
                    PreparedStatement statement2 = connection2.prepareStatement(SELEZIONA_FARMACO_PER_ID);
                    statement2.setLong(1, resultSet.getLong(2));
                    ResultSet rs = statement2.executeQuery();
                    if (rs.next()) {
                        f.setId(rs.getLong("id"));
                        f.setNome(rs.getString("nome"));
                        f.setDescrizione(rs.getString("descrizione"));
                        f.setQuantita(rs.getInt("quantity"));
                        f.setMinimo(rs.getInt("q_min"));
                        f.setOutOfStock();
                        f.setStatoFarmaco();
                    } else {
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
    public List<FarmacoPrescrizione> mappaFarmacoPrescrizione(Map<Farmaco, Integer> mappaFarmaci) {
        return mappaFarmaci.entrySet()
                .stream()
                .map(entry -> new FarmacoPrescrizione(
                        entry.getKey().getId(),
                        entry.getKey().getNome(),
                        entry.getValue())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void eliminaFarmacoDallaPrescrizione(Long id, Long prescrizione_id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ELIMINA_FARMACO_DALLA_PRESCRIZIONE);
            statement.setInt(1, id.intValue());
            statement.setInt(2, prescrizione_id.intValue());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void inserisciFarmacoNellaPrescrizione(Long farmacoId, Long prescrizioneId, int quantity) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERISCI_FARMACO_IN_PRESCRIZIONE);
            statement.setInt(1, farmacoId.intValue());
            statement.setInt(2, prescrizioneId.intValue());
            statement.setInt(3, quantity);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void aggiornaQuantitaFarmacoInFarmacoPrescrizione(Long farmacoId, Long prescrizioneId, int newQuantity) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(AGGIORNA_QUANTITA_FARMACO_IN_FARMACO_PRESCRIZIONE);
            statement.setInt(1, newQuantity);
            statement.setInt(2, prescrizioneId.intValue());
            statement.setInt(3, farmacoId.intValue());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public FarmacoPrescrizione farmacoSingoloInFarmacoPrescrizione(Farmaco f) {
        return new FarmacoPrescrizione(f.getId(), f.getNome(), 1);
    }
}
