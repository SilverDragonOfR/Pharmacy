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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project26.pharmacy_management.models.Customer;
import com.project26.pharmacy_management.models.CustomerPlan;
import com.project26.pharmacy_management.models.Inventory;
import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.models.SaleHistory;
import com.project26.pharmacy_management.models.SelectedSymptoms;
import com.project26.pharmacy_management.models.Symptoms;
import com.project26.pharmacy_management.services.CureService;
import com.project26.pharmacy_management.services.CustomerService;
import com.project26.pharmacy_management.services.InventoryService;
import com.project26.pharmacy_management.services.MyUserDetailService;
import com.project26.pharmacy_management.services.SymptomService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private InventoryService inventoryService; 
    
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private CureService cureService;

    @Autowired
    private SymptomService symptomService;

    @GetMapping("/customer/home")
    public String getCustomerHomePage() {
        return "customer/home";
    }
    
   @GetMapping("/customer/inventory")
    public String getInventory(Model model, HttpSession session) {

        List<Inventory> inventoryList = inventoryService.findAllInventory();
        List<Symptoms> symptoms=symptomService.findAll();
        model.addAttribute("inventory", inventoryList);
        model.addAttribute("symptoms", symptoms);
        model.addAttribute("selectedSymptoms", new SelectedSymptoms());

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");


        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        model.addAttribute("cart", cart);

        return "customer/inventory";
    }

    @PostMapping("customer/inventory")
    public String searchmedicine(@ModelAttribute("selectedSymptoms") SelectedSymptoms selectedSymptoms,Model model,HttpSession session){
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        model.addAttribute("cart", cart);
        List<String> symptoms=selectedSymptoms.getSelectedSymptoms();
        List<Integer> medids=cureService.findMedicationsForSymptoms(symptoms);
        List<Inventory> inventory=inventoryService.findInventory(medids);
        System.out.println(inventory.size());
        model.addAttribute("inventory", inventory);

        return "customer/search";
    }
    
    @GetMapping("/customer/profile")
    public String getCustomerProfilePage(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Optional<Customer> customer = customerService.getCustomerFromUserName(username);
        if(customer.isPresent()) {
            model.addAttribute("username", username);
            model.addAttribute("customer", customer.get());
            return "customer/profile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/customer/profile")
    @ResponseBody
    public Map<String, Object> updateCustomerProfile(HttpServletRequest request) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        Customer updatedCustomer = new Customer(jsonMap.get("address"), jsonMap.get("name"), Integer.parseInt(jsonMap.get("id")), jsonMap.get("username"), jsonMap.get("password"), jsonMap.get("role"));
        boolean wasCustomerSucessfullyUpdated = customerService.updateCustomer(updatedCustomer);

        if(wasCustomerSucessfullyUpdated) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }

    @GetMapping("/customer/customer-plan")
    public String getCustomerPlan(HttpServletRequest request, Model model) {

        String username = request.getUserPrincipal().getName();
        List<CustomerPlan> customerPlan = customerService.getCustomerPlan(username);
        List<Medicine> addToPlan = customerService.getAddToPlan(username);
        model.addAttribute("customerPlan", customerPlan);
        model.addAttribute("addToPlan", addToPlan);

        return "customer/customer-plan";
    }

    @PostMapping("/customer/add-plan")
    @ResponseBody
    public Map<String, Object> addCustomerPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        String username = request.getUserPrincipal().getName();
        boolean wasPlanAdded = customerService.addCustomerPlan(Integer.parseInt(jsonMap.get("add-med-id")), Integer.parseInt(jsonMap.get("recurring")), username);

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

    @PostMapping("/customer/delete-plan")
    @ResponseBody
    public Map<String, Object> deleteCustomerPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanDeleted = customerService.deleteCustomerPlan(Integer.parseInt(jsonMap.get("delete-med-id")), Integer.parseInt(jsonMap.get("delete-customer-id")));

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

    @PostMapping("/customer/update-plan")
    @ResponseBody
    public Map<String, Object> updateCustomerPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanUpdated = customerService.updateCustomerPlan(Integer.parseInt(jsonMap.get("update-med-id")), Integer.parseInt(jsonMap.get("update-customer-id")));

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

    @GetMapping("/customer/sales-history")
    public String getSalesHistoryPage(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        int customerId = myUserDetailService.getIdByUsername(username);
        
        List<SaleHistory> saleHistories = customerService.getSaleHistory(customerId);

        model.addAttribute("saleHistories", saleHistories);

        return "customer/sales-history";
    }
}
