package com.estate.demo.controllers;

import com.estate.demo.mappers.CustomerMapper;
import com.estate.demo.models.Customer;
import com.estate.demo.repositories.CustomerRepository;
import com.estate.demo.viewModels.CustomerViewModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @GetMapping("/registrationCustomer")
    public String showCustomerRegistrationPage(Model model) {
        CustomerViewModel customerVm = new CustomerViewModel();
        model.addAttribute("customer", customerVm);
        return "registrationCustomer";
    }

    @PostMapping("/registrationCustomer")
    public String registerCustomer(@ModelAttribute("customer") CustomerViewModel customerVm, Model model){
        Customer customer = customerMapper.CustomerVMToCustomer(customerVm);
        customerRepository.save(customer);
        return "redirect:/";
        //redirects to main page
        //successfully saves a customer to the db
    }
}
