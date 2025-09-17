-- Script para crear usuarios adicionales de prueba
-- Contraseña "123456" en MD5: e10adc3949ba59abbe56e057f20f883e

-- Usuarios adicionales para Director
INSERT INTO usuarios (username, password, nombre, apellido, email, activo, fecha_creacion, perfil_id) VALUES 
('admin', 'e10adc3949ba59abbe56e057f20f883e', 'Ana', 'Rodríguez', 'admin@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'DIRECTOR')),
('rector', 'e10adc3949ba59abbe56e057f20f883e', 'Pedro', 'Martínez', 'rector@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'DIRECTOR'));

-- Usuarios adicionales para Profesor
INSERT INTO usuarios (username, password, nombre, apellido, email, activo, fecha_creacion, perfil_id) VALUES 
('prof1', 'e10adc3949ba59abbe56e057f20f883e', 'Laura', 'Fernández', 'laura.fernandez@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'PROFESOR')),
('prof2', 'e10adc3949ba59abbe56e057f20f883e', 'Roberto', 'Silva', 'roberto.silva@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'PROFESOR')),
('prof3', 'e10adc3949ba59abbe56e057f20f883e', 'Carmen', 'Vargas', 'carmen.vargas@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'PROFESOR')),
('prof4', 'e10adc3949ba59abbe56e057f20f883e', 'Diego', 'Morales', 'diego.morales@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'PROFESOR'));

-- Usuarios adicionales para Estudiante
INSERT INTO usuarios (username, password, nombre, apellido, email, activo, fecha_creacion, perfil_id) VALUES 
('est1', 'e10adc3949ba59abbe56e057f20f883e', 'Sofía', 'Herrera', 'sofia.herrera@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'ESTUDIANTE')),
('est2', 'e10adc3949ba59abbe56e057f20f883e', 'Andrés', 'Jiménez', 'andres.jimenez@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'ESTUDIANTE')),
('est3', 'e10adc3949ba59abbe56e057f20f883e', 'Valentina', 'Castro', 'valentina.castro@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'ESTUDIANTE')),
('est4', 'e10adc3949ba59abbe56e057f20f883e', 'Sebastián', 'Rojas', 'sebastian.rojas@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'ESTUDIANTE')),
('est5', 'e10adc3949ba59abbe56e057f20f883e', 'Isabella', 'Mendoza', 'isabella.mendoza@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'ESTUDIANTE')),
('est6', 'e10adc3949ba59abbe56e057f20f883e', 'Mateo', 'Guerrero', 'mateo.guerrero@academico.com', true, NOW(), 
 (SELECT id FROM perfiles WHERE nombre = 'ESTUDIANTE'));

-- Verificar usuarios adicionales creados
SELECT 'Usuarios adicionales creados:' as info;
SELECT u.username, u.nombre, u.apellido, u.email, p.nombre as perfil 
FROM usuarios u 
JOIN perfiles p ON u.perfil_id = p.id
WHERE u.username IN ('admin', 'rector', 'prof1', 'prof2', 'prof3', 'prof4', 'est1', 'est2', 'est3', 'est4', 'est5', 'est6')
ORDER BY p.nombre, u.username;

-- Resumen total de usuarios
SELECT 'Resumen total de usuarios por perfil:' as info;
SELECT p.nombre as perfil, COUNT(u.id) as total_usuarios
FROM perfiles p 
LEFT JOIN usuarios u ON p.id = u.perfil_id
GROUP BY p.id, p.nombre
ORDER BY p.nombre;
