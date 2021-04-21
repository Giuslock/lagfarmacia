package org.univaq.oop.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.univaq.oop.domain.Ruolo;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;
import org.univaq.oop.view.ViewException;

import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable, DataInitializable<Utente> {

    //creazione degli elementi che faranno parte del menu per ogni utente
    private static final ElementiDelMenu MENU_HOME = new ElementiDelMenu("Home", "home");

    private static final ElementiDelMenu[] MENU_AMMINISTRATORI = {new ElementiDelMenu("Farmaci", "elencoFarmaciAmministratore"),
            new ElementiDelMenu("In esaurimento", "farmaciInEsaurimento")};
    private static final ElementiDelMenu MENU_FARMACISTI = new ElementiDelMenu("Prescrizioni", "elencoTuttePrescrizioni");

    private static final ElementiDelMenu[] MENU_PAZIENTI = {new ElementiDelMenu("Prescrizioni", "elencoPrescrizioniPaziente"),
            new ElementiDelMenu("Farmaci", "elencoFarmaciPaziente")};

    private static final ElementiDelMenu MENU_MEDICI = new ElementiDelMenu("Prescrizioni", "prescrizioniMedico");
    private final ViewDispatcher dispatcher;
    private Utente utente;
    @FXML
    private VBox menuBar;

    public LayoutController() {
        dispatcher = ViewDispatcher.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    //crea il menu in base all'utente
    @Override
    public void initializeData(Utente utente) {
        this.utente = utente;
        menuBar.getChildren().addAll(createButton(MENU_HOME));
        menuBar.getChildren().add(new Separator());

        if (utente.getRuolo().equals(Ruolo.AMMINISTRATORE)) {
            for (ElementiDelMenu menu : MENU_AMMINISTRATORI) {
                menuBar.getChildren().add(createButton(menu));
            }
        }
        if (utente.getRuolo().equals(Ruolo.FARMACISTA)) {
            menuBar.getChildren().add(createButton(MENU_FARMACISTI));
        }
        if (utente.getRuolo().equals(Ruolo.PAZIENTE)) {
            for (ElementiDelMenu menu : MENU_PAZIENTI) {
                menuBar.getChildren().add(createButton(menu));
            }
        }
        if (utente.getRuolo().equals(Ruolo.DOTTORE)) {
            menuBar.getChildren().add(createButton(MENU_MEDICI));
        }
    }

    //torna al login
    @FXML
    public void esciAction(MouseEvent event) throws ViewException {
        dispatcher.logout();

    }

    //crea i bottoni per ogni voce del menu
    private Button createButton(ElementiDelMenu viewItem) {
        Button button = new Button(viewItem.getNome());
        button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;");
        button.setTextFill(Paint.valueOf("white"));
        button.setPrefHeight(10);
        button.setPrefWidth(180);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //dispatcher.renderView(viewItem.getVista());
                dispatcher.renderView(viewItem.getVista(), utente);
            }
        });
        return button;
    }

}
