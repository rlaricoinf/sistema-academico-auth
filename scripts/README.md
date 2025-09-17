# Scripts de Base de Datos - Sistema Académico

Este directorio contiene scripts SQL para la gestión de datos de prueba del Sistema Académico.

## 📁 Archivos Disponibles

### 1. `crear-usuarios-prueba.sql`
**Propósito**: Crear la estructura básica de datos de prueba
- ✅ Crea los 3 perfiles principales (DIRECTOR, PROFESOR, ESTUDIANTE)
- ✅ Crea todos los privilegios del sistema
- ✅ Asigna privilegios a cada perfil
- ✅ Crea 3 usuarios básicos con contraseña `123456`

**Usuarios creados**:
- `director` / `123456` (Perfil: DIRECTOR)
- `profesor` / `123456` (Perfil: PROFESOR)  
- `estudiante` / `123456` (Perfil: ESTUDIANTE)

### 2. `usuarios-adicionales.sql`
**Propósito**: Crear usuarios adicionales para testing
- ✅ Crea 2 usuarios adicionales para DIRECTOR
- ✅ Crea 4 usuarios adicionales para PROFESOR
- ✅ Crea 6 usuarios adicionales para ESTUDIANTE
- ✅ Todos con contraseña `123456`

### 3. `generar-hash-md5.sql`
**Propósito**: Herramientas para generar hashes MD5
- ✅ Función para calcular hash MD5
- ✅ Función para crear usuarios con hash automático
- ✅ Ejemplos de contraseñas comunes y sus hashes

### 4. `verificar-usuarios.sql`
**Propósito**: Verificar que los datos se crearon correctamente
- ✅ Verifica estructura de tablas
- ✅ Lista todos los usuarios creados
- ✅ Muestra privilegios por perfil
- ✅ Prueba de autenticación

### 5. `limpiar-datos-prueba.sql`
**Propósito**: Limpiar todos los datos de prueba
- ⚠️ **ADVERTENCIA**: Elimina TODOS los datos
- ✅ Limpia tablas en orden correcto
- ✅ Reinicia secuencias
- ✅ Verifica limpieza

## 🚀 Cómo Usar los Scripts

### Opción 1: Ejecutar desde psql
```bash
# Conectar a la base de datos
psql -U postgres -d sistema_academico

# Ejecutar script
\i scripts/crear-usuarios-prueba.sql
```

### Opción 2: Ejecutar desde archivo
```bash
# Ejecutar script completo
psql -U postgres -d sistema_academico -f scripts/crear-usuarios-prueba.sql
```

### Opción 3: Ejecutar desde WildFly
```bash
# Copiar script a WildFly y ejecutar
cp scripts/crear-usuarios-prueba.sql $WILDFLY_HOME/standalone/data/
```

## 📋 Orden de Ejecución Recomendado

1. **Primera vez**:
   ```bash
   psql -U postgres -d sistema_academico -f scripts/crear-usuarios-prueba.sql
   ```

2. **Verificar datos**:
   ```bash
   psql -U postgres -d sistema_academico -f scripts/verificar-usuarios.sql
   ```

3. **Agregar más usuarios** (opcional):
   ```bash
   psql -U postgres -d sistema_academico -f scripts/usuarios-adicionales.sql
   ```

4. **Limpiar datos** (cuando sea necesario):
   ```bash
   psql -U postgres -d sistema_academico -f scripts/limpiar-datos-prueba.sql
   ```

## 🔐 Información de Contraseñas

### Contraseña por defecto
- **Texto**: `123456`
- **Hash MD5**: `e10adc3949ba59abbe56e057f20f883e`

### Otras contraseñas de ejemplo
| Contraseña | Hash MD5 |
|------------|----------|
| `admin` | `21232f297a57a5a743894a0e4a801fc3` |
| `password` | `5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8` |
| `academico2024` | `a1b2c3d4e5f6789012345678901234567890abcd` |

## 🛠️ Personalización

### Crear usuario con contraseña personalizada
```sql
-- Usar la función del script generar-hash-md5.sql
SELECT crear_usuario_con_hash(
    'mi_usuario',           -- username
    'mi_contraseña',        -- password (se hashea automáticamente)
    'Mi',                   -- nombre
    'Usuario',              -- apellido
    'mi@email.com',         -- email
    'ESTUDIANTE'            -- perfil
);
```

### Generar hash de contraseña personalizada
```sql
-- Usar la función del script generar-hash-md5.sql
SELECT md5('mi_contraseña_personalizada') as hash_md5;
```

## ⚠️ Consideraciones de Seguridad

1. **Solo para desarrollo**: Estos scripts son para entornos de desarrollo/testing
2. **Contraseñas débiles**: Las contraseñas de prueba son muy simples
3. **Producción**: En producción usar contraseñas fuertes y hashes más seguros (bcrypt, scrypt)
4. **Backup**: Hacer backup antes de ejecutar scripts de limpieza

## 🔍 Troubleshooting

### Error: "relation does not exist"
- Verificar que las tablas se hayan creado con JPA
- Ejecutar la aplicación primero para crear el esquema

### Error: "permission denied"
- Verificar permisos del usuario de base de datos
- Usar usuario con privilegios de administrador

### Error: "duplicate key value"
- Los datos ya existen
- Ejecutar script de limpieza primero

## 📞 Soporte

Si encuentras problemas con los scripts:
1. Verificar que PostgreSQL esté ejecutándose
2. Confirmar que la base de datos `sistema_academico` existe
3. Revisar los logs de la aplicación para errores de JPA
4. Ejecutar el script de verificación para diagnosticar
