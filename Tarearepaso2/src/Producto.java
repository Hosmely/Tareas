import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Producto {

    private SimpleStringProperty nombre;
    private SimpleStringProperty categoria;
    private SimpleDoubleProperty precio;
    private SimpleIntegerProperty cantidad;

    public Producto(String nombre, String categoria, double precio, int cantidad){
        this.nombre = new SimpleStringProperty(nombre);
        this.categoria = new SimpleStringProperty(categoria);
        this.precio = new SimpleDoubleProperty(precio);
        this.cantidad = new SimpleIntegerProperty(cantidad);
    }
    public StringProperty nomProperty(){
        return nombre;
    }

    public String getNombre(){
        return nombre.get();
    }

    public void setNombre(String n){
        nombre.set(n);
    }


    public StringProperty catProperty(){
        return categoria;
    }

    public String getCategoria(){
        return categoria.get();
    }

    public void setCategoria(String c){
        categoria.get();
    }

    public StringProperty preCategoria(){
        return categoria;
    }

    public DoubleProperty precioProperty(){
        return precio;
    }

    public double getPrecio(){
        return precio.get();
    }


    public void setPrecio(double p){
        precio.set(p);
    }

    public IntegerProperty cantidaProperty(){
        return cantidad;
    }

    public int getCantidad(){
        return cantidad.get();
    }

    public void setCantidad(int c){
        cantidad.set(c);
    }

    @Override
    public String toString(){
        return getNombre() + "," + getCategoria() + ","+ getPrecio()+","+getCantidad();
    }
}
