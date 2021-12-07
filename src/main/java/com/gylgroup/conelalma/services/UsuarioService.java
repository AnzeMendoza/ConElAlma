package com.gylgroup.conelalma.services;

import java.util.List;
import java.util.Optional;

import com.gylgroup.conelalma.entities.Rol;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.repositories.RolRepository;
import com.gylgroup.conelalma.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Transactional
    public void agregarUsuario(String nombre, String apellido, String celular, Rol rol, String email,
            String contrasenia, String foto) throws ExceptionService {

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new ExceptionService("YA EXISTE UN USUARIO CON EL EMAIL INDICADO!");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCelular(celular);
        usuario.setRol(rol);
        usuario.setEmail(email);
        usuario.setContrasenia(contrasenia);// encoder.encode(contrasenia) codificar la contrasenia
        usuario.setFoto(foto);
        usuario.setEstado(true);
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminar(Integer id) {

        Optional<Usuario> opUsuario = usuarioRepository.findById(id);
        if (opUsuario.isPresent()) {
            Usuario usuario = opUsuario.get();
            usuario.setEstado(false);
        }

    }

    @Transactional
    public void habilitar(Integer id) {

        Optional<Usuario> opUsuario = usuarioRepository.findById(id);
        if (opUsuario.isPresent()) {
            Usuario usuario = opUsuario.get();
            usuario.setEstado(true);
        }

    }

}
