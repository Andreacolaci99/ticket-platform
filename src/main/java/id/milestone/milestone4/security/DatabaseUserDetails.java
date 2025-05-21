package id.milestone.milestone4.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import id.milestone.milestone4.model.Ruoli;
import id.milestone.milestone4.model.Utenti;

public class DatabaseUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private List<GrantedAuthority> authorities;

    public DatabaseUserDetails(Utenti utente) {
        this.id = utente.getId();
        this.username = utente.getUsername();
        this.password = utente.getPassword();
        this.authorities = new ArrayList<>();
        Ruoli ruolo = utente.getRuolo();
        if (ruolo != null) {
            this.authorities = List.of(new SimpleGrantedAuthority(ruolo.getNome()));
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public String getUsername() {

        return this.username;
    }

}
