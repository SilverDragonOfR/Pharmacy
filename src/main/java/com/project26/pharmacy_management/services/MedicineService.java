package com.project26.pharmacy_management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.repositories.MedicineRepository;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines(){
        Optional<List<Medicine>> optionalOfList = medicineRepository.findAll();
        if(optionalOfList.isEmpty())
            return new ArrayList<>();
        return optionalOfList.get();  
    }

    public int addMedicine(Medicine medicine){
        try {
            return (medicineRepository.addMedicine(medicine));
        } catch (Exception e) {
            return -1;
        }
    }

}
