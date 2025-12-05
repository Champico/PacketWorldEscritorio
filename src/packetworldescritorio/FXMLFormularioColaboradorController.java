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
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
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
    private TextField tfConfirmarPassword;
    @FXML
    private PasswordField pfConfirmarPassword;
    @FXML
    private ToggleButton tbVerConfirmarPassword;
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
    @FXML
    private ImageView ivVerPassword;
    @FXML
    private ImageView ivVerConfirmarPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBoxRoles();
        cargarRolesProfesor();
        configurarMaximoNumeroCaracteres();
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

    @FXML
    private void clickVerPassword() {
        tbVerPassword.setOnAction(event -> verPassword(tfPassword, pfPassword, ivVerPassword, tbVerPassword));
    }

    @FXML
    private void clickVerConfirmarPassword() {
        tbVerConfirmarPassword.setOnAction(event -> verPassword(tfConfirmarPassword, pfConfirmarPassword, ivVerConfirmarPassword, tbVerConfirmarPassword));
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

    private void configurarMaximoNumeroCaracteres() {
        TextInputControl[] tf = {tfNombre, tfApPaterno, tfApMaterno, tfCurp, tfCorreo, tfNoPersonal, pfPassword, pfConfirmarPassword};
        Integer maxNumCaracteres[] = {254, 254, 254, 18, 254, 50, 25, 25};

        for (int i = 0; i < tf.length; i++) {
            limitarCaracteres(tf[i], maxNumCaracteres[i]);
        }
    }

    private void verPassword(TextField tf, PasswordField pf, ImageView iv, ToggleButton tb) {
        try {
            if (tb.isSelected()) {
                iv.setImage(new Image(
                        getClass().getResource("/images/oculto.png").toExternalForm()
                ));
                tf.setText(pf.getText());
                tf.setVisible(true);
                pf.setVisible(false);
            } else {
                iv.setImage(new Image(
                        getClass().getResource("/images/visible.png").toExternalForm()
                ));
                pf.setText(tfPassword.getText());
                tf.setVisible(false);
                pf.setVisible(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean verificarCampos() {
        if (verificarCamposVacios() && verificarReglasCampos()) {
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
            if (esInputVacio(tfObligatorios[i], labelsError[i])) {
                camposCorrectos = false;
            }
        }

        Rol rolSeleccionado = cbRol.getSelectionModel().getSelectedItem();
        if (rolSeleccionado == null) {
            mostrarMensajeErrorInput(lbErrorRol, "Seleccione un rol");
        } else {
            ocultarMensajeErrorInput(lbErrorRol);
            if (rolSeleccionado.equals(Constantes.ROL_CONDUCTOR) && esInputVacio(tfLicencia, lbErrorLicencia)) {
                camposCorrectos = false;
            }
        }
        return camposCorrectos;
    }

    private boolean esInputVacio(TextInputControl input, Label labelError) {
        String valor = input.getText().trim();
        if (valor.isEmpty()) {
            marcarErrorTextInputControl(input);
            mostrarMensajeErrorInput(labelError, "Campo obligatorio");
            return true;
        }
        limpiarErrorTextInputControl(input);
        ocultarMensajeErrorInput(labelError);
        return false;
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
        System.out.println("Input : "  + input.getId());
        if (input != null) {
            input.getStyleClass().remove("tf_error");
        }
    }

    private boolean verificarReglasCampos() {
        boolean camposCorrectos = true;

        String noPersonal = tfNoPersonal.getText();
        String correo = tfCorreo.getText().trim();
        String curp = tfCurp.getText().trim();
        String nombre = tfNombre.getText().trim();
        String apellidoPaterno = tfApPaterno.getText().trim();
        String apellidoMaterno = tfApPaterno.getText().trim();
        Integer idSucursal = 1;
        Rol rol = cbRol.getSelectionModel().getSelectedItem();
        String licencia = null;
        String password = obtenerTextoPasswordField("password");
        String confirmarPassword = obtenerTextoPasswordField("confirmarPassword");

        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            marcarErrorTextInputControl(tfCorreo);
            mostrarMensajeErrorInput(lbErrorCorreo, "Formato de correo no valido");
            camposCorrectos = false;
        } else {
            limpiarErrorTextInputControl(tfCorreo);
            ocultarMensajeErrorInput(lbErrorCorreo);
        }

        if (curp.length() != 18) {
            marcarErrorTextInputControl(tfCurp);
            mostrarMensajeErrorInput(lbErrorCurp, "Formato de curp no valido");
            camposCorrectos = false;
        } else {
            limpiarErrorTextInputControl(tfCurp);
            ocultarMensajeErrorInput(lbErrorCurp);
        }

        System.out.println("password: " + password);
        System.out.println("Coincide con el patron de contraseña?: " + password.matches("^(?=.*\\p{Lu})(?=.*\\p{Ll})(?=.*\\d).{8,}$"));
        if (!password.matches("^(?=.*\\p{Lu})(?=.*\\p{Ll})(?=.*\\d).{8,}$")) {
            marcarErrorTextInputControl(tfPassword);
            marcarErrorTextInputControl(pfPassword);
            mostrarMensajeErrorInput(lbErrorPassword, "Debe incluir almenos una letra minuscula, una mayuscula y un número");
            camposCorrectos = false;
        } else {
            limpiarErrorTextInputControl(tfPassword);
            limpiarErrorTextInputControl(pfPassword);
            ocultarMensajeErrorInput(lbErrorPassword);
        }

        if (!confirmarPassword.equalsIgnoreCase(password)) {
            marcarErrorTextInputControl(tfConfirmarPassword);
            marcarErrorTextInputControl(pfConfirmarPassword);
            camposCorrectos = false;
            if(lbErrorPassword.isVisible() == false){
                mostrarMensajeErrorInput(lbErrorConfirmarPassword, "La contraseña no coincide");
            }
        } else {
            limpiarErrorTextInputControl(tfConfirmarPassword);
            limpiarErrorTextInputControl(pfConfirmarPassword);
            ocultarMensajeErrorInput(lbErrorConfirmarPassword);
        }

        return camposCorrectos;
    }

    private String obtenerTextoPasswordField(String campo) {
        if (campo == null) {
            return "";
        }

        campo = campo.trim().toLowerCase();

        switch (campo) {
            case "password":
                return (pfPassword.isVisible() ? pfPassword.getText().trim() : tfPassword.getText()).trim();

            case "confirmarpassword":
                return (pfConfirmarPassword.isVisible() ? pfConfirmarPassword.getText().trim() : tfConfirmarPassword.getText()).trim();

            default:
                return "";
        }
    }

    public static void limitarCaracteres(TextInputControl field, int max) {
        field.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().length() <= max ? change : null
        ));
    }
}
