package com.gustavo.tienda_de_chaquetas.controllers;

import com.gustavo.tienda_de_chaquetas.dao.api.RoleRepository;
import com.gustavo.tienda_de_chaquetas.dto.LoginDto;
import com.gustavo.tienda_de_chaquetas.dto.RegisterDto;
import com.gustavo.tienda_de_chaquetas.model.Persona;
import com.gustavo.tienda_de_chaquetas.model.Role;
import com.gustavo.tienda_de_chaquetas.security.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gustavo.tienda_de_chaquetas.dao.api.PersonaRepository;

//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.Collections;
import java.util.UUID;

import javax.validation.Valid;


@Slf4j
@Controller
@RequestMapping("api/auth")
public class PersonaController {


    private PersonaRepository personaRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;


    @Autowired
	public PersonaController(AuthenticationManager authenticationManager,
                             PersonaRepository personaRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {

        this.personaRepository = personaRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;

        Role ejemploRole = new Role();

        ejemploRole.setId(1);
        ejemploRole.setName("ADMIN");
        Example<Role> exampleRole = Example.of(ejemploRole);

        if(roleRepository.findOne(exampleRole).isEmpty()){
            roleRepository.save(ejemploRole);
        }

        ejemploRole.setId(2);
        ejemploRole.setName("USER");
        exampleRole = Example.of(ejemploRole);

        if(roleRepository.findOne(exampleRole).isEmpty()){
            roleRepository.save(ejemploRole);
        }

	}

    public static long generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return mostSignificantBits ^ leastSignificantBits;
    }

/*
 *  ########  ########  ######   ####  ######  ######## ######## ########  
    ##     ## ##       ##    ##   ##  ##    ##    ##    ##       ##     ## 
    ##     ## ##       ##         ##  ##          ##    ##       ##     ## 
    ########  ######   ##   ####  ##   ######     ##    ######   ########  
    ##   ##   ##       ##    ##   ##        ##    ##    ##       ##   ##   
    ##    ##  ##       ##    ##   ##  ##    ##    ##    ##       ##    ##  
    ##     ## ########  ######   ####  ######     ##    ######## ##     ##
 */

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(RegisterDto registerDto) {
        return "formularioRegistro";
    }

    @PostMapping("/registro")
    public String procesarFormularioRegistro(@Valid RegisterDto registerDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "formularioRegistro";
        }

        this.register(registerDto);

        // Lógica para procesar el registro si la validación es exitosa

        return "redirect:/api/auth/login";
    }


    @PostMapping ("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){

        System.out.println("Entró register");

        Persona ejemploPersona = new Persona();
        ejemploPersona.setCorreo(registerDto.getCorreo());
        Example<Persona> example = Example.of(ejemploPersona);

        System.out.println(personaRepository.findByCorreo(registerDto.getCorreo()));

        if(personaRepository.findByCorreo(registerDto.getCorreo()) != null){
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        Persona user = new Persona();
        user.setId(this.generateUniqueId());
        user.setCorreo(registerDto.getCorreo());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));


        Role ejemploRole = new Role();
        ejemploRole.setId(2);
        ejemploRole.setName("USER");
        Example<Role> exampleRole = Example.of(ejemploRole);

        Role roles = roleRepository.findOne(exampleRole).orElseThrow(() -> new RuntimeException("Role no encontrado"));

        System.out.println(roles);

        user.setRoles(Collections.singletonList(roles));
        personaRepository.save(user);
        return new ResponseEntity("User registered success!", HttpStatus.OK);

    }


/*
  * ##        #######   ######   #### ##    ## 
    ##       ##     ## ##    ##   ##  ###   ## 
    ##       ##     ## ##         ##  ####  ## 
    ##       ##     ## ##   ####  ##  ## ## ## 
    ##       ##     ## ##    ##   ##  ##  #### 
    ##       ##     ## ##    ##   ##  ##   ### 
    ########  #######   ######   #### ##    ## 
*/

    @GetMapping("/login")
    public String mostrarFormularioLogin(LoginDto loginDto) {
        return "formularioLogin";
    }

    @PostMapping("login")
    public String login(@Valid LoginDto loginDto, BindingResult result, Model model){
        System.out.println(loginDto);

        if (result.hasErrors()) {
            return "formularioLogin";
        }

        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(personaRepository);

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDto.getCorreo(),
                    loginDto.getPassword(),
                    customUserDetailsService.loadUserByUsername(loginDto.getCorreo()).getAuthorities()
                )
            );


            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/welcome/index";

        }catch (Exception e) {
            // Manejar excepción de autenticación
            System.err.println("Error en el inicio de sesión para el usuario: " + loginDto.getCorreo() + ". Detalles: " + e);
            return "formularioLogin";
        }
    }



    /* @GetMapping("/")
    public String getAll(){
        log.info("Probando el log");
        personaRepository.save(new Persona(1L,"Juan","David","Porras","Gómez","juan@gmail.com","123456","password"));
        var result = personaRepository.findAll();
        System.out.println(result);
        return "index";
    }*/


    
}
