/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import packetworldescritorio.dominio.ClienteImp;
import packetworldescritorio.dominio.EnvioImp;
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Envio;
import packetworldescritorio.pojo.Paquete;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.PdfCreator;

public class FXMLPerfilEnvioController implements Initializable, INavegableChild {

    private INavegacion nav;
    private Envio envioActual;
    private Cliente clienteSeleccionado;
    private FXMLFormularioDireccionController formularioDireccionController;

    @FXML
    private Label lbTitulo;
    @FXML
    private AnchorPane apDatosCliente1;
    @FXML
    private AnchorPane apDatosCliente;
    @FXML
    private Label lbClienteNombre;
    @FXML
    private Label lbClienteTelefono;
    @FXML
    private Label lbClienteCorreo;
    @FXML
    private Label lbClienteDireccion;
    @FXML
    private Label lbClienteCodigoPostal;
    @FXML
    private Label lbClienteColonia;
    @FXML
    private Label lbClienteCiudad;
    @FXML
    private Label lbClienteEstado;
    @FXML
    private AnchorPane apDatosDestinatario;
    @FXML
    private Label lbDestinatarioNombre;
    @FXML
    private Label lbDestinatarioTelefono;
    @FXML
    private Label lbDestinatarioDireccion;
    @FXML
    private Label lbDestinatarioCodigoPostal;
    @FXML
    private Label lbDestinatarioColonia;
    @FXML
    private Label lbDestinatarioCiudad;
    @FXML
    private Label lbDestinatarioEstado;
    @FXML
    private AnchorPane apFormularioDestinatario;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApPaterno;
    @FXML
    private TextField tfApMaterno;
    @FXML
    private TextField tfTelefono;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorApPaterno;
    @FXML
    private Label lbErrorApMaterno;
    @FXML
    private Label lbErrorTelefono;
    @FXML
    private TableView<Paquete> tvPaquetes;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colPeso;
    @FXML
    private TableColumn colAlto;
    @FXML
    private TableColumn colAncho;
    @FXML
    private TableColumn colProfundidad;
    @FXML
    private Label lbCosto;
    @FXML
    private Label lbEstatusActual;
    @FXML
    private AnchorPane apDatosCliente2;
    @FXML
    private Label lbSucursalNombre;
    @FXML
    private Label lbSucursalDireccion;
    @FXML
    private Label lbSucursalCodigoPostal;
    @FXML
    private Label lbSucursalColonia;
    @FXML
    private Label lbSucursalCiudad;
    @FXML
    private Label lbSucursalEstado;

    private ObservableList<Paquete> paquetes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSeccionPaquetes();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

    @Override
    public void setObject(Object object) {
        if (object != null && object instanceof Envio) {
            Envio envio = (Envio) object;
            envioActual = envio;
            recargarDatos(envioActual);
        }
    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_ENVIOS);
    }

    private void recargarDatos(Envio envio) {
        //Orden: Envio

        Envio envioActualizado = obtenerEnvio(envio.getIdEnvio());
        if (envio == null) {
            return;
        }
        envioActual = envio;

        Cliente clienteActualizado = obtenerCliente(envio.getIdCliente());

        if (clienteActualizado != null) {
            clienteSeleccionado = clienteActualizado;
        }

        Sucursal sucursalOrigen = obtenerSucursal(envio.getIdSucursalOrigen());

        cargarDatosGeneralesEnvio(envioActualizado);
        cargarDatosCliente(clienteActualizado);
        cargarDatosDestinatario(envioActualizado);
        cargarDatosSucursal(sucursalOrigen);
        cargarDatosPaquetes(envioActualizado.getPaquetes());
    }

    /*
    private void mostrarDatosCliente() {
        apDatosCliente.setManaged(true);
        apDatosCliente.setVisible(true);
        hbMenuCliente.setManaged(false);
        hbMenuCliente.setVisible(false);
    }

    private void ocultarDatosCliente() {
        apDatosCliente.setVisible(false);
        apDatosCliente.setManaged(false);
        hbMenuCliente.setManaged(true);
        hbMenuCliente.setVisible(true);
    }
     */
    private void cargarDatosGeneralesEnvio(Envio envio) {
        if (envio != null) {
            lbTitulo.setText("Envío: " + envio.getNumeroGuia());
            lbCosto.setText("$" + envio.getCosto());
            lbEstatusActual.setText("" + (envio.getNombreEstatus() != null ? envio.getNombreEstatus() : "Desconocido"));
        }
    }

    private void cargarDatosCliente(Cliente cliente) {
        if (cliente != null) {
            lbClienteNombre.setText(cliente.getNombreCompleto());
            lbClienteTelefono.setText(cliente.getTelefono());
            lbClienteCorreo.setText(cliente.getCorreo());
            lbClienteDireccion.setText(cliente.getCalle() + " " + cliente.getNumero());
            lbClienteColonia.setText(cliente.getColonia());
            lbClienteCodigoPostal.setText(cliente.getCodigoPostal());
            lbClienteCiudad.setText(cliente.getCiudad());
            lbClienteEstado.setText(cliente.getEstado());
        }
    }

    private void cargarDatosDestinatario(Envio envio) {
        if (envio != null) {
            lbDestinatarioNombre.setText(envio.getNombreDestinatarioCompleto());
            lbDestinatarioTelefono.setText(envio.getDestinatarioTelefono());
            lbDestinatarioDireccion.setText(envio.getCalle() + " " + envio.getNumero());
            lbDestinatarioColonia.setText(envio.getColonia());
            lbDestinatarioCodigoPostal.setText(envio.getCodigoPostal());
            lbDestinatarioCiudad.setText(envio.getCiudad());
            lbDestinatarioEstado.setText(envio.getEstado());
        }
    }

    private void cargarDatosSucursal(Sucursal sucursal) {
        if (sucursal != null) {
            lbSucursalNombre.setText(sucursal.getNombre());
            lbSucursalDireccion.setText(sucursal.getCalle() + " " + sucursal.getNumero());
            lbSucursalColonia.setText(sucursal.getColonia());
            lbSucursalCodigoPostal.setText(sucursal.getCodigoPostal());
            lbSucursalCiudad.setText(sucursal.getCiudad());
            lbSucursalEstado.setText(sucursal.getEstado());
        }
    }

    private void cargarDatosPaquetes(List<Paquete> paquetesActuales) {
        paquetes.clear();

        if (paquetesActuales != null && !paquetesActuales.isEmpty()) {
            paquetes.addAll(paquetesActuales);
        }
    }

    private void borrarDatosCliente() {
        lbClienteNombre.setText("");
        lbClienteTelefono.setText("");
        lbClienteCorreo.setText("");
        lbClienteDireccion.setText("");
        lbClienteColonia.setText("");
        lbClienteCodigoPostal.setText("");
        lbClienteCiudad.setText("");
        lbClienteEstado.setText("");
    }

    private void borrarDatosDestinatario() {
        lbDestinatarioNombre.setText("");
        lbDestinatarioTelefono.setText("");
        lbDestinatarioDireccion.setText("");
        lbDestinatarioColonia.setText("");
        lbDestinatarioCodigoPostal.setText("");
        lbDestinatarioCiudad.setText("");
        lbDestinatarioEstado.setText("");
    }

    @FXML
    private void clickEditarCliente(ActionEvent event) {
    }

    @FXML
    private void clickCambiarCliente(ActionEvent event) {
    }

    @FXML
    private void clickGuardarDatosEnvio(ActionEvent event) {
    }

    @FXML
    private void clickCancelarDatosEnvio(ActionEvent event) {
    }

    @FXML
    private void clickIrRegistrarPaquete(ActionEvent event) {
    }

    @FXML
    private void clickIrEditarPaquete(ActionEvent event) {
    }

    @FXML
    private void clickEliminarPaquete(ActionEvent event) {
    }

    private Cliente obtenerCliente(int idCliente) {
        Cliente cliente = null;
        try {
            cliente = ClienteImp.obtenerCliente(idCliente);
        } catch (Exception ex) {
        }
        return cliente;
    }

    private Envio obtenerEnvio(int idEnvio) {
        Envio envio = null;
        try {
            envio = EnvioImp.obtenerEnvio(idEnvio);
        } catch (Exception ex) {
        }
        return envio;
    }

    private Sucursal obtenerSucursal(int idSucursal) {
        Sucursal sucursal = null;
        try {
            sucursal = SucursalImp.obtenerPorId(idSucursal);
        } catch (Exception ex) {
        }
        return sucursal;
    }

    @FXML
    private void clickImprimirComprobante(ActionEvent event) {
        Sucursal sucursal = obtenerSucursal(envioActual.getIdSucursalOrigen());
        PdfCreator.generarTicket(envioActual, clienteSeleccionado, sucursal);
    }

    private void configurarSeccionPaquetes() {
        inicializarTablaPaquetesVacia();
        configurarTabla();
    }

    private void configurarTabla() {
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colAlto.setCellValueFactory(new PropertyValueFactory("alto"));
        colAncho.setCellValueFactory(new PropertyValueFactory("ancho"));
        colProfundidad.setCellValueFactory(new PropertyValueFactory("profundidad"));
        colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
    }

    private void inicializarTablaPaquetesVacia() {
        paquetes = FXCollections.observableArrayList();
        tvPaquetes.setItems(paquetes);
    }
}
