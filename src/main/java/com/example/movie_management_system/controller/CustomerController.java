package com.example.movie_management_system.controller;

import com.example.movie_management_system.dto.CustomerFilterDto;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping ("/customers")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String GetAllCustomers(CustomerFilterDto filter, Model model, Pageable pageable) {
        Page<Customer> customerPage = customerService.findAll(filter, pageable);
        model.addAttribute("customerPage", customerPage);

        model.addAttribute("filter", filter);
        //model.addAttribute("customers", customers);
        return "customer/index";
    }
    
    @PostMapping("/remove/{id}")
    public String removeCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:/customers";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "/customer/form";
    }

    @PostMapping
    public String createCustomer(@ModelAttribute @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "customer/form";
        }
        try {
            customerService.save(customer);
        } catch (DataIntegrityViolationException e) {
            // Catches the duplicate name error (409 Conflict)
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "customer/form";
        }
        return "redirect:/customers";
    }

    @GetMapping("view/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/view";
                })
                .orElse("redirect:/customers");
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/update";
                })
                .orElse("redirect:/customers");
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "customer/update";
        }

        try {
            customerService.update(id, customer);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());

            model.addAttribute("customer", customer);

            return "customer/update";
        }



        return "redirect:/customers";
    }

}



