package org.univaq.oop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.univaq.oop.App;
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

public class AggModDettaglioPrescrizione implements Initializable, DataInitializable<User>{

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
    public Button creaBtn;
    @FXML
    private TextField codicetextfield;
    @FXML
    private TextArea descrizione;

    private ViewDispatcher dispatcher;
    private MedicineService farmacoService;
    private PrescriptionService prescriptionService;
    private FarmacoPrescrizioneService farmacoPrescrizioneService;
    private Map<Medicine, Integer> farmaciWithQuantityMap;
    private Prescription prescription;
    private ObservableList<MedicinePrescription> listaFarmaciNellaPrescrizione;
    private Map<Medicine, Integer> farmaciNellaPrescrizione;
    private UserService userService;
    private User user;

    public AggModDettaglioPrescrizione() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
        prescriptionService = factory.getPrescrizioneService();
        userService = factory.getUtenteService();
        farmacoPrescrizioneService = factory.getFarmacoPrescrizioneService();
        this.farmaciWithQuantityMap = new HashMap<>();
        this.farmaciNellaPrescrizione = new HashMap<>();
        this.prescription = new Prescription();

    }

    @Override
    public void initializeData(User user) {
        this.user = user;

        try {
            List<Medicine> farmaci = null;
            farmaci = farmacoService.findAllFarmaci();
            ObservableList<Medicine> farmaciData = FXCollections.observableArrayList(farmaci);
            tabellaFarmaci.setItems(farmaciData);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        List<MedicinePrescription> farmacoPrescrizioneList = farmacoPrescrizioneService.mapToFarmacoPrescrizione(this.farmaciNellaPrescrizione);
//        ObservableList<MedicinePrescription> medicinePrescriptions = FXCollections.observableArrayList(farmacoPrescrizioneList);
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

        if(this.farmaciNellaPrescrizione.containsKey(f)) {
            int quantity = this.farmaciNellaPrescrizione.get(f) + 1;
            this.farmaciNellaPrescrizione.replace(f, quantity);
        } else {
            this.farmaciNellaPrescrizione.put(f,1);
        }

        this.listaFarmaciNellaPrescrizione.setAll(
                farmacoPrescrizioneService.mapToFarmacoPrescrizione(farmaciNellaPrescrizione)
        );
    }


    @FXML
    public void rimuoviFarmacoDallaPrescrizione() {
        MedicinePrescription fp = this.tabellaFarmaciInPrescrizione.getSelectionModel().getSelectedItem();
        Long prescrizione_id = this.prescription.getId();
        this.farmacoPrescrizioneService.deleteFarmacoFromPrescrizione(fp.getId(), prescrizione_id);
        this.listaFarmaciNellaPrescrizione.remove(fp);
    }

    @FXML
    public void creaPrescrizione() throws BusinessException {
        User prova;
        prova = userService.findPatientByFiscalCode(codicetextfield.getText());
        this.prescription.setUserId(Math.toIntExact(prova.getId()));

        this.prescription.setDoctorId(user.getId().intValue());
        this.prescription.setDescription(this.descrizione.getText());
        Prescription prescrizioneInserita = this.prescriptionService.createPrescrizione(this.prescription);

        farmaciNellaPrescrizione.forEach((farmaco, quantity) -> this.farmacoPrescrizioneService.insertFarmacoInPrescrizione(
                farmaco.getId(),
                prescrizioneInserita.getId(),
                quantity
        ));
    dispatcher.renderView("prescrizioniMedico",user);

    }


}
