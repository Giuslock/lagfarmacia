package org.univaq.oop.business.impl.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AppConfigReader {

    public static Properties getProps() {
        Properties properties = new Properties();
        FileInputStream inputStream = null;
        String path = System.getProperty("user.dir").concat("/src/main/resources/app-config");

        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.err.println("Could not locate file or path is invalid");
            e.printStackTrace();
        }

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
