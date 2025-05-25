package id.milestone.milestone4.controller;

import java.security.Principal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import id.milestone.milestone4.model.Ticket;
import id.milestone.milestone4.model.Utenti;
import id.milestone.milestone4.repository.CategorieRepository;
import id.milestone.milestone4.repository.TicketRepository;
import id.milestone.milestone4.repository.UtentiRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class DashboardAdminController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/idraulica";
    }

    @GetMapping("/idraulica")
    public String homeAdmin(Model model, @RequestParam(name = "keyword", required = false) String name,
            Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        Utenti utente = utentiRepository.findByUsername(username).get();

        boolean isAdmin = utente.getRuolo() != null && utente.getRuolo().getNome().equalsIgnoreCase("ADMIN");

        List<Ticket> listaTicket;

        if (name != null && !name.isBlank()) {
            if (isAdmin) {
                listaTicket = ticketRepository.findByNameContainingIgnoreCase(name);
            } else {
                listaTicket = ticketRepository.findByUtenteAndNameContainingIgnoreCase(utente, name);
            }
        } else {
            if (isAdmin) {
                listaTicket = ticketRepository.findAll();
            } else {
                listaTicket = ticketRepository.findByUtente(utente);
            }
        }

        model.addAttribute("tickets", listaTicket);
        model.addAttribute("utente", utente);
        return "/admin/index";
    }

    @GetMapping("/idraulica/ticket/{id}")
    public String dettaglioTask(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        return "/admin/dettaglioTicket";
    }

    @GetMapping("/idraulica/create")
    public String createNewTask(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("tickets", ticketRepository.findAll());
        model.addAttribute("categorie", categorieRepository.findAll());
        model.addAttribute("utenti", utentiRepository.findByDisponibileTrue());
        return "/admin/creaTask";
    }

    @PostMapping("/idraulica/create")
    public String postCreate(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("utenti", utentiRepository.findByDisponibileTrue());
            model.addAttribute("categorie", categorieRepository.findAll());
            return "/admin/creaTask";
        }

        ticketRepository.save(formTicket);
        return "redirect:/idraulica";
    }

    @GetMapping("/idraulica/edit/{id}")
    public String editTask(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("ticket", ticketRepository.findById(id).get());
        model.addAttribute("categorie", categorieRepository.findAll());
        model.addAttribute("utenti", utentiRepository.findByDisponibileTrue());

        if (principal != null) {
            String username = principal.getName();
            Utenti utente = utentiRepository.findByUsername(username).get();
            model.addAttribute("utente", utente);
        }

        return "/admin/editTask";
    }

    @PostMapping("/idraulica/edit/{id}")
    public String updateTask(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult,
            Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categorie", categorieRepository.findAll());
            model.addAttribute("utenti", utentiRepository.findAll());
            return "/admin/editTask";
        }

        String username = principal.getName();
        Utenti utenteLoggato = utentiRepository.findByUsername(username).get();

        boolean isAdmin = utenteLoggato.getRuolo().getNome().equalsIgnoreCase("ADMIN");

        Ticket originale = ticketRepository.findById(formTicket.getId()).get();

        if (!isAdmin) {
            formTicket.setName(originale.getName());
            formTicket.setDescrizione(originale.getDescrizione());
            formTicket.setAutore(originale.getAutore());
            formTicket.setDataCreazione(originale.getDataCreazione());
            formTicket.setUtente(originale.getUtente());
            formTicket.setCategorie(originale.getCategorie());
        }

        ticketRepository.save(formTicket);
        return "redirect:/idraulica";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Integer id) {

        ticketRepository.deleteById(id);
        return "redirect:/idraulica";
    }

    @PostMapping("/utenti/{id}/toggleDisponibilita")
    public String toggleDisponibilita(@PathVariable Integer id, @RequestParam("disponibile") boolean disponibile,Model model, RedirectAttributes redirectAttributes) {
        Utenti utente = utentiRepository.findById(id).get();

        if (!disponibile) {
            boolean tuttiCompletati = true;

            for (Ticket t : utente.getTickets()) {
                if (!t.getStato().equalsIgnoreCase("COMPLETATO")) {
                    tuttiCompletati = false;
                    break;
                }
            }

            if (!tuttiCompletati) {
                redirectAttributes.addFlashAttribute("messaggio","Non puoi renderti non disponibile: hai ticket ancora attivi.");
                return "redirect:/idraulica";
            }
        }
        utente.setDisponibile(disponibile);
        utentiRepository.save(utente);
        return "redirect:/idraulica";
    }

}
