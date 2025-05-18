package id.milestone.milestone4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.milestone.milestone4.model.Utenti;

public interface UtentiRepository extends JpaRepository<Utenti, Integer>{

    public Optional<Utenti> findByUsername(String username);

}
