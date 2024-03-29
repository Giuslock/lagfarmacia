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

public class ElencoFarmaciPazienteController implements DataInitializable<Utente>, Initializable {
    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    @FXML
    private TableView<Farmaco> tabellaElencoFarmaci;
    @FXML
    private TableColumn<Farmaco, String> colonnaNome;
    @FXML
    private TableColumn<Farmaco, String> colonnaDescrizione;
    @FXML
    private TableColumn<Farmaco, Button> colonnaAzioni;

    @FXML
    private Label errorlabel;


    public ElencoFarmaciPazienteController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
    }

    @Override
    public void initializeData(Utente utente) {
        try {
            List<Farmaco> farmaci = farmacoService.trovaTuttiFarmaci();
            ObservableList<Farmaco> farmaciData = FXCollections.observableArrayList(farmaci);
            tabellaElencoFarmaci.setItems(farmaciData);
        } catch (BusinessException e) {
            errorlabel.setText("Errore nella ricerca dei farmaci");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        colonnaAzioni.setStyle("-fx-alignment: CENTER;");
        colonnaAzioni.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Farmaco, Button> param) {
                        final Button farmaciButton = new Button("Scheda");
                        farmaciButton.setOnAction(new EventHandler<>() {
                                                      @Override
                                                      public void handle(ActionEvent event) {
                                                          //carica vista per modificare il farmaco
                                                          dispatcher.renderView("schedaFarmaco", param.getValue());
                                                      }
                                                  }
                        );
                        return new SimpleObjectProperty<>(farmaciButton);
                    }

                });

    }
}
