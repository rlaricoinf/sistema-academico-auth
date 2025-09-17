-- Script para generar hashes MD5 de contraseñas
-- Este script muestra cómo calcular el hash MD5 de diferentes contraseñas

-- Función para generar hash MD5 (PostgreSQL)
CREATE OR REPLACE FUNCTION md5_hash(text) RETURNS text AS $$
BEGIN
    RETURN md5($1);
END;
$$ LANGUAGE plpgsql;

-- Ejemplos de contraseñas y sus hashes MD5
SELECT 'Contraseñas comunes y sus hashes MD5:' as info;

SELECT 
    '123456' as password,
    md5('123456') as hash_md5,
    'Contraseña por defecto' as descripcion;

SELECT 
    'admin' as password,
    md5('admin') as hash_md5,
    'Contraseña de administrador' as descripcion;

SELECT 
    'password' as password,
    md5('password') as hash_md5,
    'Contraseña común' as descripcion;

SELECT 
    'academico2024' as password,
    md5('academico2024') as hash_md5,
    'Contraseña personalizada' as descripcion;

SELECT 
    'sistema123' as password,
    md5('sistema123') as hash_md5,
    'Contraseña del sistema' as descripcion;

-- Función para crear usuario con hash MD5
CREATE OR REPLACE FUNCTION crear_usuario_con_hash(
    p_username VARCHAR(50),
    p_password VARCHAR(255),
    p_nombre VARCHAR(100),
    p_apellido VARCHAR(100),
    p_email VARCHAR(100),
    p_perfil VARCHAR(50)
) RETURNS VOID AS $$
DECLARE
    v_perfil_id BIGINT;
    v_password_hash VARCHAR(255);
BEGIN
    -- Generar hash MD5 de la contraseña
    v_password_hash := md5(p_password);
    
    -- Obtener ID del perfil
    SELECT id INTO v_perfil_id FROM perfiles WHERE nombre = p_perfil;
    
    IF v_perfil_id IS NULL THEN
        RAISE EXCEPTION 'Perfil % no encontrado', p_perfil;
    END IF;
    
    -- Insertar usuario
    INSERT INTO usuarios (username, password, nombre, apellido, email, activo, fecha_creacion, perfil_id)
    VALUES (p_username, v_password_hash, p_nombre, p_apellido, p_email, true, NOW(), v_perfil_id);
    
    RAISE NOTICE 'Usuario % creado exitosamente con perfil %', p_username, p_perfil;
END;
$$ LANGUAGE plpgsql;

-- Ejemplos de uso de la función
SELECT 'Ejemplos de creación de usuarios:' as info;

-- Crear usuario director con contraseña personalizada
-- SELECT crear_usuario_con_hash('director2', 'mipassword123', 'Juan', 'Director', 'director2@academico.com', 'DIRECTOR');

-- Crear usuario profesor con contraseña personalizada
-- SELECT crear_usuario_con_hash('profesor2', 'profesor2024', 'María', 'Profesora', 'profesor2@academico.com', 'PROFESOR');

-- Crear usuario estudiante con contraseña personalizada
-- SELECT crear_usuario_con_hash('estudiante2', 'estudiante2024', 'Carlos', 'Estudiante', 'estudiante2@academico.com', 'ESTUDIANTE');

-- Limpiar función (opcional)
-- DROP FUNCTION IF EXISTS md5_hash(text);
-- DROP FUNCTION IF EXISTS crear_usuario_con_hash(VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR);
