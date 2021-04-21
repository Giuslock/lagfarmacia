package org.univaq.oop.controller;

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

public class iscrizioneController implements Initializable, DataInitializable<Utente> {

    private final ViewDispatcher dispatcher;
    private final UtenteService utenteService;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField codiceFiscale;
    @FXML
    private TextField usernameIscriviti;
    @FXML
    private PasswordField passwordIscriviti;
    @FXML
    private Button iscriviti;
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
    private Label errore;


    public iscrizioneController() {
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

        iscriviti.disableProperty().bind(usernameIscriviti.textProperty().isEmpty()
                .or(passwordIscriviti.textProperty().isEmpty()
                        .or(nome.textProperty().isEmpty()
                                .or(cognome.textProperty().isEmpty()
                                        .or(codiceFiscale.textProperty().isEmpty())))));
    }

    @FXML
    public void iscrivitiAction() throws ViewException {

        Utente utente = new Utente();
        utente.setNome(nome.getText());
        utente.setCognome(cognome.getText());
        utente.setCodiceFiscale(codiceFiscale.getText());
        utente.setUsername(usernameIscriviti.getText());
        utente.setPassword(passwordIscriviti.getText());
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
            errore.setText("CODICE FISCALE E/O USERNAME USATO");
        }
    }


    @FXML
    public void tornaHomeAction() throws ViewException {
        dispatcher.logout();

    }

}

