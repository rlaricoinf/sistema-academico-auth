package com.academico.bean;

import com.academico.entity.Privilegio;
import com.academico.service.AutenticacionService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Bean para el manejo del menú dinámico
 */
@Named
@SessionScoped
public class MenuBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AutenticacionService autenticacionService;

    private List<MenuItem> menuItems;

    /**
     * Inicializa el menú según el perfil del usuario
     */
    public void inicializarMenu() {
        menuItems = new ArrayList<>();
        
        if (autenticacionService.isAutenticado()) {
            String perfil = autenticacionService.getPerfilUsuario();
            
            switch (perfil) {
                case "DIRECTOR":
                    cargarMenuDirector();
                    break;
                case "PROFESOR":
                    cargarMenuProfesor();
                    break;
                case "ESTUDIANTE":
                    cargarMenuEstudiante();
                    break;
                default:
                    cargarMenuBasico();
                    break;
            }
        }
    }

    /**
     * Carga el menú para el director
     */
    private void cargarMenuDirector() {
        menuItems.add(new MenuItem("Dashboard", "pi pi-home", "/app/dashboard.xhtml"));
        menuItems.add(new MenuItem("Gestión de Usuarios", "pi pi-users", "/app/usuarios.xhtml"));
        menuItems.add(new MenuItem("Gestión de Perfiles", "pi pi-id-card", "/app/perfiles.xhtml"));
        menuItems.add(new MenuItem("Gestión de Privilegios", "pi pi-key", "/app/privilegios.xhtml"));
        menuItems.add(new MenuItem("Reportes", "pi pi-chart-bar", "/app/reportes.xhtml"));
        menuItems.add(new MenuItem("Configuración", "pi pi-cog", "/app/configuracion.xhtml"));
    }

    /**
     * Carga el menú para el profesor
     */
    private void cargarMenuProfesor() {
        menuItems.add(new MenuItem("Dashboard", "pi pi-home", "/app/dashboard.xhtml"));
        menuItems.add(new MenuItem("Mis Cursos", "pi pi-book", "/app/cursos.xhtml"));
        menuItems.add(new MenuItem("Estudiantes", "pi pi-users", "/app/estudiantes.xhtml"));
        menuItems.add(new MenuItem("Calificaciones", "pi pi-star", "/app/calificaciones.xhtml"));
        menuItems.add(new MenuItem("Asistencia", "pi pi-calendar", "/app/asistencia.xhtml"));
        menuItems.add(new MenuItem("Perfil", "pi pi-user", "/app/perfil.xhtml"));
    }

    /**
     * Carga el menú para el estudiante
     */
    private void cargarMenuEstudiante() {
        menuItems.add(new MenuItem("Dashboard", "pi pi-home", "/app/dashboard.xhtml"));
        menuItems.add(new MenuItem("Mis Cursos", "pi pi-book", "/app/mis-cursos.xhtml"));
        menuItems.add(new MenuItem("Calificaciones", "pi pi-star", "/app/mis-calificaciones.xhtml"));
        menuItems.add(new MenuItem("Asistencia", "pi pi-calendar", "/app/mi-asistencia.xhtml"));
        menuItems.add(new MenuItem("Perfil", "pi pi-user", "/app/perfil.xhtml"));
    }

    /**
     * Carga un menú básico
     */
    private void cargarMenuBasico() {
        menuItems.add(new MenuItem("Dashboard", "pi pi-home", "/app/dashboard.xhtml"));
        menuItems.add(new MenuItem("Perfil", "pi pi-user", "/app/perfil.xhtml"));
    }

    /**
     * Obtiene los elementos del menú agrupados por módulo
     */
    public Map<String, List<MenuItem>> getMenuAgrupado() {
        if (menuItems == null) {
            inicializarMenu();
        }
        
        return menuItems.stream()
                .collect(Collectors.groupingBy(MenuItem::getModulo));
    }

    /**
     * Verifica si el usuario tiene acceso a una opción del menú
     */
    public boolean tieneAcceso(String codigoPrivilegio) {
        return autenticacionService.tienePrivilegio(codigoPrivilegio);
    }

    // Getters y Setters
    public List<MenuItem> getMenuItems() {
        if (menuItems == null) {
            inicializarMenu();
        }
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * Clase interna para representar un elemento del menú
     */
    public static class MenuItem implements Serializable {
        private String nombre;
        private String icono;
        private String url;
        private String modulo;

        public MenuItem() {}

        public MenuItem(String nombre, String icono, String url) {
            this.nombre = nombre;
            this.icono = icono;
            this.url = url;
            this.modulo = "General";
        }

        public MenuItem(String nombre, String icono, String url, String modulo) {
            this.nombre = nombre;
            this.icono = icono;
            this.url = url;
            this.modulo = modulo;
        }

        // Getters y Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getIcono() { return icono; }
        public void setIcono(String icono) { this.icono = icono; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getModulo() { return modulo; }
        public void setModulo(String modulo) { this.modulo = modulo; }
    }
}

