package id.milestone.milestone4.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ruoli {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserire un nome valido")
    private String nome;

    @OneToMany(mappedBy = "ruolo")
    @JsonBackReference
    private List<Utenti> utenti;

    public List<Utenti> getUtenti() {
        return utenti;
    }

    public void setUtenti(List<Utenti> utenti) {
        this.utenti = utenti;
    }

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

}
