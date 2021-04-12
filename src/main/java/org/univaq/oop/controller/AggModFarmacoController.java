package org.univaq.oop.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
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
    private TextField outofstocktext;

    @FXML
    private Button salvaButton;

    @FXML
    private Button eliminaButton;


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

        this.quantitytext.setText(String.valueOf(medicine.getMinimum()));
        this.mimimumtext.setText(String.valueOf(medicine.getMinimum()));
        this.outofstocktext.setText(String.valueOf(medicine.getMedicineStatus()));
    }


    //al click del tasto salva
    @FXML
    public void salvaAction(ActionEvent event) throws ViewException {
        try {
            medicine.setName(nametext.getText());
            medicine.setDescription(descriptiontext.getText());

            medicine.setQuantity(Integer.parseInt(quantitytext.getText()));
            medicine.setMinimum(Integer.parseInt(mimimumtext.getText()));
            //medicine.setOutOfStock(Integer.parseInt(outofstocktext.getText()));

            if( medicine.getId() == null) {
                //MedicineService.addFarmaco(medicine);
            }
            else {
                medicineService.updateFarmaco(medicine);
            }
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void eliminaAction(ActionEvent event) throws BusinessException {
        Long id  = medicine.getId();
        medicineService.deleteFarmaco(id);
    }
}


