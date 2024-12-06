package com.project26.pharmacy_management.models;

public class HistoryItem {
    private int medId;
    private Medicine medicine;
    private int quantity;
    
    public HistoryItem(int medId, Medicine medicine, int quantity) {
        this.medId = medId;
        this.medicine = medicine;
        this.quantity = quantity;
    }
    public int getMedId() {
        return medId;
    }
    public void setMedId(int medId) {
        this.medId = medId;
    }
    public Medicine getMedicine() {
        return medicine;
    }
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "HistoryItem [medId=" + medId + ", medicine=" + medicine + ", quantity=" + quantity + "]";
    }
}
