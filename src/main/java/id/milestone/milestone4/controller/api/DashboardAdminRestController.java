package id.milestone.milestone4.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.milestone.milestone4.model.Ticket;
import id.milestone.milestone4.repository.TicketRepository;

@RestController
@RequestMapping("/api/idraulica")
public class DashboardAdminRestController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> homeAdmin() {

        return ticketRepository.findAll();
    }

    @GetMapping("/categoria")
    public List<Ticket> getByCategoria(@RequestParam("nome") String nomeCategoria) {
        return ticketRepository.findByCategorieNomeIgnoreCase(nomeCategoria);
    }

    @GetMapping("/stato")
    public List<Ticket> getByStato(@RequestParam("stato") String stato) {
        return ticketRepository.findByStatoIgnoreCase(stato);
    }

}
