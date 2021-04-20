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

public class CreazioneDettaglioPrescrizioneController implements Initializable, DataInitializable<Utente> {

    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;
    private final Map<Farmaco, Integer> farmaciNellaPrescrizione;
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
    public Button creaBtn;
    @FXML
    private TextField codicetextfield;
    @FXML
    private TextArea descrizione;
    @FXML
    private Label errorlabel;
    private final Prescrizione prescrizione;
    private ObservableList<FarmacoPrescrizione> listaFarmaciNellaPrescrizione;
    private Utente utente;

    public CreazioneDettaglioPrescrizioneController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
        prescrizioneService = factory.getPrescrizioneService();
        utenteService = factory.getUtenteService();
        farmacoPrescrizioneService = factory.getFarmacoPrescrizioneService();
        this.farmaciNellaPrescrizione = new HashMap<>();
        this.prescrizione = new Prescrizione();

    }

    @Override
    public void initializeData(Utente utente) {
        this.utente = utente;

        try {
            List<Farmaco> farmaci = null;
            farmaci = farmacoService.trovaTuttiFarmaci();
            ObservableList<Farmaco> farmaciData = FXCollections.observableArrayList(farmaci);
            tabellaFarmaci.setItems(farmaciData);
        } catch (BusinessException e) {
            errorlabel.setText("Errore nella ricerca dei farmaci");
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
        } else {
            this.farmaciNellaPrescrizione.put(f, 1);
        }

        this.listaFarmaciNellaPrescrizione.setAll(
                farmacoPrescrizioneService.mappaFarmacoPrescrizione(farmaciNellaPrescrizione)
        );
    }


    @FXML
    public void rimuoviFarmacoDallaPrescrizione() {

        FarmacoPrescrizione fp = this.tabellaFarmaciInPrescrizione.getSelectionModel().getSelectedItem();
             
        this.farmaciNellaPrescrizione
            .keySet()
            .stream()
            .filter(medicine -> medicine.getId().equals(fp.getId()))
            .findFirst()
            .ifPresentOrElse(
                farmaco -> {
                    this.farmaciNellaPrescrizione.remove(farmaco);
                    this.listaFarmaciNellaPrescrizione.remove(fp);
                }, 
                () -> errorlabel.setText("Errore nella rimozione del farmaco")
            );
            
    }

    @FXML
    public void creaPrescrizione() throws BusinessException {
        try {
            this.prescrizione.setCodicePaziente(Math.toIntExact(utenteService.trovaPazienteDaCodiceFiscale(codicetextfield.getText()).getId()));
        } catch (BusinessException e) {
            errorlabel.setText("Questo codice non esiste");
        }
        this.prescrizione.setCodiceDottore(utente.getId().intValue());
        this.prescrizione.setDescrizione(this.descrizione.getText());
        Prescrizione prescrizioneInserita = this.prescrizioneService.creaPrescrizione(this.prescrizione);

        farmaciNellaPrescrizione.forEach((farmaco, quantity) -> this.farmacoPrescrizioneService.inserisciFarmacoNellaPrescrizione(
                farmaco.getId(),
                prescrizioneInserita.getId(),
                quantity
        ));
        dispatcher.renderView("prescrizioniMedico", utente);

    }


}
