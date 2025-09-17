-- Script para crear usuarios de prueba del Sistema Académico
-- Las contraseñas están hasheadas en MD5
-- Ejecutar después de crear las tablas con JPA

-- Insertar perfiles
INSERT INTO perfiles (nombre, descripcion, activo) VALUES 
('DIRECTOR', 'Director del Sistema - Acceso completo al sistema académico', true),
('PROFESOR', 'Profesor - Gestión de cursos y estudiantes', true),
('ESTUDIANTE', 'Estudiante - Consulta de información personal', true);

-- Insertar privilegios
INSERT INTO privilegios (codigo, nombre, descripcion, modulo, url, activo) VALUES 
-- Privilegios para Director
('USUARIOS_VER', 'Ver Usuarios', 'Permite ver la lista de usuarios', 'Usuarios', '/app/usuarios.xhtml', true),
('USUARIOS_CREAR', 'Crear Usuarios', 'Permite crear nuevos usuarios', 'Usuarios', '/app/usuarios.xhtml', true),
('USUARIOS_EDITAR', 'Editar Usuarios', 'Permite editar usuarios existentes', 'Usuarios', '/app/usuarios.xhtml', true),
('USUARIOS_ELIMINAR', 'Eliminar Usuarios', 'Permite eliminar usuarios', 'Usuarios', '/app/usuarios.xhtml', true),
('PERFILES_VER', 'Ver Perfiles', 'Permite ver la lista de perfiles', 'Perfiles', '/app/perfiles.xhtml', true),
('PERFILES_CREAR', 'Crear Perfiles', 'Permite crear nuevos perfiles', 'Perfiles', '/app/perfiles.xhtml', true),
('PERFILES_EDITAR', 'Editar Perfiles', 'Permite editar perfiles existentes', 'Perfiles', '/app/perfiles.xhtml', true),
('PERFILES_ELIMINAR', 'Eliminar Perfiles', 'Permite eliminar perfiles', 'Perfiles', '/app/perfiles.xhtml', true),
('PRIVILEGIOS_VER', 'Ver Privilegios', 'Permite ver la lista de privilegios', 'Privilegios', '/app/privilegios.xhtml', true),
('PRIVILEGIOS_CREAR', 'Crear Privilegios', 'Permite crear nuevos privilegios', 'Privilegios', '/app/privilegios.xhtml', true),
('PRIVILEGIOS_EDITAR', 'Editar Privilegios', 'Permite editar privilegios existentes', 'Privilegios', '/app/privilegios.xhtml', true),
('PRIVILEGIOS_ELIMINAR', 'Eliminar Privilegios', 'Permite eliminar privilegios', 'Privilegios', '/app/privilegios.xhtml', true),
('REPORTES_VER', 'Ver Reportes', 'Permite acceder a los reportes del sistema', 'Reportes', '/app/reportes.xhtml', true),
('CONFIGURACION_VER', 'Ver Configuración', 'Permite acceder a la configuración del sistema', 'Configuración', '/app/configuracion.xhtml', true),

-- Privilegios para Profesor
('CURSOS_VER', 'Ver Cursos', 'Permite ver los cursos asignados', 'Cursos', '/app/cursos.xhtml', true),
('CURSOS_EDITAR', 'Editar Cursos', 'Permite editar información de cursos', 'Cursos', '/app/cursos.xhtml', true),
('ESTUDIANTES_VER', 'Ver Estudiantes', 'Permite ver la lista de estudiantes', 'Estudiantes', '/app/estudiantes.xhtml', true),
('CALIFICACIONES_VER', 'Ver Calificaciones', 'Permite ver las calificaciones', 'Calificaciones', '/app/calificaciones.xhtml', true),
('CALIFICACIONES_EDITAR', 'Editar Calificaciones', 'Permite editar calificaciones', 'Calificaciones', '/app/calificaciones.xhtml', true),
('ASISTENCIA_VER', 'Ver Asistencia', 'Permite ver la asistencia', 'Asistencia', '/app/asistencia.xhtml', true),
('ASISTENCIA_EDITAR', 'Editar Asistencia', 'Permite editar la asistencia', 'Asistencia', '/app/asistencia.xhtml', true),

-- Privilegios para Estudiante
('MIS_CURSOS_VER', 'Ver Mis Cursos', 'Permite ver los cursos inscritos', 'Mis Cursos', '/app/mis-cursos.xhtml', true),
('MIS_CALIFICACIONES_VER', 'Ver Mis Calificaciones', 'Permite ver las propias calificaciones', 'Mis Calificaciones', '/app/mis-calificaciones.xhtml', true),
('MI_ASISTENCIA_VER', 'Ver Mi Asistencia', 'Permite ver la propia asistencia', 'Mi Asistencia', '/app/mi-asistencia.xhtml', true),
('PERFIL_EDITAR', 'Editar Perfil', 'Permite editar el perfil personal', 'Perfil', '/app/perfil.xhtml', true);

-- Asignar privilegios a perfiles
-- Director (todos los privilegios)
INSERT INTO perfil_privilegios (perfil_id, privilegio_id) 
SELECT p.id, pr.id 
FROM perfiles p, privilegios pr 
WHERE p.nombre = 'DIRECTOR' 
AND pr.codigo IN (
    'USUARIOS_VER', 'USUARIOS_CREAR', 'USUARIOS_EDITAR', 'USUARIOS_ELIMINAR',
    'PERFILES_VER', 'PERFILES_CREAR', 'PERFILES_EDITAR', 'PERFILES_ELIMINAR',
    'PRIVILEGIOS_VER', 'PRIVILEGIOS_CREAR', 'PRIVILEGIOS_EDITAR', 'PRIVILEGIOS_ELIMINAR',
    'REPORTES_VER', 'CONFIGURACION_VER'
);

-- Profesor
INSERT INTO perfil_privilegios (perfil_id, privilegio_id) 
SELECT p.id, pr.id 
FROM perfiles p, privilegios pr 
WHERE p.nombre = 'PROFESOR' 
AND pr.codigo IN (
    'CURSOS_VER', 'CURSOS_EDITAR', 'ESTUDIANTES_VER',
    'CALIFICACIONES_VER', 'CALIFICACIONES_EDITAR',
    'ASISTENCIA_VER', 'ASISTENCIA_EDITAR', 'PERFIL_EDITAR'
);

-- Estudiante
INSERT INTO perfil_privilegios (perfil_id, privilegio_id) 
SELECT p.id, pr.id 
FROM perfiles p, privilegios pr 
WHERE p.nombre = 'ESTUDIANTE' 
AND pr.codigo IN (
    'MIS_CURSOS_VER', 'MIS_CALIFICACIONES_VER', 'MI_ASISTENCIA_VER', 'PERFIL_EDITAR'
);

-- Insertar usuarios de prueba
-- Contraseña "123456" en MD5: e10adc3949ba59abbe56e057f20f883e
INSERT INTO usuarios (username, password, nombre, apellido, email, activo, fecha_creacion, perfil_id) VALUES 
('director', 'e10adc3949ba59abbe56e057f20f883e', 'Juan', 'Pérez', 'director@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'DIRECTOR')),
('profesor', 'e10adc3949ba59abbe56e057f20f883e', 'María', 'González', 'profesor@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'PROFESOR')),
('estudiante', 'e10adc3949ba59abbe56e057f20f883e', 'Carlos', 'López', 'estudiante@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'ESTUDIANTE'));

-- Verificar datos insertados
SELECT 'Perfiles creados:' as info;
SELECT * FROM perfiles;

SELECT 'Privilegios creados:' as info;
SELECT COUNT(*) as total_privilegios FROM privilegios;

SELECT 'Usuarios creados:' as info;
SELECT u.username, u.nombre, u.apellido, u.email, p.nombre as perfil 
FROM usuarios u 
JOIN perfiles p ON u.perfil_id = p.id;

SELECT 'Privilegios por perfil:' as info;
SELECT p.nombre as perfil, COUNT(pp.privilegio_id) as total_privilegios
FROM perfiles p 
LEFT JOIN perfil_privilegios pp ON p.id = pp.perfil_id
GROUP BY p.id, p.nombre
ORDER BY p.nombre;
