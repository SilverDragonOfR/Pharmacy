package com.project26.pharmacy_management.repositories;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Medicine;

@Repository
public class MedicineRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<Medicine> findMedicine(String name){
        String  query="SELECT * FROM Medicine WHERE Medicine.name=?";
        try {
            List<Map<String, Object>> rows= jdbcTemplate.queryForList(query,name);
            for(Map<String,Object> row: rows) {
                int _med_id = Integer.parseInt(row.get("med_id").toString());
                String _name = row.get("name").toString();
                String _company = row.get("company").toString();
                int _cprice = Integer.parseInt(row.get("cprice").toString());
                int _sprice = Integer.parseInt(row.get("sprice").toString());
                String _utility = row.get("utility").toString();
                int _timeTillExpiry = Integer.parseInt(row.get("time_till_expiry").toString());

                return Optional.of(new Medicine(_med_id, _company, _cprice, _name, _sprice, _timeTillExpiry, _utility));

            }
            
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
        return Optional.empty();
    };

    public Optional<List<Medicine>> findAll(){
        String query="SELECT * FROM Medicine";
        List<Medicine> medicines= new ArrayList<> ();
        try {
            List<Map<String,Object>> rows=jdbcTemplate.queryForList(query);
            for(Map<String,Object> row: rows){
                int _med_id = Integer.parseInt(row.get("med_id").toString());
                String _name = row.get("name").toString();
                String _company = row.get("company").toString();
                int _cprice = Integer.parseInt(row.get("cprice").toString());
                int _sprice = Integer.parseInt(row.get("sprice").toString());
                String _utility = row.get("utility").toString();
                int _timeTillExpiry = Integer.parseInt(row.get("time_till_expiry").toString());

                medicines.add(new Medicine(_med_id, _company, _cprice, _name, _sprice, _timeTillExpiry, _utility));
            }
            return Optional.of(medicines);
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
    };

    public Optional<Map<Integer, Integer>> getPrice(List<Integer> medIds){
        String  query="SELECT sprice FROM Medicine WHERE med_id=?";
        try {
            Map<Integer,Integer> sprices=new HashMap<>();
            for (Integer med_id : medIds){
                List<Map<String, Object>> rows= jdbcTemplate.queryForList(query, med_id);
                for(Map<String,Object> row: rows) {
                    int sprice=Integer.parseInt(row.get("sprice").toString());
                    sprices.put(med_id, sprice);
                }
            }
            return Optional.of(sprices);
            
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
    };

    public Medicine findMedicineFromId(int med_id){
        String  query="SELECT * FROM Medicine WHERE med_id=?";
        try {
            List<Map<String, Object>> rows= jdbcTemplate.queryForList(query, med_id);
            for(Map<String,Object> row: rows) {

                int _med_id = Integer.parseInt(row.get("med_id").toString());
                String _name = row.get("name").toString();
                String _company = row.get("company").toString();
                int _cprice = Integer.parseInt(row.get("cprice").toString());
                int _sprice = Integer.parseInt(row.get("sprice").toString());
                String _utility = row.get("utility").toString();
                int _timeTillExpiry = Integer.parseInt(row.get("time_till_expiry").toString());

                return new Medicine(_med_id, _company, _cprice, _name, _sprice, _timeTillExpiry, _utility);
            }
        } catch (DataAccessException | NumberFormatException e) {
            return null;
        }
        return null;
    };

    @SuppressWarnings("null")
    public int addMedicine(Medicine medicine) {
        String query = "INSERT INTO MEDICINE(name, company, cprice, sprice, utility, time_till_expiry) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" });
                ps.setString(1, medicine.getName());
                ps.setString(2, medicine.getCompany());
                ps.setDouble(3, medicine.getCprice());
                ps.setDouble(4, medicine.getSprice());
                ps.setString(5, medicine.getUtility());
                ps.setInt(6, medicine.getTime_till_expiry());
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        } catch (DataAccessException e) {
            return -1;
        }
    }
}
