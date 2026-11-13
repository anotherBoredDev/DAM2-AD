package es.iesluisbraille.accesodatos;

import java.util.Collection;
import java.util.List;

public class ClienteMySqlAlmacenamiento implements Almacenamiento<Cliente> {
    public ClienteMySqlAlmacenamiento() {
        if (!existeAlmacenamiento())  {
            prepararAlmacenamiento();
        }
    }

    @Override
    public boolean existeAlmacenamiento() {
        // TODO: Comprobar que existe la tabla que se va a utilizar
        return false;
    }

    private void prepararAlmacenamiento() {
        // TODO: Crear la tabla de Cliente
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
