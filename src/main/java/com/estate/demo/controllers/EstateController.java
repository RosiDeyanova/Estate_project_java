package com.estate.demo.controllers;

import com.estate.demo.mappers.EstateMapper;
import com.estate.demo.models.Broker;
import com.estate.demo.models.Customer;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.BrokerRepository;
import com.estate.demo.repositories.CustomerRepository;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.services.EstateService;
import com.estate.demo.viewModels.EstateViewModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.*;


@Controller
@AllArgsConstructor
@Slf4j
public class EstateController {
    private final EstateRepository estateRepository;
    private final EstateMapper estateMapper;
    private final BrokerRepository brokerRepository;
    private final EstateService estateService;
    private final CustomerRepository customerRepository;

    @GetMapping()
    public String showWelcomePage(Model model,
                                  @RequestParam(name = "brokerId", required = false) UUID brokerId,
                                  @RequestParam(name = "customerId", required = false) UUID customerId) {

        List<Estate> newestEstates = new ArrayList<>();
        if (estateRepository.findAll().size() >= 8)
        {
            newestEstates = estateRepository.findAll().subList(0,8);
        }
        else
        {
            newestEstates = estateRepository.findAll();
        }

        List<EstateViewModel> newestEstatesVM = newestEstates.stream()
                .map(estateMapper::EstateToEstateVM)
                .toList();

        model.addAttribute("estates", newestEstatesVM);
        model.addAttribute("brokerId", brokerId);
        model.addAttribute("customerId", customerId);
        return "index";
    }



    @GetMapping("/newEstate")
    public String showCreateForm(Model model,
                                 @RequestParam(name = "brokerId", required = false) UUID brokerId) {
        model.addAttribute("estate", new EstateViewModel());
        model.addAttribute("brokerId", brokerId);
        return "newEstate";
    }

    @PostMapping("/newEstate")
    public String createEstate(Model model,
                               @ModelAttribute("estate")  EstateViewModel estateVM,
                               @RequestParam(name = "brokerId") UUID brokerId,
                               RedirectAttributes redirectAttributes){
        model.addAttribute("estate", estateVM);
        Estate estate = estateMapper.EstateVMToEstate(estateVM);
        Estate savedEstate = estateRepository.save(estate);
        Broker broker = brokerRepository.findBrokerById(brokerId);
        broker.getEstates().add(savedEstate);
        brokerRepository.save(broker);
        estateRepository.save(estate);

        redirectAttributes.addAttribute("brokerId", brokerId);
        return "redirect:/";
    }

    @GetMapping("/allEstates")
    @PostMapping("/allEstates")
    public String showPaginatedEstates(Model model,
                                       @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(name = "searchTerm", required = false) String searchTerm,
                                       @RequestParam(name = "brokerId", required = false) UUID brokerId,
                                       @RequestParam(name = "customerId", required = false) UUID customerId) {
        if (page == null) {
            page = 0;
        }
        else {
            page = page - 1;
        }

        Pageable pageable = PageRequest.of(page, 12);
        Page<Estate> pageEstates = estateRepository.findAllByNameContainingIgnoreCase(searchTerm,pageable);
        if(brokerId != null) {
            Broker broker = brokerRepository.findBrokerById(brokerId);
            pageEstates = estateRepository.findAllByBroker(broker,pageable);
        }
        if(searchTerm == null || searchTerm.isEmpty())
        {
            pageEstates = estateRepository.findAll(pageable);
        }

        int totalPages = pageEstates.getTotalPages();

        List<Integer>pagenumbers = new ArrayList<Integer>();
        for (int i = 1; i <= totalPages; i++) {
            pagenumbers.add(i);
        }

        List<EstateViewModel> pageEstatesVm = new ArrayList<>();
        for (int i = 0; i < pageEstates.getContent().size(); i++) {
            EstateViewModel estateVm = estateMapper.EstateToEstateVM(pageEstates.getContent().get(i));
            pageEstatesVm.add(estateVm);
        }
        //pageEstatesVm.reversed();
        //getting the last added estates first - not working
        model.addAttribute("pageEstates", pageEstatesVm);
        model.addAttribute("pageNumbers", pagenumbers);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("brokerId", brokerId);
        model.addAttribute("customerId", customerId);
        return "allEstates";
    }

    @GetMapping("/estatePage")
    public String showSingleEstate(Model model,@RequestParam(name = "id") UUID id,
                                   @RequestParam(name = "brokerId", required = false) UUID brokerId,
                                   @RequestParam(name = "customerId", required = false) UUID customerId)
    {
        Estate estate = estateRepository.findEstateById(id);
        EstateViewModel estateVM = estateMapper.EstateToEstateVM(estate);

        if(customerId != null) {
            Customer customer = customerRepository.findCustomerById(customerId);
            estateVM.setIsLiked(estateRepository.existsEstateByIdAndCustomersLikedContains(id, customer) && customerRepository.existsCustomerByIdAndEstatesLikedContaining(customerId, estate));
        }

        model.addAttribute("estate", estateVM);
        model.addAttribute("brokerId", brokerId);
        model.addAttribute("customerId", customerId);
        return "estatePage";
    }

    @GetMapping("/editEstate")
    public String editEstate(Model model,@RequestParam(name = "id") UUID id,
                                   @RequestParam(name = "brokerId", required = false) UUID brokerId)
    {
        Optional<Estate> estate = estateRepository.findById(id);
        EstateViewModel estateViewModel = estateMapper.EstateToEstateVM(estate.get());
        model.addAttribute("estate", estateViewModel);
        model.addAttribute("brokerId", brokerId);
        return "editEstate";
    }

    @PostMapping("/editEstate")
    public String editEstate(Model model, @ModelAttribute("estate")  EstateViewModel estateVM,
                             @RequestParam(name = "id") UUID id,
                             @RequestParam(name = "brokerId") UUID brokerId,
                             RedirectAttributes redirectAttributes)
    {
        estateService.editEstate(estateVM,id);
        redirectAttributes.addAttribute("brokerId", brokerId);
        return "redirect:/allEstates";
    }

    @GetMapping("/deleteEstate")
    public String deleteEstate(@RequestParam(name = "id") UUID id,
                               @RequestParam(name = "brokerId") UUID brokerId,
                               RedirectAttributes redirectAttributes)
    {
        estateRepository.deleteById(id);
        redirectAttributes.addAttribute("brokerId", brokerId);
        return "redirect:/brokersAllUploadedEstates";
    }

    @GetMapping("likeEstate")
    public String likeEstate(@RequestParam(name = "id") UUID id,
                             @RequestParam(name = "customerId") UUID customerId,
                             RedirectAttributes redirectAttributes)
    {
        Estate estate = estateRepository.findEstateById(id);
        Customer customer = customerRepository.findCustomerById(customerId);

        Set<Estate> estates = customer.getEstatesLiked();
        estates.add(estate);
        customer.setEstatesLiked(estates);

        Set<Customer> customers = estate.getCustomersLiked();
        customers.add(customer);
        estate.setCustomersLiked(customers);

        estateRepository.save(estate);
        customerRepository.save(customer);

        redirectAttributes.addAttribute("id",id);
        redirectAttributes.addAttribute("customerId", customerId);
        return "redirect:/estatePage";
    }

    @Transactional
    @GetMapping("unlikeEstate")
    public String unlikeEstate(@RequestParam(name = "id") UUID id,
                             @RequestParam(name = "customerId") UUID customerId,
                             RedirectAttributes redirectAttributes)
    {
        Estate estate = estateRepository.findEstateById(id);
        Customer customer = customerRepository.findCustomerById(customerId);

        Set<Estate> estates = customer.getEstatesLiked();
        estates.remove(estate);
        customer.setEstatesLiked(estates);

        Set<Customer> customers = estate.getCustomersLiked();
        customers.remove(customer);
        estate.setCustomersLiked(customers);

        customerRepository.save(customer);
        estateRepository.save(estate);

        redirectAttributes.addAttribute("id",id);
        redirectAttributes.addAttribute("customerId", customerId);
        return "redirect:/estatePage";
    }
}
