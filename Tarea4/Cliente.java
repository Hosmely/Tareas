package Tareas.Tarea4;

public class Cliente {
    private String id;
    private String nombre;

      public void setId(String id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

      public String getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public Cliente(String id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public double CalcularDescuento(double subtotal){
        return 0;
}
}
