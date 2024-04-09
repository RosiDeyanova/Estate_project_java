package com.estate.demo.controllers;

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

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class EstateController {
    private final EstateRepository estateRepository;

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

        model.addAttribute("estates", newestEstates);
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("estate", new EstateViewModel());
        return "createEstate";
    }

    @PostMapping("/newestate")
    public String createEstate(@ModelAttribute EstateViewModel newEstate){

        return null;
    }
}
