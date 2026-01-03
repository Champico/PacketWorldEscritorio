/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Envio;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Constantes;

public class FXMLFormularioEnvioController implements Initializable, INavegableChild {

    private INavegacion nav;
    private Envio envioEdicion;
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
    private TableView<?> tvPaquetes;
    @FXML
    private TableColumn<?, ?> colDescripcion;
    @FXML
    private TableColumn<?, ?> colPeso;
    @FXML
    private TableColumn<?, ?> colAlto;
    @FXML
    private TableColumn<?, ?> colAncho;
    @FXML
    private TableColumn<?, ?> colProfundidad;

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

    private void regresar() {
        nav.navegar(Constantes.MODULO_ENVIOS);
    }

    @Override
    public void setObject(Object object) {
        if (object instanceof Envio) {
            Envio envio = (Envio) object;
            /*inicializarDatos();*/
        }
    }

    @FXML
    private void clickIrRegistrar(ActionEvent event) {
    }

    @FXML
    private void clickIrEditar(ActionEvent event) {
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
    }
    
    private void configurarSeccionPaquetes(){
        agregarFormularioDireccion();
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

}
