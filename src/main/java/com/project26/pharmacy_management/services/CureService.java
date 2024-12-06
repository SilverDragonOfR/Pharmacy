package com.project26.pharmacy_management.services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Cure;
import com.project26.pharmacy_management.repositories.CureRepository;

@Service
public class CureService {
    
    @Autowired
    private CureRepository curerepository;

    public boolean addCure(List<String> selectedSymptoms,int med_id){
        try {
            for(String selectedSymptom: selectedSymptoms){
                curerepository.addCure(new Cure(med_id,selectedSymptom));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Integer> findMedicationsForSymptoms(List<String> symptoms) {
        try {
            Optional<List<Integer>> medid_opt=curerepository.findMedidsBySymptoms(symptoms);
            return medid_opt.get();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
