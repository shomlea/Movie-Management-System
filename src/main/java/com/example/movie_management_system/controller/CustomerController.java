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
        List<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customer/index";
    }
    
    @PostMapping("/remove/{id}")
    public String removeCustomer(@PathVariable String id) {
        customerService.remove(id);
        return "redirect:/customers";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "/customer/form";
    }

    @PostMapping
    public String createCustomer(@RequestParam String name) {
        customerService.add(name);
        return "redirect:/customers";
    }

    @GetMapping("view/{id}")
    public String viewCustomer(@PathVariable String id, Model model) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/view";
                })
                .orElse("redirect:/customers");
    }
}
