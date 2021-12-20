package com.gylgroup.conelalma.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.gylgroup.conelalma.entities.Rol;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.repositories.RolRepository;
import com.gylgroup.conelalma.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private final String MENSAJE = "NO EXISTE NINGÚN USUARIO ASOCIADO CON EL EMAIL INDICADO!";

    @Transactional
    public void save(Usuario usuario, Rol rol, MultipartFile foto) throws ExceptionService {

        Optional<Usuario> opUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        if (opUsuario.isPresent()) {

            throw new ExceptionService("YA EXISTE UN USUARIO CON EL EMAIL INDICADO!");
        }

        if (usuarioRepository.findAll().isEmpty()) {// SI NO HAY USUARIOS EN LA BASE DE DATO SE ASIGNA ADMIN POR DEFECTO

            usuario.setRol(rolRepository.findByNombre("ADMIN").get());
            usuario.setEstado(true);
            usuario.setContrasenia(encoder.encode(usuario.getContrasenia()));
            if (!foto.isEmpty()) {
                usuario.setFoto(fotoService.saveFile(foto));
            } else {
                usuario.setFoto("");
            }
            usuarioRepository.save(usuario);

        } else {
            if (rol == null) {

                usuario.setRol(rolRepository.findByNombre("CLIENTE").get());
                usuario.setEstado(true);
                usuario.setContrasenia(encoder.encode(usuario.getContrasenia()));
                if (!foto.isEmpty()) {
                    usuario.setFoto(fotoService.saveFile(foto));
                } else {
                    usuario.setFoto("");
                }

                usuarioRepository.save(usuario);
            } else {

                usuario.setRol(rol);
                usuario.setEstado(true);
                usuario.setContrasenia(encoder.encode(usuario.getContrasenia()));
                if (!foto.isEmpty()) {
                    usuario.setFoto(fotoService.saveFile(foto));
                } else {
                    usuario.setFoto("");
                }

                usuarioRepository.save(usuario);
            }
        }

    }

    @Transactional
    public void update(Integer id, Usuario usuario, Rol rol, MultipartFile foto) throws ExceptionService {

        Optional<Usuario> opUsuario = usuarioRepository.findById(id);
        if (opUsuario.isPresent()) {

            Usuario upUsuario = opUsuario.get();
            upUsuario.setNombre(usuario.getNombre());
            upUsuario.setApellido(usuario.getApellido());
            upUsuario.setCelular(usuario.getCelular());
            upUsuario.setEmail(usuario.getEmail());
            upUsuario.setContrasenia(usuario.getContrasenia());
            upUsuario.setEstado(true);
            upUsuario.setRol(rol);
            if (!foto.isEmpty()) {
                upUsuario.setFoto(fotoService.saveFile(foto));
            }

            usuarioRepository.save(upUsuario);
        } else {
            throw new ExceptionService("NO EXISTE EL USUARIO!");
        }

    }

    @Transactional(readOnly = true)
    public Rol findByRol(String nombre) throws ExceptionService {

        Optional<Rol> opRol = rolRepository.findByNombre(nombre);
        if (opRol.isPresent()) {

            Rol rolCliente = rolRepository.findByNombre(nombre).get();
            return rolCliente;
        } else {
            throw new ExceptionService("ROL CLIENTE INEXISTENTE!");
        }

    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario finById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    public void disable(Integer id) {

        Optional<Usuario> opUsuario = usuarioRepository.findById(id);
        if (opUsuario.isPresent()) {

            Usuario usuario = opUsuario.get();
            usuario.setEstado(false);
            usuarioRepository.save(usuario);
        }

    }

    @Transactional
    public void enable(Integer id) {

        Optional<Usuario> opUsuario = usuarioRepository.findById(id);
        if (opUsuario.isPresent()) {

            Usuario usuario = opUsuario.get();
            usuario.setEstado(true);
            usuarioRepository.save(usuario);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(MENSAJE, email)));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().getNombre());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("user", usuario);

        return new User(usuario.getEmail(), usuario.getContrasenia(), Collections.singleton(authority));
    }

    @Transactional
    public List<Usuario> findAllByEstado(boolean estado) {
        return findAllByEstado(estado);
    }
}
