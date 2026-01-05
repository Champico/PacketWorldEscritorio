package packetworldescritorio;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import packetworldescritorio.dominio.CatalogoImp;
import packetworldescritorio.pojo.Asentamiento;
import packetworldescritorio.pojo.Estado;
import packetworldescritorio.pojo.Municipio;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLFormularioDireccionController implements Initializable {

    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColonia;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private ComboBox<Municipio> cbCiudad;
    @FXML
    private Label lbErrorColonia;
    @FXML
    private Label lbErrorNumero;
    @FXML
    private Label lbErrorCiudad;
    @FXML
    private Label lbErrorEstado;
    @FXML
    private Label lbErrorCodigoPostal;
    @FXML
    private Label lbErrorCalle;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfCodigoPostal;

    private ObservableList<Estado> estados;
    private ObservableList<Municipio> municipios;
    private ObservableList<Asentamiento> colonias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBox();
        configurarTextField();
    }

    private void configurarComboBox() {
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

    private void configurarTextField() {
        UIUtilidad.limitarNumeros(tfCodigoPostal, 5);
        UIUtilidad.limitarCaracteres(tfCalle, 255);
        UIUtilidad.limitarCaracteres(tfColonia, 255);
        UIUtilidad.limitarCaracteres(tfNumero, 20);
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
                    if (a.getCodigoPostal() != null && !a.getCodigoPostal().isEmpty()) {
                        tfCodigoPostal.setText(a.getCodigoPostal());
                    }
                    sugerencias.hide();
                });

                items.add(item);
            }

            sugerencias.getItems().setAll(items);

            if (!sugerencias.isShowing() && tfColonia.getScene() != null && tfColonia.getScene().getWindow() != null) {
                sugerencias.show(tfColonia, Side.BOTTOM, 0, 0);
            }
        });

        // Ocultar al perder foco
        tfColonia.focusedProperty().addListener((obs, old, focus) -> {
            if (!focus) {
                sugerencias.hide();
            }
        });
    }

    public boolean verificarCampos() {
        boolean camposCorrectos = true;
        if (verificarCodigoPostal() == false) {
            camposCorrectos = false;
        }

        if (verificarEstado() == false) {
            camposCorrectos = false;
        }

        if (verificarCiudad() == false) {
            camposCorrectos = false;
        }

        if (verificarColonia() == false) {
            camposCorrectos = false;
        }

        if (verificarCalle() == false) {
            camposCorrectos = false;
        }

        if (verificarNumero() == false) {
            camposCorrectos = false;
        }
        return camposCorrectos;
    }

    private boolean verificarCodigoPostal() {

        if (UIUtilidad.esInputVacio(tfCodigoPostal, lbErrorCodigoPostal) == true) {
            return false;
        }

        if (tfCodigoPostal.getText().trim().length() != 5) {
            UIUtilidad.marcarError(tfCodigoPostal, lbErrorCodigoPostal, "Debe tener 5 dígitos");
            return false;
        }

        if (!tfCodigoPostal.getText().matches("\\d*")) {
            UIUtilidad.marcarError(tfCodigoPostal, lbErrorCodigoPostal, "Ingrese solo números");
            return false;
        }

        UIUtilidad.limpiarError(tfCodigoPostal, lbErrorCodigoPostal);

        return true;
    }

    private boolean verificarCalle() {

        if (UIUtilidad.esInputVacio(tfCalle, lbErrorCalle) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfCalle, lbErrorCalle);

        return true;
    }

    private boolean verificarNumero() {

        if (UIUtilidad.esInputVacio(tfNumero, lbErrorNumero) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfNumero, lbErrorNumero);

        return true;
    }

    private boolean verificarColonia() {

        if (UIUtilidad.esInputVacio(tfColonia, lbErrorColonia) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfColonia, lbErrorColonia);

        return true;
    }

    private boolean verificarEstado() {
        Estado estadoSeleccionado = cbEstado.getSelectionModel().getSelectedItem();
        if (estadoSeleccionado == null) {
            UIUtilidad.mostrarLabelMensajeError(lbErrorEstado, "Seleccione un estado");
            return false;
        }

        UIUtilidad.ocultarLabelMensajeError(lbErrorEstado);
        return true;
    }

    private boolean verificarCiudad() {
        Municipio municipioSeleccionado = cbCiudad.getSelectionModel().getSelectedItem();
        if (municipioSeleccionado == null) {
            UIUtilidad.mostrarLabelMensajeError(lbErrorCiudad, "Seleccione un municipio");
            return false;
        }

        UIUtilidad.ocultarLabelMensajeError(lbErrorCiudad);
        return true;
    }

    public void inicializarDatos(String calle, String codigoPostal, String colonia, String numero, String claveEstado, String claveCiudad) {

        try {
            int posicionEstado = obtenerPosicionEstado(claveEstado);
            cbEstado.getSelectionModel().select(posicionEstado);
            cbCiudad.getSelectionModel().select(obtenerPosicionMunicipio(claveCiudad, claveEstado));
        } catch (Exception ex) {
        }

        tfCalle.setText(calle != null ? calle : "");
        tfCodigoPostal.setText(codigoPostal != null ? codigoPostal : "");
        tfColonia.setText(colonia != null ? colonia : "");
        tfNumero.setText(numero != null ? numero : "");

    }

    public String getCodigoPostal() {
        return tfCodigoPostal.getText();
    }

    public void setCodigoPostal(String codigoPostal) {
        tfCodigoPostal.setText(codigoPostal);
    }

    public Estado getEstado() {
        return cbEstado.getSelectionModel().getSelectedItem();
    }

    public void setEstado(Estado estado) {
        try {
            cbEstado.getSelectionModel().select(obtenerPosicionEstado(estado.getClaveEstado()));
        } catch (Exception ex) {
        }
    }

    public Municipio getCiudad() {
        return cbCiudad.getSelectionModel().getSelectedItem();
    }

    public void setCiudad(Municipio municipio) {
        try {
            cbCiudad.getSelectionModel()
                    .select(obtenerPosicionMunicipio(municipio.getClaveMunicipio(), municipio.getClaveEstado()));
        } catch (Exception ex) {
        }
    }

    public String getColonia() {
        return tfColonia.getText();
    }

    public void setColonia(String colonia) {
        tfColonia.setText(colonia);
    }

    public String getCalle() {
        return tfCalle.getText();
    }

    public void setCalle(String calle) {
        tfCalle.setText(calle);
    }

    public String getNumero() {
        return tfNumero.getText();
    }

    public void setNumero(String numero) {
        tfNumero.setText(numero);
    }

    private int obtenerPosicionEstado(String claveEstado) {
        for (int i = 0; i < estados.size(); i++) {
            if (estados.get(i).getClaveEstado().equals(claveEstado)) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPosicionMunicipio(String claveMunicipio, String claveEstado) {
        for (int i = 0; i < municipios.size(); i++) {
            if (municipios.get(i).getClaveEstado().equals(claveEstado)
                    && municipios.get(i).getClaveMunicipio().equals(claveMunicipio)) {
                return i;
            }
        }
        return -1;
    }

    public void limpiarCampos() {
        try {
            cbCiudad.getSelectionModel().clearSelection();
            municipios.clear();
            cbEstado.getSelectionModel().clearSelection();
        } catch (Exception ex) {
        }

        tfCalle.setText("");
        tfCodigoPostal.setText("");
        tfColonia.setText("");
        tfNumero.setText("");
    }

}
