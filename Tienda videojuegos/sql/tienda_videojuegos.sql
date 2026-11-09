-- =============================================================
-- Base de datos: Tienda de Videojuegos (alquiler)
-- Motor: MySQL 8 o superior
--
-- Se puede ejecutar más de una vez: solo crea lo que falta.
-- =============================================================

CREATE DATABASE IF NOT EXISTS tienda_videojuegos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tienda_videojuegos;

-- -------------------------------------------------------------
-- Clientes
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS cliente (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(50)  NOT NULL,
    apellido  VARCHAR(50)  NOT NULL,
    dni       VARCHAR(20)  NOT NULL,
    telefono  VARCHAR(30),
    email     VARCHAR(100)
);

-- -------------------------------------------------------------
-- Videojuegos
-- disponible = TRUE  -> se puede alquilar
-- disponible = FALSE -> tiene un alquiler activo
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS videojuego (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    titulo      VARCHAR(100)  NOT NULL,
    genero      VARCHAR(50)   NOT NULL,
    plataforma  VARCHAR(50)   NOT NULL,
    precio      DECIMAL(10,2) NOT NULL,
    disponible  BOOLEAN       NOT NULL DEFAULT TRUE
);

-- -------------------------------------------------------------
-- Alquileres: relaciona un cliente con un videojuego.
--
-- activo = TRUE  -> el videojuego todavía no se devolvió
-- activo = FALSE -> finalizado (tiene fecha_devolucion_real)
--
-- Relaciones:
--   * Un cliente no se puede borrar si tiene alquileres (RESTRICT).
--   * Si se borra un videojuego se borra su historial de alquileres
--     (CASCADE). El programa no deja borrar un videojuego con un
--     alquiler activo.
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS alquiler (
    id                         INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id                 INT     NOT NULL,
    videojuego_id              INT     NOT NULL,
    fecha_alquiler             DATE    NOT NULL,
    fecha_devolucion_prevista  DATE    NOT NULL,
    fecha_devolucion_real      DATE    NULL,
    activo                     BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_alquiler_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente (id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_alquiler_videojuego
        FOREIGN KEY (videojuego_id) REFERENCES videojuego (id)
        ON DELETE CASCADE,

    CONSTRAINT chk_fechas
        CHECK (fecha_devolucion_prevista >= fecha_alquiler)
);
