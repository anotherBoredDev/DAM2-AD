//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ClienteGestor clienteGestor = new ClienteGestor();
        PagoGestor pagoGestor = new PagoGestor();
        LectorConsola lectorConsola = new LectorConsola();

        MenuPrincipal menuPrincipal = new MenuPrincipal(clienteGestor, pagoGestor, lectorConsola);

        menuPrincipal.iniciar();
    }
}