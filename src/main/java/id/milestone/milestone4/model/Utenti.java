package id.milestone.milestone4.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Utenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="Inserire un nome valido")
    private String nome;

    @NotBlank(message="Inserire un cognome valido")
    private String cognome;

    @NotNull(message="Inserire una data valida")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name="Data di nascita")
    private LocalDate dataNascita;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Ruoli> ruoli;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public List<Ruoli> getRuoli() {
        return ruoli;
    }

    public void setRuoli(List<Ruoli> ruoli) {
        this.ruoli = ruoli;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

}
