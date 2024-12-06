package com.project26.pharmacy_management.models;
import java.util.List;

public class SelectedSymptoms {
    private List<String> selectedSymptoms;

    public SelectedSymptoms() {
    }

    public SelectedSymptoms(List<String> selectedSymptoms) {
        this.selectedSymptoms = selectedSymptoms;
    }

    public List<String> getSelectedSymptoms() {
        return selectedSymptoms;
    }

    public void setSelectedSymptoms(List<String> selectedSymptoms) {
        this.selectedSymptoms = selectedSymptoms;
    }

}
