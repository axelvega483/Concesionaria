package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.interfaz.UsuarioInterfaz;
import com.proyecto.concecionaria.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioInterfaz, UserDetailsService {

    @Autowired
    private UsuarioRepository repo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repo.save(usuario);
    }

    @Override
    public Optional<Usuario> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Usuario> listar() {
        return repo.findAllActivo();
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(usuario -> {
            usuario.setActivo(Boolean.FALSE);
            repo.save(usuario);
        });
    }

    public Boolean existe(String dni) {
        return repo.findByDniAndActivo(dni).isPresent();
    }

    public Optional<Usuario> findByCorreo(String email) {
        return repo.findByCorreo(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repo.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getActivo(), // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
        );
    }
}
