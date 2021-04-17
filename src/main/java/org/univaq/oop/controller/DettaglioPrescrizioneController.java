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
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DettaglioPrescrizioneController implements Initializable, DataInitializable<Prescription> {

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

    private final ViewDispatcher dispatcher;
    private final MedicineService farmacoService;
    private final PrescriptionService prescriptionService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;
    private Map<Medicine, Integer> farmaciWithQuantityMap;
    private Medicine medicine = new Medicine();
    private Prescription prescription;
    private User utente;


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
        this.prescription = prescription;

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

        boolean evadable = farmaciWithQuantityMap.entrySet().stream().allMatch(entry -> {
            try {
                medicine = farmacoService.findMedicineById((entry.getKey().getId().intValue()));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
            return (medicine.getQuantity() - entry.getValue()) > 0;
        });

        if (evadable) {
            for (Map.Entry<Medicine, Integer> entry : farmaciWithQuantityMap.entrySet()) {
                medicine = farmacoService.findMedicineById((entry.getKey().getId().intValue()));
                medicine.setQuantity(medicine.getQuantity() - entry.getValue());
                farmacoService.updateFarmaco(medicine);
            }
            prescription.setEvaded(evadable);
            prescriptionService.updatePrescrizione(prescription);
            dispatcher.renderView("elencoTuttePrescrizioni", utente);
        } // settare una label che da' errore e una che dice tutto a posto


    }
}
