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
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FarmaciController implements Initializable, DataInitializable<User> {

    @FXML
    private TableView<Medicine> elencoFarmaciTable;

    @FXML
    private TableColumn<Medicine, String> nomeTableColumn;

    @FXML
    private TableColumn<Medicine, String> codiceTableColumn;

    @FXML
    private TableColumn<Medicine, Integer> minimumTableColumn;

    @FXML
    private TableColumn<Medicine, Integer> quantityTableColumn1;

    @FXML
    private TableColumn<Medicine, String> statoTableColumn;

    @FXML
    private TableColumn<Medicine, Button> azioniTableColumn;

    @FXML
    private Button aggiungiButton;

    private final ViewDispatcher dispatcher;
    private final MedicineService farmacoService;


    public FarmaciController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        codiceTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        minimumTableColumn.setCellValueFactory(new PropertyValueFactory<>("minimum"));
        quantityTableColumn1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        statoTableColumn.setCellValueFactory(new PropertyValueFactory<>("medicineStatus"));
        azioniTableColumn.setStyle("-fx-alignment: CENTER;");
        azioniTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Medicine, Button>, ObservableValue<Button>>() {
                    @Override
                    public ObservableValue<Button> call(TableColumn.CellDataFeatures<Medicine, Button> param) {
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

    @Override
    public void initializeData(User user) {
        try {
            List<Medicine> farmaci = farmacoService.findAllFarmaci();
            ObservableList<Medicine> farmaciData = FXCollections.observableArrayList(farmaci);
            elencoFarmaciTable.setItems(farmaciData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }


    public void aggiungiAction(ActionEvent event) {
        Medicine farmacoVuoto = new Medicine();
        dispatcher.renderView("modificaFarmaco", farmacoVuoto);
    }

}
