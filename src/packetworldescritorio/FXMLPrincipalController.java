/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.utilidad.Constantes;

public class FXMLPrincipalController implements Initializable, INavegableChild {

    private INavegacion nav;
    @FXML
    private Button btnColaboradores;
    @FXML
    private Button btnUnidades;
    @FXML
    private Button btnEnvios;
    @FXML
    private Button btnPaquetes;
    @FXML
    private Button btnSucursales;
    @FXML
    private Button btnClientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @FXML
    private void clickIrModuloColaboradores(ActionEvent event) {
        nav.navegar(Constantes.MODULO_COLABORADORES);
    }

    @FXML
    private void clickIrModuloUnidades(ActionEvent event) {
        nav.navegar(Constantes.MODULO_UNIDADES);
    }

    @FXML
    private void clickIrModuloEnvios(ActionEvent event) {
        nav.navegar(Constantes.MODULO_ENVIOS);
    }

    @FXML
    private void clickIrModuloPaquetes(ActionEvent event) {
        nav.navegar(Constantes.MODULO_PAQUETES);
    }

    @FXML
    private void clickIrModuloClientes(ActionEvent event) {
        nav.navegar(Constantes.MODULO_CLIENTES);
    }

    @FXML
    private void clickIrModuloSucursales(ActionEvent event) {
        nav.navegar(Constantes.MODULO_SUCURSALES);
    }

    @Override
    public void setObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
