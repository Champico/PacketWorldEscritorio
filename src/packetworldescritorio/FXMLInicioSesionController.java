/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickIniciarSesion(ActionEvent event) {
        if (!hayCamposVacios()) {
            String noPersonal = tfNoPersonal.getText().trim();
            String password = pfPassword.getText().trim();

            verificarCredenciales(noPersonal, password);
        }
    }

    @FXML
    private void clickCerrar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void clickVerPassword(ActionEvent event) {
        try {
            if (tbVerPassword.isSelected()) {
                ivVisible.setImage(new Image(
                        getClass().getResource("/resources/images/oculto.png").toExternalForm()
                ));
                tfPassword.setText(pfPassword.getText());
                tfPassword.setVisible(true);
                pfPassword.setVisible(false);
            } else {
                ivVisible.setImage(new Image(
                        getClass().getResource("/resources/images/visible.png").toExternalForm()
                ));
                pfPassword.setText(tfPassword.getText());
                tfPassword.setVisible(false);
                pfPassword.setVisible(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void verificarCredenciales(String noPersonal, String password) {
        /*RSAutenticacionAdmin respuesta = InicioSesionImp.verificarCredenciales(noPersonal, password);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Credenciales verificadas", "Bienvenido(a) profesor(a) " + respuesta.getProfesor().getNombre() + " al sistemas", Alert.AlertType.INFORMATION);
            irPantallaInicio(respuesta.getProfesor());
        } else {
            Utilidades.mostrarAlertaSimple("Credenciales incorrectas", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }*/
        System.out.println("Verificando credenciales...");
    }

    private boolean hayCamposVacios() {
        boolean hayCamposVacios = false;
        String noPersonal = tfNoPersonal.getText();
        String password = pfPassword.getText();

        if (noPersonal.isEmpty()) {
            hayCamposVacios = true;
            marcarErrorTextInputControl(tfNoPersonal, lbErrorNoPersonal);
        } else {
            limpiarErrorTextInputControl(tfNoPersonal, lbErrorNoPersonal);
        }

        if (password.isEmpty()) {
            hayCamposVacios = true;
            marcarErrorTextInputControl(pfPassword, lbErrorPassword);
        } else {
            limpiarErrorTextInputControl(pfPassword, lbErrorPassword);
        }

        return hayCamposVacios;
    }

    private void marcarErrorTextInputControl(TextInputControl field, Label label) {
        if (field != null) {
            field.setStyle(field.getStyle() + "-fx-border-color: #ff3434; -fx-border-width: 1.5px; -fx-border-radius: 4px; -fx-focus-color:#ff3434; -fx-faint-focus-color: #ff52524e");
        }

        if (label != null) {
            label.setVisible(true);
        }
    }

    private void limpiarErrorTextInputControl(TextInputControl field, Label label) {
        if (field != null) {
            String estilo = field.getStyle();
            String[] estilos = {"-fx-border-color[^;]*;", "-fx-border-width[^;]*;", "-fx-border-radius[^;]*;", "-fx-focus-color[^;]*;", "-fx-faint-focus-color[^;]*;"};
            for (String estiloAQuitar : estilos) {
                estilo = estilo.replaceAll(estiloAQuitar, "");
            }
            field.setStyle(estilo);

        }

        if (label != null) {
            label.setVisible(false);
        }

    }

}
