package com.academico.service;

import com.academico.dao.PerfilDao;
import com.academico.dao.PrivilegioDao;
import com.academico.dao.UsuarioDao;
import com.academico.entity.Perfil;
import com.academico.entity.Privilegio;
import com.academico.entity.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Servicio para inicializar datos de prueba del sistema
 */
@Singleton
@Startup
public class InicializacionService {

    private static final Logger logger = Logger.getLogger(InicializacionService.class.getName());

    @Inject
    private PerfilDao perfilDao;

    @Inject
    private PrivilegioDao privilegioDao;

    @Inject
    private UsuarioDao usuarioDao;

    @PostConstruct
    public void inicializarDatos() {
        logger.info("Inicializando datos del sistema...");
        
        try {
            inicializarPrivilegios();
            inicializarPerfiles();
            inicializarUsuarios();
            logger.info("Datos del sistema inicializados correctamente");
        } catch (Exception e) {
            logger.severe("Error al inicializar datos del sistema: " + e.getMessage());
        }
    }

    /**
     * Inicializa los privilegios del sistema
     */
    private void inicializarPrivilegios() {
        // Privilegios para Director
        crearPrivilegioSiNoExiste("USUARIOS_VER", "Ver Usuarios", "Permite ver la lista de usuarios", "Usuarios", "/app/usuarios.xhtml");
        crearPrivilegioSiNoExiste("USUARIOS_CREAR", "Crear Usuarios", "Permite crear nuevos usuarios", "Usuarios", "/app/usuarios.xhtml");
        crearPrivilegioSiNoExiste("USUARIOS_EDITAR", "Editar Usuarios", "Permite editar usuarios existentes", "Usuarios", "/app/usuarios.xhtml");
        crearPrivilegioSiNoExiste("USUARIOS_ELIMINAR", "Eliminar Usuarios", "Permite eliminar usuarios", "Usuarios", "/app/usuarios.xhtml");
        
        crearPrivilegioSiNoExiste("PERFILES_VER", "Ver Perfiles", "Permite ver la lista de perfiles", "Perfiles", "/app/perfiles.xhtml");
        crearPrivilegioSiNoExiste("PERFILES_CREAR", "Crear Perfiles", "Permite crear nuevos perfiles", "Perfiles", "/app/perfiles.xhtml");
        crearPrivilegioSiNoExiste("PERFILES_EDITAR", "Editar Perfiles", "Permite editar perfiles existentes", "Perfiles", "/app/perfiles.xhtml");
        crearPrivilegioSiNoExiste("PERFILES_ELIMINAR", "Eliminar Perfiles", "Permite eliminar perfiles", "Perfiles", "/app/perfiles.xhtml");
        
        crearPrivilegioSiNoExiste("PRIVILEGIOS_VER", "Ver Privilegios", "Permite ver la lista de privilegios", "Privilegios", "/app/privilegios.xhtml");
        crearPrivilegioSiNoExiste("PRIVILEGIOS_CREAR", "Crear Privilegios", "Permite crear nuevos privilegios", "Privilegios", "/app/privilegios.xhtml");
        crearPrivilegioSiNoExiste("PRIVILEGIOS_EDITAR", "Editar Privilegios", "Permite editar privilegios existentes", "Privilegios", "/app/privilegios.xhtml");
        crearPrivilegioSiNoExiste("PRIVILEGIOS_ELIMINAR", "Eliminar Privilegios", "Permite eliminar privilegios", "Privilegios", "/app/privilegios.xhtml");
        
        crearPrivilegioSiNoExiste("REPORTES_VER", "Ver Reportes", "Permite acceder a los reportes del sistema", "Reportes", "/app/reportes.xhtml");
        crearPrivilegioSiNoExiste("CONFIGURACION_VER", "Ver Configuración", "Permite acceder a la configuración del sistema", "Configuración", "/app/configuracion.xhtml");

        // Privilegios para Profesor
        crearPrivilegioSiNoExiste("CURSOS_VER", "Ver Cursos", "Permite ver los cursos asignados", "Cursos", "/app/cursos.xhtml");
        crearPrivilegioSiNoExiste("CURSOS_EDITAR", "Editar Cursos", "Permite editar información de cursos", "Cursos", "/app/cursos.xhtml");
        crearPrivilegioSiNoExiste("ESTUDIANTES_VER", "Ver Estudiantes", "Permite ver la lista de estudiantes", "Estudiantes", "/app/estudiantes.xhtml");
        crearPrivilegioSiNoExiste("CALIFICACIONES_VER", "Ver Calificaciones", "Permite ver las calificaciones", "Calificaciones", "/app/calificaciones.xhtml");
        crearPrivilegioSiNoExiste("CALIFICACIONES_EDITAR", "Editar Calificaciones", "Permite editar calificaciones", "Calificaciones", "/app/calificaciones.xhtml");
        crearPrivilegioSiNoExiste("ASISTENCIA_VER", "Ver Asistencia", "Permite ver la asistencia", "Asistencia", "/app/asistencia.xhtml");
        crearPrivilegioSiNoExiste("ASISTENCIA_EDITAR", "Editar Asistencia", "Permite editar la asistencia", "Asistencia", "/app/asistencia.xhtml");

        // Privilegios para Estudiante
        crearPrivilegioSiNoExiste("MIS_CURSOS_VER", "Ver Mis Cursos", "Permite ver los cursos inscritos", "Mis Cursos", "/app/mis-cursos.xhtml");
        crearPrivilegioSiNoExiste("MIS_CALIFICACIONES_VER", "Ver Mis Calificaciones", "Permite ver las propias calificaciones", "Mis Calificaciones", "/app/mis-calificaciones.xhtml");
        crearPrivilegioSiNoExiste("MI_ASISTENCIA_VER", "Ver Mi Asistencia", "Permite ver la propia asistencia", "Mi Asistencia", "/app/mi-asistencia.xhtml");
        crearPrivilegioSiNoExiste("PERFIL_EDITAR", "Editar Perfil", "Permite editar el perfil personal", "Perfil", "/app/perfil.xhtml");
    }

    /**
     * Inicializa los perfiles del sistema
     */
    private void inicializarPerfiles() {
        // Perfil Director
        Perfil director = crearPerfilSiNoExiste("DIRECTOR", "Director del Sistema", 
            "Perfil con acceso completo al sistema académico");
        
        // Perfil Profesor
        Perfil profesor = crearPerfilSiNoExiste("PROFESOR", "Profesor", 
            "Perfil para profesores del sistema académico");
        
        // Perfil Estudiante
        Perfil estudiante = crearPerfilSiNoExiste("ESTUDIANTE", "Estudiante", 
            "Perfil para estudiantes del sistema académico");

        // Asignar privilegios a los perfiles
        asignarPrivilegiosAPerfil(director, new String[]{
            "USUARIOS_VER", "USUARIOS_CREAR", "USUARIOS_EDITAR", "USUARIOS_ELIMINAR",
            "PERFILES_VER", "PERFILES_CREAR", "PERFILES_EDITAR", "PERFILES_ELIMINAR",
            "PRIVILEGIOS_VER", "PRIVILEGIOS_CREAR", "PRIVILEGIOS_EDITAR", "PRIVILEGIOS_ELIMINAR",
            "REPORTES_VER", "CONFIGURACION_VER"
        });

        asignarPrivilegiosAPerfil(profesor, new String[]{
            "CURSOS_VER", "CURSOS_EDITAR", "ESTUDIANTES_VER",
            "CALIFICACIONES_VER", "CALIFICACIONES_EDITAR",
            "ASISTENCIA_VER", "ASISTENCIA_EDITAR", "PERFIL_EDITAR"
        });

        asignarPrivilegiosAPerfil(estudiante, new String[]{
            "MIS_CURSOS_VER", "MIS_CALIFICACIONES_VER", "MI_ASISTENCIA_VER", "PERFIL_EDITAR"
        });
    }

    /**
     * Inicializa los usuarios de prueba
     */
    private void inicializarUsuarios() {
        // Usuario Director
        crearUsuarioSiNoExiste("director", "123456", "Juan", "Pérez", "director@academico.com", "DIRECTOR");
        
        // Usuario Profesor
        crearUsuarioSiNoExiste("profesor", "123456", "María", "González", "profesor@academico.com", "PROFESOR");
        
        // Usuario Estudiante
        crearUsuarioSiNoExiste("estudiante", "123456", "Carlos", "López", "estudiante@academico.com", "ESTUDIANTE");
    }

    /**
     * Crea un privilegio si no existe
     */
    private void crearPrivilegioSiNoExiste(String codigo, String nombre, String descripcion, String modulo, String url) {
        if (!privilegioDao.existsByCodigo(codigo)) {
            Privilegio privilegio = new Privilegio(codigo, nombre, descripcion, modulo, url);
            privilegioDao.save(privilegio);
            logger.info("Privilegio creado: " + codigo);
        }
    }

    /**
     * Crea un perfil si no existe
     */
    private Perfil crearPerfilSiNoExiste(String nombre, String descripcion, String descripcionCompleta) {
        if (!perfilDao.existsByNombre(nombre)) {
            Perfil perfil = new Perfil(nombre, descripcionCompleta);
            perfilDao.save(perfil);
            logger.info("Perfil creado: " + nombre);
            return perfil;
        } else {
            return perfilDao.findByNombre(nombre).orElse(null);
        }
    }

    /**
     * Asigna privilegios a un perfil
     */
    private void asignarPrivilegiosAPerfil(Perfil perfil, String[] codigosPrivilegios) {
        for (String codigo : codigosPrivilegios) {
            privilegioDao.findByCodigo(codigo).ifPresent(privilegio -> {
                if (!perfil.tienePrivilegio(codigo)) {
                    perfil.agregarPrivilegio(privilegio);
                    perfilDao.update(perfil);
                }
            });
        }
    }

    /**
     * Crea un usuario si no existe
     */
    private void crearUsuarioSiNoExiste(String username, String password, String nombre, 
                                       String apellido, String email, String nombrePerfil) {
        if (!usuarioDao.existsByUsername(username)) {
            Perfil perfil = perfilDao.findByNombre(nombrePerfil).orElse(null);
            if (perfil != null) {
                Usuario usuario = new Usuario();
                usuario.setUsername(username);
                usuario.setPassword(hashPassword(password));
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setEmail(email);
                usuario.setPerfil(perfil);
                usuario.setActivo(true);
                
                usuarioDao.save(usuario);
                logger.info("Usuario creado: " + username);
            }
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
}

