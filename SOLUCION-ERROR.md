# Solución al Error de PrimeFaces/JSF

## Problema Identificado

El error que estás experimentando se debe a un conflicto de versiones entre PrimeFaces y JSF en WildFly 33 con Jakarta EE 10:

```
java.lang.ClassNotFoundException: javax.faces.event.SystemEventListener
```

## Causa del Error

1. **PrimeFaces 14.0.0** no es compatible con Jakarta EE 10
2. **WildFly 33** usa Jakarta EE 10 que cambió el namespace de `javax.*` a `jakarta.*`
3. **PrimeFaces 14.0.0** aún busca clases con el namespace `javax.faces.*`

## Solución Implementada

### 1. Cambio de Versión de PrimeFaces

```xml
<!-- En pom.xml -->
<primefaces.version>12.0.0</primefaces.version>
```

**PrimeFaces 12.0.0** es la última versión estable compatible con Jakarta EE 10.

### 2. Configuración Correcta de JSF

```xml
<!-- En web.xml -->
<context-param>
    <param-name>jakarta.faces.PROJECT_STAGE</param-name>
    <param-value>Development</param-value>
</context-param>

<context-param>
    <param-name>jakarta.faces.FACELETS_SKIP_COMMENTS</param-name>
    <param-value>true</param-value>
</context-param>
```

### 3. Configuración del DataSource

```xml
<!-- En persistence.xml -->
<persistence-unit name="AcademicoDS">
    <jta-data-source>java:jboss/datasources/AcademicoDS</jta-data-source>
    <!-- ... -->
</persistence-unit>
```

## Pasos para Resolver el Error

### Paso 1: Limpiar y Recompilar

```bash
mvn clean compile
```

### Paso 2: Configurar WildFly

```bash
# Descargar el driver PostgreSQL
wget https://jdbc.postgresql.org/download/postgresql-42.6.0.jar

# Ejecutar el script de configuración
./jboss-cli.sh --file=configure-wildfly.cli
```

### Paso 3: Desplegar

```bash
mvn clean package
mvn wildfly:deploy
```

## Verificación

1. **Verificar que no hay errores en los logs de WildFly**
2. **Acceder a la aplicación**: `http://localhost:8080/sistema-academico`
3. **Probar el login** con los usuarios de prueba

## Usuarios de Prueba

- **Director**: `director` / `123456`
- **Profesor**: `profesor` / `123456`
- **Estudiante**: `estudiante` / `123456`

## Si Persiste el Error

### Verificar Versiones

```bash
# Verificar versión de Java
java -version

# Verificar versión de Maven
mvn -version

# Verificar versión de WildFly
./standalone.sh --version
```

### Verificar Configuración

1. **PostgreSQL ejecutándose**: `systemctl status postgresql`
2. **Base de datos creada**: `psql -U postgres -c "\l"`
3. **DataSource configurado**: Verificar en la consola de administración de WildFly

### Logs de WildFly

```bash
# Ver logs en tiempo real
tail -f $WILDFLY_HOME/standalone/log/server.log

# Buscar errores específicos
grep -i "error\|exception" $WILDFLY_HOME/standalone/log/server.log
```

## Configuración Alternativa

Si el problema persiste, puedes usar **PrimeFaces 11.0.0** que también es compatible:

```xml
<primefaces.version>11.0.0</primefaces.version>
```

## Notas Importantes

- **WildFly 33** requiere **Java 21**
- **Jakarta EE 10** cambió todos los namespaces de `javax.*` a `jakarta.*`
- **PrimeFaces 12.0.0** es la versión recomendada para Jakarta EE 10
- El **DataSource** debe estar configurado antes del despliegue
