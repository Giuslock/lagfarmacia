package it.univaq.disim.oop.farmacialag.domain;

public class Prescrizione {

    private String paziente;
    private Farmaco[] lista;
    private boolean evasa;

    public Prescrizione(String paziente, Farmaco[] lista, boolean evasa) {
        this.paziente = paziente;
        this.lista = lista;
        this.evasa = evasa;
    }

    public String getPaziente() {
        return paziente;
    }

    public void setPaziente(String paziente) {
        this.paziente = paziente;
    }

    public Farmaco[] getLista() {
        return lista;
    }

    public void setLista(Farmaco[] lista) {
        this.lista = lista;
    }

    public boolean isEvasa() {
        return evasa;
    }

    public void setEvasa(boolean evasa) {
        this.evasa = evasa;
    }
}
