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
import org.univaq.oop.domain.Prescription;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PrescrizioniFarmacistaController implements DataInitializable<User>, Initializable {

    @FXML
    private TableView<Prescription> elencoPrescrizioniTable;

    @FXML
    private TableColumn<Prescription, String> numeroTableColumn;

    @FXML
    private TableColumn<Prescription, LocalDate> descriptionTableColumn;

    @FXML
    private TableColumn<Prescription, User> medicoTableColumn;

    @FXML
    private TableColumn<Prescription, User> pazienteTableColumn;

    @FXML
    private TableColumn<Prescription, Button> azioniTableColumn;

    @FXML
    private Label errorText;

    @FXML
    private Button dettagliButton;


    private ViewDispatcher dispatcher;
    private PrescriptionService prescrizioneService;
    private MedicineService farmacoService;
    private FarmacoPrescrizioneService farmacoPrescrizioneService;

    public PrescrizioniFarmacistaController() {

        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        prescrizioneService = factory.getPrescrizioneService();
        farmacoService = factory.getFarmacoService();

    }


    @Override
    public void initializeData(User user) {
        try {
            List<Prescription> prescrizioni = prescrizioneService.findAllPrescrizioni();
            ObservableList<Prescription> prescrizioniData = FXCollections.observableArrayList(prescrizioni);
            elencoPrescrizioniTable.setItems(prescrizioniData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numeroTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        medicoTableColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        pazienteTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        azioniTableColumn.setStyle("-fx-alignment: CENTER;");
        azioniTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Prescription, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Prescription, Button> param) {
                        final Button evadiButton = new Button("Evadi");

                        evadiButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
//                                Map<Medicine, Integer> dettaglio = new HashMap<>();
//                                dettaglio = farmacoPrescrizioneService.getMedicineFromPrescription(Long.parseLong(numeroTableColumn.getText()));
//                               MedicinePrescription azz = new MedicinePrescription();
                                dispatcher.renderView("dettaglioPrescrizione", param.getValue());
                            }
                        });
                        return new SimpleObjectProperty<Button>(evadiButton);
                    }
                });


    }
}
