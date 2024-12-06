package com.project26.pharmacy_management.models;

public class Symptoms {

    private String symptom;

    public Symptoms() {
    }

    public Symptoms(String symptom) {
        this.symptom = symptom;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    @Override
    public String toString() {
        return "Symptoms [symptom=" + symptom + "]";
    }
    
}
