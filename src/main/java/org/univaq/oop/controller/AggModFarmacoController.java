package org.univaq.oop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoInPrescrizioneException;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.FarmacoService;
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.ResourceBundle;

public class AggModFarmacoController implements Initializable, DataInitializable<Farmaco> {

    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    @FXML
    private Label errorMessage;
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
    private Utente utente;
    private Farmaco farmaco;

    public AggModFarmacoController() throws BusinessException {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    //vengono caricati i dati dei farmaci nelle textfield
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
            errorMessage.setText("Problemi di connessione al Database");
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void eliminaAction()  {
        Long id = farmaco.getId();
        try {
            farmacoService.eliminaFarmaco(id);
            dispatcher.renderView("elencoFarmaci", utente);
        } catch (BusinessException e) {
            this.errorMessage.setText("Il farmaco e' presente in una prescrizione e non e' possibile eliminarlo");
        }

    }
}


