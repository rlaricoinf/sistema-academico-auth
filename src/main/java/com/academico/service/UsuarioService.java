package com.academico.service;

import com.academico.dao.UsuarioDao;
import com.academico.entity.Usuario;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de usuarios
 */
@Stateless
public class UsuarioService {

    @Inject
    private UsuarioDao usuarioDao;

    /**
     * Autentica un usuario
     */
    public Optional<Usuario> autenticar(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioDao.findByUsername(username);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String passwordHash = hashPassword(password);
            
            if (usuario.getActivo() && usuario.getPassword().equals(passwordHash)) {
                // Actualizar último acceso
                usuarioDao.actualizarUltimoAcceso(usuario.getId());
                return Optional.of(usuario);
            }
        }
        
        return Optional.empty();
    }

    /**
     * Crea un nuevo usuario
     */
    public Usuario crearUsuario(String username, String password, String nombre, 
                               String apellido, String email, String nombrePerfil) {
        
        // Verificar que el username no exista
        if (usuarioDao.existsByUsername(username)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        
        // Verificar que el email no exista
        if (usuarioDao.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        // Hash de la contraseña
        String passwordHash = hashPassword(password);
        
        // Crear usuario (asumiendo que el perfil existe)
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordHash);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setActivo(true);
        
        return usuarioDao.save(usuario);
    }

    /**
     * Actualiza la contraseña de un usuario
     */
    public void cambiarPassword(Long usuarioId, String nuevaPassword) {
        Optional<Usuario> usuarioOpt = usuarioDao.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setPassword(hashPassword(nuevaPassword));
            usuarioDao.update(usuario);
        }
    }

    /**
     * Busca un usuario por username
     */
    public Optional<Usuario> findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    /**
     * Busca un usuario por email
     */
    public Optional<Usuario> findByEmail(String email) {
        return usuarioDao.findByEmail(email);
    }

    /**
     * Busca todos los usuarios activos
     */
    public List<Usuario> findAllActivos() {
        return usuarioDao.findAllActivos();
    }

    /**
     * Busca usuarios por perfil
     */
    public List<Usuario> findByPerfil(String nombrePerfil) {
        return usuarioDao.findByPerfil(nombrePerfil);
    }

    /**
     * Activa/desactiva un usuario
     */
    public void cambiarEstado(Long usuarioId, boolean activo) {
        Optional<Usuario> usuarioOpt = usuarioDao.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActivo(activo);
            usuarioDao.update(usuario);
        }
    }

    /**
     * Genera hash MD5 de una contraseña
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash de contraseña", e);
        }
    }

    /**
     * Verifica si un usuario tiene un privilegio específico
     */
    public boolean tienePrivilegio(Usuario usuario, String codigoPrivilegio) {
        if (usuario != null && usuario.getPerfil() != null) {
            return usuario.getPerfil().tienePrivilegio(codigoPrivilegio);
        }
        return false;
    }
}

