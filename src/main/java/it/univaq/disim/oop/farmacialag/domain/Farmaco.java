package it.univaq.disim.oop.farmacialag.domain;

public class Farmaco {

    private String nome;
    private String descrizione;
    private String scheda;
    private boolean stock;

    public Farmaco(String nome, String descrizione, String scheda, boolean stock) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.scheda = scheda;
        this.stock = stock;
    }

    public Farmaco(String nome, String scheda, boolean stock) {
        this.nome = nome;
        this.scheda = scheda;
        this.stock = stock;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getScheda() {
        return scheda;
    }

    public void setScheda(String scheda) {
        this.scheda = scheda;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }
}
