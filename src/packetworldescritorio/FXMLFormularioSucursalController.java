package packetworldescritorio;

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
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfCodigo;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCalle;
    @FXML
    private Label lbErrorCalle;
    @FXML
    private Label lbErrorCodigo;
    @FXML
    private Label lbErrorCodigoPostal;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorEstado;
    @FXML
    private Label lbErrorCiudad;
    @FXML
    private Label lbErrorColonia;
    @FXML
    private TextField tfNumero;
    @FXML
    private Label lbErrorNumero;
    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private ComboBox<Municipio> cbCiudad;
    @FXML
    private TextField tfColonia;

    private ObservableList<Estado> estados;
    private ObservableList<Municipio> municipios;
    private ObservableList<Asentamiento> colonias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBoxParaDirecciones();
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
            /*
            tfNoPersonal.setText(colaboradorEdicion.getNoPersonal());
            tfNombre.setText(colaboradorEdicion.getNombre());
            tfApPaterno.setText(colaboradorEdicion.getApellidoPaterno());
            tfApMaterno.setText(colaboradorEdicion.getApellidoMaterno());
            tfCorreo.setText(colaboradorEdicion.getCorreo());
            tfCurp.setText(colaboradorEdicion.getCurp());

            tfNoPersonal.setDisable(true);
            cbRol.setDisable(true);

            int posicionRol = obtenerPosicionRol(colaboradorEdicion.getIdRol());
            cbRol.getSelectionModel().select(posicionRol);

            int posicionSucursal = obtenerPosicionSucursal(colaboradorEdicion.getIdSucursal());
            cbSucursal.getSelectionModel().select(posicionSucursal);

            if (colaboradorEdicion.getIdRol() == Constantes.ID_ROL_CONDUCTOR) {
                tfLicencia.setText(colaboradorEdicion.getNumLicencia());
            }

            cargarFoto();
            ocultarCamposPassword();*/
        }
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
    }

    private void configurarComboBoxParaDirecciones() {
        cargarComboBoxEstados();

        cbEstado.valueProperty().addListener((obs, valorAnterior, valorActual) -> {
            if (valorActual != null) {
                if (valorAnterior == null || !valorAnterior.equals(valorActual)) {
                    cargarComboBoxMunicipio(valorActual.getClaveEstado());
                }
            }
        });

        cbCiudad.valueProperty().addListener((obs, valorAnterior, valorActual) -> {
            if (valorActual != null) {
                if (valorAnterior == null || !valorAnterior.equals(valorActual)) {
                    actualizarColonias(valorActual.getClaveEstado(), valorActual.getClaveMunicipio());
                }
            }
        });
        
        configurarAutocompletadoColonia();
    }

    private void cargarComboBoxEstados() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerEstados();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Estado> estadosAPI = (List<Estado>) respuesta.get(Constantes.KEY_LISTA);
            estados = FXCollections.observableArrayList();
            estados.addAll(estadosAPI);
            cbEstado.setItems(estados);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
        }
    }

    private void cargarComboBoxMunicipio(String claveEstado) {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerMunicipios(claveEstado);
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Municipio> municipiosAPI = (List<Municipio>) respuesta.get(Constantes.KEY_LISTA);
            municipios = FXCollections.observableArrayList();
            municipios.addAll(municipiosAPI);
            cbCiudad.setItems(municipios);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
        }
    }

    private void actualizarColonias(String claveEstado, String claveMunicipio) {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerColonias(claveEstado, claveMunicipio);
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Asentamiento> coloniasAPI = (List<Asentamiento>) respuesta.get(Constantes.KEY_LISTA);
            colonias = FXCollections.observableArrayList();
            colonias.addAll(coloniasAPI);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
        }
    }

  
    ContextMenu sugerencias;
    public void configurarAutocompletadoColonia() {

    sugerencias = new ContextMenu();

    tfColonia.textProperty().addListener((obs, textoAnterior, textoActual) -> {

        if (textoActual == null || textoActual.isEmpty() || colonias == null || colonias.isEmpty()) {
            sugerencias.hide();
            return;
        }

        List<Asentamiento> filtrados = colonias.stream()
                .filter(c -> c.getNombre()
                        .toLowerCase()
                        .contains(textoActual.toLowerCase()))
                .limit(8)
                .collect(Collectors.toList());

        if (filtrados.isEmpty()) {
            sugerencias.hide();
            return;
        }

        List<CustomMenuItem> items = new ArrayList<>();

        for (Asentamiento a : filtrados) {

            Label lbl = new Label(a.getNombre());
            lbl.setPrefWidth(300);

            CustomMenuItem item = new CustomMenuItem(lbl, true);

            item.setOnAction(e -> {
                tfColonia.setText(a.getNombre());
                sugerencias.hide();
            });

            items.add(item);
        }

        sugerencias.getItems().setAll(items);

        if (!sugerencias.isShowing()) {
            sugerencias.show(tfColonia, Side.BOTTOM, 0, 0);
        }
    });

    // Ocultar al perder foco
    tfColonia.focusedProperty().addListener((obs, old, focus) -> {
        if (!focus) sugerencias.hide();
    });
}


}
