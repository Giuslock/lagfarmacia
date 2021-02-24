package it.univaq.disim.oop.farmacialag.domain;
import java.util.*;


public class Prescrizione {

    private int IDpaziente;
    private HashSet<Farmaco> listaFarmaci = new HashSet<Farmaco>();
    private boolean evasa;

    public Prescrizione(int IDpaziente, HashSet<Farmaco> listaFarmaci, boolean evasa) {
        this.IDpaziente = IDpaziente;
        this.listaFarmaci.addAll(listaFarmaci);
        this.evasa = evasa;
    }

    public void addFarmaco(Farmaco farmaco) {
        listaFarmaci.add(farmaco);
    }

    public Farmaco getFarmaco(String nome) {
        for(Farmaco f : listaFarmaci) {
            if(f.getNome().equals(nome.toLowerCase())) return f;
        }
        return null;
    }

    public boolean searchFarmaco(Farmaco f) {
        return listaFarmaci.contains(f);
    }


    public boolean deleteFarmaco(Farmaco f) {
        return listaFarmaci.remove(f);
    }

    public int getIDpaziente() {
        return IDpaziente;
    }

    public void setIDpaziente(int IDpaziente) {
        this.IDpaziente = IDpaziente;
    }

    public boolean isEvasa() {
        return evasa;
    }

    public void setEvasa(boolean evasa) {
        this.evasa = evasa;
    }
}
