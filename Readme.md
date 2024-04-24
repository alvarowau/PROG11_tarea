# PROG11 Tarea - Gestión de Concesionario con Base de Datos Relacional

Este proyecto es una pequeña aplicación que interactúa con una base de datos relacional MariaDB para gestionar
propietarios y vehículos. La aplicación usa Dbeaver para administrar la base de datos y Java para la lógica de negocio.
El proyecto sigue el patrón DAO (Data Access Object) para separar la lógica de acceso a datos de la lógica de negocio.

## Objetivo del Proyecto

Desarrollar una aplicación Java que permita gestionar una base de datos de concesionarios con operaciones CRUD (Crear,
Leer, Actualizar, Eliminar). La aplicación tiene que ser capaz de:

- Insertar y eliminar propietarios y vehículos.
- Actualizar datos de vehículos.
- Listar vehículos y propietarios.
- Recuperar vehículos por marca o propietario.
- Generar informes de inventario.

## Configuración del Entorno

1. **Instalación de MariaDB y DBeaver**
    - Instala MariaDB y DBeaver para gestionar la base de datos. Sigue las instrucciones específicas para tu sistema
      operativo.
    - Inicia el servidor MariaDB e inicia DBeaver para administrar la base de datos.
    - Crea una nueva base de datos llamada `Concesionario`.

2. **Creación de Tablas**
    - Crea dos tablas en la base de datos `Concesionario`: `Vehiculos` y `Propietarios`.
    - La estructura de las tablas debe ser:
        - `Vehiculos`:
            - `mat_veh` (VARCHAR(10)): Clave primaria.
            - `marca_veh` (VARCHAR(50)): Marca del vehículo.
            - `kms_veh` (INT): Kilometraje del vehículo.
            - `precio_veh` (FLOAT): Precio del vehículo.
            - `id_prop` (INT): Clave externa de la tabla `Propietarios`.
        - `Propietarios`:
            - `id_prop` (INT, AUTO_INCREMENT): Clave primaria.
            - `nombre_prop` (VARCHAR(50)): Nombre del propietario.
            - `dni_prop` (VARCHAR(10)): DNI del propietario.

Para obtener el archivo SQL completo con la creación de tablas, [haz clic aquí](./tablas_bbdd.sql).

## Clases y Métodos

El proyecto se organiza en dos paquetes principales:

1. **com.prog11.bbdd**
    - Contiene las clases DAO (Data Access Object) que interactúan con la base de datos.
    - **ConnectionDB**:
        - `openConnection()`: Abre la conexión con la base de datos.
        - `closeConnection()`: Cierra la conexión con la base de datos.
    - **PropietariosDAO**:
        - `insertarPropietario(connection, nombre, dni)`: Inserta un nuevo propietario.
        - `obtenerVehiculosDePropietario(connection, dni)`: Recupera vehículos de un propietario por DNI.
        - `eliminarPropietario(connection, dni)`: Elimina un propietario por DNI.
    - **VehiculosDAO**:
        - `insertarVehiculo(connection, matricula, marca, kms, precio, idProp)`: Inserta un nuevo vehículo.
        - `actualizarPropietarioVehiculo(connection, matricula, idProp)`: Actualiza el propietario de un vehículo.
        - `eliminarVehiculo(connection, matricula)`: Elimina un vehículo por matrícula.
        - `obtenerTodosLosVehiculos(connection)`: Recupera todos los vehículos del concesionario.
        - `obtenerVehiculosPorMarca(connection, marca)`: Recupera vehículos por marca.
    - **FuncionalidadesExtrasDAO**:
        - `buscarVehiculosPorPrecio(connection, precioMin, precioMax)`: Busca vehículos en un rango de precio.
        - `actualizarVehiculo(connection, matricula, nuevaMarca, nuevosKms, nuevoPrecio)`: Actualiza detalles de un
          vehículo.
        - `agruparVehiculosPorMarca(connection)`: Agrupa vehículos por marca.
        - `generarInformeInventario(connection, opcion)`: Genera un informe del inventario de vehículos.

2. **com.prog11.princ**
    - Contiene la clase principal que ejecuta la aplicación.
    - **Prog11_Principal**:
        - El método `main` ejecuta diversas pruebas para validar las funcionalidades:
            - Insertar vehículos y propietarios.
            - Listar todos los vehículos.
            - Actualizar el propietario de un vehículo.
            - Eliminar vehículos y propietarios.
            - Buscar vehículos por marca y rango de precio.
            - Generar informes de inventario.

## Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

- **Paquete `com.prog11.bbdd`**:
    - **`ConnectionDB`**: Clase para gestionar la conexión a la base de datos. Contiene métodos `openConnection()`
      y `closeConnection()`.
    - **`PropietariosDAO`**: Métodos para operaciones CRUD sobre la tabla `Propietarios`. Incluye inserción,
      eliminación, y obtención de vehículos por propietario.
    - **`VehiculosDAO`**: Métodos para operaciones CRUD sobre la tabla `Vehículos`. Incluye inserción, eliminación,
      actualización del propietario del vehículo, y obtención de vehículos por marca.

- **Paquete `com.prog11.princ`**:
    - **`Prog11_Principal`**: Clase principal con el método `main`. Contiene la lógica para probar los métodos
      implementados en las clases DAO.

## Instrucciones para Ejecución

1. **Conectar a la Base de Datos**:
    - Asegúrate de tener MariaDB en ejecución.
    - Compila el proyecto y ejecuta la clase principal `Prog11_Principal`.

2. **Probar las Funcionalidades**:
    - El código en `Prog11_Principal` realiza una serie de pruebas para validar las operaciones CRUD. Esto incluye
      insertar propietarios y vehículos, listar vehículos, actualizar propietarios de vehículos, eliminar vehículos y
      propietarios, y buscar vehículos por marca o propietario.

## Reflexión sobre el Diseño del Software

El diseño de software basado en la separación de responsabilidades, donde se mantiene la lógica de acceso a datos en
clases DAO y la lógica de negocio en una clase principal, ofrece múltiples ventajas.

- **Desacoplamiento**: Al tener el código de acceso a la base de datos separado del código de negocio, se facilita el
  mantenimiento y la evolución del sistema. Cualquier cambio en la estructura de la base de datos o en la lógica de
  acceso a datos puede hacerse sin afectar la lógica principal del programa. Esto permite mayor flexibilidad para
  realizar actualizaciones o mejoras.

- **Reutilización de Código**: La estructura de las clases DAO fomenta la reutilización del código. Los métodos
  implementados para interactuar con la base de datos se pueden utilizar en diferentes partes del proyecto o incluso en
  otros proyectos con necesidades similares, sin necesidad de replicar el código. Esto mejora la productividad y reduce
  el tiempo de desarrollo para futuras extensiones.

- **Pruebas Unitarias**: Con la lógica de negocio y la lógica de acceso a datos separadas, es posible crear pruebas
  unitarias más eficaces y precisas. Las clases DAO pueden ser probadas por separado, asegurando que cada operación de
  la base de datos funcione correctamente antes de integrarla con el resto del sistema. Esto conduce a un código más
  confiable y reduce el riesgo de errores en producción.

- **Legibilidad y Mantenimiento**: El código es más fácil de leer y mantener cuando cada clase tiene una responsabilidad
  clara y bien definida. La estructura basada en clases DAO permite a los desarrolladores entender rápidamente el
  propósito de cada parte del código y encontrar errores o realizar mejoras sin afectar el resto del sistema.

- **Facilita la Escalabilidad**: Con esta separación, se puede escalar el sistema más fácilmente. Si el proyecto crece y
  requiere nuevas funcionalidades o cambios importantes, el desacoplamiento entre la lógica de negocio y el acceso a
  datos facilita la adaptación y la incorporación de nuevos componentes.

En resumen, la separación del acceso a la base de datos de la lógica de negocio es una práctica recomendada que mejora
la mantenibilidad, reutilización, y la calidad general del software.

## Propuesta para No Crear Objeto Connection en la Clase Principal

En el contexto de un proyecto que utiliza una base de datos relacional como MariaDB, es común tener un objeto de
conexión (`Connection`) para interactuar con la base de datos. Sin embargo, al mantener esta conexión en la clase
principal, pueden surgir problemas de acoplamiento, además de complejidad al pasar el objeto de conexión como parámetro
a diferentes métodos y clases. Una solución más eficiente es utilizar un patrón de diseño como Singleton para gestionar
la conexión.

### ¿Qué es el Patrón Singleton?

El patrón Singleton es un patrón de diseño que garantiza que solo exista una instancia de una clase en todo el proyecto.
Esto resulta útil cuando necesitamos mantener un recurso único, como una conexión a la base de datos, y compartirlo
entre diferentes partes del sistema.

### Implementación del Patrón Singleton para Conexión a la Base de Datos

En lugar de crear el objeto de conexión en la clase principal y pasarlo como parámetro a cada método o clase que lo
necesite, podemos implementar un Singleton para gestionar la conexión. Aquí se muestra cómo sería el proceso:

1. **Clase Singleton**: Crea una clase, como `ConnectionManager`, que almacene la instancia única de `Connection`. Esta
   clase se encargará de crear la conexión si aún no existe y de proporcionar acceso a ella.

2. **Método para Obtener la Conexión**: La clase Singleton debe tener un método estático para obtener la conexión a la
   base de datos. Si la conexión aún no se ha establecido, el método la creará y la almacenará para usos futuros.

3. **Gestión del Ciclo de Vida de la Conexión**: El Singleton también puede incluir métodos para cerrar la conexión y
   para verificar su estado, asegurando un manejo adecuado del ciclo de vida de la conexión.

### Ventajas de Usar el Patrón Singleton para la Conexión a la Base de Datos

- **Desacoplamiento**: Al centralizar la gestión de la conexión en una clase Singleton, se reduce el acoplamiento en la
  clase principal y entre otras clases. No es necesario pasar el objeto `Connection` por todo el código.

- **Simplificación del Código**: Al tener un único punto de acceso a la conexión, el código es más fácil de entender y
  mantener. No hay necesidad de preocuparse por el manejo de múltiples instancias de conexión.

- **Mejor Control del Ciclo de Vida**: El patrón Singleton permite controlar el ciclo de vida de la conexión de forma
  centralizada, lo que ayuda a evitar fugas de recursos y otros problemas relacionados con la gestión de conexiones.

- **Reutilización de Conexiones**: Al tener una única instancia de conexión, se reduce el riesgo de crear conexiones
  adicionales innecesarias, lo que puede optimizar el rendimiento y el uso de recursos.

En resumen, el uso del patrón Singleton para gestionar la conexión a la base de datos es una alternativa eficaz para
evitar la creación del objeto `Connection` en la clase principal, lo que lleva a un código más limpio, desacoplado y
fácil de mantener.

## Funcionalidades Adicionales

La clase `FuncionalidadesExtrasDAO` ofrece varias funciones útiles para trabajar con vehículos en una base de datos.
Estas funciones incluyen buscar, actualizar y agrupar vehículos, así como generar informes de inventario. A continuación
se describe el propósito de cada método y su funcionamiento:

### Método: `buscarVehiculosPorPrecio(Connection connection, float precioMin, float precioMax)`

Este método busca vehículos cuyo precio está dentro de un rango específico. Toma como parámetros:

- `connection`: la conexión a la base de datos.
- `precioMin`: el precio mínimo para la búsqueda.
- `precioMax`: el precio máximo para la búsqueda.

El resultado es una lista de cadenas con información sobre los vehículos que cumplen el criterio. Si se produce un error
durante la ejecución, se captura una excepción `SQLException` y se muestra un mensaje de error. La salida incluye
detalles como matrícula, marca, kilometraje y precio.

### Método: `actualizarVehiculo(Connection connection, String matricula, String nuevaMarca, int nuevosKms, float nuevoPrecio)`

Este método actualiza los detalles de un vehículo específico. Requiere los siguientes parámetros:

- `connection`: la conexión a la base de datos.
- `matricula`: la matrícula del vehículo a actualizar.
- `nuevaMarca`: la nueva marca del vehículo.
- `nuevosKms`: el nuevo kilometraje del vehículo.
- `nuevoPrecio`: el nuevo precio del vehículo.

El método devuelve 0 si la actualización fue exitosa, o -1 si ocurrió un error. Si se produce un error durante la
actualización, se captura una excepción `SQLException` y se muestra un mensaje de error.

### Método: `agruparVehiculosPorMarca(Connection connection)`

Este método agrupa vehículos por marca y devuelve una lista de listas de cadenas, donde cada lista interna representa un
grupo de vehículos de la misma marca. Utiliza la siguiente lógica:

- Ejecuta una consulta SQL para obtener todos los vehículos, ordenados por marca.
- Agrupa los resultados en función de la marca actual.
- Proporciona información detallada sobre cada vehículo, como matrícula, marca, kilometraje y precio.

Si ocurre un error, se captura una excepción `SQLException` y se muestra un mensaje de error.

### Método: `obtenerInventario(Connection connection)`

Este método obtiene el inventario de vehículos en la base de datos. Devuelve una lista de cadenas que representan cada
vehículo, con detalles como matrícula, marca, kilometraje y precio. Si ocurre un error durante la ejecución, se captura
una excepción `SQLException` y se muestra un mensaje de error.

### Método: `generarInformeInventario(Connection connection, int opcion)`

Este método genera un informe del inventario de vehículos y permite mostrarlo en pantalla o guardarlo en un archivo de
texto. Toma dos parámetros:

- `connection`: la conexión a la base de datos.
- `opcion`: 1 para mostrar el informe en pantalla, 2 para guardarlo en un archivo de texto.

Si la opción es 1, el informe se muestra en pantalla. Si es 2, se guarda en un archivo en el escritorio del usuario. Se
puede proporcionar un nombre de archivo para el informe. Si ocurre un error al escribir el archivo, se muestra un
mensaje de error.

### Resumen

La clase `FuncionalidadesExtrasDAO` proporciona métodos útiles para gestionar vehículos en una base de datos,
permitiendo flexibilidad en la búsqueda, actualización, agrupamiento y generación de informes. Estos métodos pueden ser
utilizados en diversas aplicaciones para facilitar la gestión y el análisis del inventario de vehículos.

## Uso de la Aplicación

Ejecuta la clase `Prog11_Principal` para interactuar con la base de datos del concesionario y probar las diferentes
funciones implementadas.
