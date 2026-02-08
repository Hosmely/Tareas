package Tareas.Tarea4;

public class ClienteVIP extends Cliente {
    private int contadorClientes = 0;
    Cliente clientesVIP[] = new Cliente[100];

    public ClienteVIP(String id, String nombre) {
        super(id, nombre);
    }

    public void AgregarCliente(String id, String nombre) {
        if (contadorClientes < clientesVIP.length) {
            boolean existe = false;
            for (int i = 0; i < contadorClientes; i++) {
                if (clientesVIP[i].getNombre().equals(nombre) || clientesVIP[i].getId().equals(id)) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                System.out.println("Este cliente VIP ya existe. El cliente no pudo ser agregado.");
            } else {
                clientesVIP[contadorClientes] = new Cliente(id, nombre);
                contadorClientes++;
                System.out.println("Cliente VIP agregado!");
            }
        } else {
            System.out.println("Se ha alcanzado la cantidad maxima de clientes VIP.");
        }
    }

    @Override
    public double CalcularDescuento(double subtotal){
        return subtotal *0.10;
    }


}
