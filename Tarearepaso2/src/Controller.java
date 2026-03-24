import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    @FXML
    private TextField fieldNombre;

    @FXML
    private TextField fieldCategoria;

    @FXML
    private TextField fieldPrecio;

    @FXML
    private TextField fieldCantidad;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btonGuardarArchivo;

    @FXML
    private Button btonCargar;

    @FXML
    private TableView<Producto> tabla;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colCantidad;

    @FXML
    private Label estadoBarra;

    @FXML
    private ProgressBar barraProgreso;

    private ObservableList<Producto> lista = FXCollections.observableArrayList();

    private File archivo = new File("inventario.txt");

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tabla.setItems(lista);

        btnEliminar.setDisable(true);
        tabla.getSelectionModel().selectedItemProperty().addListener(
            (obs, anterior, actual) -> btnEliminar.setDisable(actual == null)
        );

        btnAgregar.setOnAction(e -> Agregar());
        btnEliminar.setOnAction(e -> Eliminar());
        btonGuardarArchivo.setOnAction(e -> GuardarArchivo());
        btonCargar.setOnAction(e -> CargarArchivos());

        CargarArchivos();
    }

    public void Agregar() {
        String nombre    = fieldNombre.getText().trim();
        String categoria = fieldCategoria.getText().trim();
        String precioStr = fieldPrecio.getText().trim();
        String cantStr   = fieldCantidad.getText().trim();

        if (nombre.isEmpty() || categoria.isEmpty() ||
            precioStr.isEmpty() || cantStr.isEmpty()) {
            Alert vacio = new Alert(Alert.AlertType.WARNING);
            vacio.setTitle("Campos vacios");
            vacio.setHeaderText("Uno o mas campos estan vacios.");
            vacio.setContentText("Completa: nombre, categoria, precio y cantidad.");
            vacio.showAndWait();
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            Alert formato = new Alert(Alert.AlertType.ERROR);
            formato.setTitle("Precio invalido");
            formato.setHeaderText("El precio no es un numero valido.");
            formato.setContentText("Ingresa un valor como 1200.00 o 25.5");
            formato.showAndWait();
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantStr);
        } catch (NumberFormatException e) {
            Alert formato = new Alert(Alert.AlertType.ERROR);
            formato.setTitle("Cantidad invalida");
            formato.setHeaderText("La cantidad no es un numero entero valido.");
            formato.setContentText("Ingresa un valor entero como 5 o 30.");
            formato.showAndWait();
            return;
        }

        lista.add(new Producto(nombre, categoria, precio, cantidad));

        fieldNombre.clear();
        fieldCategoria.clear();
        fieldPrecio.clear();
        fieldCantidad.clear();

        Alert ok = new Alert(Alert.AlertType.INFORMATION);
        ok.setTitle("Producto agregado");
        ok.setHeaderText("Producto guardado correctamente.");
        ok.setContentText("\"" + nombre + "\" fue agregado al inventario.");
        ok.showAndWait();
    }

    public void Eliminar() {
        Producto seleccionado = tabla.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert warn = new Alert(Alert.AlertType.WARNING);
            warn.setTitle("Sin seleccion");
            warn.setHeaderText("Ninguna fila seleccionada.");
            warn.setContentText("Haz clic en un producto de la tabla primero.");
            warn.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminacion");
        confirm.setHeaderText("Eliminar \"" + seleccionado.getNombre() + "\"?");
        confirm.setContentText("Esta accion no se puede deshacer.");
        Optional<ButtonType> respuesta = confirm.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            lista.remove(seleccionado);
            actualizarProgreso(0, "Eliminado: " + seleccionado.getNombre());
        }
    }

    private void GuardarArchivo() {
        if (lista.isEmpty()) {
            Alert vacia = new Alert(Alert.AlertType.WARNING);
            vacia.setTitle("Lista vacia");
            vacia.setHeaderText("No hay productos para guardar.");
            vacia.setContentText("Agrega al menos un producto primero.");
            vacia.showAndWait();
            return;
        }

        List<Producto> copia = new ArrayList<>(lista);

        Thread hiloGuardado = new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    btonGuardarArchivo.setDisable(true);
                    actualizarProgreso(0, "Guardando...");
                });

                BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo));
                for (int i = 0; i < copia.size(); i++) {
                    escritor.write(copia.get(i).toString());
                    escritor.newLine();

                    Thread.sleep(300);

                    final double p = (double)(i + 1) / copia.size();
                    final String msg = "Guardando " + (i + 1) + " / " + copia.size();
                    Platform.runLater(() -> actualizarProgreso(p, msg));
                }
                escritor.close();

                Platform.runLater(() -> {
                    btonGuardarArchivo.setDisable(false);
                    actualizarProgreso(1.0, "Guardado correctamente en '" + archivo.getName() + "'.");

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setTitle("Guardado");
                    ok.setHeaderText("Inventario guardado.");
                    ok.setContentText("Se guardaron " + copia.size() +
                            " productos en '" + archivo.getName() + "'.");
                    ok.showAndWait();
                });

            } catch (IOException | InterruptedException e) {
                Platform.runLater(() -> {
                    btonGuardarArchivo.setDisable(false);
                    alertaGenerica("No se pudo guardar el archivo: " + e.getMessage());
                });
            }
        });

        hiloGuardado.setDaemon(true);
        hiloGuardado.start();
    }

    public void CargarArchivos() {
        if (!archivo.exists()) {
            actualizarProgreso(0, "No se encontro '" + archivo.getName() + "'.");
            return;
        }

        btonCargar.setDisable(true);
        btonGuardarArchivo.setDisable(true);
        actualizarProgreso(0, "Iniciando carga...");

        List<Producto> listaCargada = new ArrayList<>();

        Thread hiloCargado = new Thread(() -> {
            try {
                BufferedReader lector = new BufferedReader(new FileReader(archivo));
                List<String> lineas = lector.readAllLines();
                lector.close();

                int total = lineas.size();

                for (int i = 0; i < total; i++) {
                    String linea = lineas.get(i).trim();

                    if (!linea.isEmpty()) {
                        String[] partes = linea.split(",");
                        if (partes.length == 4) {
                            try {
                                listaCargada.add(new Producto(
                                    partes[0].trim(),
                                    partes[1].trim(),
                                    Double.parseDouble(partes[2].trim()),
                                    Integer.parseInt(partes[3].trim())
                                ));
                            } catch (NumberFormatException ignorado) {}
                        }
                    }

                    Thread.sleep(300);

                    final double p = (double)(i + 1) / total;
                    final int actual = i + 1;
                    Platform.runLater(() ->
                        actualizarProgreso(p, "Cargando linea " + actual + " de " + total + "..."));
                }

                final List<Producto> listafinal = listaCargada;
                Platform.runLater(() -> {
                    lista.setAll(listafinal);
                    actualizarProgreso(1.0, "Cargados " + listafinal.size() + " producto(s).");
                    btonCargar.setDisable(false);
                    btonGuardarArchivo.setDisable(false);
                });

            } catch (IOException | InterruptedException e) {
                Platform.runLater(() -> {
                    btonCargar.setDisable(false);
                    btonGuardarArchivo.setDisable(false);
                    alertaGenerica("No se pudo cargar el archivo: " + e.getMessage());
                });
            }
        });

        hiloCargado.setDaemon(true);
        hiloCargado.start();
    }

    private void alertaGenerica(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Ha ocurrido un problema.");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void actualizarProgreso(double valor, String mensaje) {
        barraProgreso.setProgress(valor);
        estadoBarra.setText(mensaje);
    }
}