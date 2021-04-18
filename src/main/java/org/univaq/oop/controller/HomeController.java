package org.univaq.oop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.univaq.oop.domain.User;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable, DataInitializable<User> {

    @FXML
    private Label benvenutoLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void initializeData(User utente) {
    }
}

