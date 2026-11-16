package es.iesluisbraille.accesodatos.almacenamiento.sql;

import es.iesluisbraille.accesodatos.almacenamiento.Almacenamiento;
import es.iesluisbraille.accesodatos.entidad.Cliente;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class ClienteMySqlAlmacenamiento implements Almacenamiento<Cliente> {
    private final ConexionDB conexionDB;

    public ClienteMySqlAlmacenamiento(ConexionDB conexionDB) {
        this.conexionDB = conexionDB;
        prepararAlmacenamiento();
    }

    private void prepararAlmacenamiento() {
        if (existeAlmacenamiento()) {
            return;
        }

        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS `cliente` (
                    id INT UNSIGNED NOT NULL PRIMARY KEY,
                    nombre VARCHAR(50),
                    telefono VARCHAR(9),
                    matricula VARCHAR(7)
                );
                """;

        try (Connection conexion = conexionDB.getConnection()){
            Statement statement = conexion.createStatement();
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            // TODO: Propagar error para impedir que se continue ejecutando el programa si falla la creación
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean existeAlmacenamiento() {
        try (Connection conexion = conexionDB.getConnection()){
            DatabaseMetaData metaData = conexion.getMetaData();

            // Comprueba si existe una tabla con el nombre "cliente"
            try (ResultSet rs = metaData.getTables(conexion.getCatalog(), null, "cliente", new String[]{"TABLE"})) {
                return rs.next();
            }
        } catch (SQLException e) {
            // TODO: Propagar error para impedir que se continue ejecutando el programa si falla el acceso a la DB
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void guardar(Cliente entidad) {
        // TODO: Guardar cliente en tabla Cliente
    }

    @Override
    public void guardarTodos(Collection<Cliente> entidades) {
        // TODO: Guardar lista de clientes en tabla Cliente
    }

    @Override
    public Collection<Cliente> obtenerTodos() {
        // TODO: Obtener todos los clientes de tabla Cliente
        return List.of();
    }


}
