/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.dominio.ClienteImp;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModalSeleccionarClienteController implements Initializable {

    @FXML
    private TableView<Cliente> tvClientes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApPaterno;
    @FXML
    private TableColumn colApMaterno;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnBusqueda;

    private Cliente clienteSeleccionado;
    private ObservableList<Cliente> clientes;
    private FilteredList<Cliente> filteredData;
    private SortedList<Cliente> sortedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        clienteSeleccionado = tvClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            cerrar();
        }
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrar();
    }

    @FXML
    private void clickBuscar(ActionEvent event) {
        if (tfBusqueda.getText() != null && !tfBusqueda.getText().isEmpty()) {
            cargarInformaciónClientes(tfBusqueda.getText());
        }
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));

        tvClientes.setRowFactory(tv -> {
            TableRow<Cliente> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    clienteSeleccionado = row.getItem();
                    cerrar();
                }
            });
            return row;
        });
    }

    private void cargarInformaciónClientes(String filtro) {
        HashMap<String, Object> respuesta = ClienteImp.buscar(filtro);
        boolean esError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!esError) {
            List<Cliente> clientesAPI = (List<Cliente>) respuesta.get(Constantes.KEY_LISTA);
            clientes = FXCollections.observableArrayList();
            clientes.addAll(clientesAPI);

            filteredData = new FilteredList<>(clientes, p -> true);
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tvClientes.comparatorProperty());
            tvClientes.setItems(sortedData);

            /* Explicación
            ObservableList - Es la lista original con todos los elementos
            FilteredList - Es una vista dinamica que filtra la lista original segun un predicate (Condicion de filtrado)
            SortedList - Es una vista que odena la lista filtrada, mantiene un comportamiento consistente.
            SL.comparatorProperty().bind(TV.comparatorProperty()) - Conecta el comparador que usa el TableView (cuando el usuario hace clic en los encabezados de columna) con la lista ordenada que conteiene los elementos mostrados
             */
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
        }
    }

    public void cerrar() {
        Stage stage = (Stage) tvClientes.getScene().getWindow();
        stage.close();
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

}
