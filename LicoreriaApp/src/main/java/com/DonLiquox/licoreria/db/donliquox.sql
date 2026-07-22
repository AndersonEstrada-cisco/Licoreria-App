CREATE TABLE usuarios (
      id_usuario SERIAL PRIMARY KEY,
      nombre VARCHAR(100) NOT NULL,
      cedula VARCHAR(10) NOT NULL UNIQUE,
      edad INT NOT NULL,
      correo VARCHAR(100) NOT NULL UNIQUE,
      clave VARCHAR(100) NOT NULL,
      rol VARCHAR(20) NOT NULL CHECK (rol IN ('Administrador', 'Cajero', 'Reportes'))
);

CREATE TABLE clientes (
      id_cliente SERIAL PRIMARY KEY,
      nombre VARCHAR(100) NOT NULL,
      cedula VARCHAR(10) NOT NULL UNIQUE,
      edad INT NOT NULL,
      telefono VARCHAR(10) NOT NULL,
      email VARCHAR(100) NOT NULL,
      direccion VARCHAR(150) NOT NULL
);

CREATE TABLE productos (
   id_producto SERIAL PRIMARY KEY,
   nombre VARCHAR(100) NOT NULL,
   categoria VARCHAR(50) NOT NULL,
   precio NUMERIC(10,2) NOT NULL CHECK (precio > 0),
   stock INT NOT NULL CHECK (stock >= 0)
);

CREATE TABLE ventas (
    id_venta SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL REFERENCES usuarios(id_usuario),
    id_cliente INT NOT NULL REFERENCES clientes(id_cliente),
    total NUMERIC(10,2) NOT NULL DEFAULT 0,
    fecha TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE detalle_venta (
   id_detalle SERIAL PRIMARY KEY,
   id_venta INT NOT NULL REFERENCES ventas(id_venta),
   id_producto INT NOT NULL REFERENCES productos(id_producto),
   cantidad INT NOT NULL CHECK (cantidad > 0),
   subtotal NUMERIC(10,2) NOT NULL
);


INSERT INTO usuarios (nombre, cedula, edad, correo, clave, rol) VALUES
    ('admin', '0101010101', 25, 'admin@admin.com', 'admin123', 'Administrador'),
    ('reporte', '1111111111', 18, 'reportes@reportes.com', 'reportes123', 'Reportes'),
    ('cajero', '0000000000', 18, 'cajero@cajero.com', 'cajero123', 'Cajero');

INSERT INTO productos (nombre, categoria, precio, stock) VALUES
    ('Zhumir Seco 750ml', 'Aguardiente', 8.50, 50),
    ('Zhumir Naranja 750ml', 'Aguardiente', 8.75, 40),
    ('Trópico Seco 750ml', 'Aguardiente', 7.90, 45),
    ('Ron San Miguel Añejo 750ml', 'Ron', 12.00, 35),
    ('Whisky Old Times 750ml', 'Whisky', 9.80, 20),
    ('Cerveza Pilsener 355ml', 'Cerveza', 1.10, 200),
    ('Cerveza Club Verde 355ml', 'Cerveza', 1.15, 180),
('Pájaro Azul 750ml', 'Aguardiente', 7.20, 40);

INSERT INTO clientes (nombre, cedula, edad, telefono, email, direccion)
VALUES ('Consumidor Final', '9999999999', 25, '0000000000', 'cf@cf.com', 'Sin direccion');

/* BORRO TODA LA INFO de las tablas
TRUNCATE TABLE detalle_venta, ventas, clientes, productos, RESTART IDENTITY CASCADE;
*/