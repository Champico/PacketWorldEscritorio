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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldescritorio.dominio.EnvioImp;
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Envio;
import packetworldescritorio.pojo.Session;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModuloEnviosController implements Initializable, INavegableChild {

    private INavegacion nav;

    @FXML
    private TableView<Envio> tvEnvios;
    @FXML
    private TableColumn colGuia;
    @FXML
    private TableColumn colDestinatario;
    @FXML
    private TableColumn colCalle;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colCiudad;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TableColumn colEstatus;

    private ObservableList<Envio> envios;
    private FilteredList<Envio> filteredData;
    private SortedList<Envio> sortedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónEnvios();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {

    }

    @FXML
    private void clickIrRegistrar(ActionEvent event) {
    }

    @FXML
    private void clickIrBuscar(ActionEvent event) {
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

    @FXML
    private void clickIrModificar(ActionEvent event) {
    }

    @FXML
    private void clickAsignarConductor(ActionEvent event) {
    }

    private void regresar() {
        nav.navegar(Constantes.PG_PRINCIPAL);
    }
    
    private void irPerfilEnvio(int idEnvio){
       
    }

    private void configurarTabla() {
        colGuia.setCellValueFactory(new PropertyValueFactory("numeroGuia"));
        colDestinatario.setCellValueFactory(new PropertyValueFactory("destinatario"));
        colCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        colColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        colCiudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("nombreEstatus"));
    
            tvEnvios.setRowFactory(tv -> {
            TableRow<Envio> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    irPerfilEnvio(0);
                }
            });
            return row;
        });
    
    }

    private void cargarInformaciónEnvios() {
        Integer idSucursal = null;
        try {
            idSucursal = Session.getInstance().getUsuarioActual().getIdSucursal();
        } catch (Exception ex) {
        }

        HashMap<String, Object> respuesta = EnvioImp.obtenerConLimite(idSucursal, Constantes.LIMITE_ENVIOS);
        boolean esError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!esError) {
            List<Envio> sucursalesAPI = (List<Envio>) respuesta.get(Constantes.KEY_LISTA);

            envios = FXCollections.observableArrayList();
            envios.addAll(sucursalesAPI);

            filteredData = new FilteredList<>(envios, p -> true);
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tvEnvios.comparatorProperty());
            tvEnvios.setItems(sortedData);

            /* Explicación
            ObservableList - Es la lista original con todos los elementos
            FilteredList - Es una vista dinamica que filtra la lista original segun un predicate (Condicion de filtrado)
            SortedList - Es una vista que odena la lista filtrada, mantiene un comportamiento consistente.
            SL.comparatorProperty().bind(TV.comparatorProperty()) - Conecta el comparador que usa el TableView (cuando el usuario hace clic en los encabezados de columna) con la lista ordenada que conteiene los elementos mostrados
             */
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
        }
    }

}
