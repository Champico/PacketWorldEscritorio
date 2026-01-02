/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldescritorio.dominio.ClienteImp;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.dominio.UnidadImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModuloClientesController implements Initializable, INavegableChild {

    private INavegacion nav;
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
    private TableColumn colCalle;
    @FXML
    private TableColumn colNumero;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnBusqueda;

    private ObservableList<Cliente> clientes;
    private FilteredList<Cliente> filteredData;
    private SortedList<Cliente> sortedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónClientes();
        configurarTextFieldBusqueda();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        nav.navegar(Constantes.PG_PRINCIPAL);
    }

    @FXML
    private void clickIrRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clickIrEditar(ActionEvent event) {
        Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            irFormulario(cliente);
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona un cliente", "Para editar la información de un cliente, debes seleccionarlo primero de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Eliminar cliente", "¿Estas seguro de que quieres eliminar el registro del cliente " + cliente.getNombreCompleto() + " ?" + "\n Al eliminar un registro no podras recuperar la información posteriormente");
            if (confirmarOperacion) {
                eliminarCliente(cliente.getIdCliente());
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona cliente", "Para eliminar un cliente, debe seleccionarlo de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(Cliente cliente) {
        nav.navegar(Constantes.PG_FORMULARIO_CLIENTES, cliente);
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        colNumero.setCellValueFactory(new PropertyValueFactory("numero"));
    }

    private void cargarInformaciónClientes() {
        HashMap<String, Object> respuesta = ClienteImp.obtenerTodos();
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

    private void configurarTextFieldBusqueda() {
        tfBusqueda.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                aplicarFiltros();
            }
        });
    }

    private void aplicarFiltros() {
        String textoDeBusqueda = tfBusqueda.getText();

        /*
        Un predicado es una función que recibe un objeto de tipo T y devuelve true o false
        En este caso es un cliente (cli)}
         */
        filteredData.setPredicate(cli -> {
            cli = (Cliente) cli;

            // Filtro por texto
            boolean coincideTexto = true;

            if (textoDeBusqueda != null && !textoDeBusqueda.isEmpty()) {
                String filtro = textoDeBusqueda.toLowerCase();

                String nombre = Utilidades.normalizar(cli.getNombre());
                String telefono = Utilidades.normalizar(cli.getTelefono());
                String correo = Utilidades.normalizar(cli.getCorreo());

                coincideTexto
                        = nombre.contains(filtro)
                        || telefono.contains(filtro)
                        || correo.contains(filtro);
            }

            return coincideTexto;

        });

    }

    private void eliminarCliente(int idCliente) {
        Respuesta respuesta = ClienteImp.eliminar(idCliente);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Registro eliminado", "El registro del cliente fue eliminado correctamente", Alert.AlertType.INFORMATION);
            cargarInformaciónClientes();
        } else {
            Utilidades.mostrarAlertaSimple("Error al eliminar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
}
