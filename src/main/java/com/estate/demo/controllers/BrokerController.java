package com.estate.demo.controllers;

import com.estate.demo.models.Estate;
import com.estate.demo.viewModels.BrokerViewModel;
import com.estate.demo.viewModels.EstateViewModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
@Slf4j
public class BrokerController {

    @GetMapping("/registrationBroker")
    public String showWelcomePage(Model model) {
        BrokerViewModel brokerVm = new BrokerViewModel();
        model.addAttribute("broker", brokerVm);
        return "registrationBroker";
    }

    @PostMapping("/registrationBroker")
    public String createEstate(@RequestBody EstateViewModel estateVM, Model model){
        model.addAttribute("estate", estateVM);
//        Estate estate = estateMapper.EstateVMToEstate(estateVM);
//        estateRepository.save(estate);
        return "index";
    }
}
