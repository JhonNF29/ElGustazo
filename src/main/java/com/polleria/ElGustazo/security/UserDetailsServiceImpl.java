package com.polleria.ElGustazo.security;

import com.polleria.ElGustazo.model.Usuario; 
import com.polleria.ElGustazo.repository.UsuarioRepository; 
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        String role = usuario.getRol();
        String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(authority)
                .build();
    }
}