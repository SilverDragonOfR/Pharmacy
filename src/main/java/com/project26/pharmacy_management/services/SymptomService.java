package com.project26.pharmacy_management.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Symptoms;
import com.project26.pharmacy_management.repositories.SymptomRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SymptomService {
    @Autowired
    private SymptomRepository symptomrepository;

    public boolean getSymptom(Symptoms symptom){
        try {
            return symptomrepository.getSymptom(symptom);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insertSymptom(Symptoms symptom){
        try {
            return symptomrepository.addSymptom(symptom);
        } catch (Exception e) {
            return false;
        }
    }

    public List<Symptoms> findAll(){
        Optional<List<Symptoms>> symptoms_opt=symptomrepository.findALL();
        if(symptoms_opt.isPresent()){
            return symptoms_opt.get();
        }
        return new ArrayList<>();
    }
}
