/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.dominio.ClienteImp;
import packetworldescritorio.dominio.EnvioImp;
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.dto.Respuesta;
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
import packetworldescritorio.utilidad.Utilidades;

public class FXMLPerfilEnvioController implements Initializable, INavegableChild {

    private INavegacion nav;
    private Envio envioActual;
    private Cliente clienteActual;
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

    @Override
    public void setObject(Object object) {
        if (object != null && object instanceof Envio) {
            Envio envio = (Envio) object;
            envioActual = envio;
            recargarDatos(envioActual);
        }
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

    @FXML
    private void clickEditarCliente(ActionEvent event) {
        irFormularioClienteModal(clienteActual);
    }

    @FXML
    private void clickCambiarCliente(ActionEvent event) {
        asignarCliente();
    }

    @FXML
    private void clickGuardarDatosEnvio(ActionEvent event) {
    }

    @FXML
    private void clickCancelarDatosEnvio(ActionEvent event) {
    }

    @FXML
    private void clickIrRegistrarPaquete(ActionEvent event) {
        if (paquetes.size() >= Constantes.MAX_PAQUETES_POR_ENVIO) {
            Utilidades.mostrarAlertaSimple("Máximo numero de paquetes", "El máximo número de paquetes por envío es " + Constantes.MAX_PAQUETES_POR_ENVIO, Alert.AlertType.WARNING);
            return;
        }
        irFormularioPaqueteModal(null);
    }

    @FXML
    private void clickIrEditarPaquete(ActionEvent event) {
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();
        if (paquete != null) {
            irFormularioPaqueteModal(paquete);
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona un paquete", "Para editar la información de un paquete, debes seleciconarlo primero de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clickEliminarPaquete(ActionEvent event) {
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();
        if (paquete != null) {
            Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Eliminar paquete", "¿Estas seguro de que quieres eliminar este paquete?");
            if (confirmarOperacion) {
                paquetes.remove(paquete);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona profesor", "Para eliminar un profesor, debe seleccionarlo de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_ENVIOS);
    }

    private void recargarDatos(Envio envio) {

        Envio envioActualizado = obtenerEnvio(envio.getIdEnvio());
        if (envio == null) {
            return;
        }
        envioActual = envio;

        Cliente clienteActualizado = obtenerCliente(envio.getIdCliente());

        if (clienteActualizado != null) {
            clienteActual = clienteActualizado;
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
        PdfCreator.generarTicket(envioActual, clienteActual, sucursal);
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

    //Nunca sera null cliente
    private void irFormularioClienteModal(Cliente cliente) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(Constantes.PG_FORMULARIO_CLIENTES));
            Parent vista = cargador.load();
            FXMLFormularioClienteController controlador = cargador.getController();

            if (cliente != null) {
                controlador.setObject(cliente);
            }

            controlador.setTipoModal();
            Stage context = (Stage) lbTitulo.getScene().getWindow();
            Scene escenaPrincipal = new Scene(vista);

            Stage stModal = new Stage();
            stModal.setScene(escenaPrincipal);
            stModal.setWidth(1000);
            stModal.setHeight(657);
            stModal.setResizable(false);
            stModal.setTitle(cliente != null ? "Editar Cliente" : "Registrar cliente");
            stModal.initOwner(context);
            stModal.initModality(Modality.WINDOW_MODAL);
            stModal.initStyle(StageStyle.UTILITY);

            stModal.showAndWait();

            boolean error = controlador.isError();

            if (error == false) {
                Cliente temp = controlador.getClienteSeleccionModal();
                if (temp != null) {
                    borrarDatosCliente();
                    recargarDatosClienteAPI(temp);
                    cargarDatosCliente(clienteActual);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar la ventana de buscar cliente", Alert.AlertType.ERROR);
        }

    }

    private void asignarCliente() {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(Constantes.MODAL_SELECCIONAR_CLIENTE));
            Parent vista = cargador.load();
            FXMLModalSeleccionarClienteController controlador = cargador.getController();
            Stage context = (Stage) lbTitulo.getScene().getWindow();
            Scene escenaPrincipal = new Scene(vista);

            Stage stModal = new Stage();
            stModal.setScene(escenaPrincipal);
            stModal.setWidth(700);
            stModal.setHeight(500);
            stModal.setResizable(false);
            stModal.setTitle("Asignar cliente");
            stModal.initOwner(context);
            stModal.initModality(Modality.WINDOW_MODAL);
            stModal.initStyle(StageStyle.UTILITY);

            stModal.showAndWait();

            Cliente nuevoCliente = controlador.getClienteSeleccionado();

            if (nuevoCliente != null) {
                cambiarCliente(nuevoCliente);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar la ventana de buscar cliente", Alert.AlertType.ERROR);
        }

    }

    private void irFormularioPaqueteModal(Paquete paquete) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(Constantes.PG_FORMULARIO_PAQUETES));
            Parent vista = cargador.load();
            FXMLFormularioPaqueteController controlador = cargador.getController();

            if (paquete != null) {
                controlador.setObject(paquete);
            }

            controlador.setTipoModal();
            Stage context = (Stage) lbTitulo.getScene().getWindow();
            Scene escenaPrincipal = new Scene(vista);

            Stage stModal = new Stage();
            stModal.setScene(escenaPrincipal);
            stModal.setWidth(800);
            stModal.setHeight(600);
            stModal.setResizable(false);
            stModal.setTitle(paquete != null ? "Editar paquete" : "Nuevo paquete");
            stModal.initOwner(context);
            stModal.initModality(Modality.WINDOW_MODAL);
            stModal.initStyle(StageStyle.UTILITY);

            stModal.showAndWait();

            boolean error = controlador.isError();

            if (error == false) {
                Paquete temp = controlador.getPaqueteSeleccionModal();
                if (temp != null) {
                    if (paquete != null) {
                        paquetes.remove(paquete);
                    }
                    paquetes.add(temp);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar la ventana de paquete", Alert.AlertType.ERROR);
        }

    }

    private void recargarDatosClienteAPI(Cliente cliente) {
        try {
            clienteActual = ClienteImp.obtenerCliente(cliente.getIdCliente());
        } catch (Exception ex) {
        }
    }

    private void cambiarCliente(Cliente nuevoCliente) {
        Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Cambiar cliente", "¿Estas seguro de que quieres cambiar el cliente de este envio?");
        if (confirmarOperacion) {
            Respuesta respuesta = EnvioImp.cambiarCliente(envioActual.getIdEnvio(), nuevoCliente.getIdCliente());

            if (!respuesta.isError()) {
                borrarDatosCliente();
                clienteActual = nuevoCliente;
                cargarDatosCliente(nuevoCliente);
                Utilidades.mostrarAlertaSimple("Éxito", "El cliente ha sido cambiado correctamente", Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
            }

        }

    }
}
