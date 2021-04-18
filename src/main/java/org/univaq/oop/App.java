package org.univaq.oop;

import javafx.application.Application;
import javafx.stage.Stage;
import org.univaq.oop.view.ViewDispatcher;
import org.univaq.oop.view.ViewException;


/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            ViewDispatcher viewDispatcher = ViewDispatcher.getInstance();
            viewDispatcher.loginView(stage);
        } catch (ViewException e) {
            e.printStackTrace();
        }
    }


}
