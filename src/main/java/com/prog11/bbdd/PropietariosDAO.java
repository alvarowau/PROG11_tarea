package com.prog11.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase PropietariosDAO proporciona métodos para realizar operaciones CRUD
 * sobre la tabla propietarios en la base de datos.
 */
public class PropietariosDAO {

    /**
     * Inserta un nuevo propietario en la base de datos.
     *
     * @param connection La conexión a la base de datos.
     * @param nombre     El nombre del propietario.
     * @param dni        El DNI del propietario.
     * @return 0 si la operación fue exitosa, -1 en caso contrario.
     */
    public static int insertarPropietario(Connection connection, String nombre, String dni) {
        String sql = "INSERT INTO propietarios (nombre_prop, dni_prop) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, dni);
            int result = stmt.executeUpdate();
            return result == 1 ? 0 : -1;
        } catch (SQLException e) {
            System.err.println("Error al insertar propietario: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Elimina un propietario de la base de datos por su DNI.
     *
     * @param connection La conexión a la base de datos.
     * @param dni        El DNI del propietario a eliminar.
     * @return El número de registros eliminados o -1 en caso de error.
     */
    public static int eliminarPropietario(Connection connection, String dni) {
        String sql = "DELETE FROM propietarios WHERE dni_prop = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dni);
            int result = stmt.executeUpdate();
            return result; // Devuelve el número de registros eliminados
        } catch (SQLException e) {
            System.err.println("Error al eliminar propietario: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Obtiene una lista de vehículos asociados a un propietario, según su DNI.
     *
     * @param connection La conexión a la base de datos.
     * @param dni        El DNI del propietario.
     * @return Una lista de vehículos del propietario, o null si hubo un error.
     */
    public static List<String> obtenerVehiculosDePropietario(Connection connection, String dni) {
        String sql = "SELECT v.mat_veh, v.marca_veh, v.kms_veh, v.precio_veh " +
                "FROM vehiculos v " +
                "JOIN propietarios p ON v.id_prop = p.id_prop " +
                "WHERE p.dni_prop = ?";
        List<String> vehiculos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String vehiculo = String.format("Matrícula: %s, Marca: %s, Kilómetros: %d, Precio: %.2f",
                            rs.getString("mat_veh"),
                            rs.getString("marca_veh"),
                            rs.getInt("kms_veh"),
                            rs.getFloat("precio_veh"));
                    vehiculos.add(vehiculo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículos del propietario: " + e.getMessage());
            return null;
        }
        return vehiculos;
    }
}
