/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Paquete;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;

public class FXMLFormularioPaqueteController implements Initializable, INavegableChild {

    private INavegacion nav;
    private Paquete paqueteEdicion;
    private Paquete paqueteSeleccionadoVentanaModal = null;
    private String tipoVentana;
    private boolean isError = true;

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
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
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

}
