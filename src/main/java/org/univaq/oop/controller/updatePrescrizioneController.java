package org.univaq.oop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.univaq.oop.business.*;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.MedicinePrescription;
import org.univaq.oop.domain.Prescription;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class updatePrescrizioneController implements Initializable, DataInitializable<Prescription> {

    private final ViewDispatcher dispatcher;
    private final MedicineService farmacoService;
    private final PrescriptionService prescriptionService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;
    private final UserService userService;
    @FXML
    public TableView<Medicine> tabellaFarmaci;
    @FXML
    public TableColumn<Medicine, Integer> t1_id;
    @FXML
    public TableColumn<Medicine, String> t1_nome;
    @FXML
    public Button aggiungiFarmaco;
    @FXML
    public TableView<MedicinePrescription> tabellaFarmaciInPrescrizione;
    @FXML
    public TableColumn<MedicinePrescription, String> t2_nome;
    @FXML
    public TableColumn<MedicinePrescription, Integer> t2_quantity;
    @FXML
    public Button deleteBtnT2;
    @FXML
    public Button salva;
    @FXML
    private TextField codicetextfield;
    @FXML
    private TextArea descrizione;
    private Prescription prescrizione;
    private ObservableList<MedicinePrescription> listaFarmaciNellaPrescrizione;
    private Map<Medicine, Integer> farmaciNellaPrescrizione;
    private User user;

    public updatePrescrizioneController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
        prescriptionService = factory.getPrescrizioneService();
        userService = factory.getUtenteService();
        farmacoPrescrizioneService = factory.getFarmacoPrescrizioneService();
        this.farmaciNellaPrescrizione = new HashMap<>();


    }

    @Override
    public void initializeData(Prescription prescription) {
        this.prescrizione = prescription;
        try {
            List<Medicine> farmaci = null;
            farmaci = farmacoService.findAllFarmaci();
            ObservableList<Medicine> farmaciData = FXCollections.observableArrayList(farmaci);
            tabellaFarmaci.setItems(farmaciData);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        try {
            this.farmaciNellaPrescrizione = this.farmacoPrescrizioneService.getMedicineFromPrescription(prescrizione.getId());
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        List<MedicinePrescription> farmacoPrescrizioneList = farmacoPrescrizioneService.mapToFarmacoPrescrizione(this.farmaciNellaPrescrizione);
        this.listaFarmaciNellaPrescrizione = FXCollections.observableArrayList(farmacoPrescrizioneList);
        this.tabellaFarmaciInPrescrizione.setItems(this.listaFarmaciNellaPrescrizione);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        t1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1_nome.setCellValueFactory(new PropertyValueFactory<>("name"));
        t2_nome.setCellValueFactory(new PropertyValueFactory<>("name"));
        t2_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    }

    @FXML
    public void aggiungifarmaco() {
        Medicine f = this.tabellaFarmaci.getSelectionModel().getSelectedItem();

        if (this.farmaciNellaPrescrizione.containsKey(f)) {
            int quantity = this.farmaciNellaPrescrizione.get(f) + 1;
            this.farmaciNellaPrescrizione.replace(f, quantity);
            this.listaFarmaciNellaPrescrizione.setAll(
                    farmacoPrescrizioneService.mapToFarmacoPrescrizione(this.farmaciNellaPrescrizione)
            );
            this.farmacoPrescrizioneService.updateFarmacoQuantityInFarmacoPrescrizione(f.getId(), prescrizione.getId(), quantity);
        } else {
            this.farmaciNellaPrescrizione.put(f, 1);
            this.listaFarmaciNellaPrescrizione.add(farmacoPrescrizioneService.farmacoSingoloInFarmacoPrescrizione(f));
            this.farmacoPrescrizioneService.insertFarmacoInPrescrizione(f.getId(), prescrizione.getId(), 1);
        }
    }

    @FXML
    public void salva() {
        dispatcher.renderView("prescrizioniMedico", user);
    }


    @FXML
    public void rimuoviFarmacoDallaPrescrizione() {
        MedicinePrescription fp = this.tabellaFarmaciInPrescrizione.getSelectionModel().getSelectedItem();
        this.farmacoPrescrizioneService.deleteFarmacoFromPrescrizione(fp.getId(), prescrizione.getId());

        // Devo rimuovere anche dalla lista non solo dall'observable
        // mi rendera' piu' facile l'eliminazione e/o aggiunta del farmaco nell'observable
        Medicine toDelete = null;
        for (Medicine f : farmaciNellaPrescrizione.keySet()) {
            if (f.getId().equals(fp.getId())) toDelete = f;
        }
        this.farmaciNellaPrescrizione.remove(toDelete);

        this.listaFarmaciNellaPrescrizione.remove(fp);
    }


}
