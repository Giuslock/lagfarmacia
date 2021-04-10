package org.univaq.oop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.UserService;
import org.univaq.oop.domain.Role;
import org.univaq.oop.domain.User;
import org.univaq.oop.view.ViewDispatcher;
import org.univaq.oop.view.ViewException;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable, DataInitializable<User> {

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

    private ViewDispatcher dispatcher;
    private UserService utenteService;


    public RegisterController() throws BusinessException {
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

        iscrivitiButton.disableProperty().bind(usernameIscrivitiText.textProperty().isEmpty()
                .or(passwordIscrivitiText.textProperty().isEmpty()
                .or(nomeText.textProperty().isEmpty()
                .or(cognomeText.textProperty().isEmpty()
                .or(codiceFiscaleText.textProperty().isEmpty())))));
    }

    @FXML
    public void iscrivitiAction(ActionEvent event) throws BusinessException, ViewException {
        //se è selezionato il radio button Medico, crea un utente medico
        if (medicoRadioButton.isSelected()) {
            User utente = new User();
            utente.setName(nomeText.getText());
            utente.setSurname(cognomeText.getText());
            utente.setFiscalCode(codiceFiscaleText.getText());
            utente.setUsername(usernameIscrivitiText.getText());
            utente.setPassword(passwordIscrivitiText.getText());
            utente.setRole(Role.DOCTOR);
            try {
                //aggiunge l'utente nell'elenco degli utenti e torna alla schermata di login
                utenteService.addUser(utente);
                dispatcher.logout();
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void tornaHomeAction(ActionEvent event) throws ViewException {
        dispatcher.logout();

    }

}

