package org.univaq.oop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.univaq.oop.business.*;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;
import org.univaq.oop.domain.Prescription;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class DettaglioPrescrizionePazienteController implements DataInitializable<Prescription>, Initializable {
    private final ViewDispatcher dispatcher;
    private final MedicineService farmacoService;
    private final PrescriptionService prescriptionService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;
    @FXML
    private TableView<MedicinePrescription> dettaglioPrescrizioneTable;
    @FXML
    private TableColumn<MedicinePrescription, String> nomeTableColumn;
    @FXML
    private TableColumn<MedicinePrescription, String> codiceTableColumn;
    @FXML
    private TableColumn<MedicinePrescription, Integer> quantityTableColumn;
    private Map<Medicine, Integer> farmaciWithQuantityMap;
    private User utente;

    public DettaglioPrescrizionePazienteController() {
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
        } catch (BusinessException e) {
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        codiceTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));


    }


}
