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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.dominio.EnvioImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Envio;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModalAsignarEnvioController implements Initializable {

    @FXML
    private HBox hbMenu;
    @FXML
    private AnchorPane apEnvio;
    @FXML
    private Label lbDestinatarioColonia;
    @FXML
    private Label lbDestinatarioDireccion;
    @FXML
    private Label lbDestinatarioNombre;
    @FXML
    private Label lbGuia;
    @FXML
    private Label lbClienteNombre;
    @FXML
    private Label lbDestinatarioCodigoPostal;
    @FXML
    private Label lbConductorActual;
    @FXML
    private Label lbDestinatarioCiudad;
    @FXML
    private Label lbDestinatarioEstado;
    @FXML
    private TextField tfBuscarGuia;
    @FXML
    private TextField tfConductor;
    @FXML
    private AnchorPane apConductor;
    @FXML
    private Label lbConductorSucursal;
    @FXML
    private Label lbConductorNombre;
    @FXML
    private Label lbConductorNoPersonal;
    @FXML
    private Label lbConductorCorreo;

    Colaborador conductorSeleccionado;
    Envio envioSeleccionado;
    Integer idColaboradorOriginal;

    @FXML
    private Label lbErrorGuia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void clickBuscarEnvio(ActionEvent event) {
        buscarEnvio();
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

            conductorSeleccionado = controlador.getColaboradorSeleccionado();

            if (conductorSeleccionado != null) {
                mostrarDatosConductorSeleccionado(conductorSeleccionado);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar la ventana de buscar colaborador", Alert.AlertType.ERROR);
        }

    }

    private void mostrarDatosConductorSeleccionado(Colaborador conductor) {
        if (conductor != null) {
            lbConductorNombre.setText(conductor.getNombreCompleto());
            lbConductorNoPersonal.setText(conductor.getNoPersonal());
            lbConductorCorreo.setText(conductor.getCorreo());
            lbConductorSucursal.setText(conductor.getNombreSucursal());
        }
        apConductor.setVisible(true);
    }

    private void mostrarDatosEnvio(Envio envio) {
        if (envio != null) {
            lbGuia.setText(envio.getNumeroGuia());
            lbConductorActual.setText(envio.getIdConductor() != null ? envio.getNombreConductor() : "Ninguno");
            lbDestinatarioNombre.setText(envio.getNombreDestinatarioCompleto());
            lbDestinatarioDireccion.setText(envio.getCalle() + " " + envio.getNumero());
            lbDestinatarioColonia.setText(envio.getColonia());
            lbDestinatarioCodigoPostal.setText(envio.getCodigoPostal());
            lbDestinatarioCiudad.setText(envio.getCiudad());
            lbDestinatarioEstado.setText(envio.getEstado());
        }
        apEnvio.setVisible(true);
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        if (envioSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Seleccione un envio", "Debe seleccionar un envío", Alert.AlertType.WARNING);
            return;
        }

        if (conductorSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Seleccione un conductor", "Debe seleccionar un conductor", Alert.AlertType.WARNING);
            return;
        }

        envioSeleccionado.setIdConductor(conductorSeleccionado.getIdColaborador());

        if (idColaboradorOriginal == null) {
            asignarConductor(envioSeleccionado);
        } else {
            if (idColaboradorOriginal == conductorSeleccionado.getIdColaborador()) {
                Utilidades.mostrarAlertaSimple("Mismo conductor", "Ha seleccionado el mismo conductor", Alert.AlertType.WARNING);
            } else {
                Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Cambiar conductor", "¿Estas seguro de que quieres cambiar al conductor?");
                if (confirmarOperacion) {
                    asignarConductor(envioSeleccionado);
                }
            }
        }

    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrar();
    }

    private Envio obtenerEnvioPorGuia(String guia) {
        return EnvioImp.obtenerEnvioPorGuia(guia);
    }

    private void buscarEnvio() {
        if (UIUtilidad.esInputVacio(tfBuscarGuia, lbErrorGuia) == true) {
            return;
        }

        UIUtilidad.limpiarError(tfBuscarGuia, lbErrorGuia);

        envioSeleccionado = obtenerEnvioPorGuia(tfBuscarGuia.getText().trim());

        if (envioSeleccionado != null) {
            idColaboradorOriginal = envioSeleccionado.getIdConductor();
            mostrarDatosEnvio(envioSeleccionado);
        } else {
            Utilidades.mostrarAlertaSimple("No encontrado", "No se encontro ningún paquete con esa guía", Alert.AlertType.WARNING);
        }

    }

    private void cerrar() {
        Stage stage = (Stage) tfConductor.getScene().getWindow();
        stage.close();
    }

    private void asignarConductor(Envio envio) {
        Respuesta respuesta = EnvioImp.asignarConductor(envio);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Éxito", "Se asigno correctamente el conductor", Alert.AlertType.INFORMATION);
            cerrar();
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

}
