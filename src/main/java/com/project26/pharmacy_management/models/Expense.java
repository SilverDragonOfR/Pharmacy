package com.project26.pharmacy_management.models;

public class Expense {
    private int expenseId;
    private ExpenseType expenseType;
    private Owner owner;
    private int amount;
    private String dateOfExpense;
    
    public Expense() {
    }
    public Expense(int expenseId, ExpenseType expenseType, Owner owner, int amount, String dateOfExpense) {
        this.expenseId = expenseId;
        this.expenseType = expenseType;
        this.owner = owner;
        this.amount = amount;
        this.dateOfExpense = dateOfExpense;
    }
    public int getExpenseId() {
        return expenseId;
    }
    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }
    public ExpenseType getExpenseType() {
        return expenseType;
    }
    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }
    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getDateOfExpense() {
        return dateOfExpense;
    }
    public void setDateOfExpense(String dateOfExpense) {
        this.dateOfExpense = dateOfExpense;
    }
    @Override
    public String toString() {
        return "Expense [expenseId=" + expenseId + ", expenseType=" + expenseType + ", owner=" + owner + ", amount="
                + amount + ", dateOfExpense=" + dateOfExpense + "]";
    }

    
}
