package org.univaq.oop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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


public class DettaglioPrescrizionePazienteController implements DataInitializable<Prescrizione>, Initializable {
    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoPrescrizioneService farmacoPrescrizioneService;
    @FXML
    private TableView<FarmacoPrescrizione> tabellaPrescrizione;
    @FXML
    private TableColumn<FarmacoPrescrizione, String> colonnaNome;
    @FXML
    private TableColumn<FarmacoPrescrizione, String> colonnaCodice;
    @FXML
    private TableColumn<FarmacoPrescrizione, Integer> colonnaQuantità;
    @FXML
    private Label errore;

    private Map<Farmaco, Integer> mappaFarmaciEQuantita;
    private Utente utente;

    public DettaglioPrescrizionePazienteController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
        prescrizioneService = factory.getPrescrizioneService();
        farmacoPrescrizioneService = factory.getFarmacoPrescrizioneService();

    }

    @Override
    public void initializeData(Prescrizione prescrizione) {
        Long id = prescrizione.getId();
        try {
            mappaFarmaciEQuantita = farmacoPrescrizioneService.ottieniFarmaciDallaPrescrizione(id);
        } catch (BusinessException e) {
            errore.setText("Errore nella ricerca dei farmaci nella prescrizione");
        }
        List<FarmacoPrescrizione> listaFarmaciWithQuantity = mappaFarmaciEQuantita.entrySet().stream().map(entry -> new FarmacoPrescrizione(
                entry.getKey().getId(),
                entry.getKey().getNome(),
                entry.getKey().getDescrizione(),
                entry.getValue())
        )
                .collect(Collectors.toList());
        ObservableList<FarmacoPrescrizione> farmaciData = FXCollections.observableArrayList(listaFarmaciWithQuantity);
        tabellaPrescrizione.setItems(farmaciData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaCodice.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonnaQuantità.setCellValueFactory(new PropertyValueFactory<>("quantita"));


    }


}
