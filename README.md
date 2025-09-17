# Sistema Académico - JavaEE

Sistema de gestión académica desarrollado con JavaEE 8, JSF, PrimeFaces, Hibernate y PostgreSQL, diseñado para ejecutarse en WildFly 33.

## Características

- **Autenticación de usuarios** con sistema de login seguro
- **Gestión de perfiles** (Director, Profesor, Estudiante)
- **Sistema de privilegios** granular por módulo
- **Menú dinámico** según el perfil del usuario
- **Interfaz moderna** con PrimeFaces y diseño responsivo
- **Base de datos PostgreSQL** con JPA/Hibernate
- **Arquitectura JavaEE** con CDI, EJB y JSF

## Tecnologías Utilizadas

- **Java 21**
- **JavaEE 8** (Jakarta EE)
- **JSF 4.0** con PrimeFaces 12.0
- **Hibernate 6.2** con JPA
- **PostgreSQL 42.6**
- **WildFly 33**
- **Maven** para gestión de dependencias

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/academico/
│   │       ├── bean/          # Managed Beans de JSF
│   │       ├── dao/           # Data Access Objects
│   │       ├── entity/        # Entidades JPA
│   │       ├── filter/        # Filtros de servlet
│   │       ├── interceptor/   # Interceptores CDI
│   │       ├── security/      # Componentes de seguridad
│   │       └── service/       # Servicios EJB
│   ├── resources/
│   │   ├── META-INF/
│   │   │   ├── beans.xml      # Configuración CDI
│   │   │   └── persistence.xml # Configuración JPA
│   │   └── messages.properties # Mensajes de internacionalización
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── faces-config.xml # Configuración JSF
│       │   └── web.xml         # Descriptor de aplicación web
│       ├── app/                # Páginas protegidas
│       ├── error/              # Páginas de error
│       ├── index.xhtml         # Página de inicio
│       └── login.xhtml         # Página de login
└── pom.xml                     # Configuración Maven
```

## Configuración de la Base de Datos

### PostgreSQL

1. Instalar PostgreSQL
2. Crear la base de datos:
```sql
CREATE DATABASE sistema_academico;
```

3. Configurar las credenciales en `persistence.xml`:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/sistema_academico"/>
<property name="jakarta.persistence.jdbc.user" value="postgres"/>
<property name="jakarta.persistence.jdbc.password" value="postgres"/>
```

## Configuración de WildFly

### 1. Instalar WildFly 33

Descargar desde: https://www.wildfly.org/downloads/

### 2. Configurar PostgreSQL Driver y DataSource

**Opción A: Usar el script de configuración (Recomendado)**

```bash
# Descargar el driver PostgreSQL
wget https://jdbc.postgresql.org/download/postgresql-42.6.0.jar

# Ejecutar el script de configuración
./jboss-cli.sh --file=configure-wildfly.cli
```

**Opción B: Configuración manual**

```bash
# Crear el módulo PostgreSQL
./jboss-cli.sh --connect
module add --name=org.postgresql --resources=postgresql-42.6.0.jar --dependencies=javax.api,javax.transaction.api

# Crear el DataSource
/subsystem=datasources/data-source=AcademicoDS:add(
    jndi-name=java:jboss/datasources/AcademicoDS,
    driver-name=postgresql,
    connection-url=jdbc:postgresql://localhost:5432/sistema_academico,
    user-name=postgres,
    password=postgres
)

# Configurar el driver
/subsystem=datasources/jdbc-driver=postgresql:add(
    driver-name=postgresql,
    driver-module-name=org.postgresql,
    driver-class-name=org.postgresql.Driver
)
```

## Compilación y Despliegue

### 1. Compilar el proyecto

```bash
mvn clean compile
```

### 2. Empaquetar como WAR

```bash
mvn clean package
```

### 3. Desplegar en WildFly

```bash
# Copiar el archivo WAR al directorio deployments de WildFly
cp target/sistema-academico.war $WILDFLY_HOME/standalone/deployments/

# O usar el plugin de Maven
mvn wildfly:deploy
```

## Usuarios de Prueba

El sistema se inicializa automáticamente con los siguientes usuarios:

| Usuario | Contraseña | Perfil | Descripción |
|---------|------------|--------|-------------|
| director | 123456 | DIRECTOR | Acceso completo al sistema |
| profesor | 123456 | PROFESOR | Gestión de cursos y estudiantes |
| estudiante | 123456 | ESTUDIANTE | Consulta de información personal |

## Perfiles y Privilegios

### Director
- Gestión completa de usuarios
- Administración de perfiles y privilegios
- Acceso a reportes y configuración
- Todas las funcionalidades del sistema

### Profesor
- Gestión de cursos asignados
- Administración de estudiantes
- Registro de calificaciones
- Control de asistencia

### Estudiante
- Consulta de cursos inscritos
- Visualización de calificaciones
- Consulta de asistencia
- Gestión de perfil personal

## Funcionalidades Implementadas

### ✅ Autenticación
- Pantalla de login con validación
- Hash MD5 de contraseñas
- Gestión de sesiones
- Redirección automática según perfil

### ✅ Autorización
- Sistema de perfiles (Director, Profesor, Estudiante)
- Privilegios granulares por módulo
- Menú dinámico según permisos
- Filtros de seguridad

### ✅ Interfaz de Usuario
- Diseño moderno y responsivo
- PrimeFaces para componentes UI
- Páginas de error personalizadas
- Navegación intuitiva

### ✅ Base de Datos
- Entidades JPA con relaciones
- Configuración automática de tablas
- Datos de prueba iniciales
- Transacciones JTA

## Acceso al Sistema

Una vez desplegado, acceder a:
- **URL**: http://localhost:8080/sistema-academico
- **Login**: Se redirige automáticamente a `/login.xhtml`
- **Dashboard**: `/app/dashboard.xhtml` (requiere autenticación)

## Desarrollo

### Estructura de Entidades

- **Usuario**: Información personal y credenciales
- **Perfil**: Roles del sistema (Director, Profesor, Estudiante)
- **Privilegio**: Permisos específicos por módulo
- **PerfilPrivilegio**: Relación muchos a muchos entre perfiles y privilegios

### Servicios Principales

- **UsuarioService**: Gestión de usuarios y autenticación
- **AutenticacionService**: Control de sesiones y autorización
- **InicializacionService**: Carga de datos iniciales
- **MenuBean**: Generación dinámica de menús

### Seguridad

- Filtros de autenticación
- Interceptores de autorización
- Validación de privilegios
- Gestión segura de sesiones

## Troubleshooting

### Error de conexión a base de datos
- Verificar que PostgreSQL esté ejecutándose
- Comprobar que el DataSource `AcademicoDS` esté configurado en WildFly
- Validar credenciales en la configuración del DataSource

### Error de despliegue
- Verificar que WildFly 33 esté ejecutándose
- Comprobar logs de WildFly en `standalone/log/`
- Validar configuración de Java 21
- Asegurar que el driver PostgreSQL esté instalado como módulo

### Error de PrimeFaces/JSF
- Verificar que se esté usando PrimeFaces 12.0.0 (compatible con Jakarta EE 10)
- Comprobar que las configuraciones de JSF usen `jakarta.faces.*` en lugar de `javax.faces.*`
- Validar que el `web.xml` tenga las configuraciones correctas

### Problemas de autenticación
- Verificar que los datos iniciales se hayan cargado
- Comprobar hash de contraseñas
- Validar configuración de sesiones
- Asegurar que el `InicializacionService` se ejecute al inicio

## Licencia

Este proyecto es de uso educativo y demostrativo.

