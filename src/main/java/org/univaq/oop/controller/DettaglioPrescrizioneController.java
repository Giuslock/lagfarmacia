package org.univaq.oop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.univaq.oop.business.FarmacoPrescrizioneService;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;
import org.univaq.oop.domain.Prescription;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
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
    private TableColumn<MedicinePrescription, String> codiceTableColumn;

    @FXML
    private TableColumn<MedicinePrescription, Integer> quantityTableColumn;

    @FXML
    private Button evadiButton;

    private ViewDispatcher dispatcher;
    private MedicineService farmacoService;
    private PrescriptionService prescriptionService;
    private FarmacoPrescrizioneService farmacoPrescrizioneService;
    private Map<Medicine, Integer> farmaciWithQuantityMap;

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
        farmaciWithQuantityMap = farmacoPrescrizioneService.getMedicineFromPrescription(id);
        List<MedicinePrescription> listaFarmaciWithQuantity = farmaciWithQuantityMap.entrySet().stream().map(entry -> new MedicinePrescription(
                entry.getKey().getId(),
                entry.getKey().getName(),
                entry.getKey().getDescription(),
                entry.getValue())
        )
                .collect(Collectors.toList());
        ObservableList<MedicinePrescription> farmaciData = FXCollections.observableArrayList(listaFarmaciWithQuantity);
        dettaglioPrescrizioneTable.setItems(farmaciData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        codiceTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));



    }

    public void evadiAction() {
        //farmacoPrescrizioneService.evadePrescription(prescription);
       // dispatcher.renderView("modificaFarmaco", farmacoVuoto);
    }
}
