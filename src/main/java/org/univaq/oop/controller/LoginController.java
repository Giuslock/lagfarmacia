package org.univaq.oop.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.LagBusinessFactory;
import org.univaq.oop.business.UtenteNonTrovatoException;
import org.univaq.oop.business.UtenteService;
import org.univaq.oop.domain.Utente;
import org.univaq.oop.view.ViewDispatcher;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    private final ViewDispatcher dispatcher;
    private final UtenteService utenteService;
    @FXML
    private Label labelErrorLogin;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Button loginButton;
    @FXML
    private Button iscrivitiButton;

    public LoginController() {
        dispatcher = ViewDispatcher.getInstance();
        LagBusinessFactory factory = LagBusinessFactory.getInstance();
        utenteService = factory.getUtenteService();
    }

    //se i campi sono vuoti, l'utente non può effettuare l'accesso
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.disableProperty().bind(usernameText.textProperty().isEmpty().or(passwordText.textProperty().isEmpty()));
    }

    //al click del tasto login
    @FXML
    private void loginAction() {
        try {
            //prova ad autenticare l'utente, se lo trova lo restituisce e carica la home page, altrimenti compare il messaggio del catch
            Utente utente = utenteService.autenticazione(usernameText.getText(), passwordText.getText());
            dispatcher.loggedIn(utente);
        } catch (UtenteNonTrovatoException e) {
            labelErrorLogin.setText("Ops! Username e/o password errati!");
        } catch (BusinessException e) {
            dispatcher.renderError(e);
        }

    }

    //al click del tasto iscriviti
    @FXML
    private void iscrivitiAction() {
        dispatcher.iscrivitiView();
    }
}
