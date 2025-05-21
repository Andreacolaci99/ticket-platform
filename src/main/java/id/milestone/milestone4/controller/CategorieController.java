package id.milestone.milestone4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.milestone.milestone4.model.Categorie;
import id.milestone.milestone4.model.Ticket;
import id.milestone.milestone4.repository.CategorieRepository;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/categorie")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("categorie", categorieRepository.findAll());
        model.addAttribute("categorieObj", new Categorie());
        return "admin/categorie/index";
    }

    @PostMapping("/create")
    public String creaCategoria(@Valid @ModelAttribute("categorieObj") Categorie categorie,BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            categorieRepository.save(categorie);
        }

        return "redirect:/categorie";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        Categorie cat = categorieRepository.findById(id).get();

        for(Ticket t : cat.getTicket()){
            t.getCategorie().remove(cat);
        }

        categorieRepository.deleteById(id);

        return "redirect:/categorie";
    }
}
