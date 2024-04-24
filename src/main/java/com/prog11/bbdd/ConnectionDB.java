package com.prog11.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase proporciona métodos para abrir y cerrar conexiones
 * con la base de datos MariaDB. Se utiliza para establecer y
 * cerrar conexiones con la base de datos de un concesionario.
 */
public class ConnectionDB {
    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mariadb://localhost/concesionario";
    // Usuario para la conexión
    private static final String USER = "root";
    // Contraseña para la conexión
    private static final String PASSWORD = "root";

    /**
     * Abre una conexión a la base de datos utilizando la URL, el usuario y la contraseña definidos.
     *
     * @return Un objeto {@link Connection} si la conexión es exitosa, o null si hay un error.
     */
    public static Connection openConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Cierra una conexión abierta a la base de datos.
     *
     * @param connection La conexión a cerrar. Si es null, no se realiza ninguna acción.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
