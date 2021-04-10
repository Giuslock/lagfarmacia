package org.univaq.oop.controller;

import java.net.URL;
import java.util.ResourceBundle;

public interface DataInitializable<T> {

    default void initializeData(T t) {

    }

    void initialize(URL location, ResourceBundle resources);

}
