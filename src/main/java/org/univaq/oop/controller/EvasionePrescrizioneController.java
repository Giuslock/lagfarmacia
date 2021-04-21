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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EvasionePrescrizioneController implements Initializable, DataInitializable<Prescrizione> {


    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;

    @FXML
    private TextField idFarmacoPrescrizione;

    @FXML
    private TableView<FarmacoPrescrizione> tabellaDettaglioPrescrizione;

    @FXML
    private TableColumn<?, ?> colonnaCodice;

    @FXML
    private TableColumn<?, ?>  colonnaNome;

    @FXML
    private TableColumn<?, ?> colonnaQuantità;

    @FXML
    private Button evadiButton;

    @FXML
    private Label errorlabel;






    private Map<Farmaco, Integer> mappaFarmaciEQuantita;
    private Farmaco farmaco = new Farmaco();
    private Prescrizione prescrizione;
    private Utente utente;

    public EvasionePrescrizioneController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
        prescrizioneService = factory.getPrescrizioneService();
        farmacoPrescrizioneService = factory.getFarmacoPrescrizioneService();

    }

    @Override
    public void initializeData(Prescrizione prescrizione) {
        Long id = prescrizione.getId();
        this.prescrizione = prescrizione;


        try {
            mappaFarmaciEQuantita = farmacoPrescrizioneService.ottieniFarmaciDallaPrescrizione(id);
        } catch (BusinessException e) {
            errorlabel.setText("Errore nella ricerca dei farmaci dalla prescrizione");
        }

        List<FarmacoPrescrizione> listaFarmaciWithQuantity = mappaFarmaciEQuantita.entrySet().stream().map(entry -> new FarmacoPrescrizione(
                entry.getKey().getId(),
                entry.getKey().getNome(),
                entry.getKey().getDescrizione(),
                entry.getValue())
        )
                .collect(Collectors.toList());
        ObservableList<FarmacoPrescrizione> farmaciData = FXCollections.observableArrayList(listaFarmaciWithQuantity);
        tabellaDettaglioPrescrizione.setItems(farmaciData);
        this.idFarmacoPrescrizione.setText(String.valueOf(prescrizione.getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaCodice.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonnaQuantità.setCellValueFactory(new PropertyValueFactory<>("quantita"));


    }

    public void evadiAction() throws BusinessException {

        boolean evadable = mappaFarmaciEQuantita.entrySet().stream().allMatch(entry -> {
            try {
                farmaco = farmacoService.trovaFarmacoDaId((entry.getKey().getId().intValue()));
            } catch (BusinessException e) {

                errorlabel.setText("Errore nella ricerca del farmaco da id");

            }
            return (farmaco.getQuantita() - entry.getValue()) > 0;
        });

        if (evadable) {
            for (Map.Entry<Farmaco, Integer> entry : mappaFarmaciEQuantita.entrySet()) {
                farmaco = farmacoService.trovaFarmacoDaId((entry.getKey().getId().intValue()));
                farmaco.setQuantita(farmaco.getQuantita() - entry.getValue());
                farmacoService.aggiornaFarmaco(farmaco);
            }
            prescrizione.setEvasa(evadable);
            prescrizioneService.aggiornaPrescrizione(prescrizione);
            dispatcher.renderView("elencoTuttePrescrizioni", utente);
        } else {
            errorlabel.setText("La prescrizione non e' evadibile");
        }


    }
}
