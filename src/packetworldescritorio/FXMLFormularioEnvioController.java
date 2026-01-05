/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.dominio.ClienteImp;
import packetworldescritorio.dominio.EnvioImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Envio;
import packetworldescritorio.pojo.Paquete;
import packetworldescritorio.pojo.Session;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLFormularioEnvioController implements Initializable, INavegableChild {

    private INavegacion nav;
    private Cliente clienteSeleccionado;
    private FXMLFormularioDireccionController formularioDireccionController;

    @FXML
    private Label lbTitulo;
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
    private HBox hbMenuCliente;

    private ObservableList<Paquete> paquetes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSeccionCliente();
        configurarSeccionDestinatario();
        configurarSeccionPaquetes();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Regresar", "¿Estas seguro de que quieres regresar, los datos no se guardaran?");
        if (confirmarOperacion) {
            regresar();
        }
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

    @FXML
    private void clickGuardar(ActionEvent event) {
        registrarEnvio();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Cancelar", "¿Estas seguro de que quieres cancelar, los datos no se guardaran?");
        if (confirmarOperacion) {
            regresar();
        }
    }

    @FXML
    private void clickSeleccionarCliente(ActionEvent event) {
        asignarCliente();
    }

    @FXML
    private void clickCrearNuevoCliente(ActionEvent event) {
        irFormularioClienteModal(null);
    }

    @FXML
    private void clickQuitarCliente(ActionEvent event) {
        clienteSeleccionado = null;
        borrarDatosCliente();
        ocultarDatosCliente();
    }

    @FXML
    private void clickEditarCliente(ActionEvent event) {
        if (clienteSeleccionado != null) {
            irFormularioClienteModal(clienteSeleccionado);
        }
    }

    @FXML
    private void clickCambiarCliente(ActionEvent event) {
        asignarCliente();

    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_ENVIOS);
    }

    @Override
    public void setObject(Object object) {

    }

    private void configurarSeccionPaquetes() {
        inicializarTablaPaquetesVacia();
        configurarTabla();
    }

    private void configurarSeccionDestinatario() {
        agregarFormularioDireccion();
    }

    private void configurarSeccionCliente() {

    }

    private void agregarFormularioDireccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.COMP_FORMULARIO_DIRECCION));
            AnchorPane componenteFormulario = loader.load();

            componenteFormulario.setLayoutX(0);
            componenteFormulario.setLayoutY(85);

            formularioDireccionController = loader.getController();
            apFormularioDestinatario.getChildren().add(componenteFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarCampos() {
        boolean camposCorrectos = true;

        if (verificarCliente() == false) {
            camposCorrectos = false;
        }
        if (verificarNombre() == false) {
            camposCorrectos = false;
        }
        if (verificarApellidoPaterno() == false) {
            camposCorrectos = false;
        }
        if (verificarTelefono() == false) {
            camposCorrectos = false;
        }
        if (formularioDireccionController.verificarCampos() == false) {
            camposCorrectos = false;
        }
        return camposCorrectos;
    }

    private boolean verificarNombre() {

        if (UIUtilidad.esInputVacio(tfNombre, lbErrorNombre) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfNombre, lbErrorNombre);

        return true;
    }

    private boolean verificarApellidoPaterno() {

        if (UIUtilidad.esInputVacio(tfApPaterno, lbErrorApPaterno) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfApPaterno, lbErrorApPaterno);

        return true;
    }

    private boolean verificarTelefono() {
        if (UIUtilidad.esInputVacio(tfTelefono, lbErrorTelefono) == true) {
            return false;
        }

        if (tfTelefono.getText().trim().matches("^[0-9+\\- ]{1,20}$") == false) {
            UIUtilidad.marcarError(tfTelefono, lbErrorTelefono, "Formato de teléfono no válido");
            return false;
        }

        UIUtilidad.limpiarError(tfTelefono, lbErrorTelefono);

        return true;
    }

    private boolean verificarCliente() {
        if (clienteSeleccionado == null) {
            return false;
        }

        return true;
    }

    private void registrarEnvio() {
        boolean camposCorrectos = verificarCampos();

        if (camposCorrectos == true) {

            Envio envio = new Envio();
            envio.setDestinatarioNombre(tfNombre.getText());
            envio.setDestinatarioApellidoP(tfApPaterno.getText());
            envio.setDestinatarioApellidoM(tfApMaterno.getText());
            envio.setDestinatarioTelefono(tfTelefono.getText());
            envio.setCalle(formularioDireccionController.getCalle());
            envio.setColonia(formularioDireccionController.getColonia());
            envio.setCodigoPostal(formularioDireccionController.getCodigoPostal());
            envio.setEstado(formularioDireccionController.getEstado().getNombre());
            envio.setCiudad(formularioDireccionController.getCiudad().getNombre());
            envio.setClaveEstado(formularioDireccionController.getEstado().getClaveEstado());
            envio.setClaveCiudad(formularioDireccionController.getCiudad().getClaveMunicipio());
            envio.setNumero(formularioDireccionController.getNumero());
            envio.setIdCliente(clienteSeleccionado.getIdCliente());
            envio.setIdSucursalOrigen(Session.getInstance().getUsuarioActual().getIdSucursal());

            if (paquetes.isEmpty()) {
                Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Envío sin paquetes", "¿Estas seguro de que quieres crear el envío sin paquetes?");
                if (confirmarOperacion) {
                    registrarDatosDeEnvio(envio);
                }
            } else {
                envio.setPaquetes(paquetes);
                registrarDatosDeEnvio(envio);
            }

        }

    }

    private void registrarDatosDeEnvio(Envio envio) {
        Respuesta respuesta = EnvioImp.registrar(envio);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Éxito", "Se ha creado el nuevo envío con éxito", Alert.AlertType.INFORMATION);
             envio = EnvioImp.obtenerEnvioPorGuia(respuesta.getMensaje());
             if(envio != null){
                 irPaginaPerfilEnvio(envio);
             }else{
                 regresar();
             }
             
        } else {
            Utilidades.mostrarAlertaSimple("Ocurrio un error", respuesta.getMensaje(), Alert.AlertType.ERROR);
            return;
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

            clienteSeleccionado = controlador.getClienteSeleccionado();

            if (clienteSeleccionado != null) {
                borrarDatosCliente();
                cargarDatosCliente(clienteSeleccionado);
                mostrarDatosCliente();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar la ventana de buscar cliente", Alert.AlertType.ERROR);
        }

    }

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
                    recargarDatosCliente(temp);
                    cargarDatosCliente(clienteSeleccionado);
                    mostrarDatosCliente();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar la ventana de buscar cliente", Alert.AlertType.ERROR);
        }

    }

    private void recargarDatosCliente(Cliente cliente) {
        if (cliente == null) {
            clienteSeleccionado = null;
            return;
        }
        try {
            if (cliente.getIdCliente() > 0) {
                clienteSeleccionado = ClienteImp.obtenerCliente(cliente.getIdCliente());
            } else {
                clienteSeleccionado = ClienteImp.obtenerClientePorCorreo(cliente.getCorreo());
            }
        } catch (Exception ex) {
            clienteSeleccionado = null;
        }
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

    private void irFormularioPaqueteModal(Paquete paquete) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(Constantes.PG_FORMULARIO_PAQUETES));
            Parent vista = cargador.load();
            FXMLFormularioPaqueteController controlador = cargador.getController();

            if (paquete != null) {
                controlador.setObject(paquete);
            }

            controlador.setTipoModalSeleccion();
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

    private void irPaginaPerfilEnvio(Envio envio) {
        nav.navegar(Constantes.PG_PERFIL_ENVIO, envio);
    }

}
