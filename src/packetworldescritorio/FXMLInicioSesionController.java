/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.dominio.InicioSesionImp;
import packetworldescritorio.dto.RSAutenticacionColaborador;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private ImageView ivVisible;
    @FXML
    private ToggleButton tbVerPassword;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfNoPersonal;
    @FXML
    private Label lbErrorNoPersonal;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private Label lbErrorCredenciales;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btn_iniciarSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickIniciarSesion(ActionEvent event) {
        
        btn_iniciarSesion.setDisable(true);
        if (!hayCamposVacios()) { 
            String noPersonal = tfNoPersonal.getText().trim();
            String password =  obtenerPassword();
            verificarCredenciales(noPersonal, password);
        } else {
            btn_iniciarSesion.setDisable(false);
        }
    }

    @FXML
    private void clickCerrar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void clickVerPassword(ActionEvent event) {
        tbVerPassword.setDisable(true);
        try {
            if (tbVerPassword.isSelected()) {
                ivVisible.setImage(new Image(getClass().getResource("/images/oculto.png").toExternalForm()));
                tfPassword.setText(pfPassword.getText());
                tfPassword.setVisible(true);
                pfPassword.setVisible(false);
            } else {
                ivVisible.setImage(new Image(getClass().getResource("/images/visible.png").toExternalForm()));
                pfPassword.setText(tfPassword.getText());
                tfPassword.setVisible(false);
                pfPassword.setVisible(true);
            }
        } catch (Exception ex) {
        }
        tbVerPassword.setDisable(false);
    }

    private void verificarCredenciales(String noPersonal, String password) {
        RSAutenticacionColaborador respuesta = InicioSesionImp.verificarCredenciales(noPersonal, password);
        if (!respuesta.isError()) {
            irPantallaInicio(respuesta.getColaborador());
        } else {
            lbErrorCredenciales.setText(respuesta.getMensaje());
            lbErrorCredenciales.setVisible(true);
            btn_iniciarSesion.setDisable(false);
        }
    }

    private boolean hayCamposVacios() {
        boolean hayCamposVacios = false;
        String noPersonal = tfNoPersonal.getText();
        String password = obtenerPassword();

        if (noPersonal.isEmpty()) {
            hayCamposVacios = true;
            marcarErrorTextInputControl(tfNoPersonal, lbErrorNoPersonal);
        } else {
            limpiarErrorTextInputControl(tfNoPersonal, lbErrorNoPersonal);
        }

        if (password.isEmpty()) {
            hayCamposVacios = true;
            marcarErrorTextInputControl(pfPassword, lbErrorPassword);
            marcarErrorTextInputControl(tfPassword, lbErrorPassword);
        } else {
            limpiarErrorTextInputControl(pfPassword, lbErrorPassword);
            limpiarErrorTextInputControl(tfPassword, lbErrorPassword);
        }

        if (lbErrorCredenciales.isVisible()) {
            lbErrorCredenciales.setText("");
            lbErrorCredenciales.setVisible(false);
        }

        return hayCamposVacios;
    }

    private void marcarErrorTextInputControl(TextInputControl field, Label label) {
        if (field != null) {
            field.getStyleClass().add("tf_error");
        }

        if (label != null) {
            label.setVisible(true);
        }
    }

    private void limpiarErrorTextInputControl(TextInputControl field, Label label) {
        if (field != null) {
            field.getStyleClass().remove("tf_error");
        }

        if (label != null) {
            label.setVisible(false);
        }

    }

    private void irPantallaInicio(Colaborador colaborador) {
        try {
            /* Cargar el FXML*/
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(Constantes.PG_APLICACION));
            Parent vista = cargador.load();
            FXMLAplicacionController controlador = cargador.getController();
            controlador.cargarInformacion(colaborador);
            Scene escenaPrincipal = new Scene(vista);

            /*Cerrar ventana de login*/
            Stage stLogin = (Stage) tfNoPersonal.getScene().getWindow();
            stLogin.close();

            /*Abrir ventana principal*/
            Stage stPrincipal = new Stage();
            stPrincipal.setScene(escenaPrincipal);
            stPrincipal.initStyle(StageStyle.DECORATED);
            stPrincipal.setMinWidth(1000);
            stPrincipal.setMinHeight(600);
            stPrincipal.setTitle("Packet World Admin");

            /* Poner icono */
            try {
                stPrincipal.getIcons().add(new Image(getClass().getResourceAsStream("/images/isotipo_low_definition.png")));
            } catch (Exception ex) {
            }

            stPrincipal.show();

        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar el sistema ", Alert.AlertType.ERROR);
            Platform.exit();
        }
    }
    
    
    private String obtenerPassword(){
         return pfPassword.isVisible() ? pfPassword.getText().trim() : tfPassword.getText().trim();
    }

}
