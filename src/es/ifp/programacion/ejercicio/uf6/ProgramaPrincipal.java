
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.List;

// Librerias

public class ProgramaPrincipal {
    public static class Empleado {
        private int id;
        private String nombre;
        private String apellido;
        private double salario;

        public Empleado(int id, String Nombre, String Apellidos, double salario, GestionBBDD gestorBBDD) {
            this.id = id;
            this.nombre = Nombre;
            this.apellido = Apellidos;
            this.salario = salario;
        }

        public Empleado(String Nombre, String Apellidos, double salario) {
            this.nombre = Nombre;
            this.apellido = Apellidos;
            this.salario = salario;
        }
        // Getters y setters de los atributos

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.apellido = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public double getSalario() {
            return salario;
        }

        public void setSalario(double salario) {
            this.salario = salario;
        }

    }
    // Fin de la clase Empleado

    // clase de gestion de BBDD
    public static class GestionBBDD {
        public GestionBBDD() {

        }

        private Connection conexion;

        public void conectarBBDD(String URL, String Usuario, String Contraseña) throws SQLException {
            conexion = DriverManager.getConnection(URL, Usuario, Contraseña);
        }

        public void CerrarBBDD() throws SQLException {
            if (conexion != null) {
                conexion.close();
            }
        }

        public void insertarEmpleado(Empleado empleado) throws SQLException {
            PreparedStatement consulta = conexion
                    .prepareStatement("INSERT INTO empleados (nombre, apellido, salario) VALUES (?, ?, ?)");
            consulta.setString(1, empleado.getNombre());
            consulta.setString(2, empleado.getApellido());
            consulta.setDouble(3, empleado.getSalario());
            consulta.executeUpdate();
        }

        public void borrarEmpleado(Empleado empleado) throws SQLException {
            PreparedStatement consulta = conexion.prepareStatement("DELETE FROM empleados WHERE id = ?");
            consulta.setInt(1, empleado.getId());
            consulta.executeUpdate();
        }

        public Empleado[] obtenerEmpleados() throws SQLException {
            Statement consulta = conexion.createStatement();
            ResultSet resultado = consulta.executeQuery("SELECT * FROM empleados");
            Empleado[] empleados = new Empleado[0];
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellidos");
                double salario = resultado.getDouble("salario");
                Empleado empleado = new Empleado(id, nombre, apellido, salario, null);
                empleados = agregarEmpleado(empleados, empleado);
            }
            return empleados;
        }

        private Empleado[] agregarEmpleado(Empleado[] empleados, Empleado empleado) {
            Empleado[] nuevosEmpleados = new Empleado[empleados.length + 1];
            for (int i = 0; i < empleados.length; i++) {
                nuevosEmpleados[i] = empleados[i];
            }
            nuevosEmpleados[empleados.length] = empleado;
            return nuevosEmpleados;
        }

        public void mostrarEmpleados() throws SQLException {
            String sql = "SELECT * FROM empleados";
            Statement statement = conexion.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String nombre = result.getString("nombre");
                String apellido = result.getString("apellidos");
                double salario = result.getDouble("salario");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Salario: " + salario);
            }
        }
    }

    /**
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        // Conectar a la base de datos
        GestionBBDD gestorBBDD = new GestionBBDD();
        String URL = "jdbc:mysql://localhost:3306/empresaDB?useSSL=false";
        String Usuario = "root";
        String Contraseña = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            gestorBBDD.conectarBBDD(URL, Usuario, Contraseña);
            System.out.println("Conexión a la base de datos establecida.");

            // Crear varios objetos de tipo empleado
            Empleado empleado1 = new Empleado("Juan", "Pérez", 2000.0);
            Empleado empleado2 = new Empleado("María", "García", 2500.0);
            Empleado empleado3 = new Empleado("Pedro", "López", 1800.0);

            // Insertar los empleados en la base de datos
            gestorBBDD.insertarEmpleado(empleado1);
            gestorBBDD.insertarEmpleado(empleado2);
            gestorBBDD.insertarEmpleado(empleado3);

            // Mostrar todos los registros de la tabla
            gestorBBDD.mostrarEmpleados();

            // Recuperar todos los empleados de la base de datos
            Empleado[] empleados = gestorBBDD.obtenerEmpleados();
            for (Empleado empleado : empleados) {
                System.out.println(empleado.getNombre() + " " + empleado.getApellido() + " - Salario: "
                        + empleado.getSalario());
            }

            // Borrar un empleado de la base de datos
            gestorBBDD.borrarEmpleado(empleado3);

            // Recuperar de nuevo todos los empleados de la base de datos
            empleados = gestorBBDD.obtenerEmpleados();
            for (Empleado empleado : empleados) {
                System.out.println(empleado.getNombre() + " " + empleado.getApellido() + " - Salario: "
                        + empleado.getSalario());
            }

            // Cerrar la conexión a la base de datos
            gestorBBDD.CerrarBBDD();
            System.out.println("Conexión a la base de datos cerrada.");
        } catch (SQLException e) {
            System.out.println("Error en la conexión a la base de datos: " + e.getMessage());
        }
    }
}