package org.univaq.oop.controller;

public class ElementiDelMenu {

    private String nome;
    private String vista;

    public ElementiDelMenu(String nome, String vista) {
        super();
        this.nome = nome;
        this.vista = vista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

}
