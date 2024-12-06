package com.project26.pharmacy_management.models;

import java.util.List;

public class MedicineForm {

    private Medicine medicine;
    private List<String> selectedSymptoms;
    
    public MedicineForm() {
    }

    public MedicineForm(Medicine medicine, List<String> selectedSymptoms) {
        this.medicine = medicine;
        this.selectedSymptoms = selectedSymptoms;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public List<String> getSelectedSymptoms() {
        return selectedSymptoms;
    }

    public void setSelectedSymptoms(List<String> selectedSymptoms) {
        this.selectedSymptoms = selectedSymptoms;
    }
    
}
