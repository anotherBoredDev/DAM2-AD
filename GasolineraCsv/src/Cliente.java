import java.util.Objects;

public class Cliente implements Comparable<Cliente> {
    private final int id;
    private final String nombre;
    private final String telefono;
    private final String matricula;

    public Cliente(int id, String nombre, String telefono, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nombre, cliente.nombre) && Objects.equals(telefono, cliente.telefono) && Objects.equals(matricula, cliente.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, telefono, matricula);
    }

    @Override
    public int compareTo(Cliente o) {
        int comparacion = nombre.compareToIgnoreCase(o.getNombre());
        if (comparacion == 0) {
            comparacion = this.id - o.getId();
        }
        return comparacion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMatricula() {
        return matricula;
    }
}
