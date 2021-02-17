package it.univaq.disim.oop.farmacialag.domain;

public class Dottore extends Paziente {

    private String username;
    private String password;
    private String nome;
    private String cognome;

    public Dottore(String username, String password, String nome, String cognome, String username1, String password1, String nome1, String cognome1) {
        super(username, password, nome, cognome);
        this.username = username1;
        this.password = password1;
        this.nome = nome1;
        this.cognome = cognome1;
    }


}
