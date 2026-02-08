package Tareas.Tarea4;

import java.util.Scanner;

public class Pedido {
    private String id;
    private Cliente tipocliente;
    private String estado;
    private DetallePedido detalles[] = new DetallePedido[100];
    private int contador = 0;
    static Pedido[] pedidos = new Pedido[100]; 
    static int contadorPedidos = 0; 
    Scanner scan = new Scanner(System.in);

    public Pedido(String id, Cliente tipocliente) {
        this.id = id;
        this.tipocliente = tipocliente;
        this.estado = "BORRADOR";
    }

    public void CambiarEstado() {
        System.out.println("1.BORRADOR\n2.CONFIRMAR\n3.CANCELAR");
        int opcion = scan.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("El pedido ya esta en borrador");
                break;
            case 2:
                if (contador == 0) {
                    System.out.println("No se puede confirmar el pedido, ya que no tiene productos.");
                } else {
                    estado = "CONFIRMADO";
                    System.out.println("El pedido ha sido confirmado");
                }
                break;
            case 3:
                estado = "CANCELADO";
                System.out.println("El pedido ha sido cancelado");
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
    }

    public void AgregarProductosAlPedido(String producto, int cantidad, double precioUnitario) {
        if (contador < detalles.length) {
            detalles[contador++] = new DetallePedido(producto, cantidad, precioUnitario);
            System.out.println("Producto agregado al pedido.");
        } else {
            System.out.println("Se ha alcanzado la cantidad maxima de productos permitidos en el pedido.");
        }
    }

    public double CalculaSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < contador; i++) {
            double totalProducto = detalles[i].getCantidad() * detalles[i].getPrecioUnitario();
            subtotal += totalProducto;
        }
        return subtotal;
    }

    public double CalcularDescuento() {
        double subtotal = CalculaSubtotal();
        return tipocliente.CalcularDescuento(subtotal);
    }

    public double CalcularTotalFinal() {
        return CalculaSubtotal() - CalcularDescuento();
    }

    public static void agregarPedido(Pedido pedido) {
        if (contadorPedidos < pedidos.length) {
            pedidos[contadorPedidos++] = pedido;
            System.out.println("Pedido agregado!");
        } else {
            System.out.println("No se pueden agregar mas pedidos.");
        }
    }

    public static void listarPedidos() {
        if (contadorPedidos == 0) {
            System.out.println("No hay pedidos registrados.");
        } else {
            for (int i = 0; i < contadorPedidos; i++) {
                System.out.println("Pedido ID: " + pedidos[i].id + " | Cliente: " + pedidos[i].tipocliente.getNombre() + " | Estado: " + pedidos[i].estado);
            }
        }
    }
}
