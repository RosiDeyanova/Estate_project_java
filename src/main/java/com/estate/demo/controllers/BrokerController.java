package com.estate.demo.controllers;

import com.estate.demo.mappers.BrokerMapper;
import com.estate.demo.models.Broker;
import com.estate.demo.repositories.BrokerRepository;
import com.estate.demo.viewModels.BrokerViewModel;
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
public class BrokerController {
    private final BrokerMapper brokerMapper;
    private final BrokerRepository brokerRepository;

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
}
