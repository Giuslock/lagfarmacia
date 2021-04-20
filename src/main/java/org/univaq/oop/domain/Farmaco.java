package org.univaq.oop.domain;

import java.io.Serializable;
import java.util.Objects;

public class Farmaco implements Serializable {

    private Long id;

    private String nome;

    private String descrizione;

    private int minimo;

    private int quantita;

    private boolean inEsaurimento;

    private String statoFarmaco;

    public Farmaco() {
    }

    public Farmaco(String nome, String descrizione, int minimo, int quantita) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.minimo = minimo;
        this.quantita = quantita;
    }

    public Farmaco(Long id, String nome, String descrizione, int minimo, int quantita, boolean inEsaurimento) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.minimo = minimo;
        this.quantita = quantita;
        this.inEsaurimento = inEsaurimento;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public boolean isInEsaurimento() {
        return inEsaurimento;
    }

    public void setInEsaurimento() {
        this.inEsaurimento = quantita <= minimo;
    }

    public void setStatoFarmaco() {
        if (this.inEsaurimento && this.quantita == 0) {this.statoFarmaco = "NON DISPONIBILE";}
        else if(this.inEsaurimento){this.statoFarmaco = "IN ESAURIMENTO";}
        else this.statoFarmaco="DISPONIBILE";
    }

    public String getStatoFarmaco() {
        return this.statoFarmaco;
    }

    @Override
    public String toString() {
        return "Farmaco{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", minimo=" + minimo +
                ", quantita=" + quantita +
                ", disponibilità=" + inEsaurimento +
                ", statoFarmaco=" + statoFarmaco +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Farmaco farmaco = (Farmaco) o;
        return getId().equals(farmaco.getId()) &&
                Objects.equals(getNome(), farmaco.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome());
    }
}
