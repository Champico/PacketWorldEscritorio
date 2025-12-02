/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.utilidad.Constantes;

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
    private ComboBox<?> cbRol;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        if(verificarCamposVacios()){
            System.out.println("Exito");
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }

    
    
    
    

    
    
    
    
    
    
    
    
    
    private boolean verificarCamposVacios() {
        boolean camposCorrectos = true;

        TextInputControl[] tfObligatorios = {tfNombre, tfApPaterno, tfCurp, tfCorreo, tfNoPersonal, pfPassword, pfConfirmarPassword};
        Label[] labelsError = {lbErrorNombre, lbErrorApPaterno, lbErrorCurp, lbErrorCorreo, lbErrorNoPersonal, lbErrorPassword, lbErrorConfirmarPassword };

        for(int i = 0; i < tfObligatorios.length; i++){
            verificarInputVacio(tfObligatorios[i], labelsError[i]);
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
