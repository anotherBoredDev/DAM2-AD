import java.util.List;

public interface ClienteAlmacenamiento extends Almacenamiento<Cliente, Integer> {
    Cliente buscarPorMatricula(String matricula);
    int conseguirSiguienteId();
}
