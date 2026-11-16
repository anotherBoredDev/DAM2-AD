package es.iesluisbraille.accesodatos.almacenamiento.sql;

import es.iesluisbraille.accesodatos.almacenamiento.Almacenamiento;
import es.iesluisbraille.accesodatos.entidad.Pago;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class PagoMySqlAlmacenamiento implements Almacenamiento<Pago> {
    private final ConexionDB conexionDB;

    public PagoMySqlAlmacenamiento(ConexionDB conexionDB) {
        this.conexionDB = conexionDB;
        prepararAlmacenamiento();
    }

    private void prepararAlmacenamiento() {
        if (existeAlmacenamiento()) {
            return;
        }

        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS `pago` (
                    id INT UNSIGNED NOT NULL PRIMARY KEY,
                    cliente_id INT UNSIGNED NOT NULL,
                    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
                    importe FLOAT,
                    litros FLOAT,
                    combustible ENUM('DIESEL', 'GASOLINA95'),
                    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
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
            try (ResultSet rs = metaData.getTables(conexion.getCatalog(), null, "pago", new String[]{"TABLE"})) {
                return rs.next();
            }
        } catch (SQLException e) {
            // TODO: Propagar error para impedir que se continue ejecutando el programa si falla el acceso a la DB
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void guardar(Pago entidad) {
        // TODO: Guardar cliente en tabla Pago
    }

    @Override
    public void guardarTodos(Collection<Pago> entidades) {
        // TODO: Guardar lista de clientes en tabla Pago
    }

    @Override
    public Collection<Pago> obtenerTodos() {
        // TODO: Obtener todos los clientes de tabla Pago
        return List.of();
    }


}
