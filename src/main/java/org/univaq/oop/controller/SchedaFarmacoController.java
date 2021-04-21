package org.univaq.oop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.univaq.oop.business.FarmacoService;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.ResourceBundle;

public class SchedaFarmacoController implements Initializable, DataInitializable<Farmaco> {
    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    @FXML
    private TextField nome;
    @FXML
    private TextField descrizione;
    @FXML
    private TextField status;
    @FXML
    private Button indietro;
    @FXML
    private Label errore;
    private Utente utente;
    private Farmaco farmaco;

    public SchedaFarmacoController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
    }


    public void backAction() {
        dispatcher.renderView("elencoFarmaciPaziente", utente);
    }


    @Override
    public void initializeData(Farmaco farmaco) {
        this.farmaco = farmaco;
        this.nome.setText(farmaco.getNome());
        this.descrizione.setText(farmaco.getDescrizione());
        this.status.setText(farmaco.getStatoFarmaco());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
