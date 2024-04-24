package com.prog11.princ;

import com.prog11.bbdd.ConnectionDB;
import com.prog11.bbdd.FuncionalidadesExtrasDAO;
import com.prog11.bbdd.PropietariosDAO;
import com.prog11.bbdd.VehiculosDAO;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que demuestra el uso de DAOs para gestionar
 * propietarios y vehículos en una base de datos de concesionarios.
 */
public class Prog11_Principal {

    /**
     * Método principal que se ejecuta al iniciar el programa.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Establecer conexión con la base de datos
        Connection connection = ConnectionDB.openConnection();

        if (connection == null) {
            System.out.println("Error al conectar con la base de datos.");
            return;
        }

        // Insertar propietarios
        System.out.println("Insertando propietarios...");
        PropietariosDAO.insertarPropietario(connection, "Álvaro Bajo", "12345678A");
        PropietariosDAO.insertarPropietario(connection, "Ana García", "87654321B");
        PropietariosDAO.insertarPropietario(connection, "Pedro Martínez", "12312312C");
        PropietariosDAO.insertarPropietario(connection, "María López", "98765432D");
        PropietariosDAO.insertarPropietario(connection, "Luis Fernández", "45645645E");
        PropietariosDAO.insertarPropietario(connection, "Carla Sánchez", "65432178F");

        // Insertar vehículos
        System.out.println("Insertando vehículos...");
        VehiculosDAO.insertarVehiculo(connection, "HRC8383", "Hyundai", 50000, 15000, 1);
        VehiculosDAO.insertarVehiculo(connection, "DEF5678", "Honda", 30000, 12000, 1);
        VehiculosDAO.insertarVehiculo(connection, "GHI9012", "Ford", 70000, 18000, 2);
        VehiculosDAO.insertarVehiculo(connection, "JKL1234", "Toyota", 15000, 22000, 1);
        VehiculosDAO.insertarVehiculo(connection, "MNO5678", "Kia", 25000, 17000, 2);
        VehiculosDAO.insertarVehiculo(connection, "PQR9012", "Nissan", 45000, 14000, 3);
        VehiculosDAO.insertarVehiculo(connection, "STU3456", "Mazda", 55000, 21000, 3);
        VehiculosDAO.insertarVehiculo(connection, "VWX7890", "Chevrolet", 30000, 19000, 4);
        VehiculosDAO.insertarVehiculo(connection, "XYZ1234", "Honda", 10000, 13000, 5);
        VehiculosDAO.insertarVehiculo(connection, "LMN5678", "Honda", 20000, 16000, 6);
        VehiculosDAO.insertarVehiculo(connection, "OPQ9012", "Honda", 35000, 15000, 4);

        // Listar todos los vehículos
        System.out.println("Todos los vehículos:");
        List<String> todosVehiculos = VehiculosDAO.obtenerTodosLosVehiculos(connection);
        for (String vehiculo : todosVehiculos) {
            System.out.println(vehiculo);
        }

        // Actualizar propietario de un vehículo
        System.out.println("Actualizando propietario de ABC1234 a Ana García...");
        VehiculosDAO.actualizarPropietarioVehiculo(connection, "ABC1234", 2);

        // Listar todos los vehículos después de la actualización
        System.out.println("Todos los vehículos después de la actualización:");
        todosVehiculos = VehiculosDAO.obtenerTodosLosVehiculos(connection);
        for (String vehiculo : todosVehiculos) {
            System.out.println(vehiculo);
        }

        // Eliminar un vehículo
        System.out.println("Eliminando vehículo HRC8383...");
        int resultado = VehiculosDAO.eliminarVehiculo(connection, "HRC8383");
        System.out.println("Resultado eliminación: " + resultado);

        // Eliminar un vehículo que no existe
        System.out.println("Intentando eliminar vehículo inexistente XYZ7890:");
        resultado = VehiculosDAO.eliminarVehiculo(connection, "XYZ7890");
        System.out.println("Resultado eliminación: " + resultado);

        // Listar todos los vehículos después de eliminaciones
        System.out.println("Todos los vehículos después de eliminaciones:");
        todosVehiculos = VehiculosDAO.obtenerTodosLosVehiculos(connection);
        for (String vehiculo : todosVehiculos) {
            System.out.println(vehiculo);
        }

        // Listar vehículos por marca específica
        System.out.println("Vehículos de la marca Ford:");
        List<String> fordVehiculos = VehiculosDAO.obtenerVehiculosPorMarca(connection, "Ford");
        for (String vehiculo : fordVehiculos) {
            System.out.println(vehiculo);
        }

        // Listar vehículos de un propietario por DNI
        System.out.println("Vehículos de Ana García:");
        List<String> vehiculosDeAna = PropietariosDAO.obtenerVehiculosDePropietario(connection, "87654321B");
        for (String vehiculo : vehiculosDeAna) {
            System.out.println(vehiculo);
        }

        // Eliminar un propietario sin vehículos
        System.out.println("Eliminando propietario Ana García...");
        resultado = PropietariosDAO.eliminarPropietario(connection, "87654321B");
        System.out.println("Resultado eliminación: " + resultado);

        // Eliminar propietario con vehículos
        System.out.println("Intentando eliminar propietario Juan Pérez...");
        resultado = PropietariosDAO.eliminarPropietario(connection, "12345678A");
        System.out.println("Resultado eliminación: " + resultado);

        // Buscar vehículos por rango de precio
        System.out.println("Vehículos entre 10,000 y 20,000:");
        List<String> vehiculosPorPrecio = FuncionalidadesExtrasDAO.buscarVehiculosPorPrecio(connection, 10000, 20000);
        if (vehiculosPorPrecio != null) {
            for (String vehiculo : vehiculosPorPrecio) {
                System.out.println(vehiculo);
            }
        }

        // Actualizar datos de un vehículo
        System.out.println("Actualizando marca de OPQ9012 a Toyota...");
        resultado = FuncionalidadesExtrasDAO.actualizarVehiculo(connection, "OPQ9012", "Toyota", 35000, 14000);
        System.out.println("Resultado de actualización: " + (resultado == 0 ? "Éxito" : "Fallo"));

        // Agrupando vehículos por marca
        System.out.println("Agrupando vehículos por marca:");
        List<List<String>> agrupadosPorMarca = FuncionalidadesExtrasDAO.agruparVehiculosPorMarca(connection);
        if (agrupadosPorMarca != null) {
            for (List<String> grupo : agrupadosPorMarca) {
                if (!grupo.isEmpty()) {
                    String primeraEntrada = grupo.get(0);
                    // Asegurar la extracción de la marca
                    String[] partes = primeraEntrada.split(",");
                    String marca = partes[1].split(":")[1].trim();
                    System.out.println("Marca: " + marca);
                    for (String vehiculo : grupo) {
                        System.out.println("  " + vehiculo);
                    }
                } else {
                    System.out.println("Marca: Desconocida");
                }
            }
        }


        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Desea ver el informe en pantalla (1) o guardarlo en un archivo de texto (2)?");
        int eleccion = scanner.nextInt();

        FuncionalidadesExtrasDAO.generarInformeInventario(connection, eleccion);

        // Cerrar la conexión a la base de datos
        ConnectionDB.closeConnection(connection);
    }
}

