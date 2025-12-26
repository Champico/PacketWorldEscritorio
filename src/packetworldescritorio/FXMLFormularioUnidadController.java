/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.dominio.CatalogoImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.pojo.TipoUnidad;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLFormularioUnidadController implements Initializable, INavegableChild {

    private INavegacion nav;

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private ComboBox<Integer> cbAno;
    @FXML
    private TextField tfVin;
    @FXML
    private Label lbErrorVin;
    @FXML
    private Label lbErrorMarca;
    @FXML
    private Label lbErrorModelo;
    @FXML
    private Label lbErrorAno;
    @FXML
    private Label lbErrorTipo;
    @FXML
    private ComboBox<TipoUnidad> cbTipo;

    private Unidad unidadEdicion = null;

    private ObservableList<TipoUnidad> tiposDeUnidad;
    private ObservableList<Integer> anos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarComboBoxAno();
        cargarComboBoxTiposUnidad();
        configurarMaximoNumeroCaracteres();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        if (object instanceof Unidad) {
            Unidad unidad = (Unidad) object;
            inicializarDatos(unidad);
        }
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_UNIDADES);
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        boolean camposCorrectos = unidadEdicion == null ? verificarCamposNuevo() : verificarCamposEdicion();

        if (camposCorrectos == true) {

            Unidad unidad = new Unidad();
            unidad.setVin(tfVin.getText());
            unidad.setEstatus(Constantes.UNIDAD_ESTATUS_ACTIVA);
            unidad.setMarca(tfMarca.getText());
            unidad.setModelo(tfModelo.getText());
            unidad.setAnio(cbAno.getSelectionModel().getSelectedItem().toString());
            unidad.setIdTipo(cbTipo.getSelectionModel().getSelectedItem().getIdTipoUnidad());

            if (unidadEdicion == null) {
                registrarUnidad(unidad);
            } else {
                editarUnidad(unidad);
            }

        }
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
    }


    public void inicializarDatos(Unidad unidadEdicion) {
        this.unidadEdicion = unidadEdicion;
        if (unidadEdicion != null) {

            lbTitulo.setText("Editar unidad");

            tfVin.setText(unidadEdicion.getVin());
            tfMarca.setText(unidadEdicion.getMarca());
            tfModelo.setText(unidadEdicion.getModelo());

            tfVin.setDisable(true);

            int posicionAno = obtenerPosicionAno(unidadEdicion.getAnio());
            cbAno.getSelectionModel().select(posicionAno);

        }
    }

    private void cargarComboBoxAno() {
        int anoActual = LocalDate.now().getYear();

        ArrayList<Integer> anosTemp = new ArrayList<>();
        for (int ano = anoActual + 3; ano > anoActual - 50; ano--) {
            anosTemp.add(ano);
        }

        anos = FXCollections.observableArrayList();
        anos.addAll(anosTemp);
        cbAno.setItems(anos);
    }

    private int obtenerPosicionAno(String ano) {
        try {
            int numero = Integer.parseInt(ano);
            for (int i = 0; i < anos.size(); i++) {
                if (anos.get(i) == numero) {
                    return i;
                }
            }
        } catch (NumberFormatException ex) {
            return -1;
        }
        return -1;
    }

    private void cargarComboBoxTiposUnidad() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerTiposUnidad();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<TipoUnidad> sucursalAPI = (List<TipoUnidad>) respuesta.get(Constantes.KEY_LISTA);
            tiposDeUnidad = FXCollections.observableArrayList();
            tiposDeUnidad.addAll(sucursalAPI);
            cbTipo.setItems(tiposDeUnidad);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
            nav.navegar(Constantes.MODULO_COLABORADORES);
        }
    }

    private void configurarMaximoNumeroCaracteres() {
        TextInputControl[] tf = {tfVin, tfMarca, tfModelo};
        Integer maxNumCaracteres[] = {17, 255, 255};

        for (int i = 0; i < tf.length; i++) {
            UIUtilidad.limitarCaracteres(tf[i], maxNumCaracteres[i]);
        }
    }

    private boolean verificarCamposNuevo() {
        boolean camposCorrectos = true;
        if (verificarVin() == false) {
            camposCorrectos = false;
        }
        if (verificarMarca() == false) {
            camposCorrectos = false;
        }
        if (verificarModelo() == false) {
            camposCorrectos = false;
        }
        if (verificarAno() == false) {
            camposCorrectos = false;
        }

        if (verificarTipoUnidad() == false) {
            camposCorrectos = false;
        }
        return camposCorrectos;
    }

    private boolean verificarCamposEdicion() {
        boolean camposCorrectos = true;
        if (verificarMarca() == false) {
            camposCorrectos = false;
        }
        if (verificarModelo() == false) {
            camposCorrectos = false;
        }
        if (verificarAno() == false) {
            camposCorrectos = false;
        }
        return camposCorrectos;
    }

    private boolean verificarMarca() {

        if (UIUtilidad.esInputVacio(tfMarca, lbErrorMarca) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfMarca, lbErrorMarca);

        return true;
    }

    private boolean verificarVin() {

        if (UIUtilidad.esInputVacio(tfVin, lbErrorVin) == true) {
            return false;
        }

        if (tfVin.getText().trim().length() != 17) {
            UIUtilidad.marcarError(tfVin, lbErrorVin, "El vin debe tener 7 caracteres");
            return false;
        }

        UIUtilidad.limpiarError(tfVin, lbErrorVin);

        return true;
    }

    private boolean verificarModelo() {
        if (UIUtilidad.esInputVacio(tfModelo, lbErrorModelo) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfModelo, lbErrorModelo);

        return true;
    }

    private boolean verificarAno() {
        Integer anoSeleccionado = cbAno.getSelectionModel().getSelectedItem();
        if (anoSeleccionado == null) {
            UIUtilidad.mostrarLabelMensajeError(lbErrorAno, "Seleccione un año");
            return false;
        }

        UIUtilidad.ocultarLabelMensajeError(lbErrorAno);
        return true;
    }

    private boolean verificarTipoUnidad() {
        TipoUnidad tipoUnidadSeleccionado = cbTipo.getSelectionModel().getSelectedItem();
        if (tipoUnidadSeleccionado == null) {
            UIUtilidad.mostrarLabelMensajeError(lbErrorTipo, "Seleccione un tipo de unidad");
            return false;
        } else {
            UIUtilidad.ocultarLabelMensajeError(lbErrorTipo);
        }

        UIUtilidad.ocultarLabelMensajeError(lbErrorTipo);
        return true;
    }

    private void registrarUnidad(Unidad unidad) {

    }

    private void editarUnidad(Unidad unidad) {

    }
    
}
