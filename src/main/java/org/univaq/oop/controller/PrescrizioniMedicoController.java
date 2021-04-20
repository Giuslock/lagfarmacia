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
import org.univaq.oop.business.FarmacoService;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.PrescrizioneService;
import org.univaq.oop.domain.Prescrizione;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrescrizioniMedicoController implements DataInitializable<Utente>, Initializable {

    private final ViewDispatcher dispatcher;
    private final PrescrizioneService prescrizioneService;
    private final FarmacoService farmacoService;
    @FXML
    private Label errorLab;
    @FXML
    private TableView<Prescrizione> elencoPrescrizioniTable;
    @FXML
    private TableColumn<?, ?> numeroTableColumn;
    @FXML
    private TableColumn<?, ?> idTableColumn;
    @FXML
    private TableColumn<?, ?> descriptionTableColumn;
    @FXML
    private TableColumn<Prescrizione, Button> azioniTableColumn1;
    @FXML
    private TableColumn<?, ?> pazienteTableColumn;
    @FXML
    private Label pazienteLab;
    @FXML
    private Button modificaButton;
    @FXML
    private Button aggiungiButton;
    private Utente utente;

    public PrescrizioniMedicoController() {

        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        prescrizioneService = factory.getPrescrizioneService();
        farmacoService = factory.getFarmacoService();
    }


    @Override
    public void initializeData(Utente utente) {
        this.utente = utente;
        try {
            List<Prescrizione> prescrizioni = prescrizioneService.trovaTuttePrescrizioni();
            ObservableList<Prescrizione> prescrizioniData = FXCollections.observableArrayList(prescrizioni);
            elencoPrescrizioniTable.setItems(prescrizioniData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        pazienteTableColumn.setCellValueFactory(new PropertyValueFactory<>("codiceDottore"));
        azioniTableColumn1.setCellValueFactory(new PropertyValueFactory<>("codicePaziente"));
        azioniTableColumn1.setStyle("-fx-alignment: CENTER;");
        azioniTableColumn1.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Prescrizione, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Prescrizione, Button> param) {
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
        Prescrizione voidPrescrizione = new Prescrizione();
        dispatcher.renderView("CreaPrescrizione", utente);
    }


    @FXML
    public void modificaCercataAction() {
        Prescrizione prescrizione = new Prescrizione();


        prescrizione.setCodicePaziente(Integer.parseInt(pazienteLab.getText()));
        prescrizione.setCodiceDottore(Integer.parseInt(azioniTableColumn1.getText()));
        dispatcher.renderView("modificaPrescrizione", prescrizione);
    }
}






