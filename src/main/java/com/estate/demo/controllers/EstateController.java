package com.estate.demo.controllers;

import com.estate.demo.mappers.EstateMapper;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.viewModels.EstateViewModel;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@AllArgsConstructor
@Slf4j
public class EstateController {
    private final EstateRepository estateRepository;
    private final EstateMapper estateMapper;

    @GetMapping()
    public String showWelcomePage(Model model) {

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
        return "index";
    }



    @GetMapping("/newEstate")
    public String showCreateForm(Model model) {
        model.addAttribute("estate", new EstateViewModel());
        return "newEstate";
    }

    @PostMapping("/newEstate")
    public String createEstate(@ModelAttribute("estate")  EstateViewModel estateVM, Model model){
        model.addAttribute("estate", estateVM);
        Estate estate = estateMapper.EstateVMToEstate(estateVM);
        estateRepository.save(estate);
        return "redirect:/";
    }

    @GetMapping("/allEstates")
    @PostMapping("/allEstates")
    public String showPaginatedEstates(Model model,@RequestParam(name = "page") Integer page) {
        if (page == null) {
            page = 0;
        }
        else {
            page = page - 1;
        }

        Pageable pageable = PageRequest.of(page, 12);
        Page<Estate> pageEstates = estateRepository.findAll(pageable);
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

        model.addAttribute("pageEstates", pageEstatesVm);
        model.addAttribute("pageNumbers", pagenumbers);
        model.addAttribute("currentPage", page);
        return "allEstates";
    }

    @GetMapping("/estatePage")
    public String showPaginatedEstates(Model model,@RequestParam(name = "id") UUID id)
    {
        Optional<Estate> estate = estateRepository.findById(id);
        model.addAttribute("estate", estate.get());
        return "estatePage";
    }
}
