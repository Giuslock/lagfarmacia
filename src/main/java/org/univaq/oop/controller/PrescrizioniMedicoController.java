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
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.business.PrescriptionService;
import org.univaq.oop.domain.Prescription;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrescrizioniMedicoController implements DataInitializable<User>, Initializable {

    private final ViewDispatcher dispatcher;
    private final PrescriptionService prescrizioneService;
    private final MedicineService farmacoService;
    @FXML
    private Label errorLab;
    @FXML
    private TableView<Prescription> elencoPrescrizioniTable;
    @FXML
    private TableColumn<?, ?> numeroTableColumn;
    @FXML
    private TableColumn<?, ?> idTableColumn;
    @FXML
    private TableColumn<?, ?> descriptionTableColumn;
    @FXML
    private TableColumn<Prescription, Button> azioniTableColumn1;
    @FXML
    private TableColumn<?, ?> pazienteTableColumn;
    @FXML
    private Label pazienteLab;
    @FXML
    private Button modificaButton;
    @FXML
    private Button aggiungiButton;
    private User utente;

    public PrescrizioniMedicoController() {

        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        prescrizioneService = factory.getPrescrizioneService();
        farmacoService = factory.getFarmacoService();
    }


    @Override
    public void initializeData(User user) {
        this.utente = user;
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
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        pazienteTableColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        azioniTableColumn1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        azioniTableColumn1.setStyle("-fx-alignment: CENTER;");
        azioniTableColumn1.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Prescription, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Prescription, Button> param) {
                        final Button updateButton = new Button("Modifica");

                        updateButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                dispatcher.renderView("modificaPrescrizione", param.getValue());

                            }
                        });
                        return new SimpleObjectProperty<Button>(updateButton);
                    }
                });
    }

    @FXML
    public void aggiungiAction() {
        Prescription voidPrescription = new Prescription();
        dispatcher.renderView("CreaPrescrizione", utente);
    }


    @FXML
    public void modificaCercataAction() {
        Prescription prescription = new Prescription();


        prescription.setUserId(Integer.parseInt(pazienteLab.getText()));
        prescription.setDoctorId(Integer.parseInt(azioniTableColumn1.getText()));
        dispatcher.renderView("modificaPrescrizione", prescription);
    }
}






