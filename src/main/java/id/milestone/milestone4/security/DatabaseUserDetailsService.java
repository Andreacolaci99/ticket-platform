package id.milestone.milestone4.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import id.milestone.milestone4.model.Utenti;
import id.milestone.milestone4.repository.UtentiRepository;


@Service
public class DatabaseUserDetailsService implements UserDetailsService{

    @Autowired
    private UtentiRepository utentiRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Utenti> optUser = utentiRepository.findByUsername(username);
        if(optUser.isPresent()){
            return new DatabaseUserDetails(optUser.get());
        }else{
            throw new UsernameNotFoundException("L'username non è stato trovato");
        }
    }
}