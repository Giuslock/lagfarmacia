package org.univaq.oop.domain;

import java.io.Serializable;
import java.util.*;

public class Prescription implements Serializable {

    private Long id;

    private boolean evaded;

    private String description;

    private Map<Medicine, Integer> medicines;

    private int doctorId;

    private int userId;

    public Prescription() {
    }

    public Prescription(boolean evaded, String description, int doctorId, int userId) {
        this.evaded = evaded;
        this.description = description;
        this.doctorId = doctorId;
        this.userId = userId;
    }

    public Prescription(Long id, boolean evaded, String description, int doctorId, int userId) {
        this.id = id;
        this.evaded = evaded;
        this.description = description;
        this.doctorId = doctorId;
        this.userId = userId;
    }

    public Prescription(Long id, boolean evaded, String description, Map<Medicine, Integer> medicines, int doctorId, int userId) {
        this.id = id;
        this.evaded = evaded;
        this.description = description;
        this.medicines = medicines;
        this.doctorId = doctorId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEvaded() {
        return evaded;
    }

    public void setEvaded(boolean evaded) {
        this.evaded = evaded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Medicine, Integer> getMedicines() {
        return medicines;
    }

    public void setMedicines(Map<Medicine, Integer> medicines) {
        this.medicines = medicines;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Prescrizione{" +
                "id=" + id +
                ", f_evaso=" + evaded +
                ", descrizione='" + description + '\'' +
                ", medico_id=" + doctorId +
                ", utente_id=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}