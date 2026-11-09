# Tienda de Videojuegos — Proyecto Final POO

Sistema de escritorio para administrar el **alquiler de videojuegos**:
clientes, videojuegos y alquileres.

Hecho en **Java 26 + Swing**, con **MVC**, **patrón DAO** y **JDBC** sobre
**MySQL**. Proyecto de **Apache NetBeans** (Ant).

## Requisitos

- JDK 26
- Apache NetBeans 31 (o cualquier IDE que abra proyectos Ant)
- MySQL 8 o superior

El driver de MySQL ya viene en `Tienda videojuegos/lib/`.

## 1. Crear la base de datos

1. Abrí MySQL Workbench (o la consola `mysql`) con el usuario `root`.
2. Ejecutá `Tienda videojuegos/sql/tienda_videojuegos.sql`.
   Crea la base `tienda_videojuegos` con las tablas `cliente`,
   `videojuego` y `alquiler`. Se puede correr más de una vez.
3. (Opcional) Ejecutá `Tienda videojuegos/sql/datos_prueba.sql` para
   cargar clientes y videojuegos de ejemplo.

Desde la consola:

```
mysql -u root -p < "Tienda videojuegos/sql/tienda_videojuegos.sql"
```

## 2. Configurar la conexión

Los datos de conexión están **en un solo lugar**:
`Tienda videojuegos/src/dao/ConexionBD.java`.

Cambiá `PASSWORD` (y `USUARIO` si hace falta) por los de tu MySQL.

## 3. Ejecutar

1. En NetBeans: **File → Open Project** y elegí la carpeta
   `Tienda videojuegos`.
2. **Run** (F6). Se abre el menú principal con tres botones:
   Clientes, Videojuegos y Alquileres.

## Estructura

```
src/
├── models/        Cliente, Videojuego, Alquiler
├── dao/           Interfaces DAO + ConexionBD
│   └── impl/      Implementaciones JDBC
├── controllers/   Un controlador por pantalla + menú principal
├── views/         Pantallas Swing
└── tienda/videojuegos/TiendaVideojuegos.java   (main)
```

## Reglas de negocio

1. Solo se puede alquilar un videojuego **disponible**.
2. Al registrar un alquiler, el videojuego pasa a **no disponible**.
3. Cada alquiler está asociado a **un cliente y un videojuego**
   (claves foráneas en la base).
4. Al finalizar un alquiler, el videojuego vuelve a estar **disponible**.
5. No se puede finalizar **dos veces** el mismo alquiler.
6. No se puede eliminar un videojuego con un **alquiler activo**.
7. No se aceptan **fechas inválidas** (formato `dd/mm/aaaa` y la
   devolución prevista no puede ser anterior al alquiler).

Registrar y finalizar un alquiler cambian dos tablas a la vez, por eso
se hacen en una **transacción** (`AlquilerDAOImpl`).

La disponibilidad de un videojuego **no se edita a mano**: la cambian
solamente los alquileres.

## Diagramas

- `docs/diagrama-casos-de-uso.svg`
- `docs/diagrama-secuencia-registrar-alquiler.svg`
