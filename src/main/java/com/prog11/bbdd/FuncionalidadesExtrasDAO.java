package com.prog11.bbdd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que contiene funciones adicionales para gestionar vehículos en una base de datos.
 * Incluye métodos para agrupar vehículos, buscar por precio, actualizar detalles,
 * y generar informes de inventario.
 */
public class FuncionalidadesExtrasDAO {

    /**
     * Busca vehículos cuyo precio está dentro de un rango específico.
     *
     * @param connection La conexión a la base de datos.
     * @param precioMin  El precio mínimo para la búsqueda.
     * @param precioMax  El precio máximo para la búsqueda.
     * @return Una lista de cadenas con información sobre los vehículos que cumplen el criterio.
     */
    public static List<String> buscarVehiculosPorPrecio(Connection connection, float precioMin, float precioMax) {
        String sql = "SELECT mat_veh, marca_veh, kms_veh, precio_veh " +
                "FROM vehiculos " +
                "WHERE precio_veh BETWEEN ? AND ?";
        List<String> vehiculos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setFloat(1, precioMin);
            stmt.setFloat(2, precioMax);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String vehiculo = String.format("Matrícula: %s, Marca: %s, Kilómetros: %d, Precio: %.2f",
                            rs.getString("mat_veh"),
                            rs.getString("marca_veh"),
                            rs.getInt("kms_veh"),
                            rs.getFloat("precio_veh")
                    );
                    vehiculos.add(vehiculo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar vehículos por precio: " + e.getMessage());
            return null;
        }
        return vehiculos;
    }

    /**
     * Actualiza los detalles de un vehículo específico.
     *
     * @param connection  La conexión a la base de datos.
     * @param matricula   La matrícula del vehículo a actualizar.
     * @param nuevaMarca  La nueva marca del vehículo.
     * @param nuevosKms   El nuevo kilometraje del vehículo.
     * @param nuevoPrecio El nuevo precio del vehículo.
     * @return 0 si la actualización fue exitosa, -1 si hubo un error.
     */
    public static int actualizarVehiculo(Connection connection,
                                         String matricula,
                                         String nuevaMarca,
                                         int nuevosKms,
                                         float nuevoPrecio) {
        String sql = "UPDATE vehiculos SET marca_veh = ?, kms_veh = ?, precio_veh = ? WHERE mat_veh = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nuevaMarca);
            stmt.setInt(2, nuevosKms);
            stmt.setFloat(3, nuevoPrecio);
            stmt.setString(4, matricula);
            int result = stmt.executeUpdate();
            return result == 1 ? 0 : -1;
        } catch (SQLException e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Agrupa vehículos por marca.
     *
     * @param connection La conexión a la base de datos.
     * @return Una lista de listas de cadenas, donde cada lista interna representa un grupo de vehículos por marca.
     */
    public static List<List<String>> agruparVehiculosPorMarca(Connection connection) {
        String sql = "SELECT marca_veh, mat_veh, kms_veh, precio_veh FROM vehiculos ORDER BY marca_veh";
        List<List<String>> agrupadosPorMarca = new ArrayList<>();
        List<String> grupoActual = null;
        String marcaActual = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String marca = rs.getString("marca_veh");
                String vehiculo = String.format("Matrícula: %s, Marca: %s, Kilómetros: %d, Precio: %.2f",
                        rs.getString("mat_veh"),
                        marca,
                        rs.getInt("kms_veh"),
                        rs.getFloat("precio_veh")
                );

                if (!marca.equals(marcaActual)) {
                    grupoActual = new ArrayList<>();
                    agrupadosPorMarca.add(grupoActual);
                    marcaActual = marca;
                }

                grupoActual.add(vehiculo);
            }
        } catch (SQLException e) {
            System.err.println("Error al agrupar vehículos por marca: " + e.getMessage());
            return null;
        }
        return agrupadosPorMarca;
    }

    /**
     * Obtiene el inventario de vehículos en la base de datos.
     *
     * @param connection La conexión a la base de datos.
     * @return Una lista de cadenas que representan cada vehículo en el inventario.
     */
    public static List<String> obtenerInventario(Connection connection) {
        String sql = "SELECT mat_veh, marca_veh, kms_veh, precio_veh FROM vehiculos";
        List<String> inventario = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String vehiculo = String.format(
                        "Matrícula: %s, Marca: %s, Kilómetros: %d, Precio: %.2f",
                        rs.getString("mat_veh"),
                        rs.getString("marca_veh"),
                        rs.getInt("kms_veh"),
                        rs.getFloat("precio_veh")
                );
                inventario.add(vehiculo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener inventario de vehículos: " + e.getMessage());
            return null;
        }
        return inventario;
    }

    /**
     * Genera un informe del inventario de vehículos y permite mostrarlo en pantalla o guardarlo en un archivo de texto.
     *
     * @param connection La conexión a la base de datos.
     * @param opcion     1 para mostrar el informe en pantalla, 2 para guardarlo en un archivo.
     */
    public static void generarInformeInventario(Connection connection, int opcion) {
        List<String> inventario = obtenerInventario(connection);

        if (inventario == null) {
            System.out.println("No se pudo obtener el inventario.");
            return;
        }

        if (opcion == 1) {
            // Mostrar el informe en pantalla
            System.out.println("Informe de inventario de vehículos:");
            for (String vehiculo : inventario) {
                System.out.println(vehiculo);
            }
        } else if (opcion == 2) {
            // Guardar el informe en un archivo en el escritorio
            String userHome = System.getProperty("user.home");
            String escritorioPath;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                escritorioPath = userHome + "\\Desktop"; // Escritorio en Windows
            } else {
                escritorioPath = userHome + "/Desktop"; // Escritorio en Unix/Linux/macOS
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el nombre del archivo (sin extensión):");
            String nombreArchivoBase = scanner.nextLine();

            // Crear un nombre de archivo único para evitar conflictos
            File archivo = new File(escritorioPath + "/" + nombreArchivoBase + ".txt");
            int contador = 1;

            while (archivo.exists()) {
                archivo = new File(escritorioPath + "/" + nombreArchivoBase + "_" + contador + ".txt");
                contador++;
            }

            // Guardar el informe en el archivo
            try (FileWriter writer = new FileWriter(archivo)) {
                for (String vehiculo : inventario) {
                    writer.write(vehiculo + "\n");
                }

                writer.write("\nGracias por usar la aplicación de ÁlvaroWau.");
                System.out.println("Informe guardado en: " + archivo.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error al escribir el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Opción no válida.");
        }
    }
}
