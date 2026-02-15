package Tareas.Tarea4;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Incidencia {
    private int id;
    private String descripcion;
    private Date fechaRegistro = new Date();
    private String nivelPrioridad;


    public String getDescripcion() {
        return descripcion;
    }
    public Incidencia(int id, String descripcion, Date fechaRegisto, String nivelPrioridad)throws IllegalArgumentException, DescripcionInvalidaException, PrioridadInvalidaException {
        if(id <1000)
            throw new IllegalArgumentException("El codigo Id debe empezar desde 1000");
        else
            this.id = id;

        if(descripcion.equals(null))
            throw new DescripcionInvalidaException("La descripcion no puede ser nula.");
        else if(descripcion.isBlank())
            throw new DescripcionInvalidaException("La descripcion no puede estar vacia.");
        else if( descripcion.trim().length() <10)
            throw new DescripcionInvalidaException("la descripcion no puede tener menos de 10 caracteres.");
        else
            this.descripcion =descripcion;

        if(!nivelPrioridad.equalsIgnoreCase("ALTA")&&!nivelPrioridad.equalsIgnoreCase("MEDIA" )&&!nivelPrioridad.equalsIgnoreCase("ALTA"))
            throw new PrioridadInvalidaException("El nivel de prioridad solo puede ser ALTA, MEDIA o BAJA.");
        else
            this.nivelPrioridad = nivelPrioridad.toUpperCase();

        this.fechaRegistro = fechaRegisto;

        System.out.println("Incidencia registrada.");
    }

    public Incidencia(){}


    @Override
    public String toString(){
        DateFormat formato = DateFormat.getDateInstance(DateFormat.SHORT);
        String fechaformateada = formato.format(fechaRegistro);
        return String.format("Id: %d\tDescripcion: %s\tFecha de registro: %s\tPrioridad: %s  ", id,descripcion,fechaformateada,nivelPrioridad);
    }


    public Date validarFecha(String fecha) throws ParseException,FechaInvalidaException{
        Date fechactual = new Date();
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaaux= formato.parse(fecha);
        if(fechaaux.after(fechactual))
            throw new FechaInvalidaException("La fecha no puede ser futura.");
        else
            return fechaaux;
    }

    // public static Incidencia Buscar(String clave){
    //     for (iterable_type iterable_element : iterable) {
            
    //     }
    // }

}
