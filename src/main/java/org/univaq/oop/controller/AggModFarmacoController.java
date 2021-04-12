package org.univaq.oop.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;
import org.univaq.oop.view.ViewException;

import java.net.URL;
import java.util.ResourceBundle;

public class AggModFarmacoController implements Initializable, DataInitializable <Medicine> {

    @FXML
    private TextField nametext;

    @FXML
    private TextField descriptiontext;


    @FXML
    private TextField mimimumtext;

    @FXML
    private TextField quantitytext;

    @FXML
    private Button salvaButton;

    @FXML
    private Button eliminaButton;

    private User utente;
    private Medicine medicine;
    private ViewDispatcher dispatcher;
    private MedicineService medicineService;

    public AggModFarmacoController() throws BusinessException {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        medicineService = factory.getFarmacoService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    //vengono caricati i dati dei farmaci nelle textfield
    @Override
    public void initializeData(Medicine medicine) {
        this.medicine = medicine;
        this.nametext.setText(medicine.getName());
        this.descriptiontext.setText(medicine.getDescription());
        this.quantitytext.setText(String.valueOf(medicine.getQuantity()));
        this.mimimumtext.setText(String.valueOf(medicine.getMinimum()));

    }

    // id, data, idFarmaco1, qtyFarmaco2, idFaramco2....

    //al click del tasto salva
    @FXML
    public void salvaAction()  {
        try {
            medicine.setName(nametext.getText());
            medicine.setDescription(descriptiontext.getText());
            medicine.setQuantity(Integer.parseInt(quantitytext.getText()));
            medicine.setMinimum(Integer.parseInt(mimimumtext.getText()));
            medicine.setOutOfStock();
            medicine.setStatoFarmaco();

            if( medicine.getId() == null) {
                medicineService.addFarmaco(medicine);
            }

            else {
                medicineService.updateFarmaco(medicine);
            }

            dispatcher.renderView("elencoFarmaci",utente);
        } catch (BusinessException e) {

            dispatcher.renderError(e);
        }
    }

    @FXML
    public void eliminaAction() throws BusinessException {
        Long id  = medicine.getId();
        medicineService.deleteFarmaco(id);
    }
}


