package org.univaq.oop.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.univaq.oop.business.*;
import org.univaq.oop.domain.Prescription;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;

import java.util.List;
import java.util.ResourceBundle;

import java.net.URL;

public class PrescrizioniMedicoController implements DataInitializable <User>, Initializable {

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
        private TableColumn<?, ?> azioniTableColumn1;

        @FXML
        private TableColumn<?, ?> pazienteTableColumn;


        @FXML
        private Label pazienteLab;

        @FXML
        private Button modificaButton;

        @FXML
        private Button aggiungiButton;

        @FXML
        void aggiungiAction(Event event) {

        }

        @FXML
        void modificaCercataAction(Event event) {

        }

        private ViewDispatcher dispatcher;
        private PrescriptionService prescrizioneService;
        private MedicineService farmacoService;

        public PrescrizioniMedicoController() {

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
            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            pazienteTableColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
            azioniTableColumn1.setCellValueFactory(new PropertyValueFactory<>("userId"));
            azioniTableColumn1.setStyle("-fx-alignment: CENTER;");
        }

        @FXML
        public void aggiungiAction() {
            Prescription voidPrescription = new Prescription();
            //   voidPrescription.setDoctorId(user.getId());
            dispatcher.renderView("modificaPrescrizione", voidPrescription);
        }





    @FXML
    public void modificaCercataAction() {
        Prescription prescription = new Prescription();


        prescription.setUserId(Integer.parseInt(pazienteLab.getText()));
        prescription.setDoctorId(Integer.parseInt(azioniTableColumn1.getText()));
        dispatcher.renderView("modificaPrescrizione", prescription);
    }
    }






