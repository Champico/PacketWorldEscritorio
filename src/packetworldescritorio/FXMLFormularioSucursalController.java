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
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.dominio.UnidadImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Asentamiento;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Estado;
import packetworldescritorio.pojo.Municipio;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.pojo.TipoUnidad;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
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
        regresar();
    }
    
    
    private void regresar() {
        nav.navegar(Constantes.MODULO_SUCURSALES);
    }


    public void inicializarDatos(Sucursal sucursalEdicion) {
        this.sucursalEdicion = sucursalEdicion;
        if (sucursalEdicion != null) {

            lbTitulo.setText("Editar sucursal");

            tfNombre.setText(sucursalEdicion.getNombre());
            tfCodigo.setText(sucursalEdicion.getCodigo());

            tfCodigo.setDisable(true);

            if (formularioDireccionController != null) {
                formularioDireccionController.inicializarDatos(sucursalEdicion.getCalle(), sucursalEdicion.getCodigoPostal(), sucursalEdicion.getColonia(), sucursalEdicion.getNumero(), sucursalEdicion.getClaveEstado(), sucursalEdicion.getClaveCiudad());
            }
        }
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        boolean camposCorrectos = verificarCampos();

        if (camposCorrectos == true) {

            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(tfNombre.getText());
            sucursal.setCodigo(tfCodigo.getText());
            sucursal.setCalle(formularioDireccionController.getCalle());
            sucursal.setColonia(formularioDireccionController.getColonia());
            sucursal.setCodigoPostal(formularioDireccionController.getCodigoPostal());
            sucursal.setEstado(formularioDireccionController.getEstado().getNombre());
            sucursal.setCiudad(formularioDireccionController.getCiudad().getNombre());
            sucursal.setClaveEstado(formularioDireccionController.getEstado().getClaveEstado());
            sucursal.setClaveCiudad(formularioDireccionController.getCiudad().getClaveMunicipio());
            sucursal.setNumero(formularioDireccionController.getNumero());

            if (sucursalEdicion == null) {
                registrarSucursal(sucursal);
            } else {
                sucursal.setIdSucursal(sucursalEdicion.getIdSucursal());
                editarSucursal(sucursal);
            }

        }
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        regresar();
    }

    private void agregarFormularioDireccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constantes.COMP_FORMULARIO_DIRECCION));
            AnchorPane componenteFormulario = loader.load();

            componenteFormulario.setLayoutX(54);
            componenteFormulario.setLayoutY(98);

            formularioDireccionController = loader.getController();
            apFormularioSucursal.getChildren().add(componenteFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean verificarCampos() {
        boolean camposCorrectos = true;
        if (verificarCodigo() == false) {
            camposCorrectos = false;
        }

        if (verificarNombre() == false) {
            camposCorrectos = false;
        }
        
        if (formularioDireccionController.verificarCampos() == false) {
            camposCorrectos = false;
        }

        return camposCorrectos;
    }

    private void registrarSucursal(Sucursal sucursal) {
        Respuesta respuesta = SucursalImp.registrar(sucursal);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Registrada correctamente", "La sucursal ha sido registrada correctamente", Alert.AlertType.INFORMATION);
            regresar();
        } else {
            Utilidades.mostrarAlertaSimple("Ocurrio un error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarSucursal(Sucursal sucursal) {
        Respuesta respuesta = SucursalImp.editar(sucursal);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Editara correctamente", "La sucursal ha sido editada correctamente", Alert.AlertType.INFORMATION);
            regresar();
        } else {
            Utilidades.mostrarAlertaSimple("Ocurrio un error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private boolean verificarNombre() {

        if (UIUtilidad.esInputVacio(tfNombre, lbErrorNombre) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfNombre, lbErrorNombre);

        return true;
    }

    private boolean verificarCodigo() {

        if (UIUtilidad.esInputVacio(tfCodigo, lbErrorCodigo) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfCodigo, lbErrorCodigo);

        return true;
    }
}
