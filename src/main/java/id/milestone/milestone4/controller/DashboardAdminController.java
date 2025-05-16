package id.milestone.milestone4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import id.milestone.milestone4.model.Ticket;
import id.milestone.milestone4.repository.TicketRepository;
import jakarta.validation.Valid;




@Controller
@RequestMapping("/")
public class DashboardAdminController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/idraulica")
    public String homeAdmin(Model model, @RequestParam(name="keyword", required=false) String name) {
        
        List<Ticket> listaTicket;

        if (name != null && !name.isBlank()) {
            listaTicket = ticketRepository.findByNameContainingIgnoreCase(name);
        } else {
            listaTicket = ticketRepository.findAll();
        }
        model.addAttribute("tickets", listaTicket);
        return "/admin/index";
    }
    
    @GetMapping("/idraulica/{id}")
    public String dettaglioTask(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        return "/admin/dettaglioTicket";
    }

    @GetMapping("/idraulica/create")
    public String createNewTask(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("tickets", ticketRepository.findAll());
        return "/admin/creaTask";
    }
    
    @PostMapping("/idraulica/create")
    public String postCreate(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()){
            return "/admin/creaTask";
        }
        
        ticketRepository.save(formTicket);
        return "redirect:/idraulica";
    }

    @GetMapping("/idraulica/edit/{id}")
    public String editTask(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
    
        return "/admin/editTask";
    }

    @PostMapping("/idraulica/edit/{id}")
    public String updateTask(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "/admin/editTask";
        }
        ticketRepository.save(formTicket);

        return "redirect:/idraulica";
    }
    
   @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Integer id) {

        ticketRepository.deleteById(id);
        return "redirect:/idraulica";
    }
    
}
