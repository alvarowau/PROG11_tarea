package com.prog11.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase VehiculosDAO proporciona métodos para realizar operaciones CRUD
 * sobre la tabla de vehículos en la base de datos.
 */
public class VehiculosDAO {

    /**
     * Inserta un nuevo vehículo en la base de datos.
     *
     * @param connection La conexión a la base de datos.
     * @param matricula  La matrícula del vehículo.
     * @param marca      La marca del vehículo.
     * @param kms        El número de kilómetros del vehículo.
     * @param precio     El precio del vehículo.
     * @param idProp     El ID del propietario del vehículo.
     * @return 0 si la inserción fue exitosa, -1 en caso contrario.
     */
    public static int insertarVehiculo(Connection connection,
                                       String matricula,
                                       String marca,
                                       int kms,
                                       float precio,
                                       int idProp) {
        String sql = "INSERT INTO vehiculos (mat_veh, marca_veh, kms_veh, precio_veh, id_prop) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            stmt.setString(2, marca);
            stmt.setInt(3, kms);
            stmt.setFloat(4, precio);
            stmt.setInt(5, idProp);
            int result = stmt.executeUpdate();
            return result == 1 ? 0 : -1;
        } catch (SQLException e) {
            System.err.println("Error al insertar vehículo: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Elimina un vehículo de la base de datos por su matrícula.
     *
     * @param connection La conexión a la base de datos.
     * @param matricula  La matrícula del vehículo a eliminar.
     * @return 0 si la eliminación fue exitosa, -1 si el vehículo no existe, o -1 en caso de error.
     */
    public static int eliminarVehiculo(Connection connection, String matricula) {
        String sql = "DELETE FROM vehiculos WHERE mat_veh = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            int result = stmt.executeUpdate();
            return result == 1 ? 0 : -1;
        } catch (SQLException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Actualiza el propietario de un vehículo por su matrícula.
     *
     * @param connection La conexión a la base de datos.
     * @param matricula  La matrícula del vehículo.
     * @param idProp     El nuevo ID del propietario.
     * @return 0 si la actualización fue exitosa, -1 si el vehículo no existe, o -1 en caso de error.
     */
    public static int actualizarPropietarioVehiculo(Connection connection,
                                                    String matricula,
                                                    int idProp) {
        String sql = "UPDATE vehiculos SET id_prop = ? WHERE mat_veh = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProp);
            stmt.setString(2, matricula);
            int result = stmt.executeUpdate();
            return result == 1 ? 0 : -1;
        } catch (SQLException e) {
            System.err.println("Error al actualizar propietario del vehículo: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Obtiene todos los vehículos de la base de datos junto con el nombre del propietario.
     *
     * @param connection La conexión a la base de datos.
     * @return Una lista de cadenas con información sobre cada vehículo, o null en caso de error.
     */
    public static List<String> obtenerTodosLosVehiculos(Connection connection) {
        String sql = "SELECT v.mat_veh, v.marca_veh, v.kms_veh, v.precio_veh, p.nombre_prop " +
                "FROM vehiculos v " +
                "JOIN propietarios p ON v.id_prop = p.id_prop";
        List<String> vehiculos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String vehiculo = String.format(
                            "Matrícula: %s, Marca: %s, Kilómetros: %d, Precio: %.2f, Propietario: %s",
                            rs.getString("mat_veh"),
                            rs.getString("marca_veh"),
                            rs.getInt("kms_veh"),
                            rs.getFloat("precio_veh"),
                            rs.getString("nombre_prop"));
                    vehiculos.add(vehiculo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los vehículos: " + e.getMessage());
            return null;
        }
        return vehiculos;
    }

    /**
     * Obtiene los vehículos por marca.
     *
     * @param connection La conexión a la base de datos.
     * @param marca      La marca de los vehículos a buscar.
     * @return Una lista de cadenas con información sobre vehículos de esa marca,
     * o null si ocurre un error.
     */
    public static List<String> obtenerVehiculosPorMarca(Connection connection, String marca) {
        String sql = "SELECT v.mat_veh, v.marca_veh, kms_veh, precio_veh, p.nombre_prop " +
                "FROM vehiculos v " +
                "JOIN propietarios p ON v.id_prop = p.id_prop " +
                "WHERE v.marca_veh = ?";
        List<String> vehiculos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, marca);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String vehiculo = String.format(
                            "Matrícula: %s, Marca: %s, Kilómetros: %d, Precio: %.2f, Propietario: %s",
                            rs.getString("mat_veh"),
                            rs.getString("marca_veh"),
                            rs.getInt("kms_veh"),
                            rs.getFloat("precio_veh"),
                            rs.getString("nombre_prop"));
                    vehiculos.add(vehiculo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículos por marca: " + e.getMessage());
            return null;
        }
        return vehiculos;
    }

    /**
     * Obtiene una lista de vehículos con información básica como matrícula, marca, kms y precio.
     *
     * @param connection La conexión a la base de datos.
     * @return Una lista de cadenas con detalles de vehículos, o null si hay un error.
     */
    public static List<String> obtenerVehiculos(Connection connection) {
        String sql = "SELECT v.mat_veh, v.marca_veh, kms_veh, precio_veh FROM vehiculos v";
        List<String> vehiculos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String vehiculo = String.format(
                            "Matrícula: %s, Marca: %s, Kilómetros: %d, Precio: %.2f",
                            rs.getString("mat_veh"),
                            rs.getString("marca_veh"),
                            rs.getInt("kms_veh"),
                            rs.getFloat("precio_veh"));
                    vehiculos.add(vehiculo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículos: " + e.getMessage());
            return null;
        }
        return vehiculos;
    }
}
