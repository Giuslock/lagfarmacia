package it.univaq.disim.oop.farmacialag.domain;

public class Farmaco {

    private String nome;
    private String descrizione;
    private int q_min;
    private int quantita;
    private int f_stock;

    public Farmaco(String nome, String descrizione, int q_min, int quantita, int f_stock) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.q_min = q_min;
        this.quantita = quantita;
        this.f_stock = f_stock;
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

    public int getQ_min() {
        return q_min;
    }

    public void setQ_min(int q_min) {
        this.q_min = q_min;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getF_stock() {
        return f_stock;
    }

    public void setF_stock(int f_stock) {
        this.f_stock = f_stock;
    }
}
