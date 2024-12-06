package com.project26.pharmacy_management.models;

import java.util.List;

public class SaleHistory {
    private int customerId;
    private int saleId;
    private Customer customer;
    private Sales sales;
    private List<HistoryItem> items;
    
    public SaleHistory(int customerId, int saleId, Customer customer, Sales sales, List<HistoryItem> items) {
        this.customerId = customerId;
        this.saleId = saleId;
        this.customer = customer;
        this.sales = sales;
        this.items = items;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getSaleId() {
        return saleId;
    }
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Sales getSales() {
        return sales;
    }
    public void setSales(Sales sales) {
        this.sales = sales;
    }
    public List<HistoryItem> getItems() {
        return items;
    }
    public void setItems(List<HistoryItem> items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return "SaleHistory [customerId=" + customerId + ", saleId=" + saleId + ", customer=" + customer + ", sales="
                + sales + ", items=" + items + "]";
    }

    
}