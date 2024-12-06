package com.project26.pharmacy_management.models;

public class SaleQuantity {
    private int saleId;
    private int medId;
    private int quantity;
    
    public SaleQuantity(int saleId, int medId, int quantity) {
        this.saleId = saleId;
        this.medId = medId;
        this.quantity = quantity;
    }
    public int getSaleId() {
        return saleId;
    }
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }
    public int getMedId() {
        return medId;
    }
    public void setMedId(int medId) {
        this.medId = medId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "SaleQuantity [saleId=" + saleId + ", medId=" + medId + ", quantity=" + quantity + "]";
    }

    
}
