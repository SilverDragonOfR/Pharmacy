package com.project26.pharmacy_management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Expense;
import com.project26.pharmacy_management.models.ExpenseType;
import com.project26.pharmacy_management.models.Owner;
import com.project26.pharmacy_management.repositories.ExpenseRepository;
import com.project26.pharmacy_management.repositories.OwnerRepository;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public List<ExpenseType> getAllExpenseTypes() {
        return expenseRepository.getAllExpenseTypes();
    }

    public boolean isExpenseTypePresent(ExpenseType type) {
        return expenseRepository.isExpenseTypePresent(type);
    }

    public boolean addExpenseType(ExpenseType type) {
        return expenseRepository.addExpenseType(type);
    }

    public List<Expense> getAllExpenses(String username) {
        Optional<Owner> ownerOptional = ownerRepository.findOwner(username);
        if(!ownerOptional.isPresent()) {
            return new ArrayList<>();
        }

        Owner owner = ownerOptional.get();
        return expenseRepository.getAllExpenses(owner);
    }

    public boolean addExpense(Expense expense) {
        return expenseRepository.addExpense(expense);
    }

}
