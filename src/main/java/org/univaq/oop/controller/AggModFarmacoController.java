package org.univaq.oop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoService;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.ResourceBundle;

public class AggModFarmacoController implements Initializable, DataInitializable<Farmaco> {

    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
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
    @FXML
    private Label errorlabel;
    private Utente utente;
    private Farmaco farmaco;

    public AggModFarmacoController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salvaButton.disableProperty().bind(descriptiontext.textProperty().isEmpty().or(mimimumtext.textProperty().isEqualTo("0")));
        eliminaButton.disableProperty().bind(descriptiontext.textProperty().isEmpty().or(mimimumtext.textProperty().isEqualTo("0")));

    }

    @Override
    public void initializeData(Farmaco farmaco) {
        this.farmaco = farmaco;
        this.nametext.setText(farmaco.getNome());
        this.descriptiontext.setText(farmaco.getDescrizione());
        this.quantitytext.setText(String.valueOf(farmaco.getQuantita()));
        this.mimimumtext.setText(String.valueOf(farmaco.getMinimo()));

    }

    @FXML
    public void salvaAction() {
        try {
            farmaco.setNome(nametext.getText());
            farmaco.setDescrizione(descriptiontext.getText());
            farmaco.setQuantita(Integer.parseInt(quantitytext.getText()));
            farmaco.setMinimo(Integer.parseInt(mimimumtext.getText()));
            farmaco.setInEsaurimento();
            farmaco.setStatoFarmaco();

            if (farmaco.getId() == null) {
                farmacoService.aggiungiFarmaco(farmaco);

            } else {
                farmacoService.aggiornaFarmaco(farmaco);
            }

            dispatcher.renderView("elencoFarmaci", utente);
        } catch (BusinessException e) {
            errorlabel.setText("Errore nell'aggiunta dei farmaci");
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void eliminaAction() {
        try {
            farmacoService.eliminaFarmaco(farmaco.getId());
            dispatcher.renderView("elencoFarmaci", utente);
        } catch (BusinessException e) {
            this.errorlabel.setText("Il farmaco e' presente in una prescrizione e non e' possibile eliminarlo");
        }

    }
}


