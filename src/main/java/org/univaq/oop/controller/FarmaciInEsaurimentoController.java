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
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FarmaciInEsaurimentoController implements Initializable, DataInitializable<Utente> {

    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    @FXML
    private TableView<Farmaco> tabellaElencoFarmaci;
    @FXML
    private TableColumn<Farmaco, String> colonnaNome;
    @FXML
    private TableColumn<Farmaco, String> colonnaCodice;
    @FXML
    private TableColumn<Farmaco, String> colonnaStato;
    @FXML
    private TableColumn<Farmaco, String> colonnaDisponibilità;
    @FXML
    private TableColumn<Farmaco, String> colonnaMinimo;
    @FXML
    private TableColumn<Farmaco, Button> colonnaAzioni;
    @FXML
    private Label errore;


    public FarmaciInEsaurimentoController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
    }

    @Override
    public void initializeData(Utente utente) {
        try {
            List<Farmaco> farmaci = farmacoService.trovaFarmaciInEsaurimento();
            ObservableList<Farmaco> farmaciData = FXCollections.observableArrayList(farmaci);
            tabellaElencoFarmaci.setItems(farmaciData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaCodice.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonnaStato.setCellValueFactory(new PropertyValueFactory<>("statoFarmaco"));
        colonnaDisponibilità.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        colonnaMinimo.setCellValueFactory(new PropertyValueFactory<>("minimo"));
        colonnaAzioni.setStyle("-fx-alignment: CENTER;");
        colonnaAzioni.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Farmaco, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Farmaco, Button> param) {
                        final Button farmaciButton = new Button("Modifica");
                        farmaciButton.setOnAction(new EventHandler<ActionEvent>() {
                                                      @Override
                                                      public void handle(ActionEvent event) {
                                                          //carica vista per modificare il farmaco
                                                          dispatcher.renderView("modificaFarmaco", param.getValue());
                                                      }
                                                  }
                        );
                        return new SimpleObjectProperty<Button>(farmaciButton);
                    }
                });

    }
}
