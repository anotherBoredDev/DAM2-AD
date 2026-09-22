import java.util.Comparator;
import java.util.Objects;

public class Cliente implements Comparable<Cliente>{
    private final int ID;
    private String nombre;
    private String telefono;
    private String matricula;

    public Cliente(int ID, String nombre, String telefono, String matricula) {
        this.ID = ID;
        this.nombre = nombre;
        this.telefono = telefono;
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cliente cliente)) return false;
        return ID == cliente.ID && Objects.equals(nombre, cliente.nombre) && Objects.equals(telefono, cliente.telefono) && Objects.equals(matricula, cliente.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, nombre, telefono, matricula);
    }

    @Override
    public int compareTo(Cliente o) {
        int comparacion = nombre.compareToIgnoreCase(o.getNombre());
        if (comparacion == 0) {
            comparacion = this.ID - o.getID();
        }
        return comparacion;
    }

    public int getID() {
        return ID;
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
