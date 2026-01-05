/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import packetworldescritorio.dominio.PaqueteImp;
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.dto.CaracteristicasPaquete;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Paquete;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLFormularioPaqueteController implements Initializable, INavegableChild {

    private INavegacion nav;
    private Paquete paqueteEdicion;
    private Paquete paqueteSeleccionadoVentanaModal = null;
    private String tipoVentana;
    private boolean isError = true;
    private CaracteristicasPaquete limites;

    @FXML
    private AnchorPane apFormularioColaborador;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfAncho;
    @FXML
    private TextField tfAltura;
    @FXML
    private TextField tfProfundidad;
    @FXML
    private Label lbErrorDescripcion;
    @FXML
    private Label lbErrorPeso;
    @FXML
    private Label lbErrorAncho;
    @FXML
    private Label lbErrorAltura;
    @FXML
    private Label lbErrorProfundidad;
    @FXML
    private Label lbTitulo;
    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoVentana = Constantes.TIPO_VENTANA_PAGINA;
        configurarMaximoNumeroCaracteres();
        obtenerCaracteristicasMaximaPaquete();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        boolean camposCorrectos = verificarCampos();

        if (camposCorrectos == true) {

            Paquete paquete = new Paquete();
            paquete.setDescripcion(tfDescripcion.getText());
            paquete.setPeso(Float.parseFloat(tfPeso.getText()));
            paquete.setAncho(Float.parseFloat(tfAncho.getText()));
            paquete.setAlto(Float.parseFloat(tfAltura.getText()));
            paquete.setProfundidad(Float.parseFloat(tfProfundidad.getText()));

            if (paqueteEdicion == null) {
                registrarPaquete(paquete);
            } else {
                paquete.setIdPaquete(paqueteEdicion.getIdPaquete());
                editarPaquete(paquete);
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

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        if (object instanceof Paquete) {
            Paquete paquete = (Paquete) object;
            inicializarDatos(paquete);
        }
    }

    public void setTipoModalSeleccion() {
        this.tipoVentana = Constantes.TIPO_VENTANA_MODAL_SELECCION;
        btnRegresar.setVisible(false);
    }

    public void setTipoModal() {
        this.tipoVentana = Constantes.TIPO_VENTANA_MODAL;
        btnRegresar.setVisible(false);
    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_CLIENTES);
    }

    public void inicializarDatos(Paquete paqueteEdicion) {
        this.paqueteEdicion = paqueteEdicion;
        if (paqueteEdicion != null) {

            lbTitulo.setText("Editar paquete");

            tfDescripcion.setText(paqueteEdicion.getDescripcion());
            tfPeso.setText(paqueteEdicion.getPeso() + "");
            tfAncho.setText(paqueteEdicion.getAncho() + "");
            tfAltura.setText(paqueteEdicion.getAlto() + "");
            tfProfundidad.setText(paqueteEdicion.getProfundidad() + "");

        }
    }

    private void configurarMaximoNumeroCaracteres() {
        UIUtilidad.limitarCaracteres(tfDescripcion, 255);
        TextInputControl[] tf = {tfPeso, tfAltura, tfAncho, tfProfundidad};

        for (int i = 0; i < tf.length; i++) {
            UIUtilidad.aplicarFormatoNumericoDecimal(tf[i], 3, 2);
        }
    }

    public boolean isError() {
        return isError;
    }

    private void cerrar() {
        Stage stage = (Stage) lbTitulo.getScene().getWindow();
        stage.close();
    }

    public Paquete getPaqueteSeleccionModal() {
        return paqueteSeleccionadoVentanaModal;
    }

    private boolean verificarCampos() {
        boolean camposCorrectos = true;

        if (verificarDescripcion() == false) {
            camposCorrectos = false;
        }

        if (verificarPeso() == false) {
            camposCorrectos = false;
        }

        if (verificarAlto() == false) {
            camposCorrectos = false;
        }

        if (verificarAncho() == false) {
            camposCorrectos = false;
        }

        if (verificarProfundidad() == false) {
            camposCorrectos = false;
        }

        return camposCorrectos;
    }

    private boolean verificarDescripcion() {

        if (UIUtilidad.esInputVacio(tfDescripcion, lbErrorDescripcion) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfDescripcion, lbErrorDescripcion);

        return true;
    }

    private boolean verificarPeso() {
        Float value = null;

        if (UIUtilidad.esInputVacio(tfPeso, lbErrorPeso) == true) {
            return false;
        }

        try {
            value = Float.parseFloat(tfPeso.getText().trim());
        } catch (Exception ex) {
            UIUtilidad.marcarError(tfPeso, lbErrorPeso, "No es un número valido");
            return false;
        }

        if (value < limites.getPesoMinimo()) {
            UIUtilidad.marcarError(tfPeso, lbErrorPeso, "El peso mínimo es " + limites.getPesoMinimo());
            return false;
        }

        if (value > limites.getPesoMaximo()) {
            UIUtilidad.marcarError(tfPeso, lbErrorPeso, "El peso máximo es " + limites.getPesoMaximo());
            return false;
        }

        UIUtilidad.limpiarError(tfPeso, lbErrorPeso);

        return true;
    }

    private boolean verificarAlto() {
        Float value = null;

        if (UIUtilidad.esInputVacio(tfAltura, lbErrorAltura) == true) {
            return false;
        }

        try {
            value = Float.parseFloat(tfAltura.getText().trim());
        } catch (Exception ex) {
            UIUtilidad.marcarError(tfAltura, lbErrorAltura, "No es un número valido");
            return false;
        }

        if (value < limites.getAlturaMinima()) {
            UIUtilidad.marcarError(tfAltura, lbErrorAltura, "La altura mínima es " + limites.getAlturaMinima());
            return false;
        }

        if (value > limites.getAlturaMaxima()) {
            UIUtilidad.marcarError(tfAltura, lbErrorAltura, "La altura máxima es " + limites.getAlturaMaxima());
            return false;
        }

        UIUtilidad.limpiarError(tfAltura, lbErrorAltura);

        return true;
    }

    private boolean verificarAncho() {
        Float value = null;

        if (UIUtilidad.esInputVacio(tfAncho, lbErrorAncho) == true) {
            return false;
        }

        try {
            value = Float.parseFloat(tfAncho.getText().trim());
        } catch (Exception ex) {
            UIUtilidad.marcarError(tfAncho, lbErrorAncho, "No es un número valido");
            return false;
        }

        if (value < limites.getAnchoMinimo()) {
            UIUtilidad.marcarError(tfAncho, lbErrorAncho, "El ancho mínimo es " + limites.getAnchoMinimo());
            return false;
        }

        if (value > limites.getAnchoMaximo()) {
            UIUtilidad.marcarError(tfAncho, lbErrorAncho, "El ancho máximo es " + limites.getAnchoMaximo());
            return false;
        }

        UIUtilidad.limpiarError(tfAncho, lbErrorAncho);

        return true;
    }

    private boolean verificarProfundidad() {
        Float value = null;

        if (UIUtilidad.esInputVacio(tfProfundidad, lbErrorProfundidad) == true) {
            return false;
        }

        try {
            value = Float.parseFloat(tfProfundidad.getText().trim());
        } catch (Exception ex) {
            UIUtilidad.marcarError(tfProfundidad, lbErrorProfundidad, "No es un número valido");
            return false;
        }

        if (value < limites.getProfundadMinima()) {
            UIUtilidad.marcarError(tfProfundidad, lbErrorProfundidad, "La profundidad mínima es " + limites.getProfundadMinima());
            return false;
        }

        if (value > limites.getProfundidadMaxima()) {
            UIUtilidad.marcarError(tfProfundidad, lbErrorProfundidad, "La profundidad máxima es " + limites.getProfundidadMaxima());
            return false;
        }

        UIUtilidad.limpiarError(tfProfundidad, lbErrorProfundidad);

        return true;
    }

    private void obtenerCaracteristicasMaximaPaquete() {
        limites = PaqueteImp.obtenerCaracteristicasMaximas();
    }

    private void registrarPaquete(Paquete paquete) {
        paqueteSeleccionadoVentanaModal = paquete;
        if (tipoVentana.equals(Constantes.TIPO_VENTANA_PAGINA)) {
            Respuesta respuesta = PaqueteImp.registrar(paquete);
            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple("Registrada correctamente", "El paquete ha sido agregado correctamente", Alert.AlertType.INFORMATION);
                regresar();
            } else {
                Utilidades.mostrarAlertaSimple("Ocurrio un error", respuesta.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            isError = false;
            cerrar();
        }

    }

    private void editarPaquete(Paquete paquete) {
        paqueteSeleccionadoVentanaModal = paquete;
        if (tipoVentana.equals(Constantes.TIPO_VENTANA_PAGINA)) {
            Respuesta respuesta = PaqueteImp.editar(paquete);
            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple("Editado correctamente", "El paquete ha sido editado correctamente", Alert.AlertType.INFORMATION);
                regresar();
            } else {
                Utilidades.mostrarAlertaSimple("Ocurrio un error", respuesta.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            isError = false;
            cerrar();
        }
    }

}
