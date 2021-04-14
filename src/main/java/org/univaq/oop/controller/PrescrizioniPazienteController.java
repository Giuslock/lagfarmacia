package org.univaq.oop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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


public class PrescrizioniPazienteController  implements DataInitializable<User>, Initializable {

        @FXML
        private Label errorLab;

        @FXML
        private TableView<Prescription> elencoPrescrizioniTable;

        @FXML
        private TableColumn<?, ?> numeroTableColumn;

        @FXML
        private TableColumn<?, ?> idTableColumn;

        @FXML
        private TableColumn<?, ?> dottoreTableColumn;

    private ViewDispatcher dispatcher;
    private PrescriptionService prescrizioneService;
    private MedicineService farmacoService;


    public PrescrizioniPazienteController() throws BusinessException {

        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        prescrizioneService = factory.getPrescrizioneService();
        //farmacoService = factory.getFarmacoService();
    }

    @Override
    public void initializeData(User user) {

        int paziente = Math.toIntExact(user.getId());
        try {
            List<Prescription> prescrizioni = prescrizioneService.findPrescrizioniByPatient(paziente);
            ObservableList<Prescription> prescrizioniData = FXCollections.observableArrayList(prescrizioni);
            elencoPrescrizioniTable.setItems(prescrizioniData);
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
      ;
        dottoreTableColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));

       dottoreTableColumn.setStyle("-fx-alignment: CENTER;");

    }
}



