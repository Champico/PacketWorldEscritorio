/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Constantes;

public class FXMLModuloEnviosController implements Initializable, INavegableChild {

    private INavegacion nav;

    @FXML
    private TableView<?> tvEnvios;
    @FXML
    private TableColumn colGuia;
    @FXML
    private TableColumn colDestinatario;
    @FXML
    private TableColumn colCalle;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colCiudad;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TableColumn colEstatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {

    }

    @FXML
    private void clickIrRegistrar(ActionEvent event) {
    }

    @FXML
    private void clickIrBuscar(ActionEvent event) {
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

        private void regresar() {
        nav.navegar(Constantes.PG_PRINCIPAL);
    }
        
        
        
        
        
        
        
        
        
        
        
        
}
