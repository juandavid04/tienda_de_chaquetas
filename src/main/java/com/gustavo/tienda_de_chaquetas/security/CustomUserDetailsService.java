package com.gustavo.tienda_de_chaquetas.security;

import com.gustavo.tienda_de_chaquetas.dao.api.PersonaRepository;
import com.gustavo.tienda_de_chaquetas.model.Persona;
import com.gustavo.tienda_de_chaquetas.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private PersonaRepository personaRepository;

    @Autowired
    public CustomUserDetailsService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Persona ejemploPersona = new Persona();
        ejemploPersona.setCorreo(correo);
        Example<Persona> example = Example.of(ejemploPersona);

        Persona user = personaRepository.findByCorreo(correo);

        return new User(user.getCorreo(), user.getPassword(), mapRolesToAuthorities(user.getRoles()) );
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}