package com.project26.pharmacy_management.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project26.pharmacy_management.models.Employee;
import com.project26.pharmacy_management.models.Expense;
import com.project26.pharmacy_management.models.ExpenseType;
import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.models.MedicineForm;
import com.project26.pharmacy_management.models.Owner;
import com.project26.pharmacy_management.models.Supplier;
import com.project26.pharmacy_management.models.SupplierPlan;
import com.project26.pharmacy_management.models.Symptoms;
import com.project26.pharmacy_management.services.CureService;
import com.project26.pharmacy_management.services.EmployeeService;
import com.project26.pharmacy_management.services.ExpenseService;
import com.project26.pharmacy_management.services.MedicineService;
import com.project26.pharmacy_management.services.OwnerService;
import com.project26.pharmacy_management.services.SymptomService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class OwnerController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private CureService cureService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/owner/home")
    public String getOwnerHomePage() {
        return "owner/home";
    }

    @GetMapping("/owner/suppliers")
    public String getSuppliers(Model model){
        List<Supplier> suppliers=ownerService.findSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "owner/suppliers";
    }

    @GetMapping("/owner/supplier/update")
    public String showSupplierUpdateForm(@RequestParam("sup_id") int sup_id, Model model) {
        Supplier supplier = ownerService.findSupplier(sup_id);
        model.addAttribute("supplier", supplier);
        return "owner/supplier_update";
    }

    @PostMapping("/owner/supplier/update")
    public String submitSupplierUpdateForm(@ModelAttribute("supplier") Supplier supplier){
        ownerService.updateSupplier(supplier);
        return "redirect:/owner/suppliers";
    }

    @GetMapping("/owner/supplier/plan")
    public String getSupplierPlan(@RequestParam("sup_id") int sup_id, HttpServletRequest request, Model model) {

        List<SupplierPlan> supplierPlan = ownerService.getSupplierPlan(sup_id);
        List<Medicine> addToPlan = ownerService.getAddToPlan(sup_id);

        model.addAttribute("supplierPlan", supplierPlan);
        model.addAttribute("addToPlan", addToPlan);
        model.addAttribute("supId", sup_id);

        return "owner/supplier-plan";
    }

    @PostMapping("/owner/supplier/add-plan")
    @ResponseBody
    public Map<String, Object> addSupplierPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanAdded = ownerService.addSupplierPlan(Integer.parseInt(jsonMap.get("add-med-id")), Integer.parseInt(jsonMap.get("recurring")), Integer.parseInt(jsonMap.get("add-sup-id")));

        if(wasPlanAdded) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }

    @PostMapping("/owner/supplier/delete-plan")
    @ResponseBody
    public Map<String, Object> deleteSupplierPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanDeleted = ownerService.deleteSupplierPlan(Integer.parseInt(jsonMap.get("delete-med-id")), Integer.parseInt(jsonMap.get("delete-sup-id")));

        if(wasPlanDeleted) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }

    @PostMapping("/owner/supplier/update-plan")
    @ResponseBody
    public Map<String, Object> updateSupplierPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanUpdated = ownerService.updateSupplierPlan(Integer.parseInt(jsonMap.get("update-med-id")), Integer.parseInt(jsonMap.get("update-sup-id")));

        if(wasPlanUpdated) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }

    @GetMapping("/owner/addsupplier")
    public String addSupplier(Model model){
        Supplier supplier=new Supplier();
        model.addAttribute("supplier", supplier);
        return "owner/addsupplier";
    }

    @PostMapping("/owner/addsupplier")
    public String addForm(@ModelAttribute("supplier") Supplier supplier){
        ownerService.addSupplier(supplier);
        return "redirect:/owner/home";
    }

    @GetMapping("/owner/addemployee")
    public String addEmployee(Model model){
        Employee employee=new Employee();
        model.addAttribute("employee", employee);
        return "owner/addemployee";
    }

    @PostMapping("/owner/addemployee")
    public String submitForm(@ModelAttribute("employee") Employee employee){
        ownerService.addEmployee(employee);
        return "redirect:/owner/home";
    }

    @GetMapping("/owner/employees")
    public String getEmployeeDetails(Model model) {
        List<Employee> employeeList = employeeService.findAllEmployee();
        model.addAttribute("employees", employeeList);
        return "owner/employees";
    }

    @GetMapping("/owner/employee/update")
    public String showUpdateForm(@RequestParam("username") String username, Model model) {
        Employee employee=employeeService.findEmployee(username);
        model.addAttribute("employee", employee);
        return "owner/emp_update";
    }

    @PostMapping("/owner/employee/update")
    public String submitUpdateForm(@ModelAttribute("employee") Employee employee){
        ownerService.updateEmployee(employee);
        return "redirect:/owner/employees";
    }
    
    @GetMapping("/owner/profile")
    public String getOwnerProfilePage(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Optional<Owner> owner = ownerService.getOwnerFromUserName(username);
        if(owner.isPresent()) {
            model.addAttribute("username", username);
            model.addAttribute("owner", owner.get());
            return "owner/profile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/owner/profile")
    @ResponseBody
    public Map<String, Object> updateOwnerProfile(HttpServletRequest request) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        Owner updatedOwner = new Owner(jsonMap.get("address"), jsonMap.get("first_name"), jsonMap.get("last_name"), Integer.parseInt(jsonMap.get("partnership_percent")), Integer.parseInt(jsonMap.get("id")), jsonMap.get("username"), jsonMap.get("password"), jsonMap.get("role"));
        boolean wasOwnerSucessfullyUpdated = ownerService.updateOwner(updatedOwner);

        if(wasOwnerSucessfullyUpdated) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }

    @GetMapping("/owner/buy-medicines")
    public String buyMedicines(Model model, HttpSession session) {
        List<Medicine> medicines = medicineService.getAllMedicines();
        List<Supplier> suppliers = ownerService.findSuppliers();
        model.addAttribute("medicines", medicines);
        model.addAttribute("suppliers", suppliers);

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        model.addAttribute("cart", cart);

        return "owner/buy-medicines";
    }

    @PostMapping("/owner/cart/update")
    @ResponseBody
    public Map<String, Object> updateCart(@RequestBody Map<Integer, Integer> cart, HttpSession session) {
        session.setAttribute("cart", cart);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Session Cart Updated Sucessfully !");
        return jsonResponse;
    }

    @PostMapping("/owner/cart/checkout")
    @ResponseBody
    public Map<String, Object> checkout(HttpSession session, HttpServletRequest request) throws IOException {
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
        else if(!ownerService.makePurchase(cart, request.getUserPrincipal().getName(), Integer.valueOf(jsonMap.get("sup_id")))){
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        } else {
            session.removeAttribute("cart");
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        }
    }
    
    @GetMapping("/owner/addsymptom")
    public String addSymptom(Model model){
        Symptoms symptom=new Symptoms();
        model.addAttribute("symptom", symptom);
        return "owner/addsymptom";
    }

    @PostMapping("/owner/addsymptom")
    public String submitSymptom(@ModelAttribute("symptom") Symptoms symptom){
        if(symptomService.getSymptom(symptom)){
            return "redirect:/owner/addsymptom";
        }
        else {
            if(symptomService.insertSymptom(symptom)){
                return "redirect:/owner/home";
            }
            return "redirect:/owner/addsymptom";
        }
    }

    @GetMapping("/owner/addmedicine")
    public String addmedicineForm(Model model){
        MedicineForm medicineForm=new MedicineForm();
        List<Symptoms> symptoms=symptomService.findAll();
        model.addAttribute("symptoms", symptoms);
        model.addAttribute("medicineForm", medicineForm);
        return "owner/addmedicine";
    }

    @PostMapping("/owner/addmedicine")
    public String submitmedicineForm(@ModelAttribute("medicineForm") MedicineForm medicineForm){
        Medicine medicine=medicineForm.getMedicine();
        List<String> selectedSymptoms=medicineForm.getSelectedSymptoms();
        int med_id=medicineService.addMedicine(medicine);
        if(med_id!=-1){
            if(cureService.addCure(selectedSymptoms,med_id)){
                return "redirect:/owner/home";
            }
            return "redirect:/owner/addmedicine";
        }
        else {
            return "redirect:/owner/addmedicine";
        }
    }

    @GetMapping("/owner/expense-type")
    public String getExpenseType(Model model){
        List<ExpenseType> allExpenseTypes = expenseService.getAllExpenseTypes();
        ExpenseType newExpenseType = new ExpenseType();
        model.addAttribute("allExpenseTypes", allExpenseTypes);
        model.addAttribute("newExpenseType", newExpenseType);
        return "owner/expense-type";
    }

    @PostMapping("/owner/expense-type")
    public String addExpenseType(@ModelAttribute("newExpenseType") ExpenseType newExpenseType){

        if(expenseService.isExpenseTypePresent(newExpenseType)){
            return "redirect:/owner/expense-type";
        }
        else {
            expenseService.addExpenseType(newExpenseType);
            return "redirect:/owner/expense-type";
        }
    }

    @GetMapping("/owner/expenses")
    public String getExpenses(Model model, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        List<Expense> expenses = expenseService.getAllExpenses(username);
        model.addAttribute("expenses", expenses);
        return "owner/expenses";
    }

    @GetMapping("/owner/add-expense")
    public String getAddExpenses(Model model){
        Expense newExpense = new Expense();
        List<ExpenseType> expenseTypes = expenseService.getAllExpenseTypes();
        model.addAttribute("newExpense", newExpense);
        model.addAttribute("expenseTypes", expenseTypes);
        return "owner/add-expense";
    }

    @PostMapping("/owner/add-expense")
    public String postAddExpense(@ModelAttribute("newExpense") Expense newExpense, HttpServletRequest request){

        String username = request.getUserPrincipal().getName();
        Optional<Owner> ownerOptional = ownerService.getOwnerFromUserName(username);
        if(ownerOptional.isEmpty()) {
            return "redirect:/";
        }
        Owner owner = ownerOptional.get();
        newExpense.setOwner(owner);

        if(expenseService.addExpense(newExpense)) {
            return "redirect:/owner/expenses";
        } else {
            return "redirect:/owner/add-expense";
        }
    }
    
}
