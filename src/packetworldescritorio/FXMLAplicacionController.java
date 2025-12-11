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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Session;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLAplicacionController implements Initializable, INavegacion {

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbRol;

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarInformacion(Colaborador colaborador) {
       Session.getInstance().setUsuarioActual(colaborador);
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

    @FXML
    private void clickCerrarSesion(ActionEvent event) {
        Boolean confirmarCerrarSesion = Utilidades.mostrarAlertaConfirmacion("Cerrar Sesión", "¿Estás seguro que quieres cerrar sesión?");
        if (confirmarCerrarSesion) {
            Session.getInstance().limpiarSesion();
            irPantallaInicioSesion();
        }
    }

    private void irPantallaInicioSesion() {
        try {
            /* Cargar el FXML*/
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(Constantes.PG_INICIO_SESION));
            Parent vista = cargador.load();
            Scene escenaLogin = new Scene(vista);

            /*Cerrar ventana principal*/
            Stage stPrincipal = (Stage) lbNombre.getScene().getWindow();
            stPrincipal.close();

            /*Abrir ventana principal*/
            Stage stLogin = new Stage();
            stLogin.setScene(escenaLogin);
            stLogin.initStyle(StageStyle.UNDECORATED);
            stLogin.setResizable(false);
            stLogin.setWidth(800);
            stLogin.setHeight(500);

            /* Mostrar */
            stLogin.show();

        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error inesperado", Alert.AlertType.ERROR);
            ex.printStackTrace();
            Platform.exit();
        }
    }

}
