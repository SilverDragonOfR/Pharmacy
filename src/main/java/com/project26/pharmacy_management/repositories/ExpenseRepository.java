package com.project26.pharmacy_management.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Expense;
import com.project26.pharmacy_management.models.ExpenseType;
import com.project26.pharmacy_management.models.Owner;

@Repository
public class ExpenseRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ExpenseType> getAllExpenseTypes() {
        String query = "SELECT type FROM expense_type;";
        try {
            List<ExpenseType> expenseTypes = new ArrayList<>();

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
            for(Map<String, Object> row : rows) {
                String _type = row.get("type").toString();
                expenseTypes.add(new ExpenseType(_type));
            }

            return expenseTypes;

        } catch (DataAccessException | NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    public boolean isExpenseTypePresent(ExpenseType type) {
        String query = "SELECT type FROM expense_type WHERE type=?;";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, type.getType());          
            return !rows.isEmpty();
        } catch (DataAccessException | NumberFormatException e) {
            return false;
        }
    }

    public boolean addExpenseType(ExpenseType type) {
        String query = "INSERT INTO expense_type(type) VALUES (?);";
        try {
            jdbcTemplate.update(query, type.getType());
            return true;
        } catch (DataAccessException | NumberFormatException e) {
            return false;
        }
    }

    public List<Expense> getAllExpenses(Owner owner) {
        String query = "SELECT expense_id, expense_type, amount, date_of_expense FROM other_expense;";
        try {
            List<Expense> expenses = new ArrayList<>();

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
            for(Map<String, Object> row : rows) {
                int _expense_id = Integer.parseInt(row.get("expense_id").toString());
                ExpenseType _expense_type = new ExpenseType(row.get("expense_type").toString());
                int _amount = Integer.parseInt(row.get("amount").toString());
                String _date_of_expense = row.get("date_of_expense").toString();
                expenses.add(new Expense(_expense_id, _expense_type, owner, _amount, _date_of_expense));
            }

            return expenses;

        } catch (DataAccessException | NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    public boolean addExpense(Expense expense) {
        String query = "INSERT INTO other_expense(expense_type, owner_id, amount, date_of_expense) VALUES (?, ?, ?, NOW());";
        try {
            jdbcTemplate.update(query, expense.getExpenseType().getType(), expense.getOwner().getId(), expense.getAmount());
            return true;
        } catch (DataAccessException | NumberFormatException e) {
            System.out.println(e);
            return false;
        }
    }
}
