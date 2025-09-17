package com.academico.security;

import com.academico.service.AutenticacionService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.logging.Logger;

/**
 * Interceptor para verificar autorización en métodos
 */
@Interceptor
public class AuthorizationInterceptor {

    private static final Logger logger = Logger.getLogger(AuthorizationInterceptor.class.getName());

    @Inject
    private AutenticacionService autenticacionService;

    @AroundInvoke
    public Object verificarAutorizacion(InvocationContext context) throws Exception {
        
        // Verificar si el usuario está autenticado
        if (!autenticacionService.isAutenticado()) {
            logger.warning("Intento de acceso no autorizado a: " + 
                          context.getTarget().getClass().getSimpleName() + "." + 
                          context.getMethod().getName());
            throw new SecurityException("Usuario no autenticado");
        }

        // Verificar si el método requiere un privilegio específico
        RequierePrivilegio requierePrivilegio = context.getMethod().getAnnotation(RequierePrivilegio.class);
        if (requierePrivilegio != null) {
            String codigoPrivilegio = requierePrivilegio.value();
            if (!autenticacionService.tienePrivilegio(codigoPrivilegio)) {
                logger.warning("Usuario " + autenticacionService.getNombreUsuario() + 
                              " no tiene privilegio " + codigoPrivilegio + 
                              " para acceder a: " + context.getMethod().getName());
                throw new SecurityException("Privilegio insuficiente: " + codigoPrivilegio);
            }
        }

        // Verificar si el método requiere un perfil específico
        RequierePerfil requierePerfil = context.getMethod().getAnnotation(RequierePerfil.class);
        if (requierePerfil != null) {
            String nombrePerfil = requierePerfil.value();
            if (!autenticacionService.tienePerfil(nombrePerfil)) {
                logger.warning("Usuario " + autenticacionService.getNombreUsuario() + 
                              " no tiene perfil " + nombrePerfil + 
                              " para acceder a: " + context.getMethod().getName());
                throw new SecurityException("Perfil insuficiente: " + nombrePerfil);
            }
        }

        return context.proceed();
    }
}

