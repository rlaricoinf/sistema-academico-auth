-- Script para verificar usuarios y sus contraseñas
-- Este script permite verificar que los usuarios se crearon correctamente

-- Verificar estructura de tablas
SELECT 'Estructura de la base de datos:' as info;
SELECT table_name, column_name, data_type 
FROM information_schema.columns 
WHERE table_name IN ('usuarios', 'perfiles', 'privilegios', 'perfil_privilegios')
ORDER BY table_name, ordinal_position;

-- Verificar perfiles creados
SELECT 'Perfiles disponibles:' as info;
SELECT id, nombre, descripcion, activo 
FROM perfiles 
ORDER BY nombre;

-- Verificar privilegios creados
SELECT 'Privilegios disponibles:' as info;
SELECT id, codigo, nombre, modulo, activo 
FROM privilegios 
ORDER BY modulo, codigo;

-- Verificar usuarios creados
SELECT 'Usuarios creados:' as info;
SELECT 
    u.id,
    u.username,
    u.nombre,
    u.apellido,
    u.email,
    u.activo,
    u.fecha_creacion,
    p.nombre as perfil,
    LENGTH(u.password) as longitud_hash
FROM usuarios u 
JOIN perfiles p ON u.perfil_id = p.id
ORDER BY p.nombre, u.username;

-- Verificar asignación de privilegios por perfil
SELECT 'Privilegios por perfil:' as info;
SELECT 
    p.nombre as perfil,
    pr.codigo as privilegio,
    pr.nombre as nombre_privilegio,
    pr.modulo
FROM perfiles p
JOIN perfil_privilegios pp ON p.id = pp.perfil_id
JOIN privilegios pr ON pp.privilegio_id = pr.id
ORDER BY p.nombre, pr.modulo, pr.codigo;

-- Contar usuarios por perfil
SELECT 'Resumen de usuarios por perfil:' as info;
SELECT 
    p.nombre as perfil,
    COUNT(u.id) as total_usuarios,
    COUNT(CASE WHEN u.activo = true THEN 1 END) as usuarios_activos
FROM perfiles p 
LEFT JOIN usuarios u ON p.id = u.perfil_id
GROUP BY p.id, p.nombre
ORDER BY p.nombre;

-- Verificar contraseñas (mostrar solo la longitud del hash por seguridad)
SELECT 'Verificación de contraseñas (longitud del hash):' as info;
SELECT 
    username,
    nombre,
    apellido,
    perfil,
    CASE 
        WHEN LENGTH(password) = 32 THEN 'Hash MD5 válido (32 caracteres)'
        ELSE 'Hash inválido (' || LENGTH(password) || ' caracteres)'
    END as estado_hash
FROM (
    SELECT 
        u.username,
        u.nombre,
        u.apellido,
        p.nombre as perfil,
        u.password
    FROM usuarios u 
    JOIN perfiles p ON u.perfil_id = p.id
) usuarios_info
ORDER BY perfil, username;

-- Función para verificar login (simular autenticación)
CREATE OR REPLACE FUNCTION verificar_login(
    p_username VARCHAR(50),
    p_password VARCHAR(255)
) RETURNS TABLE(
    usuario_id BIGINT,
    username VARCHAR(50),
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    perfil VARCHAR(50),
    login_exitoso BOOLEAN
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        u.id,
        u.username,
        u.nombre,
        u.apellido,
        p.nombre as perfil,
        (u.password = md5(p_password) AND u.activo = true) as login_exitoso
    FROM usuarios u 
    JOIN perfiles p ON u.perfil_id = p.id
    WHERE u.username = p_username;
END;
$$ LANGUAGE plpgsql;

-- Probar autenticación de usuarios de prueba
SELECT 'Prueba de autenticación:' as info;

-- Probar con contraseña correcta
SELECT 'Login con contraseña correcta (123456):' as info;
SELECT * FROM verificar_login('director', '123456');
SELECT * FROM verificar_login('profesor', '123456');
SELECT * FROM verificar_login('estudiante', '123456');

-- Probar con contraseña incorrecta
SELECT 'Login con contraseña incorrecta (wrong):' as info;
SELECT * FROM verificar_login('director', 'wrong');
SELECT * FROM verificar_login('profesor', 'wrong');
SELECT * FROM verificar_login('estudiante', 'wrong');

-- Limpiar función de prueba
DROP FUNCTION IF EXISTS verificar_login(VARCHAR, VARCHAR);
