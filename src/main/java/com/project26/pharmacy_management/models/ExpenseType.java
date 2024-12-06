package com.project26.pharmacy_management.models;

public class ExpenseType {

    private String type;

    public ExpenseType() {
    }

    public ExpenseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ExpenseType [type=" + type + "]";
    }
    
}
