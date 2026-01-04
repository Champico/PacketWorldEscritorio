/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import packetworldescritorio.dominio.ClienteImp;
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLFormularioClienteController implements Initializable, INavegableChild {

    private INavegacion nav;
    private Cliente clienteEdicion;
    private Cliente clienteSeleccionadoVentanaModal = null;
    private String tipoVentana;
    private boolean isError = true;

    private FXMLFormularioDireccionController formularioDireccionController;
    @FXML
    private Label lbTitulo;
    @FXML
    private AnchorPane apFormularioColaborador;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApPaterno;
    @FXML
    private TextField tfApMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorApPaterno;
    @FXML
    private Label lbErrorApMaterno;
    @FXML
    private Label lbErrorCorreo;
    @FXML
    private Label lbErrorTelefono;
    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoVentana = Constantes.TIPO_VENTANA_PAGINA;
        agregarFormularioDireccion();
        configurarMaximoNumeroCaracteres();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        if (object instanceof Cliente) {
            Cliente cliente = (Cliente) object;
            inicializarDatos(cliente);
        }
    }

    public void setTipoModal() {
        this.tipoVentana = Constantes.TIPO_VENTANA_MODAL;
        btnRegresar.setVisible(false);
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_CLIENTES);
    }

    public void inicializarDatos(Cliente clienteEdicion) {
        this.clienteEdicion = clienteEdicion;
        if (clienteEdicion != null) {

            lbTitulo.setText("Editar cliente");

            tfNombre.setText(clienteEdicion.getNombre());
            tfApPaterno.setText(clienteEdicion.getApellidoPaterno());
            tfApMaterno.setText(clienteEdicion.getApellidoMaterno());
            tfCorreo.setText(clienteEdicion.getCorreo());
            tfTelefono.setText(clienteEdicion.getTelefono());

            if (formularioDireccionController != null) {
                formularioDireccionController.inicializarDatos(clienteEdicion.getCalle(), clienteEdicion.getCodigoPostal(), clienteEdicion.getColonia(), clienteEdicion.getNumero(), clienteEdicion.getClaveEstado(), clienteEdicion.getClaveCiudad());
            }
        }
    }

    private void agregarFormularioDireccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.COMP_FORMULARIO_DIRECCION));
            AnchorPane componenteFormulario = loader.load();

            componenteFormulario.setLayoutX(55);
            componenteFormulario.setLayoutY(113);

            formularioDireccionController = loader.getController();
            apFormularioColaborador.getChildren().add(componenteFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        boolean camposCorrectos = verificarCampos();

        if (camposCorrectos == true) {

            Cliente cliente = new Cliente();
            cliente.setNombre(tfNombre.getText());
            cliente.setApellidoPaterno(tfApPaterno.getText());
            cliente.setApellidoMaterno(tfApMaterno.getText());
            cliente.setCorreo(tfCorreo.getText());
            cliente.setTelefono(tfTelefono.getText());
            cliente.setCalle(formularioDireccionController.getCalle());
            cliente.setColonia(formularioDireccionController.getColonia());
            cliente.setCodigoPostal(formularioDireccionController.getCodigoPostal());
            cliente.setEstado(formularioDireccionController.getEstado().getNombre());
            cliente.setCiudad(formularioDireccionController.getCiudad().getNombre());
            cliente.setClaveEstado(formularioDireccionController.getEstado().getClaveEstado());
            cliente.setClaveCiudad(formularioDireccionController.getCiudad().getClaveMunicipio());
            cliente.setNumero(formularioDireccionController.getNumero());
            
            if (clienteEdicion == null) {
                clienteSeleccionadoVentanaModal = cliente;
                registrarCliente(cliente);
            } else {
                cliente.setIdCliente(clienteEdicion.getIdCliente());
                clienteSeleccionadoVentanaModal = cliente;
                editarCliente(cliente);
            }

        }
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        if (tipoVentana.equals(Constantes.TIPO_VENTANA_PAGINA)) {
            regresar();
        } else {
            cerrar();
        }
    }

    private boolean verificarCampos() {
        boolean camposCorrectos = true;

        if (verificarNombre() == false) {
            camposCorrectos = false;
        }
        if (verificarApellidoPaterno() == false) {
            camposCorrectos = false;
        }
        if (verificarCorreo() == false) {
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

    private boolean verificarCorreo() {
        if (UIUtilidad.esInputVacio(tfCorreo, lbErrorCorreo) == true) {
            return false;
        }

        if (tfCorreo.getText().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") == false) {
            UIUtilidad.marcarError(tfCorreo, lbErrorCorreo, "Formato de correo no valido");
            return false;
        }

        UIUtilidad.limpiarError(tfCorreo, lbErrorCorreo);

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

    private void registrarCliente(Cliente cliente) {
        Respuesta respuesta = ClienteImp.registrar(cliente);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Registrado correctamente", "El cliente ha sido registrado correctamente", Alert.AlertType.INFORMATION);
            isError = false;
            if (tipoVentana.equals(Constantes.TIPO_VENTANA_PAGINA)) {
                regresar();
            } else {
                cerrar();
            }
        } else {
            isError = true;
            Utilidades.mostrarAlertaSimple("Ocurrio un error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarCliente(Cliente cliente) {
        Respuesta respuesta = ClienteImp.editar(cliente);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Editado correctamente", "El cliente ha sido editado correctamente", Alert.AlertType.INFORMATION);
            isError = false;
            if (tipoVentana.equals(Constantes.TIPO_VENTANA_PAGINA)) {
                regresar();
            } else {
                cerrar();
            }
        } else {
            isError = true;
            Utilidades.mostrarAlertaSimple("Ocurrio un error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void configurarMaximoNumeroCaracteres() {
        TextInputControl[] tf = {tfNombre, tfApPaterno, tfApMaterno, tfCorreo};
        int maxNumCaracteres = 254;

        for (int i = 0; i < tf.length; i++) {
            UIUtilidad.limitarCaracteres(tf[i], maxNumCaracteres);
        }

        UIUtilidad.setPhoneNumberFormatter(tfTelefono);
    }

    public boolean isError() {
        return isError;
    }

    private void cerrar() {
        Stage stage = (Stage) lbTitulo.getScene().getWindow();
        stage.close();
    }
    
    public Cliente getClienteSeleccionModal(){
        return clienteSeleccionadoVentanaModal;
    }

}
