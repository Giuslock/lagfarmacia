package org.univaq.oop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.UtenteService;
import org.univaq.oop.domain.Ruolo;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;
import org.univaq.oop.view.ViewException;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable, DataInitializable<Utente> {

    private final ViewDispatcher dispatcher;
    private final UtenteService utenteService;
    @FXML
    private TextField nomeText;
    @FXML
    private TextField cognomeText;
    @FXML
    private TextField codiceFiscaleText;
    @FXML
    private TextField usernameIscrivitiText;
    @FXML
    private PasswordField passwordIscrivitiText;
    @FXML
    private Button iscrivitiButton;
    @FXML
    private Button HomeButton;
    @FXML
    private RadioButton medicoRadioButton;
    @FXML
    private RadioButton pazienteRadioButton;
    @FXML
    private RadioButton farmacistaRadioButton;
    @FXML
    private RadioButton amministratoreRadioButton;
    @FXML
    private Label errorlabel;


    public RegisterController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        utenteService = factory.getUtenteService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final ToggleGroup group = new ToggleGroup();

        medicoRadioButton.setToggleGroup(group);
        medicoRadioButton.setSelected(true);

        pazienteRadioButton.setToggleGroup(group);
        farmacistaRadioButton.setToggleGroup(group);

        iscrivitiButton.disableProperty().bind(usernameIscrivitiText.textProperty().isEmpty()
                .or(passwordIscrivitiText.textProperty().isEmpty()
                        .or(nomeText.textProperty().isEmpty()
                                .or(cognomeText.textProperty().isEmpty()
                                        .or(codiceFiscaleText.textProperty().isEmpty())))));
    }

    @FXML
    public void iscrivitiAction() throws ViewException {

        Utente utente = new Utente();
        utente.setNome(nomeText.getText());
        utente.setCognome(cognomeText.getText());
        utente.setCodiceFiscale(codiceFiscaleText.getText());
        utente.setUsername(usernameIscrivitiText.getText());
        utente.setPassword(passwordIscrivitiText.getText());
        if (amministratoreRadioButton.isSelected())
            utente.setRuolo(Ruolo.AMMINISTRATORE);
        else if (medicoRadioButton.isSelected())
            utente.setRuolo(Ruolo.DOTTORE);
        else if (farmacistaRadioButton.isSelected())
            utente.setRuolo(Ruolo.FARMACISTA);
        else
            utente.setRuolo(Ruolo.PAZIENTE);
        try {
            utenteService.aggiungiUtente(utente);
            dispatcher.logout();
        } catch (BusinessException e) {
            errorlabel.setText("CODICE FISCALE E/O USERNAME USATO");
        }
    }


    @FXML
    public void tornaHomeAction() throws ViewException {
        dispatcher.logout();

    }

}

