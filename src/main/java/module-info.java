module org.univaq.oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens org.univaq.oop to javafx.fxml;
    opens org.univaq.oop.controller to javafx.fxml;

    exports org.univaq.oop;
    exports org.univaq.oop.controller;
    exports org.univaq.oop.domain;
    exports org.univaq.oop.business;
}
