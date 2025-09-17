package com.academico.filter;

import com.academico.service.AutenticacionService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filtro de seguridad para controlar el acceso a las páginas protegidas
 */
@WebFilter("/app/*")
public class SecurityFilter implements Filter {

    @Inject
    private AutenticacionService autenticacionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        // Permitir acceso a recursos estáticos
        if (path.startsWith("/resources/") || path.startsWith("/javax.faces.resource/")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Verificar si el usuario está autenticado
        HttpSession session = httpRequest.getSession(false);
        boolean isAuthenticated = false;
        
        if (session != null) {
            // Verificar si hay un usuario en la sesión
            Object usuario = session.getAttribute("usuarioLogueado");
            isAuthenticated = (usuario != null);
        }
        
        if (!isAuthenticated) {
            // Redirigir al login si no está autenticado
            httpResponse.sendRedirect(contextPath + "/login.xhtml");
            return;
        }
        
        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Limpieza del filtro
    }
}

