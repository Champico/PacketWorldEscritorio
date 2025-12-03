/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import packetworldescritorio.dominio.CatalogoImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLFormularioColaboradorController implements Initializable, INavegableChild {

    @FXML
    private TextField tfNoPersonal;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApPaterno;
    @FXML
    private TextField tfApMaterno;
    @FXML
    private ComboBox<Rol> cbRol;
    @FXML
    private TextField tfLicencia;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfPassword;
    @FXML
    private ToggleButton tbVerPassword;
    @FXML
    private ImageView ivVisible;
    @FXML
    private TextField tfConfirmarPassword;
    @FXML
    private PasswordField pfConfirmarPassword;
    @FXML
    private ToggleButton tbVerConfirmarPassword;
    @FXML
    private ImageView ivVisible1;
    @FXML
    private Label lbErrorNoPersonal;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorApPaterno;
    @FXML
    private Label lbErrorApMaterno;
    @FXML
    private Label lbErrorRol;
    @FXML
    private Label lbErrorLicencia;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private Label lbErrorConfirmarPassword;

    private INavegacion nav;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfCorreo;
    @FXML
    private Label lbErrorCurp;
    @FXML
    private Label lbErrorCorreo;

    private ObservableList<Rol> roles;

    @FXML
    private Label lbLicencia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBoxRoles();
        cargarRolesProfesor();

    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        nav.navegar(Constantes.MODULO_COLABORADORES);
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        if (verificarCampos()) {
            System.out.println("Exito");
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }

    private void configurarComboBoxRoles() {
        cargarRolesProfesor();
        agregarEventoCambioRol();
    }

    private void cargarRolesProfesor() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerRolesSistema();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Rol> rolesAPI = (List<Rol>) respuesta.get(Constantes.KEY_LISTA);
            roles = FXCollections.observableArrayList();
            roles.addAll(rolesAPI);
            cbRol.setItems(roles);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
            nav.navegar(Constantes.MODULO_COLABORADORES);
        }
    }

    private void agregarEventoCambioRol() {
        cbRol.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                if (newValue.getRol().equals(Constantes.ROL_CONDUCTOR)) {
                    lbLicencia.setVisible(true);
                    tfLicencia.setVisible(true);
                } else {
                    lbLicencia.setVisible(false);
                    tfLicencia.setVisible(false);
                }
            }
        });

    }

    private boolean verificarCampos() {
        if (verificarCamposVacios()) {
            System.out.println("Exito");
            return true;
        }
        return false;
    }

    private boolean verificarCamposVacios() {
        boolean camposCorrectos = true;

        TextInputControl[] tfObligatorios = {tfNombre, tfApPaterno, tfCurp, tfCorreo, tfNoPersonal, pfPassword, pfConfirmarPassword};
        Label[] labelsError = {lbErrorNombre, lbErrorApPaterno, lbErrorCurp, lbErrorCorreo, lbErrorNoPersonal, lbErrorPassword, lbErrorConfirmarPassword};

        for (int i = 0; i < tfObligatorios.length; i++) {
            if (!verificarInputVacio(tfObligatorios[i], labelsError[i])) {
                camposCorrectos = false;
            }
        }

        Rol rolSeleccionado = cbRol.getSelectionModel().getSelectedItem();
        if (rolSeleccionado == null) {
            mostrarMensajeErrorInput(lbErrorRol, "Seleccione un rol");
        } else {
            if (rolSeleccionado.equals(Constantes.ROL_CONDUCTOR) && !verificarInputVacio(tfLicencia, lbErrorLicencia)) {
                camposCorrectos = false;
            }
        }
        return camposCorrectos;
    }

    private boolean verificarInputVacio(TextInputControl input, Label labelError) {
        String valor = input.getText();
        if (valor.isEmpty()) {
            marcarErrorTextInputControl(input);
            mostrarMensajeErrorInput(labelError, "Campo obligatorio");
            return false;
        }
        limpiarErrorTextInputControl(tfNombre);
        ocultarMensajeErrorInput(lbErrorNombre);
        return true;
    }

    private void mostrarMensajeErrorInput(Label label, String mensaje) {
        if (label != null && mensaje != null) {
            label.setText(mensaje);
            label.setVisible(true);
        }
    }

    private void ocultarMensajeErrorInput(Label label) {
        if (label != null) {
            label.setText("");
            label.setVisible(false);
        }
    }

    private void marcarErrorTextInputControl(TextInputControl input) {
        if (input != null) {
            input.getStyleClass().add("tf_error");
        }
    }

    private void limpiarErrorTextInputControl(TextInputControl input) {
        if (input != null) {
            input.getStyleClass().remove("tf_error");
        }
    }

}
