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


public class PrescrizioniPazienteController implements DataInitializable<Utente>, Initializable {
    private final ViewDispatcher dispatcher;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoService farmacoService;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Prescrizione> elencoPrescrizioniTable;
    @FXML
    private TableColumn<Prescrizione, String> numeroTableColumn;
    @FXML
    private TableColumn<Prescrizione, String> descriptionTableColumn;
    @FXML
    private TableColumn<Prescrizione, Utente> medicoTableColumn;
    @FXML
    private TableColumn<Prescrizione, Utente> pazienteTableColumn;
    @FXML
    private TableColumn<Prescrizione, Button> azioniTableColumn;
    @FXML
    private TableColumn<?, ?> statoTableColumn;
    @FXML
    private Label errorText;
    @FXML
    private Button dettagliButton;
    private FarmacoPrescrizioneService farmacoPrescrizioneService;

    public PrescrizioniPazienteController() {

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
            elencoPrescrizioniTable.setItems(prescrizioniData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numeroTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        medicoTableColumn.setCellValueFactory(new PropertyValueFactory<>("codiceDottore"));
        pazienteTableColumn.setCellValueFactory(new PropertyValueFactory<>("codicePaziente"));
        statoTableColumn.setCellValueFactory(new PropertyValueFactory<>("stato"));
        azioniTableColumn.setStyle("-fx-alignment: CENTER;");
        azioniTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Prescrizione, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Prescrizione, Button> param) {
                        final Button dettaglioButton = new Button("dettaglio");

                        dettaglioButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
//                              Map<Medicine, Integer> dettaglio = new HashMap<>();
//                                dettaglio = farmacoPrescrizioneService.getMedicineFromPrescription(Long.parseLong(numeroTableColumn.getText()));
//                               MedicinePrescription azz = new MedicinePrescription();
                                dispatcher.renderView("dettaglioPrescrizionePaziente", param.getValue());
                            }
                        });
                        return new SimpleObjectProperty<Button>(dettaglioButton);
                    }
                });


    }


}



