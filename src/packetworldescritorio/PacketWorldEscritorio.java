/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.utilidad.Utilidades;

public class PacketWorldEscritorio extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));
            Scene escenaLogin = new Scene(vista);
            primaryStage.setScene(escenaLogin);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);

            try {
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/isotipo_low_definition.png")));
            } catch (Exception ex) {
            }

            primaryStage.show();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error al iniciar", "Ocurrio un error al abrir la aplicación", Alert.AlertType.ERROR);
            ex.printStackTrace();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
