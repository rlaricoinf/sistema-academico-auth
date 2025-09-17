-- Script para limpiar datos de prueba del Sistema Académico
-- ⚠️ ADVERTENCIA: Este script eliminará TODOS los datos de la base de datos
-- Ejecutar solo en entornos de desarrollo o testing

-- Deshabilitar restricciones de clave foránea temporalmente
SET session_replication_role = replica;

-- Eliminar datos en orden correcto (respetando las relaciones)
DELETE FROM perfil_privilegios;
DELETE FROM usuarios;
DELETE FROM privilegios;
DELETE FROM perfiles;

-- Habilitar restricciones de clave foránea
SET session_replication_role = DEFAULT;

-- Verificar que las tablas estén vacías
SELECT 'Verificación de limpieza:' as info;

SELECT 'Usuarios restantes:' as tabla, COUNT(*) as total FROM usuarios
UNION ALL
SELECT 'Perfiles restantes:' as tabla, COUNT(*) as total FROM perfiles
UNION ALL
SELECT 'Privilegios restantes:' as tabla, COUNT(*) as total FROM privilegios
UNION ALL
SELECT 'Perfil-Privilegios restantes:' as tabla, COUNT(*) as total FROM perfil_privilegios;

-- Reiniciar secuencias (si existen)
-- Esto asegura que los nuevos registros empiecen desde 1
DO $$
DECLARE
    seq_name TEXT;
BEGIN
    -- Reiniciar secuencia de usuarios
    SELECT pg_get_serial_sequence('usuarios', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
        RAISE NOTICE 'Secuencia de usuarios reiniciada';
    END IF;
    
    -- Reiniciar secuencia de perfiles
    SELECT pg_get_serial_sequence('perfiles', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
        RAISE NOTICE 'Secuencia de perfiles reiniciada';
    END IF;
    
    -- Reiniciar secuencia de privilegios
    SELECT pg_get_serial_sequence('privilegios', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
        RAISE NOTICE 'Secuencia de privilegios reiniciada';
    END IF;
END $$;

-- Mensaje de confirmación
SELECT 'Limpieza completada. La base de datos está lista para nuevos datos.' as resultado;
