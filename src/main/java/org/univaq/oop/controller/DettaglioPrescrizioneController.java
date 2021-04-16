package org.univaq.oop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.univaq.oop.business.*;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;
import org.univaq.oop.domain.Prescription;
import org.univaq.oop.view.ViewDispatcher;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DettaglioPrescrizioneController implements Initializable,DataInitializable<Prescription> {

    @FXML
    private TableView<MedicinePrescription> dettaglioPrescrizioneTable;

    @FXML
    private TableColumn<MedicinePrescription, String> nomeTableColumn;

    @FXML
    private TableColumn<MedicinePrescription, Long> codiceTableColumn;

    @FXML
    private TableColumn<MedicinePrescription, Integer> quantityTableColumn;

    @FXML
    private TextField farmpreTextField;

    @FXML
    private Button evadiButton;

    private ViewDispatcher dispatcher;
    private MedicineService farmacoService;
    private PrescriptionService prescriptionService;
    private FarmacoPrescrizioneService farmacoPrescrizioneService;
    private Map<Medicine, Integer> farmaciWithQuantityMap;
    private  Medicine medicine = new Medicine();



    public DettaglioPrescrizioneController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
        prescriptionService = factory.getPrescrizioneService();
        farmacoPrescrizioneService = factory.getFarmacoPrescrizioneService();

    }

    @Override
    public void initializeData(Prescription prescription) {
        Long id = prescription.getId();
        try {
            farmaciWithQuantityMap = farmacoPrescrizioneService.getMedicineFromPrescription(id);
        } catch (org.univaq.oop.business.BusinessException e) {
            e.printStackTrace();
        }
        List<MedicinePrescription> listaFarmaciWithQuantity = farmaciWithQuantityMap.entrySet().stream().map(entry -> new MedicinePrescription(
                entry.getKey().getId(),
                entry.getKey().getName(),
                entry.getKey().getDescription(),
                entry.getValue())
        )
                .collect(Collectors.toList());
        ObservableList<MedicinePrescription> farmaciData = FXCollections.observableArrayList(listaFarmaciWithQuantity);
        dettaglioPrescrizioneTable.setItems(farmaciData);
        this.farmpreTextField.setText(String.valueOf(prescription.getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        codiceTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));



    }

    public void evadiAction() throws BusinessException {
        int id = Integer.parseInt(farmpreTextField.getText());
        List<Long> columnData = new ArrayList<>();
        for (MedicinePrescription item : dettaglioPrescrizioneTable.getItems()) {
            columnData.add(codiceTableColumn.getCellObservableValue(item).getValue());
        }

        List<Integer> columnData2 = new ArrayList<>();
        for (MedicinePrescription item2 : dettaglioPrescrizioneTable.getItems()) {
            columnData2.add(quantityTableColumn.getCellObservableValue(item2).getValue());
        }
        int cont = 0;
        for (Long fid: columnData) {
            medicine = farmacoService.findFarmacoByCodice(Math.toIntExact(fid));
            int cavallo = columnData2.get(cont);
            int test = medicine.getQuantity();
            medicine.setQuantity(test - cavallo);
            farmacoService.updateFarmaco(medicine);
            cont++;



        }

    }
}
