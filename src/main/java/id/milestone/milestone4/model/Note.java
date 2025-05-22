package id.milestone.milestone4.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String autore;

    @NotNull(message="Inserire una data valida")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name="Data Creazione")
    private LocalDate dataCreazione;
 
    @NotBlank
    @Column(length=2500)
    private String campoTesto;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    @ManyToMany
    @JsonBackReference
    private List<Utenti> utenti;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public LocalDate getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDate dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getCampoTesto() {
        return campoTesto;
    }

    public void setCampoTesto(String campoTesto) {
        this.campoTesto = campoTesto;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Utenti> getUtenti() {
        return utenti;
    }

    public void setUtenti(List<Utenti> utenti) {
        this.utenti = utenti;
    }
}
