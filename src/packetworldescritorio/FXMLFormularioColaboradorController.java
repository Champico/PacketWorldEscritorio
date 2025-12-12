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
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.pojo.Session;
import packetworldescritorio.utilidad.Utilidades;
import packetworldescritorio.utilidad.UIUtilidad;

public class FXMLFormularioColaboradorController implements Initializable, INavegableChild {

    Colaborador colaboradorEdicion = null;

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
    @FXML
    private Label lbPassword;
    @FXML
    private Label lbConfirmarPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBoxRoles();
        cargarRolesProfesor();
        configurarMaximoNumeroCaracteres();
    }

    public void inicializarDatos(Colaborador colaboradorEdicion) {
        this.colaboradorEdicion = colaboradorEdicion;
        if (colaboradorEdicion != null) {
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

            if (colaboradorEdicion.getIdRol() == Constantes.ID_ROL_CONDUCTOR) {
                tfLicencia.setText(colaboradorEdicion.getNumLicencia());
            }

            ocultarCamposPassword();
        }
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        if (object instanceof Colaborador) {
            Colaborador colaborador = (Colaborador) object;
            inicializarDatos(colaborador);
        }
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_COLABORADORES);
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        boolean camposCorrectos = colaboradorEdicion == null ? verificarCamposNuevo() : verificarCamposEdicion();

        if (camposCorrectos == true) {
            int idSucursal = Session.getInstance().getUsuarioActual().getIdSucursal();
            Rol rol = cbRol.getSelectionModel().getSelectedItem();
            String password = obtenerTextoPasswordField("password");

            Colaborador colaborador = new Colaborador();
            colaborador.setNoPersonal(tfNoPersonal.getText());
            colaborador.setNombre(tfNombre.getText().trim());
            colaborador.setApellidoPaterno(tfApPaterno.getText().trim());
            colaborador.setApellidoMaterno(tfApMaterno.getText().trim());
            colaborador.setCurp(tfCurp.getText().trim());
            colaborador.setCorreo(tfCorreo.getText().trim());
            colaborador.setIdSucursal(idSucursal);
            colaborador.setIdRol(rol.getIdRol());
            colaborador.setPassword(password);

            if (colaboradorEdicion == null) {
                registrarColaborador(colaborador);
            } else {
                editarColaborador(colaborador);
            }

        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        regresar();
    }

    @FXML
    private void clickVerPassword(ActionEvent event) {
        ToggleButton tb = (ToggleButton) event.getSource();

        if (tb == tbVerPassword) {
            alternarPassword(tfPassword, pfPassword, ivVerPassword, tbVerPassword);
        } else if (tb == tbVerConfirmarPassword) {
            alternarPassword(tfConfirmarPassword, pfConfirmarPassword, ivVerConfirmarPassword, tbVerConfirmarPassword);
        }
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
            UIUtilidad.limitarCaracteres(tf[i], maxNumCaracteres[i]);
        }
    }

    private void alternarPassword(TextField tf, PasswordField pf, ImageView iv, ToggleButton tb) {
        tb.setDisable(true);
        if (tb.isSelected()) {
            tf.setText(pf.getText());
            tf.setVisible(true);
            pf.setVisible(false);
            try {
                iv.setImage(new Image(getClass().getResource("/images/visible.png").toExternalForm()));
            } catch (Exception ex) {
            }
        } else {
            pf.setText(tf.getText());
            tf.setVisible(false);
            pf.setVisible(true);
            try {
                iv.setImage(new Image(getClass().getResource("/images/oculto.png").toExternalForm()));
            } catch (Exception ex) {
            }
        }
        tb.setDisable(false);
    }

    private boolean verificarCamposNuevo() {
        boolean camposCorrectos = true;
        if (verificarNoPersonal() == false) {
            camposCorrectos = false;
        }

        if (verificarNombre() == false) {
            camposCorrectos = false;
        }
        if (verificarApellidoPaterno() == false) {
            camposCorrectos = false;
        }
        if (verificarCurp() == false) {
            camposCorrectos = false;
        }
        if (verificarCorreo() == false) {
            camposCorrectos = false;
        }
        if (verificarPassword() == false) {
            camposCorrectos = false;
        }
        if (verificarRol() == false) {
            camposCorrectos = false;
        }

        return camposCorrectos;
    }

    private boolean verificarCamposEdicion() {
        boolean camposCorrectos = true;
        if (verificarNombre() == false) {
            camposCorrectos = false;
        }
        if (verificarApellidoPaterno() == false) {
            camposCorrectos = false;
        }
        if (verificarCurp() == false) {
            camposCorrectos = false;
        }
        if (verificarCorreo() == false) {
            camposCorrectos = false;
        }
        return camposCorrectos;
    }

    private boolean verificarNoPersonal() {

        if (UIUtilidad.esInputVacio(tfNoPersonal, lbErrorNoPersonal) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfNoPersonal, lbErrorNoPersonal);

        return true;
    }

    private boolean verificarNombre() {

        if (UIUtilidad.esInputVacio(tfNombre, lbErrorNombre) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfNombre, lbErrorNombre);

        return true;
    }

    private boolean verificarApellidoPaterno() {

        if (UIUtilidad.esInputVacio(tfApPaterno, lbErrorApPaterno) == true) {
            return false;
        }

        UIUtilidad.limpiarError(tfApPaterno, lbErrorApPaterno);

        return true;
    }

    private boolean verificarCurp() {

        if (UIUtilidad.esInputVacio(tfCurp, lbErrorCurp) == true) {
            return false;
        }

        if (tfCurp.getText().trim().length() != 18) {
            UIUtilidad.marcarError(tfCurp, lbErrorCurp, "El curp debe tener 18 caracteres");
            return false;
        }

        UIUtilidad.limpiarError(tfCurp, lbErrorCurp);

        return true;
    }

    private boolean verificarCorreo() {
        if (UIUtilidad.esInputVacio(tfCorreo, lbErrorCorreo) == true) {
            return false;
        }

        if (tfCorreo.getText().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") == false) {
            UIUtilidad.marcarError(tfCorreo, lbErrorCorreo, "Formato de correo no valido");
            return false;
        }

        UIUtilidad.limpiarError(tfCorreo, lbErrorCorreo);

        return true;
    }

    private boolean verificarRol() {
        Rol rolSeleccionado = cbRol.getSelectionModel().getSelectedItem();
        if (rolSeleccionado == null) {
            UIUtilidad.mostrarLabelMensajeError(lbErrorRol, "Seleccione un rol");
            return false;
        } else {
            UIUtilidad.ocultarLabelMensajeError(lbErrorRol);
            if (rolSeleccionado.toString().equals(Constantes.ROL_CONDUCTOR) && UIUtilidad.esInputVacio(tfLicencia, lbErrorLicencia) == true) {
                return false;
            }
        }

        UIUtilidad.ocultarLabelMensajeError(lbErrorRol);
        return true;
    }

    private boolean verificarPassword() {
        String password = obtenerTextoPasswordField("password");
        String confirmarPassword = obtenerTextoPasswordField("confirmarPassword");

        if (!password.matches("^(?=.*\\p{Lu})(?=.*\\p{Ll})(?=.*\\d).{8,}$")) {
            UIUtilidad.marcarErrorTextInputControl(tfPassword);
            UIUtilidad.marcarErrorTextInputControl(pfPassword);
            UIUtilidad.mostrarLabelMensajeError(lbErrorPassword, "Debe incluir almenos una letra minuscula, una mayuscula y un número");

            UIUtilidad.limpiarErrorTextInputControl(tfConfirmarPassword);
            UIUtilidad.limpiarErrorTextInputControl(pfConfirmarPassword);
            UIUtilidad.ocultarLabelMensajeError(lbErrorConfirmarPassword);

            return false;
        } else {
            UIUtilidad.limpiarErrorTextInputControl(tfPassword);
            UIUtilidad.limpiarErrorTextInputControl(pfPassword);
            UIUtilidad.ocultarLabelMensajeError(lbErrorPassword);

            if (!confirmarPassword.equals(password)) {
                UIUtilidad.marcarErrorTextInputControl(tfConfirmarPassword);
                UIUtilidad.marcarErrorTextInputControl(pfConfirmarPassword);
                UIUtilidad.mostrarLabelMensajeError(lbErrorConfirmarPassword, "La contraseña no coincide");
                return false;
            } else {
                UIUtilidad.limpiarErrorTextInputControl(tfConfirmarPassword);
                UIUtilidad.limpiarErrorTextInputControl(pfConfirmarPassword);
                UIUtilidad.ocultarLabelMensajeError(lbErrorConfirmarPassword);
                return true;
            }
        }
    }

    private void registrarColaborador(Colaborador colaborador) {
        Respuesta respuesta = ColaboradorImp.registrar(colaborador);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Colaborador registrado", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            regresar();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarColaborador(Colaborador colaborador) {
        colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador());
        Respuesta respuesta = ColaboradorImp.editar(colaborador);

        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Colaborador editado", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            regresar();
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }

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

    private void ocultarCamposPassword() {
        lbPassword.setVisible(false);
        tfPassword.setVisible(false);
        pfPassword.setVisible(false);
        tbVerPassword.setVisible(false);

        lbConfirmarPassword.setVisible(false);
        tfConfirmarPassword.setVisible(false);
        pfConfirmarPassword.setVisible(false);
        tbVerConfirmarPassword.setVisible(false);
    }

    
    private int obtenerPosicionRol(int idRol) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getIdRol() == idRol) {
                return i;
            }
        }
        return -1;
    }

    
}
