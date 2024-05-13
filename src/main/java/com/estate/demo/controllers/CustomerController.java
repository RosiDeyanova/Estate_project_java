package com.estate.demo.controllers;

import com.estate.demo.enums.EstateStatus;
import com.estate.demo.mappers.CustomerMapper;
import com.estate.demo.mappers.EstateMapper;
import com.estate.demo.mappers.EstateMapperImpl;
import com.estate.demo.models.Customer;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.CustomerRepository;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.security.PasswordConfig;
import com.estate.demo.services.CustomerService;
import com.estate.demo.viewModels.CustomerViewModel;
import com.estate.demo.viewModels.EstateViewModel;
import com.estate.demo.viewModels.PageData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final EstateRepository estateRepository;
    private final CustomerService customerService;
    private final EstateMapper estateMapper;

    @GetMapping("/registrationCustomer")
    public String showCustomerRegistrationPage(Model model) {
        CustomerViewModel customerVm = new CustomerViewModel();
        model.addAttribute("customer", customerVm);
        return "registrationCustomer";
    }

    @PostMapping("/registrationCustomer")
    public String registerCustomer(@ModelAttribute("customer") CustomerViewModel customerVm, Model model, RedirectAttributes redirectAttributes){
        Customer customer = customerMapper.CustomerVMToCustomer(customerVm);
        customerRepository.save(customer);
        redirectAttributes.addFlashAttribute("customerId", customer.getId());
        return "redirect:/";
        //redirects to main page
        //successfully saves a customer to the db
    }

    @GetMapping("/customerLogin")
    public String customerLogin(Model model)
    {
        CustomerViewModel customerVm = new CustomerViewModel();
        model.addAttribute("customer", customerVm);
        return "customerLogin";
    }

    @PostMapping("/customerLogin")
    public String customerLogin(Model model, @ModelAttribute("customer") CustomerViewModel customerVm, RedirectAttributes redirectAttributes)
    {
        Customer customer = customerRepository.findCustomerByEmail(customerVm.getEmail());
        PasswordConfig passwordConfig = new PasswordConfig();
        boolean isPassswordCorrect = passwordConfig.equalPassword(customerVm.getPassword(),customer.getPassword());

        if (customer == null || !isPassswordCorrect) {
            return "customerLogin";
        }
        redirectAttributes.addAttribute("customerId", customer.getId());
        return "redirect:/";
    }

    @GetMapping("/buyEstate")
    public String buyEstate(Model model,
                            @RequestParam(name = "estateId") UUID estateId,
                            @RequestParam(name = "customerId") UUID customerId,
                            RedirectAttributes redirectAttributes)
    {

        Customer customer = customerRepository.findCustomerById(customerId);
        Estate estate = estateRepository.findEstateById(estateId);
        customer.getEstatesBought().add(estate);
        customerRepository.save(customer);
        estate.setCustomer(customer);
        estate.setStatus(EstateStatus.Sold);
        estateRepository.save(estate);

        redirectAttributes.addFlashAttribute("customerId", customer.getId());
        return "redirect:/customersEstates";
    }


    @GetMapping("/customersEstates")
    @PostMapping("/customersEstates")
    public String customersEstates (Model model,
                                    @RequestParam(name = "customerId") UUID customerId,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(name = "searchTerm", required = false) String searchTerm)
    {
        if (page == null) {
            page = 0;
        }
        else {
            page = page - 1;
        }

        PageData<EstateViewModel> allCustomerEstatesPD = customerService.getAllCustomerEstates(customerId,page, searchTerm);
        Customer customer = customerRepository.findCustomerById(customerId);
        List<EstateViewModel> estateLiked = new ArrayList<>();
        for (Estate estate : customer.getEstatesLiked()) {
            EstateViewModel viewModel = estateMapper.EstateToEstateVM(estate);
            estateLiked.add(viewModel);
        }
        model.addAttribute("pageData", allCustomerEstatesPD);
        model.addAttribute("estatesLiked", estateLiked);
        model.addAttribute("customerId", customerId);
        model.addAttribute("searchTerm", searchTerm);

        return "customersEstates";
    }

    }


