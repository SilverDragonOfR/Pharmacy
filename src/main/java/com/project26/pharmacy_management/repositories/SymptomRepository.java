package com.project26.pharmacy_management.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Symptoms;

@Repository
public class SymptomRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean getSymptom(Symptoms symptom){
        String query="SELECT * FROM SYMPTOMS WHERE symptom=?";
        try {
            List<Map<String,Object>> rows=jdbcTemplate.queryForList(query,symptom.getSymptom());
            return !rows.isEmpty();
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean addSymptom(Symptoms symptom){
        String query="INSERT INTO SYMPTOMS VALUES(?)";
        try {
            jdbcTemplate.update(query,symptom.getSymptom());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public Optional<List<Symptoms>> findALL(){
        String query="SELECT * FROM SYMPTOMS";
        try {
            List<Map<String,Object>> rows=jdbcTemplate.queryForList(query);
            List<Symptoms> symptoms=new ArrayList<>();
            for(Map<String,Object> row : rows){
                String _symptom=row.get("symptom").toString();
                symptoms.add(new Symptoms(_symptom));
            }
            return Optional.of(symptoms);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
