package com.estate.demo.controllers;

import com.estate.demo.mappers.BrokerMapper;
import com.estate.demo.models.Broker;
import com.estate.demo.repositories.BrokerRepository;
import com.estate.demo.services.BrokerService;
import com.estate.demo.viewModels.BrokerViewModel;
import com.estate.demo.viewModels.PageData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@Slf4j
public class BrokerController {
    private final BrokerMapper brokerMapper;
    private final BrokerRepository brokerRepository;
    private final BrokerService brokerService;

    @GetMapping("/registrationBroker")
    public String showBrokerRegistrationPage(Model model) {
        BrokerViewModel brokerVm = new BrokerViewModel();
        model.addAttribute("broker", brokerVm);
        return "registrationBroker";
    }

    @PostMapping("/registrationBroker")
    public String registerBroker(@ModelAttribute("broker") BrokerViewModel brokerVM, Model model){
        Broker broker = brokerMapper.BrokerVMToBroker(brokerVM);
        brokerRepository.save(broker);
        return "redirect:/";
        //redirects to main page
        //successfully saves a broker to the db
    }

    @GetMapping("/allBrokers")
    @PostMapping("/allBrokers")
    public String showAllBrokersPage(Model model,
                                     @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(name = "searchTerm", required = false) String searchTerm) {
        if (page == null) {
            page = 0;
        }
        else {
            page = page - 1;
        }

        PageData<BrokerViewModel> allBrokersPD = brokerService.getAllBrokersWithSearch(page, searchTerm);

        model.addAttribute("pageData", allBrokersPD);


        return "allBrokers";
    }
}

