/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModalAsignarConductorController implements Initializable {

    @FXML
    private TextField tfConductor;
    @FXML
    private Label lbMarca;
    @FXML
    private Label lbModelo;
    @FXML
    private Label lbVIN;
    @FXML
    private Label lbConductor;
    @FXML
    private Label lbNII;

    private Unidad unidadEdicion = null;

    private Colaborador conductorEdicion = null;

    public void cargarInformacion(Unidad unidad) {
        this.unidadEdicion = unidad;

        if (unidadEdicion != null) {
            lbNII.setText(unidadEdicion.getNumIdInterno());
            lbMarca.setText(unidadEdicion.getMarca());
            lbModelo.setText(unidadEdicion.getModelo());
            lbVIN.setText(unidadEdicion.getVin());
            lbConductor.setText(unidadEdicion.getIdColaborador() == null ? "ninguno" : unidadEdicion.getNombreColaborador());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {

    }

    private void cerrar() {
        Stage stage = (Stage) lbNII.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {

    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrar();
    }

    @FXML
    private void clickBuscarConductor(ActionEvent event) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(Constantes.MODAL_SELECCIONAR_CONDUCTOR));
            Parent vista = cargador.load();
            FXMLModalSeleccionarConductorController controlador = cargador.getController();
            Stage context = (Stage) tfConductor.getScene().getWindow();
            Scene escenaPrincipal = new Scene(vista);

            Stage stModal = new Stage();
            stModal.setScene(escenaPrincipal);
            stModal.setWidth(640);
            stModal.setHeight(500);
            stModal.setResizable(false);
            stModal.setTitle("Seleccionar conductor");
            stModal.initOwner(context);
            stModal.initModality(Modality.WINDOW_MODAL);
            stModal.initStyle(StageStyle.UTILITY);

            try {
                stModal.getIcons().add(new Image(getClass().getResourceAsStream("/images/lupa.png")));
            } catch (Exception ex) {
            }

            stModal.showAndWait();

            conductorEdicion = controlador.getColaboradorSeleccionado();

            if (conductorEdicion != null) {
                tfConductor.setText(
                        (conductorEdicion.getNombre() != null ? conductorEdicion.getNombre() + " " : "")
                        + (conductorEdicion.getApellidoPaterno() != null ? conductorEdicion.getApellidoPaterno() + " " : "")
                        + (conductorEdicion.getApellidoMaterno() != null ? conductorEdicion.getApellidoMaterno() : "")
                );
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar la ventana de buscar colaborador", Alert.AlertType.ERROR);
        }

    }

}
