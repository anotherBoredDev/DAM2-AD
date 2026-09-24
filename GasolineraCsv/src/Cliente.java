public class Cliente implements Comparable<Cliente>{
    private final int id;
    private String nombre;
    private String telefono;
    private String matricula;

    public Cliente(int id, String nombre, String telefono, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.matricula = matricula;
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
