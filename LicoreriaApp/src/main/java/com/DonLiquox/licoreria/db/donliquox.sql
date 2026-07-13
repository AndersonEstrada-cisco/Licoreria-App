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
INSERT INTO  usuarios(nombre, cedula, edad, correo, clave, rol)
VALUES ('admin', '000000000', '0', 'admin', 'admin', 'Administrador');

INSERT INTO usuarios(nombre, cedula, edad, correo, clave, rol)
VALUES ('alexis', '0202659272', 19, 'alexis@gmail.com', 'alex15', 'Administrador');
