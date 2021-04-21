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

public class PrescrizioniFarmacistaController implements DataInitializable<Utente>, Initializable {

    private final ViewDispatcher dispatcher;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoService farmacoService;
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
    private Label errorText;
    @FXML
    private Button dettagliButton;
    @FXML
    private Label errorlabel;

    private FarmacoPrescrizioneService farmacoPrescrizioneService;

    public PrescrizioniFarmacistaController() {

        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        prescrizioneService = factory.getPrescrizioneService();
        farmacoService = factory.getFarmacoService();

    }


    @Override
    public void initializeData(Utente utente) {
        try {
            List<Prescrizione> prescrizioni = prescrizioneService.trovaPrescrizioniDaEvadere();
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
        azioniTableColumn.setStyle("-fx-alignment: CENTER;");
        azioniTableColumn.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Prescrizione, Button> param) {
                        final Button evadiButton = new Button("Evadi");

                        evadiButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                dispatcher.renderView("dettaglioPrescrizione", param.getValue());

                            }
                        });
                        return new SimpleObjectProperty<>(evadiButton);
                    }
                });


    }
}
