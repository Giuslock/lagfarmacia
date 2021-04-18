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
import org.univaq.oop.domain.Role;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;
import org.univaq.oop.view.ViewException;

import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable, DataInitializable<User> {

    //creazione degli elementi che faranno parte del menu per ogni utente
    private static final MenuElement MENU_HOME = new MenuElement("Home", "home");

    private static final MenuElement[] MENU_AMMINISTRATORI = {new MenuElement("Farmaci", "elencoFarmaci"),
            new MenuElement("In esaurimento", "esaurimento")};
    private static final MenuElement MENU_FARMACISTI = new MenuElement("Prescrizioni", "elencoTuttePrescrizioni");

    private static final MenuElement[] MENU_PAZIENTI = {new MenuElement("Prescrizioni", "elencoPrescrizioni"),
            new MenuElement("Farmaci", "elencoFarmaciPaziente")};

    private static final MenuElement MENU_MEDICI = new MenuElement("Prescrizioni", "prescrizioniMedico");
    private final ViewDispatcher dispatcher;
    private User utente;
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
    public void initializeData(User utente) {
        this.utente = utente;
        menuBar.getChildren().addAll(createButton(MENU_HOME));
        menuBar.getChildren().add(new Separator());

        if (utente.getRole().equals(Role.ADMIN)) {
            for (MenuElement menu : MENU_AMMINISTRATORI) {
                menuBar.getChildren().add(createButton(menu));
            }
        }
        if (utente.getRole().equals(Role.PHARMACIST)) {
            menuBar.getChildren().add(createButton(MENU_FARMACISTI));
        }
        if (utente.getRole().equals(Role.PATIENT)) {
            for (MenuElement menu : MENU_PAZIENTI) {
                menuBar.getChildren().add(createButton(menu));
            }
        }
        if (utente.getRole().equals(Role.DOCTOR)) {
            menuBar.getChildren().add(createButton(MENU_MEDICI));
        }
    }

    //torna al login
    @FXML
    public void esciAction(MouseEvent event) throws ViewException {
        dispatcher.logout();

    }

    //crea i bottoni per ogni voce del menu
    private Button createButton(MenuElement viewItem) {
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
