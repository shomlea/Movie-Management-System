package com.example.movie_management_system.controller;

import org.springframework.ui.Model;
import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping ("/customers")
public class CustomerController {
    private CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping
    public String GetAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer/index";
    }
    
    @PostMapping("/remove/{id}")
    public String removeCustomer(@PathVariable String id) {
        customerService.removeCustomer(id);
        return "redirect:/customers";
    }
    @GetMapping("/add")
    public String showAddForm() {
        return "/customer/form"; // templates/customers/add.html
    }

    @PostMapping
    public String createCustomer(@RequestParam String name) {
        customerService.addCustomer(name);
        return "redirect:/customers";
    }
}
