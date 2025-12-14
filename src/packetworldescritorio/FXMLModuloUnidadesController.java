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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.dominio.UnidadesImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModuloUnidadesController implements Initializable, INavegableChild {

    private INavegacion nav;
    @FXML
    private TableView<Unidad> tvUnidades;
    @FXML
    private TableColumn colIdentificador;
    @FXML
    private TableColumn colVIN;
    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colModelo;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private Button btnBusqueda;
    @FXML
    private TextField tfBusqueda;

    private ObservableList<Unidad> unidades;
    private FilteredList<Unidad> filteredData;
    private SortedList<Unidad> sortedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónUnidades();
        configurarTextFieldBusqueda();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        nav.navegar(Constantes.PG_PRINCIPAL);
    }

    @FXML
    private void clickIrRegistrar(ActionEvent event) {
    }

    @FXML
    private void clickIrEditar(ActionEvent event) {
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
    }

    private void configurarTabla() {
        colIdentificador.setCellValueFactory(new PropertyValueFactory("numIdInterno"));
        colVIN.setCellValueFactory(new PropertyValueFactory("vin"));
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
    }

    private void cargarInformaciónUnidades() {
        HashMap<String, Object> respuesta = UnidadesImp.obtenerTodos();
        boolean esError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!esError) {
            List<Unidad> unidadesAPI = (List<Unidad>) respuesta.get(Constantes.KEY_LISTA);
            unidades = FXCollections.observableArrayList();
            unidades.addAll(unidadesAPI);

            filteredData = new FilteredList<>(unidades, p -> true);
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tvUnidades.comparatorProperty());
            tvUnidades.setItems(sortedData);

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

    private void configurarTextFieldBusqueda() {
        tfBusqueda.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                aplicarFiltros();
            }
        });
    }

    private void aplicarFiltros() {
        String textoDeBusqueda = tfBusqueda.getText();

        /*
        Un predicado es una función que recibe un objeto de tipo T y devuelve true o false
        En este caso es una unidad (uni)}
         */
        filteredData.setPredicate(uni -> {

            uni = (Unidad) uni;

            // Filtro por texto
            boolean coincideTexto = true;

            if (textoDeBusqueda != null && !textoDeBusqueda.isEmpty()) {
                String filtro = textoDeBusqueda.toLowerCase();

                String NII = Utilidades.normalizar(uni.getNumIdInterno());
                String VIN = Utilidades.normalizar(uni.getVin());
                String marca = Utilidades.normalizar(uni.getMarca());

                coincideTexto
                        = NII.contains(filtro)
                        || VIN.contains(filtro)
                        || marca.contains(filtro);
            }

            return coincideTexto;

        });

    }
}
