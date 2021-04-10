## Requirements
### JDK 11
### Maven
### MySQL 8

## ISTRUZIONI (Linux / MacOS)
- Rinominare il file di configurazione in **src/main/resources/app-config.example** in **app-config**
```
mv src/main/resources/app-config.example src/main/resources/app-config
```
- Bisogna aggiungere le proprie credenziali e il nome del database nel file **app-config** che si trova in **src/main/resources** 
- Eseguire il programma
``` 
mvn javafx:run
 ```
- Dopo la prima esecuzione, bisogna commentare nel metodo main() del file App.java le seguenti righe
```
//        initDB();
//        DatabaseSeeder databaseSeeder = new DatabaseSeeder();
//        databaseSeeder.seed();
```


