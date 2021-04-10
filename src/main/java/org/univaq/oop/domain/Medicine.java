package org.univaq.oop.domain;

import java.io.Serializable;
import java.util.Objects;

public class Medicine implements Serializable {

    private Long id;

    private String name;

    private String description;

    private int minimum;

    private int quantity;

    private boolean outOfStock;

    private String medicineStatus;

    public Medicine(String name, String description, int minimum, int quantity) {
        this.name = name;
        this.description = description;
        this.minimum = minimum;
        this.quantity = quantity;
    }

    public Medicine(Long id, String name, String description, int minimum, int quantita, boolean outOfStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minimum = minimum;
        this.quantity = quantita;
        this.outOfStock = outOfStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        if (quantity <= minimum) this.outOfStock = true;
        else this.outOfStock = false;
    }

    public void setStatoFarmaco() {
        if (outOfStock) this.medicineStatus = "RUNNING OUT";
        else this.medicineStatus = "AVAILABLE";
    }

    public String getMedicineStatus() {
        return this.medicineStatus;
    }
    
    @Override
    public String toString() {
        return "Farmaco{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", minimum=" + minimum +
                ", quantity=" + quantity +
                ", outOfStock=" + outOfStock +
                ", medicineStatus=" + medicineStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return getId().equals(medicine.getId()) &&
                Objects.equals(getName(), medicine.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
