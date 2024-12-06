package com.project26.pharmacy_management.repositories;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Cure;

@Repository
public class CureRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addCure(Cure cure){
        String query="INSERT INTO CURE VALUES(?,?)";
        try {
            jdbcTemplate.update(query,cure.getMed_id(),cure.getSymptom());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public Optional<List<Integer>> findMedidsBySymptoms(List<String> symptoms) {
        String sql = "SELECT med_id FROM cure " +
                     "WHERE symptom IN (" + String.join(",", symptoms.stream().map(s -> "?").toList()) + ") " +
                     "GROUP BY med_id " +
                     "HAVING COUNT(DISTINCT symptom) = ?";

        Object[] params = new Object[symptoms.size() + 1];
        for (int i = 0; i < symptoms.size(); i++) {
            params[i] = symptoms.get(i);
        }
        params[symptoms.size()] = symptoms.size();
    
        List<Integer> medids=new ArrayList<>();
        List<Map<String,Object>> rows=jdbcTemplate.queryForList(sql,params);
        for(Map<String,Object> row: rows){
            int _med_id=Integer.parseInt(row.get("med_id").toString());
            medids.add(_med_id);
        }
        return Optional.of(medids);
    }
}
