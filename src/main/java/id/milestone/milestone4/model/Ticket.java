package id.milestone.milestone4.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="Inserire il nome del ticket")
    @Column(name="Titolo ticket")
    private String name;

    @NotBlank(message="Inserire il nome dell' autore")
    private String autore;

    @NotBlank(message="Inserire la descrizione del ticket")
    @Column(length=1000)
    private String descrizione;

    @NotNull(message="Inserire una data valida")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name="Data Creazione")
    private LocalDate dataCreazione;

    @NotBlank
    private String stato;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utenti utente;

    @OneToMany(mappedBy="ticket",cascade = CascadeType.REMOVE)
    private List<Note> note;

    public List<Note> getNote() {
        return note;
    }

    public void setNote(List<Note> note) {
        this.note = note;
    }

    public Utenti getUtente() {
        return utente;
    }

    public void setUtente(Utenti utente) {
        this.utente = utente;
    }

    @ManyToMany
    private List<Categorie> categorie;

    public List<Categorie> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<Categorie> categorie) {
        this.categorie = categorie;
    }

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

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
