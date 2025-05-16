package id.milestone.milestone4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import id.milestone.milestone4.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

    public List<Ticket> findByNameContainingIgnoreCase(String name);
}
