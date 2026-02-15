package Tareas.Tarea4;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class principal {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Incidencia enter;
        int opcion;
        List<Incidencia> incidencias = new ArrayList<>();
        do{
            System.out.println("\n-----Menu-----");
            System.out.println("1. Registrar incidencia");
            System.out.println("2. Listar incidencia");
            System.out.println("3. Buscar incidencias por palabra clave");
            System.out.println("0. Salir");
            opcion = scan.nextInt();
            scan.nextLine();
            switch(opcion){
                case 1:
                    System.out.println("Ingrese Id: ");
                    int id = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Ingrese descripcion: ");
                    String descripcion = scan.nextLine();
                    System.out.println("Ingrese fecha de registro: ");
                    String fecha = scan.nextLine();
                    System.out.println("Ingrese nivel de prioridad: ");
                    String prioridad = scan.nextLine();

                    Incidencia nuevo = new Incidencia();
                   
                    try{
                         Date fechacorrecta = nuevo.validarFecha(fecha);
                         enter = new Incidencia(id, descripcion, fechacorrecta, prioridad);
                         incidencias.add(enter);
                    }catch(ParseException pe){
                        System.out.println(pe.getMessage());
                    }catch(FechaInvalidaException fie){
                        System.out.println(fie.getMessage());
                    }catch(IllegalArgumentException iae){
                        System.out.println(iae.getMessage());
                    }catch(DescripcionInvalidaException die){
                        System.out.println(die.getMessage());
                    }catch(PrioridadInvalidaException pie){
                        System.out.println(pie.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("-----Listado de incidencias-----");
                    for (Incidencia incidencia : incidencias) {
                        System.out.println(incidencia);
                    }
                    break;
                case 3:
                    System.out.println("Ingrese una palabra clave de la descripcion de la incidencia: ");
                    String clave = scan.nextLine();
                    boolean encontrado = false;
                    for(Incidencia incidencia : incidencias){
                        if(incidencia.getDescripcion().contains(clave)){
                            System.out.println("Incidencia encontrada:");
                            System.out.println(incidencia.toString());
                            encontrado = true;
                            break;
                        }
                    }
                    if(encontrado == false)
                        System.out.print("La Incidencia no existe.");
                    break;
                case 0:
                    
                    break;
                default:

                    break;
            }
        }while(opcion!=0);
        scan.close();
    }
}
