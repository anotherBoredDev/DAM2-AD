package es.iesluisbraille.accesodatos;

import java.util.Collection;

public interface Almacenamiento<T> {
    boolean existeAlmacenamiento();

    void guardar(T entidad);

    void guardarTodos(Collection<T> entidades);

    Collection<T> obtenerTodos();
}
