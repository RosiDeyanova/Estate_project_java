package com.estate.demo.controllers;

import com.estate.demo.mappers.EstateMapper;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.viewModels.EstateViewModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
@Slf4j
public class EstateController {
    private final EstateRepository estateRepository;
    private final EstateMapper estateMapper;

    @GetMapping()
    public String showWelcomePage(Model model) {

        List<Estate> newestEstates = new ArrayList<>();
        if (estateRepository.findAll().size() > 8)
        {
            newestEstates = estateRepository.findAll().subList(0,7);
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

}
