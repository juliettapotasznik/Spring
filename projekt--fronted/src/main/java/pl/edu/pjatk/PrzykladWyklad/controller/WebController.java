package pl.edu.pjatk.PrzykladWyklad.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.pjatk.PrzykladWyklad.model.Capybara;
import pl.edu.pjatk.PrzykladWyklad.service.CapybaraService;

import java.util.List;
import java.util.Optional;


@Controller

public class WebController {
    private CapybaraService service;

    @Autowired
    public WebController(CapybaraService service) {
        this.service = service;
    }

    @GetMapping(value = "/index")
    public String getViewAll(Model model) {
        model.addAttribute("capybaras", service.findAll());
        return "index";
    }

    @GetMapping(value = "/addCapybara")
    private String getAddValue(Model model) {
        model.addAttribute("capybara", new Capybara("", 0));
        return "addCapybara";
    }

    @PostMapping(value = "/addCapybara")

    private String addValue(Model model, Capybara capybara, RedirectAttributes redirectAttributes) {
         if(capybara.getAge()<0) {

             model.addAttribute("errorMessage", "Age cannot be less than zero!");
       return "addCapybara";
         } else if (capybara.getName().isEmpty()) {
             model.addAttribute("errorMessage", "Name cannot be empty");
             return "addCapybara";

         }
        service.add(capybara);
        redirectAttributes.addFlashAttribute("succesMessage", "Capybara created ");
        return "redirect:/index";
    }

    @GetMapping("/deleteCapybara/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
       Capybara capybara = service.findById(id);

        if (capybara!=null) {
            model.addAttribute("capybara", capybara);
            return "deleteCapybara";
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/deleteCapybara/{id}")
    public String deleteCapybara(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Capybara deleted successfully");
        return "redirect:/index";
    }
    @GetMapping("/editCapybara/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Capybara capybara = service.findById(id);

        if (capybara!=null) {
            model.addAttribute("capybara", capybara);
            return "editCapybara";
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/editCapybara/{id}")
    public String editCapybara(Model model, @ModelAttribute Capybara updatedCapybara,
                               RedirectAttributes redirectAttributes) {

        if (updatedCapybara.getAge() <= 0) {
            model.addAttribute("errorMessage", "Age cannot be less than zero!");
            return "editCapybara";
        } else if (updatedCapybara.getName().isEmpty()) {
            model.addAttribute("errorMessage", "Name cannot be empty");
            return "editCapybara";
        }
        else
        {
        Capybara capybara = service.updateCapybara(updatedCapybara);
            redirectAttributes.addFlashAttribute("successMessage", "Capybara updated successfully");
            return "redirect:/index";
        }
    }



}
