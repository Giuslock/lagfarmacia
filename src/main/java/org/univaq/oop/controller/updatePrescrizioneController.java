package org.univaq.oop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.univaq.oop.business.*;
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.FarmacoPrescrizione;
import org.univaq.oop.domain.Prescrizione;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class updatePrescrizioneController implements Initializable, DataInitializable<Prescrizione> {

    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;
    private final UtenteService utenteService;
    @FXML
    public TableView<Farmaco> tabellaFarmaci;
    @FXML
    public TableColumn<Farmaco, Integer> t1_id;
    @FXML
    public TableColumn<Farmaco, String> t1_nome;
    @FXML
    public Button aggiungiFarmaco;
    @FXML
    public TableView<FarmacoPrescrizione> tabellaFarmaciInPrescrizione;
    @FXML
    public TableColumn<FarmacoPrescrizione, String> t2_nome;
    @FXML
    public TableColumn<FarmacoPrescrizione, Integer> t2_quantity;
    @FXML
    public Button deleteBtnT2;
    @FXML
    public Button salva;
    @FXML
    private TextField codicetextfield;
    @FXML
    private TextArea descrizione;
    @FXML
    private Label errorlabel;
    
    private Prescrizione prescrizione;
    private ObservableList<FarmacoPrescrizione> listaFarmaciNellaPrescrizione;
    private Map<Farmaco, Integer> farmaciNellaPrescrizione;
    private Utente utente;

    public updatePrescrizioneController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
        prescrizioneService = factory.getPrescrizioneService();
        utenteService = factory.getUtenteService();
        farmacoPrescrizioneService = factory.getFarmacoPrescrizioneService();
        this.farmaciNellaPrescrizione = new HashMap<>();


    }

    @Override
    public void initializeData(Prescrizione prescrizione) {
        this.prescrizione = prescrizione;
        try {
            List<Farmaco> farmaci = null;
            farmaci = farmacoService.trovaTuttiFarmaci();
            ObservableList<Farmaco> farmaciData = FXCollections.observableArrayList(farmaci);
            tabellaFarmaci.setItems(farmaciData);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        try {
            this.farmaciNellaPrescrizione = this.farmacoPrescrizioneService.ottieniFarmaciDallaPrescrizione(this.prescrizione.getId());
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        List<FarmacoPrescrizione> farmacoPrescrizioneList = farmacoPrescrizioneService.mappaFarmacoPrescrizione(this.farmaciNellaPrescrizione);
        this.listaFarmaciNellaPrescrizione = FXCollections.observableArrayList(farmacoPrescrizioneList);
        this.tabellaFarmaciInPrescrizione.setItems(this.listaFarmaciNellaPrescrizione);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        t1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        t2_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        t2_quantity.setCellValueFactory(new PropertyValueFactory<>("quantita"));

    }

    @FXML
    public void aggiungifarmaco() {
        Farmaco f = this.tabellaFarmaci.getSelectionModel().getSelectedItem();

        if (this.farmaciNellaPrescrizione.containsKey(f)) {
            int quantity = this.farmaciNellaPrescrizione.get(f) + 1;
            this.farmaciNellaPrescrizione.replace(f, quantity);
            this.listaFarmaciNellaPrescrizione.setAll(
                    farmacoPrescrizioneService.mappaFarmacoPrescrizione(this.farmaciNellaPrescrizione)
            );
            this.farmacoPrescrizioneService.aggiornaQuantitaFarmacoInFarmacoPrescrizione(f.getId(), prescrizione.getId(), quantity);
        } else {
            this.farmaciNellaPrescrizione.put(f, 1);
            this.listaFarmaciNellaPrescrizione.add(farmacoPrescrizioneService.farmacoSingoloInFarmacoPrescrizione(f));
            this.farmacoPrescrizioneService.inserisciFarmacoNellaPrescrizione(f.getId(), prescrizione.getId(), 1);
        }
    }

    @FXML
    public void salva() {
        dispatcher.renderView("prescrizioniMedico", utente);
    }


    @FXML
    public void rimuoviFarmacoDallaPrescrizione() {
        FarmacoPrescrizione fp = this.tabellaFarmaciInPrescrizione.getSelectionModel().getSelectedItem();
        this.farmacoPrescrizioneService.eliminaFarmacoDallaPrescrizione(fp.getId(), prescrizione.getId());

        // Devo rimuovere anche dalla lista non solo dall'observable
        // mi rendera' piu' facile l'eliminazione e/o aggiunta del farmaco nell'observable
        Farmaco toDelete = null;
        for (Farmaco f : farmaciNellaPrescrizione.keySet()) {
            if (f.getId().equals(fp.getId())) toDelete = f;
        }
        this.farmaciNellaPrescrizione.remove(toDelete);

        this.listaFarmaciNellaPrescrizione.remove(fp);
    }


}
