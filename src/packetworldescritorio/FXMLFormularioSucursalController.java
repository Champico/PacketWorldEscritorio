package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import packetworldescritorio.dominio.CatalogoImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Asentamiento;
import packetworldescritorio.pojo.Estado;
import packetworldescritorio.pojo.Municipio;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.pojo.TipoUnidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLFormularioSucursalController implements Initializable, INavegableChild {

    private INavegacion nav;
    private Sucursal sucursalEdicion;

    @FXML
    private TextField tfCodigo;
    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbErrorCodigo;
    @FXML
    private Label lbErrorNombre;

    @FXML
    private Label lbTitulo;
    @FXML
    private AnchorPane apFormularioSucursal;

    private FXMLFormularioDireccionController formularioDireccionController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agregarFormularioDireccion();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        if (object instanceof Sucursal) {
            Sucursal sucursal = (Sucursal) object;
            inicializarDatos(sucursal);

        }
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        nav.navegar(Constantes.PG_PRINCIPAL);
    }

    public void inicializarDatos(Sucursal sucursalEdicion) {
        this.sucursalEdicion = sucursalEdicion;
        if (sucursalEdicion != null) {

            lbTitulo.setText("Editar sucursal");

            tfNombre.setText(sucursalEdicion.getNombre());
            tfCodigo.setText(sucursalEdicion.getCodigo());

            tfCodigo.setDisable(true);

            if (formularioDireccionController != null) {
                formularioDireccionController.inicializarDatos(sucursalEdicion.getCalle(), sucursalEdicion.getCodigoPostal(), sucursalEdicion.getColonia(), sucursalEdicion.getNumero(), sucursalEdicion.getIdEstado(), sucursalEdicion.getIdCiudad());
            }
        }
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
    }

    private void agregarFormularioDireccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.COMP_FORMULARIO_DIRECCION));
            AnchorPane componenteFormulario = loader.load();

            componenteFormulario.setLayoutX(50);
            componenteFormulario.setLayoutY(98);

            formularioDireccionController = loader.getController();
            apFormularioSucursal.getChildren().add(componenteFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
