package com.project26.pharmacy_management.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Employee;
import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.models.MyUser;
import com.project26.pharmacy_management.models.Owner;
import com.project26.pharmacy_management.models.Purchases;
import com.project26.pharmacy_management.models.Supplier;
import com.project26.pharmacy_management.models.SupplierPlan;
import com.project26.pharmacy_management.repositories.EmployeeRepository;
import com.project26.pharmacy_management.repositories.InventoryRepository;
import com.project26.pharmacy_management.repositories.MedicineRepository;
import com.project26.pharmacy_management.repositories.MyUserRepository;
import com.project26.pharmacy_management.repositories.OwnerRepository;
import com.project26.pharmacy_management.repositories.PurchasesRepository;
import com.project26.pharmacy_management.repositories.SupplierRepository;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private SupplierRepository supplierrepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private EmployeeRepository employeerepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MyUserDetailService userService;

    public List<Supplier> findSuppliers(){
        Optional<List<Supplier>> sup_optional=supplierrepository.findAllSuppliers();
        if(sup_optional.isEmpty())
            return new ArrayList<>();
        return sup_optional.get();
    }

    public boolean addSupplier(Supplier supplier){
        try {
            return supplierrepository.createSupplier(supplier);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addEmployee(Employee employee){
        try {
            employee.setRole("EMPLOYEE");
            Optional<MyUser> user = userService.SaveUser((MyUser) employee);
            if(user.isPresent()){
                int id=userService.getIdByUsername(employee.getUsername());
                employee.setId(id);
                employeerepository.insertEmployee(employee);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateEmployee(Employee employee){
        try {
            return employeerepository.updateEmployee(employee);
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Owner> getOwnerFromUserName(String username){
        Optional<Owner> owner = ownerRepository.findOwner(username);
        if(owner.isPresent()){
            return owner;
        }
        else{
            return Optional.empty();
        }
    }

    public boolean updateOwner(Owner toUpdateOwner){
        boolean wasOwnerUpdated = ownerRepository.updateOwner(toUpdateOwner);
        return wasOwnerUpdated;
    }

    public Supplier findSupplier(int sup_id){
        Optional<Supplier> supplier_optional = supplierrepository.findSupplier(sup_id);
        if(supplier_optional.isPresent()){
            Supplier supplier = supplier_optional.get();
            return supplier;
        }
        return new Supplier();
    }

    public boolean updateSupplier(Supplier toUpdateSupplier){
        boolean wasSupplierUpdated = supplierrepository.updateSupplier(toUpdateSupplier);
        return wasSupplierUpdated;
    }

    public List<SupplierPlan> getSupplierPlan(int sup_id) {
        return supplierrepository.getSupplierPlan(sup_id);
    }

    public List<Medicine> getAddToPlan(int sup_id) {
        return supplierrepository.addToPlan(sup_id);
    }

    public boolean deleteSupplierPlan(int medId, int supId) {
        return supplierrepository.deleteSupplierPlan(medId, supId);
    }

    public boolean addSupplierPlan(int medId, int recurring, int supId) {
        return supplierrepository.addSupplierPlan(medId, recurring, supId);
    }

    public boolean updateSupplierPlan(int medId, int supId) {
        return supplierrepository.updateSupplierPlan(medId, supId);
    }

    public boolean makePurchase(Map<Integer, Integer> Cart, String username, Integer sup_id){
        Optional<MyUser> userOptional = myUserRepository.findUser(username);
        if(!userOptional.isPresent()){
            return false;
        }
        MyUser user = userOptional.get();
        boolean isInventoryUpdated = inventoryRepository.addInventory(Cart);
        if(!isInventoryUpdated){
            return false;
        }
        Optional<Map<Integer, Integer>> priceOptional = medicineRepository.getPrice(new ArrayList<>(Cart.keySet()));
        if(!priceOptional.isPresent()){
            return false;
        }
        Map<Integer, Integer> price = priceOptional.get();
        Integer totalAmount = 0;
        for(Map.Entry<Integer, Integer> medicine : Cart.entrySet()){
            totalAmount += price.get(medicine.getKey())*medicine.getValue();
        }
        LocalDate today = LocalDate.now();
        DateTimeFormatter mysqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String mysqlDateString = today.format(mysqlFormatter);

        Purchases purchase = new Purchases(-1, sup_id, user.getId(), mysqlDateString, totalAmount);
        Integer purchaseId = purchasesRepository.createPurchase(purchase);

        if(purchaseId == -1)
            return false;

        boolean arePurchasesAdded = purchasesRepository.addPurchases(Cart, purchaseId);

        return arePurchasesAdded;
    }
}
