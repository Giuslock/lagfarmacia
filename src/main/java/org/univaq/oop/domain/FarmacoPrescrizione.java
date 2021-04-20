package org.univaq.oop.domain;

public class FarmacoPrescrizione {
    private Long id;

    private String nome;

    private String descrizione;

    private int quantita;

    public FarmacoPrescrizione() {
    }

    public FarmacoPrescrizione(Long id, String nome, int quantita) {
        this.id = id;
        this.nome = nome;
        this.quantita = quantita;
    }

    public FarmacoPrescrizione(Long id, String nome, String descrizione, int quantita) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.quantita = quantita;
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

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
