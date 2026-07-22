#  Don Liquox - Sistema de Gestión para Licorería

Proyecto académico - Chasi Alexis &amp; Estrada Anderson


Aplicación de escritorio desarrollada en **Java** y **JavaFX** bajo el patrón arquitectónico **MVC (Modelo-Vista-Controlador)**, diseñada para optimizar y automatizar el control de inventarios, la gestión de clientes y usuarios, las transacciones comerciales y la generación de reportes analíticos para una establecimiento comercial. La persistencia de los datos se gestiona mediante una base de datos relacional en **PostgreSQL** alojada en la nube con **Neon**.

---

## Características Principales

* **Control de Roles y Seguridad:** Acceso personalizado según el perfil autenticado:
  * **Administrador:** Control total de inventarios, usuarios, clientes, ventas y reportes.
  * **Cajero:** Módulo especializado para el registro ágil de ventas y selección de clientes.
  * **Reportes:** Acceso exclusivo al análisis y exportación de estadísticas comerciales.
* **Gestión de Inventario (CRUD):** Administración completa del catálogo de bebidas y productos (control de stock, categorías y precios unitarios).
* **Gestión de Clientes (CRUD):** Registro, validación de cédulas y consulta de la cartera de compradores frecuentes.
* **Facturación y Ventas:** Carrito de compras interactivo con cálculo en tiempo real de subtotales, totales y descuento automático de existencias en el inventario.
* **Reportes Analíticos:** Filtro de transacciones por rangos de fecha y exportación de datos de los productos con mayor salida comercial.

---

##  Tecnologías y Herramientas Utilizadas

* **Lenguaje:** Java (JDK 26)
* **Interfaz Gráfica:** JavaFX (Vistas FXML y Controladores)
* **Base de Datos:** PostgreSQL (Alojado en la nube con Neon)
* **Conectividad:** JDBC (Java Database Connectivity)
* **Gestor de Dependencias:** Apache Maven / Maven Wrapper (`mvnw.cmd`)
* **Empaquetado y Compilación:** 
  * `maven-shade-plugin` (Empaquetado consolidado de tipo *fat jar*)
  * `jpackage-maven-plugin` y `jpackage` (Generación de instalador nativo)
  * **WiX Toolset (v3.11.2):** Herramienta interna para la compilación del instalador `.exe` en Windows.

---

##  Arquitectura del Proyecto (Patrón MVC)

El código fuente se encuentra modularizado bajo una estructura limpia de paquetes:

* `app`: Clase principal de ejecución y lanzador (`Launcher.java`) optimizado para JavaFX.
* `model`: Clases de dominio y entidades del negocio (Persona, Usuario, Cliente, Producto, Venta, DetalleVenta).
* `controller`: Controladores de JavaFX que enlazan las vistas FXML con la lógica de negocio.
* `dao`: Implementación del patrón DAO y la interfaz genérica `ICRUD<T>` para la comunicación SQL con PostgreSQL.
* `db`: Configuración de la conexión a la base de datos y scripts de inicialización.
* `view`: Archivos de interfaz gráfica en formato `.fxml`.

---

## Documentos del trabajo

En la carpeta "Documetacion" se encuetra el informe, manual y video del proyecto.

---

##  Instalación y Ejecución

### Opción 1: Usando el Instalador Nativo (.exe)
1. Descarga el archivo instalador oficial `LicoreriaApp-1.0.0.exe` que se encuentra en LicoreriaApp/src/main/java/com/DonLiquox/licoreria/dist.
2. Ejecuta el archivo y se crara el directorio con la app.
3. Busca C:\Program Files\DonLiquox en buscador de Windows.
4. Abre la aplicación.

### Opción 2: Ejecución desde el Código Fuente (Desarrollo)
1. Clona este repositorio:
   ```bash
   git clone [https://github.com/tu-usuario/LicoreriaApp.git](https://github.com/tu-usuario/LicoreriaApp.git)
