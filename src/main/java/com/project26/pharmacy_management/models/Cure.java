package com.project26.pharmacy_management.models;

public class Cure {

    private int med_id;
    private String symptom;
    
    public Cure() {
    }

    public Cure(int med_id, String symptom) {
        this.med_id = med_id;
        this.symptom = symptom;
    }

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    @Override
    public String toString() {
        return "Cure [med_id=" + med_id + ", symptom=" + symptom + "]";
    }

    
}
