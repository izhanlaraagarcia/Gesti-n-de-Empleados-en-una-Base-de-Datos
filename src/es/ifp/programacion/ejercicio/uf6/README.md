# Para hacer un ejercicio en Java modular con BBDD, debes seguir algunos pasos generales:

> Este código es un programa simple que interactúa con una base de datos de MySQL. La clase principal `ProgramaPrincipal` contiene la definición de dos clases internas: `Empleado` y `GestionBBDD`. `Empleado` es una clase simple que tiene cuatro atributos: `id`, `nombre`, `apellido`, y `salario`. La clase `GestionBBDD` es la clase que maneja todas las operaciones de la base de datos. En ella, se definen los métodos para insertar, borrar, obtener y mostrar los registros de la tabla "empleados" en la base de datos. 

La función principal `main` instancia un objeto de la clase `GestionBBDD` y se conecta a la base de datos. Luego, se crean varios objetos de tipo `Empleado` y se insertan en la base de datos utilizando el método `insertarEmpleado` de la clase `GestionBBDD`. Finalmente, se muestra una lista de todos los registros de la tabla "empleados" en la base de datos utilizando el método `mostrarEmpleados` de la clase `GestionBBDD`.


Ejercicio en PDF: [EjercicioEvaluablePractico_DAW_PRO_UF6_2022_2023(2).pdf](EjercicioEvaluablePractico_DAW_PRO_UF6_2022_2023(2).pdf)

1. Crea una clase para representar cada tabla de la base de datos y sus atributos. Por ejemplo, si tienes una tabla de empleados, deberías crear una clase Empleado con atributos como id, nombre, apellido y salario.
2. Crea una clase para gestionar la conexión a la base de datos y realizar operaciones en ella. En esta clase, es recomendable que tengas métodos para conectarte y desconectarte de la base de datos, así como métodos para realizar operaciones de inserción, actualización y eliminación de registros en la base de datos.
3. En tu clase principal, crea instancias de las clases que representan las tablas de la base de datos y utiliza la clase de gestión de la base de datos para realizar operaciones en la base de datos. Asegúrate de modularizar tu código y dividirlo en paquetes según su funcionalidad.
4. Utiliza excepciones para manejar errores al conectar o interactuar con la base de datos.

A continuación te presento un ejemplo más detallado para que puedas comprender mejor cómo hacerlo:

Supongamos que tienes una tabla de empleados con cuatro atributos: id, nombre, apellido y salario. Para representar esta tabla en tu código, crearás una clase Empleado con estos atributos y sus respectivos getters y setters. Por ejemplo:

``` java
public class Empleado {
    private int id;
    private String nombre;
    private String apellido;
    private double salario;

    public Empleado(int id, String nombre, String apellido, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.salario = salario;
    }

    // Getters y setters
}
```
Luego, creas una clase de gestión de la base de datos para interactuar con la tabla de empleados. En esta clase, tendrás métodos para conectarte y desconectarte de la base de datos, así como métodos para realizar operaciones de inserción, actualización y eliminación de registros en la tabla de empleados. Por ejemplo:

```` java
public class GestionBBDD {
    private Connection conexion;

    public void conectar() throws SQLException {
        // Código para conectarse a la base de datos
    }

    public void desconectar() throws SQLException {
        // Código para desconectarse de la base de datos
    }

    public void insertarEmpleado(Empleado empleado) throws SQLException {
        // Código para insertar un empleado en la tabla de empleados
    }

    public void actualizarEmpleado(Empleado empleado) throws SQLException {
        // Código para actualizar un empleado en la tabla de empleados
    }

    public void eliminarEmpleado(Empleado empleado) throws SQLException {
        // Código para eliminar un empleado de la tabla de empleados
    }
}
````

Por último, en tu clase principal, crearás instancias de las clases Empleado y GestionBBDD y utilizarás la clase GestionBBDD para realizar operaciones en la base de datos. Por ejemplo:

```` java
public class Main {
    public static void main(String[] args) {
        // Crear instancia de Empleado
        Empleado empleado = new Empleado(1, "Juan", "Pérez", 1500.0);

        // Crear instancia de GestionBBDD y conectar a la base de datos
        GestionBBDD gestorBBDD = new GestionBBDD();
        try {
            gestorBBDD.conectar();

            // Insertar el empleado en la BBDD
            gestorBBDD.insertarEmpleado(empleado);
                    // Recuperar todos los empleados de la base de datos y mostrarlos por consola
        List<Empleado> empleados = gestorBBDD.recuperarEmpleados();
        for (Empleado e : empleados) {
            System.out.println(e.toString());
        }

        // Eliminar el empleado insertado anteriormente de la base de datos
        gestorBBDD.eliminarEmpleado(empleado);

        // Recuperar todos los empleados de la base de datos y mostrarlos por consola
        empleados = gestorBBDD.recuperarEmpleados();
        for (Empleado e : empleados) {
            System.out.println(e.toString());
        }

    } catch (SQLException e) {
        System.out.println("Error al conectar o realizar operaciones en la base de datos: " + e.getMessage());
    } finally {
        // Cerrar la conexión con la base de datos
        gestorBBDD.cerrarConexion();
    }
}
````


# SQL

Los archivos SQL contienen los comandos SQL necesarios para crear y gestionar la base de datos. Por ejemplo, podrían tener los siguientes comandos:

1. Crear la base de datos:

```` sql
CREATE DATABASE empresadb;
````

2. Crear la tabla empleados:
````sql
CREATE TABLE empleados (
    id integer PRIMARY KEY,
    Nombre VARCHAR(50) NULL,
    Apellidos VARCHAR(50) NULL,
    salario integer NULL,
    PRIMARY KEY (id)
);
````


# Librearias

> Para que detecte el driver de MySQL necesitamos añadir el archivo jar dentro de una carpeta llamada LIB/Librerias en nuestro proyecto, tal y como tenemos en el nuestro. 

Instalar para que te detecte el controlador de la BBDD: https://dev.mysql.com/downloads/connector/j/
Error en caso de no instalar Driver: `Error en la conexión a la base de datos: No suitable driver found for jdbc:mysql://localhost:3306/empleadosDB`.

En VSCode, puedes seguir estos pasos para agregar una librería externa:

1. Crea una carpeta llamada "lib" en el directorio raíz de tu proyecto.
2. Descarga el archivo JAR del controlador JDBC que deseas usar y guárdalo en la carpeta "lib".
3. En VSCode, haz clic derecho en la carpeta "lib" y selecciona "Add Folder to Java Source Path".
4. Abre el archivo "settings.json" en tu proyecto y agrega la siguiente configuración:

```json
{
    "java.project.referencedLibraries": [
        "lib/*.jar"
    ]
}
```

5. Guarda el archivo "settings.json" y reinicia VSCode.
6. Ahora puedes importar el controlador JDBC y usarlo en tu código.

``` java
import java.sql.*;

public class GestionBBDD {
    static final String URL = "jdbc:mysql://localhost:3306/empresaDB";
    static final String USER = "root";
    static final String PASS = "password";

    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error en la conexión a la base de datos: " + e.getMessage());
        }
    }
}
``` 

Asegúrate de reemplazar "password" con tu contraseña de MySQL y "empresaDB" con el nombre de tu base de datos.

---
## Firma

[Izhan Lara Garcia](https://github.com/izhanlaraagarcia)