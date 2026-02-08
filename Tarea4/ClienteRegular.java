package Tareas.Tarea4;

public class ClienteRegular extends Cliente {
    private int contadorClientes = 0;
    Cliente clientesRegulares[] = new Cliente[100];

    public ClienteRegular(String id, String nombre) {
        super(id, nombre);
    }

    public void AgregarCliente(String id, String nombre) {
    if (contadorClientes < clientesRegulares.length) {
        boolean existe = false;
        for (int i = 0; i < contadorClientes; i++) {
            if (clientesRegulares[i].getNombre().equals(nombre) || clientesRegulares[i].getId().equals(id)) {
                existe = true;
                break;
            }
        }
        if (existe) {
            System.out.println("Este cliente ya existe. El cliente no pudo ser agregado.");
        } else {
            clientesRegulares[contadorClientes] = new Cliente(id, nombre);
            contadorClientes++;
            System.out.println("Cliente agregado!");
        }
    } else {
        System.out.println("Se ha alcanzado la cantidad maxima de clientes.");
    }
}

}
