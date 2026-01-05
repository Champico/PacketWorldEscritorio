/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.dominio.EnvioImp;
import packetworldescritorio.dominio.PaqueteImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Envio;
import packetworldescritorio.pojo.Paquete;
import packetworldescritorio.pojo.Session;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.UIUtilidad;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModuloPaquetesController implements Initializable, INavegableChild {

    private ObservableList<Paquete> paquetes;

    private INavegacion nav;
    @FXML
    private TableView<Paquete> tvPaquetes;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colPeso;
    @FXML
    private TableColumn colAlto;
    @FXML
    private TableColumn colAncho;
    @FXML
    private TableColumn colProfundidad;
    @FXML
    private TableColumn colEnvio;
    @FXML
    private TextField tfBuscarGuia;
    @FXML
    private Label lbErrorGuia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónPaquetes();
    }

    @Override
    public void setObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        nav.navegar(Constantes.PG_PRINCIPAL);
    }
    
        @FXML
    private void clickEditarPaquetesEnvio(ActionEvent event) {
        buscarEnvio();
    }

    private void configurarTabla() {
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colAlto.setCellValueFactory(new PropertyValueFactory("alto"));
        colAncho.setCellValueFactory(new PropertyValueFactory("ancho"));
        colProfundidad.setCellValueFactory(new PropertyValueFactory("profundidad"));
        colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        colEnvio.setCellValueFactory(new PropertyValueFactory("guiaEnvio"));
    }

    private void cargarInformaciónPaquetes() {
        HashMap<String, Object> respuesta = PaqueteImp.obtenerConLimite(Session.getInstance().getUsuarioActual().getIdSucursal(), Constantes.LIMITE_PAQUETES);
        boolean esError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!esError) {
            List<Paquete> paquetesAPI = (List<Paquete>) respuesta.get(Constantes.KEY_LISTA);
            paquetes = FXCollections.observableArrayList();
            paquetes.addAll(paquetesAPI);
            tvPaquetes.setItems(paquetes);

        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
        }

    }

    private void buscarEnvio() {
        if (UIUtilidad.esInputVacio(tfBuscarGuia, lbErrorGuia) == true) {
            return;
        }

        UIUtilidad.limpiarError(tfBuscarGuia, lbErrorGuia);

        Envio envioSeleccionado = obtenerEnvioPorGuia(tfBuscarGuia.getText().trim());

        if (envioSeleccionado != null) {
            irPaginaPerfilEnvio(envioSeleccionado);
        } else {
            Utilidades.mostrarAlertaSimple("No encontrado", "No se encontro ningún paquete con esa guía", Alert.AlertType.WARNING);
        }
    }

    private Envio obtenerEnvioPorGuia(String guia) {
        return EnvioImp.obtenerEnvioPorGuia(guia);
    }


    private void irPaginaPerfilEnvio(Envio envio) {
        nav.navegar(Constantes.PG_PERFIL_ENVIO, envio);
    }

}
