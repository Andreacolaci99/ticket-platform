package id.milestone.milestone4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import id.milestone.milestone4.model.Ticket;
import id.milestone.milestone4.model.Utenti;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByUtente(Utenti utente);

    List<Ticket> findByUtenteAndNameContainingIgnoreCase(Utenti utente, String name);

    List<Ticket> findByNameContainingIgnoreCase(String name);

}
