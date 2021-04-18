package org.univaq.oop.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.univaq.oop.controller.DataInitializable;
import org.univaq.oop.domain.User;

import java.io.IOException;

public class ViewDispatcher {

    private static final ViewDispatcher instance = new ViewDispatcher();

    private Stage stage;
    private BorderPane layout;

    private ViewDispatcher() {
    }

    public static ViewDispatcher getInstance() {
        return instance;
    }

    //metodo che carica la pagina di login
    public void loginView(Stage stage) throws ViewException {
        try {
            this.stage = stage;
            FXMLLoader startPage = new FXMLLoader(getClass().getResource("/viste/login.fxml"));
            Parent login = startPage.load();
            Scene scene = new Scene(login);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new ViewException(e);
        }
    }

    //metodo che carica la pagina di iscrizione
    public void iscrivitiView() {
        try {
            Parent loginView = loadView("iscrizione").getView();
            Scene scene = new Scene(loginView);
            stage.setScene(scene);
        } catch (ViewException e) {
            renderError(e);
        }
    }

    //metodo che carica la home page layout
    public void loggedIn(User utente) {
        try {
            View<User> layoutView = loadView("layout");
            DataInitializable<User> layoutController = layoutView.getController();
            layoutController.initializeData(utente);
            layout = (BorderPane) layoutView.getView();
            renderView("home", utente);
            Scene scene = new Scene(layout);
            stage.setScene(scene);
        } catch (ViewException e) {
            renderError(e);
        }
    }

    //metodo che esce e torna alla login
    public void logout() throws ViewException {
        try {
            FXMLLoader startPage = new FXMLLoader(getClass().getResource("/viste/login.fxml"));
            Parent login = startPage.load();
            Scene scene = new Scene(login);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new ViewException(e);
        }
    }

    //carica una generica vista
    public <T> void renderView(String viewName, T data) {
        try {
            View<T> view = loadView(viewName);
            DataInitializable<T> controller = view.getController();
            controller.initializeData(data);
            layout.setCenter(view.getView());
        } catch (ViewException e) {
            renderError(e);
        }
    }

    public void renderError(Exception e) {
        e.printStackTrace();
        System.exit(1);
    }

    //prende una vista dalla cartella per caricarla
    private <T> View<T> loadView(String viewName) throws ViewException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/" + viewName + ".fxml"));
            Parent parent = loader.load();
            return new View<T>(parent, loader.getController());
        } catch (IOException ex) {
            throw new ViewException(ex);
        }
    }

}