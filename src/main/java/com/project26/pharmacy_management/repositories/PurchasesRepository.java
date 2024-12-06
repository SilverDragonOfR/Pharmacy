package com.project26.pharmacy_management.repositories;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Purchases;

@Repository
public class PurchasesRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer createPurchase(Purchases purchase){
        try {
            String query = "INSERT INTO Purchases(sup_id, owner_id, date_of_purchase, total_amount) VALUES (?, ?, ?, ?);";
            jdbcTemplate.update(query, purchase.getSup_id(), purchase.getOwner_id(), purchase.getDate_of_purchase(), purchase.getTotal_amount());
            String selectQuery = "SELECT MAX(purchase_id) FROM Purchases;";
            Integer purchaseId = jdbcTemplate.queryForObject(selectQuery, Integer.class);
            return purchaseId;
        } catch (DataAccessException e) {
            return -1;
        }
    }

    public boolean addPurchases(Map<Integer, Integer> cart, Integer purchaseId){
        try {
            String query = "INSERT INTO purchase_med(purchase_id, med_id, quantity) VALUES (?, ?, ?)";
            for (Map.Entry<Integer, Integer> medicine : cart.entrySet()) {
                jdbcTemplate.update(query, purchaseId, medicine.getKey(), medicine.getValue());
            }
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }
}
