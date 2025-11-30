/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;

public class FXMLAplicacionController implements Initializable, INavegacion {

    private Colaborador colaboradorSesion;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbRol;

    @FXML
    private Label lbNumPersonal;

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarInformacion(Colaborador colaborador) {
        colaboradorSesion = colaborador;
        lbNombre.setText(colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno());
        lbRol.setText("Rol: " + colaborador.getRol());
        showScreen("FXMLPrincipal.fxml");
    }

    public void showScreen(String fxml) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(fxml));
            Node pantalla = cargador.load();

            Object controlador = cargador.getController();

            if (controlador instanceof INavegableChild) {
                INavegableChild child = (INavegableChild) controlador;
                child.setNavegador(this);
            }

            contentArea.getChildren().setAll(pantalla);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void navegar(String fxml) {
        showScreen(fxml);
    }

}
