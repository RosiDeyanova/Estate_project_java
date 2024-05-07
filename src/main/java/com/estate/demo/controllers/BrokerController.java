package com.estate.demo.controllers;

import com.estate.demo.mappers.BrokerMapper;
import com.estate.demo.models.Broker;
import com.estate.demo.repositories.BrokerRepository;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.security.PasswordConfig;
import com.estate.demo.services.BrokerService;
import com.estate.demo.services.EstateService;
import com.estate.demo.viewModels.BrokerViewModel;
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

import java.util.Objects;
import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
public class BrokerController {
    private final BrokerMapper brokerMapper;
    private final BrokerRepository brokerRepository;
    private final BrokerService brokerService;
    private final EstateRepository estateRepository;
    private final EstateService estateService;

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

    @GetMapping("/brokersAllUploadedEstates")
    @PostMapping("/brokersAllUploadedEstates")
    public String showAllEstatesByBroker(Model model,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                         @RequestParam(name = "searchTerm", required = false) String searchTerm,
                                         @RequestParam(name = "brokerId") UUID brokerId) {
        if (page == null) {
            page = 0;
        }
        else {
            page = page - 1;
        }

        PageData<EstateViewModel> allBrokersPD = estateService.getAllEstatesOfBrokerWithSearch(page, searchTerm, brokerId);

        model.addAttribute("pageData", allBrokersPD);
        model.addAttribute("brokerId", brokerId);
        model.addAttribute("customerId", null);
        model.addAttribute("searchTerm", searchTerm);

        return "brokersAllUploadedEstates";
    }

    @GetMapping("brokerLogin")
    public String brokerLogin(Model model)
    {
        BrokerViewModel broker = new BrokerViewModel();
        model.addAttribute("broker", broker);
        return "brokerLogin";
    }

    @PostMapping("brokerLogin")
    public String brokerLogin(@ModelAttribute("broker") BrokerViewModel brokerVM, Model model, RedirectAttributes redirectAttributes) {
        Broker broker = brokerRepository.findBrokerByEmail(brokerVM.getEmail());
        PasswordConfig passwordConfig = new PasswordConfig();
        boolean isPassswordCorrect = passwordConfig.equalPassword(brokerVM.getPassword(),broker.getPassword());

        if (broker == null || !isPassswordCorrect) {
            return "brokerLogin";
        }
        redirectAttributes.addAttribute("brokerId", broker.getId());
        return "redirect:/";
    }
}

