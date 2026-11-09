-- =============================================================
-- Datos de ejemplo (opcional) para probar el sistema.
-- Ejecutar DESPUÉS de tienda_videojuegos.sql y sobre tablas vacías.
-- =============================================================

USE tienda_videojuegos;

INSERT INTO cliente (nombre, apellido, dni, telefono, email) VALUES
    ('Juan',  'Pérez',  '30111222', '1155550001', 'juan.perez@mail.com'),
    ('María', 'Gómez',  '32333444', '1155550002', 'maria.gomez@mail.com'),
    ('Lucas', 'Fernández', '35555666', '1155550003', 'lucas.f@mail.com');

INSERT INTO videojuego (titulo, genero, plataforma, precio, disponible) VALUES
    ('EA Sports FC 26',       'Deportes', 'Playstation 5',     3500.00, TRUE),
    ('Mario Kart World',      'Carreras', 'Nintendo Switch 2', 4000.00, TRUE),
    ('Minecraft',             'Aventura', 'PC',                2000.00, TRUE),
    ('Halo Infinite',         'Acción',   'Xbox Series X/S',   2500.00, TRUE);
