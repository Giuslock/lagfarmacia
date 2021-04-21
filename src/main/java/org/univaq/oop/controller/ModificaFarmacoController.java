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

public class ModificaFarmacoController implements Initializable, DataInitializable<Farmaco> {

    private final ViewDispatcher dispatcher;
    private final FarmacoService farmacoService;
    @FXML
    private TextField nome;
    @FXML
    private TextField descrizione;
    @FXML
    private TextField mimimo;
    @FXML
    private TextField quantità;
    @FXML
    private Button salva;
    @FXML
    private Button elimina;
    @FXML
    private Label errore;

    private Utente utente;
    private Farmaco farmaco;

    public ModificaFarmacoController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        farmacoService = factory.getFarmacoService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salva.disableProperty().bind(descrizione.textProperty().isEmpty().or(mimimo.textProperty().isEqualTo("0")));
        elimina.disableProperty().bind(descrizione.textProperty().isEmpty().or(mimimo.textProperty().isEqualTo("0")));

    }

    @Override
    public void initializeData(Farmaco farmaco) {
        this.farmaco = farmaco;
        this.nome.setText(farmaco.getNome());
        this.descrizione.setText(farmaco.getDescrizione());
        this.quantità.setText(String.valueOf(farmaco.getQuantita()));
        this.mimimo.setText(String.valueOf(farmaco.getMinimo()));

    }

    @FXML
    public void salvaAction() {
        try {
            farmaco.setNome(nome.getText());
            farmaco.setDescrizione(descrizione.getText());
            farmaco.setQuantita(Integer.parseInt(quantità.getText()));
            farmaco.setMinimo(Integer.parseInt(mimimo.getText()));
            farmaco.setInEsaurimento();
            farmaco.setStatoFarmaco();

            if (farmaco.getId() == null) {
                farmacoService.aggiungiFarmaco(farmaco);

            } else {
                farmacoService.aggiornaFarmaco(farmaco);
            }

            dispatcher.renderView("elencoFarmaciAmministratore", utente);
        } catch (BusinessException e) {
            errore.setText("Errore nell'aggiunta dei farmaci");
            dispatcher.renderError(e);
        }
    }

    @FXML
    public void eliminaAction() {
        try {
            farmacoService.eliminaFarmaco(farmaco.getId());
            dispatcher.renderView("elencoFarmaciAmministratore", utente);
        } catch (BusinessException e) {
            this.errore.setText("Il farmaco e' presente in una prescrizione e non e' possibile eliminarlo");
        }

    }
}


