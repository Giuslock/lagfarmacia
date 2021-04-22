package org.univaq.oop.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.univaq.oop.business.*;
import org.univaq.oop.domain.Prescrizione;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ElencoPrescrizioniPazienteController implements DataInitializable<Utente>, Initializable {
    private final ViewDispatcher dispatcher;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoService farmacoService;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Prescrizione> tabellaElencoPrescrizioni;
    @FXML
    private TableColumn<Prescrizione, String> colonnaNumero;
    @FXML
    private TableColumn<Prescrizione, String> colonnnaDescrizione;
    @FXML
    private TableColumn<Prescrizione, Utente> colonnaMedico;
    @FXML
    private TableColumn<Prescrizione, Utente> colonnaPaziente;
    @FXML
    private TableColumn<Prescrizione, Button> colonnaAzioni;
    @FXML
    private TableColumn<?, ?> colonnaStato;
    @FXML
    private Label errorText;
    @FXML
    private Button dettagliButton;
    @FXML
    private Label errorlabel;
    private FarmacoPrescrizioneService farmacoPrescrizioneService;

    public ElencoPrescrizioniPazienteController() {

        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        prescrizioneService = factory.getPrescrizioneService();
        farmacoService = factory.getFarmacoService();

    }


    @Override
    public void initializeData(Utente utente) {
        int paziente = Math.toIntExact(utente.getId());
        try {
            List<Prescrizione> prescrizioni = prescrizioneService.trovaPrescrizioniDalPaziente(paziente);
            ObservableList<Prescrizione> prescrizioniData = FXCollections.observableArrayList(prescrizioni);
            tabellaElencoPrescrizioni.setItems(prescrizioniData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colonnaNumero.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonnnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        colonnaMedico.setCellValueFactory(new PropertyValueFactory<>("codiceDottore"));
        colonnaPaziente.setCellValueFactory(new PropertyValueFactory<>("codicePaziente"));
        colonnaStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        colonnaAzioni.setStyle("-fx-alignment: CENTER;");
        colonnaAzioni.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Prescrizione, Button> param) {
                        final Button dettaglioButton = new Button("dettaglio");

                        dettaglioButton.setOnAction(new EventHandler<>() {
                            @Override
                            public void handle(ActionEvent event) {
                                dispatcher.renderView("dettaglioPrescrizionePaziente", param.getValue());
                            }
                        });
                        return new SimpleObjectProperty<>(dettaglioButton);
                    }
                });


    }


}



