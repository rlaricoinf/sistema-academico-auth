# Instrucciones de Despliegue - Sistema Académico

## Resumen de Cambios Realizados

### 1. Corrección de Versiones
- **PrimeFaces**: Cambiado de 14.0.0 a 12.0.0 (compatible con Jakarta EE 10)
- **JSF**: Configurado para usar `jakarta.faces.*` en lugar de `javax.faces.*`

### 2. Configuración de Base de Datos
- **DataSource**: Configurado como `AcademicoDS`
- **Persistence Unit**: Actualizado para usar el DataSource correcto
- **Esquema**: Configurado para generación automática

### 3. Archivos de Configuración
- **web.xml**: Actualizado con configuraciones de Jakarta EE 10
- **persistence.xml**: Configurado para WildFly 33
- **faces-config.xml**: Configuración de navegación y locale

## Pasos para Desplegar

### 1. Configurar PostgreSQL
```sql
CREATE DATABASE sistema_academico;
```

### 2. Configurar WildFly
```bash
# Descargar driver PostgreSQL
wget https://jdbc.postgresql.org/download/postgresql-42.6.0.jar

# Ejecutar configuración
./jboss-cli.sh --file=configure-wildfly.cli
```

### 3. Compilar y Desplegar
```bash
mvn clean package
mvn wildfly:deploy
```

### 4. Verificar Despliegue
- URL: http://localhost:8080/sistema-academico
- Login: director/123456

## Archivos Creados/Modificados

### Nuevos Archivos
- `configure-wildfly.cli` - Script de configuración
- `SOLUCION-ERROR.md` - Documentación del error
- `deploy-instructions.md` - Este archivo

### Archivos Modificados
- `pom.xml` - Versión de PrimeFaces
- `web.xml` - Configuraciones de Jakarta EE 10
- `persistence.xml` - DataSource y entidades
- `README.md` - Instrucciones actualizadas

## Verificación de Funcionamiento

1. **Sin errores en logs de WildFly**
2. **Acceso a la aplicación**
3. **Login funcional**
4. **Menú dinámico según perfil**
5. **Dashboard personalizado**

## Usuarios de Prueba

| Usuario | Contraseña | Perfil |
|---------|------------|--------|
| director | 123456 | DIRECTOR |
| profesor | 123456 | PROFESOR |
| estudiante | 123456 | ESTUDIANTE |

## Troubleshooting

Si persiste el error:
1. Verificar que WildFly 33 esté ejecutándose
2. Comprobar que PostgreSQL esté activo
3. Validar configuración del DataSource
4. Revisar logs de WildFly
