/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import packetworldescritorio.dominio.CatalogoImp;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.pojo.Session;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Utilidades;
import packetworldescritorio.utilidad.UIUtilidad;

public class FXMLFormularioColaboradorController implements Initializable, INavegableChild {

    
    private INavegacion nav;
    
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
    private ComboBox<Sucursal> cbSucursal;
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
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfCorreo;
    @FXML
    private Label lbErrorCurp;
    @FXML
    private Label lbErrorCorreo;
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
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbErrorSucursal;

    private Colaborador colaboradorEdicion = null;
    private ObservableList<Rol> roles;
    private ObservableList<Sucursal> sucursales;
    private byte[] imagenBytes = null;
    private boolean fotoEditada = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBoxRoles();
        configurarComboBoxSucursal();
        cargarRolesProfesor();
        configurarMaximoNumeroCaracteres();
    }

    public void inicializarDatos(Colaborador colaboradorEdicion) {
        this.colaboradorEdicion = colaboradorEdicion;
        if (colaboradorEdicion != null) {

            lbTitulo.setText("Editar colaborador");

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
    private void clickSeleccionarFoto(ActionEvent event) {
        mostrarDialogoSeleccion();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        boolean camposCorrectos = colaboradorEdicion == null ? verificarCamposNuevo() : verificarCamposEdicion();

        if (camposCorrectos == true) {
            Rol rol = cbRol.getSelectionModel().getSelectedItem();
            Sucursal sucursal = cbSucursal.getSelectionModel().getSelectedItem();
            String password = obtenerTextoPasswordField("password");

            Colaborador colaborador = new Colaborador();
            colaborador.setNoPersonal(tfNoPersonal.getText());
            colaborador.setNombre(tfNombre.getText().trim());
            colaborador.setApellidoPaterno(tfApPaterno.getText().trim());
            colaborador.setApellidoMaterno(tfApMaterno.getText().trim());
            colaborador.setCurp(tfCurp.getText().trim());
            colaborador.setCorreo(tfCorreo.getText().trim());
            colaborador.setIdSucursal(sucursal.getIdSucursal());
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
    private void clickCancelar(ActionEvent event) {
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

    private void configurarComboBoxSucursal() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerSucursales();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Sucursal> sucursalAPI = (List<Sucursal>) respuesta.get(Constantes.KEY_LISTA);
            sucursales = FXCollections.observableArrayList();
            sucursales.addAll(sucursalAPI);
            cbSucursal.setItems(sucursales);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
            nav.navegar(Constantes.MODULO_COLABORADORES);
        }
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
                iv.setImage(new Image(getClass().getResource("/images/oculto.png").toExternalForm()));
            } catch (Exception ex) {
            }
        } else {
            pf.setText(tf.getText());
            tf.setVisible(false);
            pf.setVisible(true);
            try {
                iv.setImage(new Image(getClass().getResource("/images/visible.png").toExternalForm()));
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
        if (verificarSucursal() == false) {
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

    private boolean verificarSucursal() {
        Sucursal sucursalSeleccionada = cbSucursal.getSelectionModel().getSelectedItem();
        if (sucursalSeleccionada == null) {
            UIUtilidad.mostrarLabelMensajeError(lbErrorSucursal, "Seleccione una sucursal");
            return false;
        } else {
            UIUtilidad.ocultarLabelMensajeError(lbErrorSucursal);
        }

        UIUtilidad.ocultarLabelMensajeError(lbErrorRol);
        return true;
    }

    private boolean verificarPassword() {
        String password = obtenerTextoPasswordField("password");
        String confirmarPassword = obtenerTextoPasswordField("confirmarPassword");

        if (password == null || password.isEmpty()) {
            marcarErrorCampoPassword("password", "Campo obligatorio");
            limpiarErrorCampoPassword("confirmarPassword");
            return false;
        }

        if (password.length() < 8) {
            marcarErrorCampoPassword(password, "La contraseña debe ser minimo de 8 caracteres");
            limpiarErrorCampoPassword("confirmarPassword");
            return false;
        }

        if (!password.matches("^(?=.*\\p{Lu})(?=.*\\p{Ll})(?=.*\\d).{8,}$")) {
            marcarErrorCampoPassword("password", "Debe incluir almenos una letra minuscula, una mayuscula y un número");
            limpiarErrorCampoPassword("confirmarPassword");
            return false;
        }

        limpiarErrorCampoPassword("password"); //La contraseña paso los filtros, esta bien

        if (!confirmarPassword.equals(password)) {
            marcarErrorCampoPassword("confirmarPassword", "La contraseña no coincide");
            return false;
        }

        limpiarErrorCampoPassword("confirmarPassword"); //Coincide la confirmación
        return true;
    }

    private void marcarErrorCampoPassword(String campo, String mensaje) {
        if (campo == null) {
            return;
        }
        campo = campo.trim().toLowerCase();
        switch (campo) {
            case "password":
                UIUtilidad.marcarErrorTextInputControl(tfPassword);
                UIUtilidad.marcarErrorTextInputControl(pfPassword);
                UIUtilidad.mostrarLabelMensajeError(lbErrorPassword, mensaje);
                break;
            case "confirmarpassword":
                UIUtilidad.marcarErrorTextInputControl(tfConfirmarPassword);
                UIUtilidad.marcarErrorTextInputControl(pfConfirmarPassword);
                UIUtilidad.mostrarLabelMensajeError(lbErrorConfirmarPassword, mensaje);
                break;
            default:
        }
    }

    private void limpiarErrorCampoPassword(String campo) {
        if (campo == null) {
            return;
        }
        campo = campo.trim().toLowerCase();
        switch (campo) {
            case "password":
                UIUtilidad.limpiarErrorTextInputControl(tfPassword);
                UIUtilidad.limpiarErrorTextInputControl(pfPassword);
                UIUtilidad.ocultarLabelMensajeError(lbErrorPassword);
                break;
            case "confirmarpassword":
                UIUtilidad.limpiarErrorTextInputControl(tfConfirmarPassword);
                UIUtilidad.limpiarErrorTextInputControl(pfConfirmarPassword);
                UIUtilidad.ocultarLabelMensajeError(lbErrorConfirmarPassword);
                break;
            default:
        }
    }

    private void registrarColaborador(Colaborador colaborador) {
        Respuesta respuesta = ColaboradorImp.registrar(colaborador);
        if (!respuesta.isError()) {
            if (fotoEditada == true) {
                Integer idColaborador = obtenerIdColaboradorPorNumeroPersonal(colaborador.getNoPersonal());
                if (idColaborador != null) {
                    Respuesta respuestaFoto = enviarFoto(idColaborador);
                    Utilidades.mostrarAlertaSimple("Colaborador registrado", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                    if (respuestaFoto.isError()) {
                         Utilidades.mostrarAlertaSimple("No se pudo guardar la foto", "Ocurrio un error al guardar la foto, intente subirla más tarde", Alert.AlertType.ERROR);
                    }
                } else {
                    Utilidades.mostrarAlertaSimple("No se pudo guardar la foto", "Ocurrio un error al guardar la foto, intente subirla más tarde", Alert.AlertType.ERROR);
                }
            } else {
                Utilidades.mostrarAlertaSimple("Colaborador registrado", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            }
            regresar();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarColaborador(Colaborador colaborador) {
        colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador());

        boolean textoEditado = !colaborador.equalsForm(colaboradorEdicion);

        Respuesta respuestaEditar = null;
        Respuesta respuestaSubirFoto = null;

        if (textoEditado) {
            respuestaEditar = ColaboradorImp.editar(colaborador);

            if (respuestaEditar.isError()) {
                Utilidades.mostrarAlertaSimple("Error al editar", respuestaEditar.getMensaje(), Alert.AlertType.ERROR);
                return;
            }
        }

        if (fotoEditada) {
            respuestaSubirFoto = enviarFoto(colaborador.getIdColaborador());

            if (respuestaSubirFoto.isError()) {
                if (textoEditado) {
                    Utilidades.mostrarAlertaSimple("Datos del colaborador editados", respuestaEditar.getMensaje(), Alert.AlertType.INFORMATION);
                    Utilidades.mostrarAlertaSimple("No se pudo guardar la foto", respuestaSubirFoto.getMensaje(), Alert.AlertType.ERROR);
                } else {
                    Utilidades.mostrarAlertaSimple("No se pudo guardar la foto", respuestaSubirFoto.getMensaje(), Alert.AlertType.ERROR);
                }
                return;
            } else {
                if (textoEditado) {
                    Utilidades.mostrarAlertaSimple("Colaborador editado", respuestaEditar.getMensaje(), Alert.AlertType.INFORMATION);
                } else {
                    Utilidades.mostrarAlertaSimple("Se ha guardado la nueva imagen", respuestaSubirFoto.getMensaje(), Alert.AlertType.INFORMATION);
                }
            }
        }

        if (!textoEditado && !fotoEditada) {
            Utilidades.mostrarAlertaSimple("No se editó ningún campo", "No se realizó ninguna modificación", Alert.AlertType.INFORMATION);
        }

        regresar();
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

    private int obtenerPosicionSucursal(int idSucursal) {
        for (int i = 0; i < sucursales.size(); i++) {
            if (sucursales.get(i).getIdSucursal() == idSucursal) {
                return i;
            }
        }
        return -1;
    }

    private void mostrarDialogoSeleccion() {
        FileChooser dialogo = new FileChooser();
        dialogo.setTitle("Selecciona una foto");
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de imagen (.png, .jpg, .jpeg) ", "*.jpg", "*.png", "*.jpeg");
        dialogo.getExtensionFilters().add(filtroImg);
        File foto = dialogo.showOpenDialog(ivFoto.getParent().getScene().getWindow());

        if (foto != null) {
            if (Utilidades.verificarTamañoMaximoArchivo(foto, Constantes.MAX_SIZE_MB_IMAGE)) {
                try {
                    imagenBytes = Utilidades.fileImageToBytes(foto);
                    mostrarFoto(imagenBytes);
                    fotoEditada = true;
                } catch (Exception ex) {
                    Utilidades.mostrarAlertaSimple("Error al cargar la imagen", ex.getMessage(), Alert.AlertType.ERROR);
                    fotoEditada = false;
                }
            } else {
                Utilidades.mostrarAlertaSimple("Error al cargar la imagen", "Tamaño máximo de imagen: " + Constantes.MAX_SIZE_MB_IMAGE + " MB", Alert.AlertType.ERROR);

            }

        }
    }

    private void mostrarFoto(byte[] foto) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(foto);
            Image imagen = new Image(bis);
            ivFoto.setImage(imagen);
        } catch (Exception ex) {
        }
    }

    private void cargarFoto() {
        imagenBytes = ColaboradorImp.obtenerFotoBytes(colaboradorEdicion.getIdColaborador());
        if (imagenBytes != null) {
            mostrarFoto(imagenBytes);
        }
    }

    private Respuesta enviarFoto(int idColaborador) {
        return ColaboradorImp.subirFoto(imagenBytes, idColaborador);
    }

    private Integer obtenerIdColaboradorPorNumeroPersonal(String noPersonal) {
        Integer idColaborador = null;
        HashMap<String, Object> respuesta = ColaboradorImp.buscarPorNumPersonal(noPersonal);
        try {
            if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
                Colaborador colaborador = (Colaborador) respuesta.get(Constantes.KEY_COLABORADOR);
                if (colaborador != null) {
                    idColaborador = colaborador.getIdColaborador();
                }
            }
        } catch (Exception ex) {
            return null;
        }

        return idColaborador;
    }

}
