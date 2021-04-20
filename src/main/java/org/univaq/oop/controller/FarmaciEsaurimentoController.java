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

public class FarmaciEsaurimentoController implements Initializable, DataInitializable<Utente> {

    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    @FXML
    private TableView<Farmaco> elencoFarmaciTable;
    @FXML
    private TableColumn<Farmaco, String> nomeTableColumn;
    @FXML
    private TableColumn<Farmaco, String> codiceTableColumn;
    @FXML
    private TableColumn<Farmaco, String> statoTableColumn;
    @FXML
    private TableColumn<Farmaco, String> disponibilitaTableColumn;
    @FXML
    private TableColumn<Farmaco, String> qtaMinimaTableColumn;
    @FXML
    private TableColumn<Farmaco, Button> azioniTableColumn;


    public FarmaciEsaurimentoController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
    }

    @Override
    public void initializeData(Utente utente) {
        try {
            List<Farmaco> farmaci = farmacoService.trovaFarmaciInEsaurimento();
            ObservableList<Farmaco> farmaciData = FXCollections.observableArrayList(farmaci);
            elencoFarmaciTable.setItems(farmaciData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        codiceTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        statoTableColumn.setCellValueFactory(new PropertyValueFactory<>("statoFarmaco"));
        disponibilitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        qtaMinimaTableColumn.setCellValueFactory(new PropertyValueFactory<>("minimo"));
        azioniTableColumn.setStyle("-fx-alignment: CENTER;");
        azioniTableColumn.setCellValueFactory(
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
