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
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModuloSucursalesController implements Initializable, INavegableChild {

    private INavegacion nav;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnBusqueda;
    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCalle;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TableColumn colCiudad;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colEstatus;

    private ObservableList<Sucursal> sucursales;
    private FilteredList<Sucursal> filteredData;
    private SortedList<Sucursal> sortedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónSucursales();
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
        irFormulario(null);
    }

    @FXML
    private void clickIrEditar(ActionEvent event) {
        Sucursal sucursal = tvSucursales.getSelectionModel().getSelectedItem();
        if (sucursal != null) {
            irFormulario(sucursal);
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona una sucursal", "Para editar la información de una sucursal, debes seleciconarlo primero de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
                Sucursal sucursal = tvSucursales.getSelectionModel().getSelectedItem();
        if (sucursal != null) {
            
             if (sucursal.getEstatus().equalsIgnoreCase(Constantes.UNIDAD_ESTATUS_INACTIVA)) {
                Utilidades.mostrarAlertaSimple("Alerta", "Esta sucursal no esta activa", Alert.AlertType.INFORMATION);
                return;
            }
             
             boolean confirmacion = Utilidades.mostrarAlertaConfirmacion("¿Esta seguro de dar de bara?", "¿Esta seguro que quiere dar de baja esta sucursal?");
             
             if(confirmacion == true){
                Respuesta respuesta = SucursalImp.darDeBaja(sucursal.getIdSucursal());
                if(respuesta.isError()){
                   Utilidades.mostrarAlertaSimple("Error al dar de baja", respuesta.getMensaje(), Alert.AlertType.ERROR);
                }else{
                   Utilidades.mostrarAlertaSimple("Exito", "La sucursal ha sido dada de baja con exito", Alert.AlertType.INFORMATION);
                    cargarInformaciónSucursales();
                }
             }
             
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona un colaborador", "Para editar la información de una sucursal, debes seleciconarlo primero de la tabla", Alert.AlertType.WARNING);
        }
    }
    
        private void irFormulario(Sucursal sucursal) {
        nav.navegar(Constantes.PG_FORMULARIO_SUCURSALES, sucursal);
    }

    private void configurarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        colColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        colCiudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
    }

    private void cargarInformaciónSucursales() {
        HashMap<String, Object> respuesta = SucursalImp.obtenerTodos();
        boolean esError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!esError) {
            List<Sucursal> sucursalesAPI = (List<Sucursal>) respuesta.get(Constantes.KEY_LISTA);
            sucursales = FXCollections.observableArrayList();
            sucursales.addAll(sucursalesAPI);

            filteredData = new FilteredList<>(sucursales, p -> true);
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tvSucursales.comparatorProperty());
            tvSucursales.setItems(sortedData);

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
        En este caso es una sucursal (suc)}
         */
        filteredData.setPredicate(suc -> {

            suc = (Sucursal) suc;

            // Filtro por texto
            boolean coincideTexto = true;

            if (textoDeBusqueda != null && !textoDeBusqueda.isEmpty()) {
                String filtro = textoDeBusqueda.toLowerCase();

                String nombre = Utilidades.normalizar(suc.getNombre());
                String codigo = String.valueOf(suc.getCodigo()).toLowerCase();

                coincideTexto
                        = nombre.contains(filtro)
                        || codigo.contains(filtro);
            }

            return coincideTexto;

        });

    }

}
