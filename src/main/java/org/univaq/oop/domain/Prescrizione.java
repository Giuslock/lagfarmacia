package org.univaq.oop.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class Prescrizione implements Serializable {

    private Long id;

    private boolean evasa;

    private String descrizione;

    private Map<Farmaco, Integer> medicine;

    private int codiceDottore;

    private int codicePaziente;

    private String stato;

    public Prescrizione() {
    }

    public Prescrizione(boolean evasa, String descrizione, int codiceDottore, int codicePaziente) {
        this.evasa = evasa;
        this.descrizione = descrizione;
        this.codiceDottore = codiceDottore;
        this.codicePaziente = codicePaziente;
    }

    public Prescrizione(Long id, boolean evasa, String descrizione, int codiceDottore, int codicePaziente) {
        this.id = id;
        this.evasa = evasa;
        this.descrizione = descrizione;
        this.codiceDottore = codiceDottore;
        this.codicePaziente = codicePaziente;
    }

    public Prescrizione(Long id, boolean evasa, String descrizione, Map<Farmaco, Integer> medicine, int codiceDottore, int codicePaziente) {
        this.id = id;
        this.evasa = evasa;
        this.descrizione = descrizione;
        this.medicine = medicine;
        this.codiceDottore = codiceDottore;
        this.codicePaziente = codicePaziente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean attualmenteEvasa() {
        return evasa;
    }

    public void setEvasa(boolean evasa) {
        this.evasa = evasa;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Map<Farmaco, Integer> getMedicine() {
        return medicine;
    }

    public void setMedicine(Map<Farmaco, Integer> medicine) {
        this.medicine = medicine;
    }

    public int getCodiceDottore() {
        return codiceDottore;
    }

    public void setCodiceDottore(int codiceDottore) {
        this.codiceDottore = codiceDottore;
    }

    public int getCodicePaziente() {
        return codicePaziente;
    }

    public void setCodicePaziente(int codicePaziente) {
        this.codicePaziente = codicePaziente;
    }

    public String getStato() {
        return stato;
    }

    public void setStato() {
        if (attualmenteEvasa()) {
            this.stato = "evasa";
        } else {
            this.stato = "non evasa";
        }
    }

    @Override
    public String toString() {
        return "Prescrizione{" +
                "id=" + id +
                ", stato=" + evasa +
                ", descrizione='" + descrizione + '\'' +
                ", codiceDottore=" + codiceDottore +
                ", codicePaziente=" + codicePaziente +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescrizione that = (Prescrizione) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

