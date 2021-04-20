package org.univaq.oop.domain;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Utente implements Serializable {

    private Long id;

    private String nome;

    private String cognome;

    private String username;

    private String password;

    private Ruolo ruolo;

    private String codiceFiscale;

    private List<Prescrizione> prescrizioni;

    public Utente() {
    }

    public Utente(String nome, String cognome, String username, String password, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Utente(Long id, String nome, String cognome, String username, String password, Ruolo ruolo, String codiceFiscale) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.codiceFiscale = codiceFiscale;
    }

    public List<Prescrizione> getPrescrizioni() {
        return prescrizioni;
    }

    public void setPrescrizioni(List<Prescrizione> prescrizioni) {
        this.prescrizioni = prescrizioni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + nome + '\'' +
                ", surname='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + ruolo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return getId().equals(utente.getId()) &&
                getUsername().equals(utente.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername());
    }
}
