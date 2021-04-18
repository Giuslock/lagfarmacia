package org.univaq.oop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.MedicineService;
import org.univaq.oop.domain.Medicine;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.ResourceBundle;

public class SchedaFarmacoController implements Initializable, DataInitializable<Medicine> {
    private final ViewDispatcher dispatcher;
    private final MedicineService medicineService;
    @FXML
    private TextField nametext;
    @FXML
    private TextField descriptiontext;
    @FXML
    private TextField statusText;
    @FXML
    private Button backButton;
    private User utente;
    private Medicine medicine;

    public SchedaFarmacoController() throws BusinessException {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        medicineService = factory.getFarmacoService();
    }


    public void backAction() {
        dispatcher.renderView("elencoFarmaciPaziente", utente);
    }


    @Override
    public void initializeData(Medicine medicine) {
        this.medicine = medicine;
        this.nametext.setText(medicine.getName());
        this.descriptiontext.setText(medicine.getDescription());

        this.statusText.setText(medicine.getMedicineStatus());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
