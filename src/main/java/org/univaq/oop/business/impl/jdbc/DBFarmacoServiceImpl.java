package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoInPrescrizioneException;
import org.univaq.oop.business.FarmacoNonTrovato;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.Prescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBFarmacoServiceImpl implements MedicineService {

    private static final String SELECT_ALL = "select * from farmaco";
    private static final String SELECT_FARMACO = "select * from farmaco where id=?";
    private static final String SELECT_FARMACO_ESAURIMENTO = "select * from farmaco where in_esaurimento=1";
    private static final String DESTROY_FARMACO = "delete from farmaco where id=?";
    private static final String INSERT_FARMACO = "insert into farmaco ( nome,descrizione,q_min,quantity) values  (?,?,?,?) ;";
    private static final String UPDATE_FARMACO = "update farmaco set nome=?,descrizione=?,q_min=?,quantity=?  where id=?";
    private static final String UPDATE_QUANTITY_FARMACO = "update farmaco set quantity=? where id=?";

    @Override
    public List<Medicine> findAllFarmaci() throws BusinessException {
        List<Medicine> ll = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setId(rs.getLong("id"));
                medicine.setName(rs.getString("nome"));
                medicine.setDescription(rs.getString("descrizione"));
                medicine.setQuantity(rs.getInt("quantity"));
                medicine.setMinimum(rs.getInt("q_min"));
                medicine.setOutOfStock();
                medicine.setStatoFarmaco();
                ll.add(medicine);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ll;
    }

    @Override
    public void addFarmaco(Medicine farmaco) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_FARMACO);
            statement.setString(1, farmaco.getName());
            statement.setString(2, farmaco.getDescription());
            statement.setInt(3, farmaco.getMinimum());
            statement.setInt(4, farmaco.getQuantity());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void updateFarmaco(Medicine farmaco) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_FARMACO);
            statement.setString(1, farmaco.getName());
            statement.setString(2, farmaco.getDescription());
            statement.setInt(3, farmaco.getMinimum());
            statement.setInt(4, farmaco.getQuantity());
            statement.setLong(5,farmaco.getId());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Medicine findMedicineById(Integer codice) throws BusinessException {
        Medicine medicine = new Medicine();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_FARMACO);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
            medicine.setId(rs.getLong("id"));
            medicine.setName(rs.getString("nome"));
            medicine.setDescription(rs.getString("descrizione"));
            medicine.setQuantity(rs.getInt("quantity"));
            medicine.setMinimum(rs.getInt("q_min"));
            medicine.setOutOfStock();
            medicine.setStatoFarmaco();
            }
            else{
            throw new FarmacoNonTrovato();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return medicine;


    }

    @Override
    public void deleteFarmaco(Long codice) throws FarmacoInPrescrizioneException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DESTROY_FARMACO);
            statement.setLong(1, codice);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new FarmacoInPrescrizioneException();

    }
    }

    @Override
    public void aggiornaQtaFarmaco(Prescription prescrizione) throws BusinessException {

    }

    @Override
    public List<String> findAllFarmaciByNome() throws BusinessException {
        return null;
    }

    @Override
    public Medicine findFarmacoByName(String string) throws BusinessException {
        return null;
    }

    @Override
    public List<Medicine> findFarmaciInEsaurimento() throws BusinessException {
        List<Medicine> ll = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_FARMACO_ESAURIMENTO);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setId(rs.getLong("id"));
                medicine.setName(rs.getString("nome"));
                medicine.setDescription(rs.getString("descrizione"));
                medicine.setQuantity(rs.getInt("quantity"));
                medicine.setMinimum(rs.getInt("q_min"));
                medicine.setOutOfStock();
                medicine.setStatoFarmaco();
                ll.add(medicine);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ll;
    }
}
