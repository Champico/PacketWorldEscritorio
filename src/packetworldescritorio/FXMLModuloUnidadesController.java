/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.dominio.SucursalImp;
import packetworldescritorio.dominio.UnidadImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
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
    private TableColumn colNombreColaborador;
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
        irFormulario(null);
    }

    @FXML
    private void clickIrEditar(ActionEvent event) {
        Unidad unidad = tvUnidades.getSelectionModel().getSelectedItem();
        if (unidad == null) {
            Utilidades.mostrarAlertaSimple("Selecciona una unidad", "Para editar la información de una unidad, debes seleccionarlo primero de la tabla", Alert.AlertType.WARNING);
            return;
        }
    }

    @FXML
    private void clickAsignarConductor(ActionEvent event) {
        Unidad unidad = tvUnidades.getSelectionModel().getSelectedItem();
        if (unidad == null) {
            Utilidades.mostrarAlertaSimple("Selecciona una unidad", "Para asignar un conductor, debes seleccionarlo primero de la tabla", Alert.AlertType.WARNING);
            return;
        }

        if (unidad.getEstatus().equalsIgnoreCase(Constantes.UNIDAD_ESTATUS_INACTIVA)) {
            Utilidades.mostrarAlertaSimple("Unidad inactiva", "Esta unidad esta inactiva, no se puede asignar conductor", Alert.AlertType.ERROR);
            return;
        }
        
        asignarConductor(unidad);
    }

    @FXML
    private void clickDarDeBaja(ActionEvent event) {
        Unidad unidad = tvUnidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            if (unidad.getEstatus().equalsIgnoreCase(Constantes.UNIDAD_ESTATUS_INACTIVA)) {
                Utilidades.mostrarAlertaSimple("Alerta", "Esta unidad no esta activa", Alert.AlertType.INFORMATION);
                return;
            }

            HashMap confirmacion = Utilidades.mostrarAlertaConfirmacionConMotivo("Dar de baja unidad", "Escribe el motivo por el cual se da de baja esta unidad", "Dar de baja");
            if ((Boolean) confirmacion.get(Constantes.KEY_CONFIRMACION) == true) {
                String motivo = (String) confirmacion.get(Constantes.KEY_MOTIVO);
                Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Dar de baja unidad", "¿Estas seguro de que quieres dar de baja la unidad " + (unidad.getTipo() != null ? unidad.getNumIdInterno() : "") + " " + (unidad.getMarca() != null ? unidad.getMarca() : "") + " " + (unidad.getModelo() != null ? unidad.getModelo() : "") + " ? " + "\n Esta acción no se puede deshacer");
                if (confirmarOperacion) {
                    unidad.setMotivoBaja(motivo);
                    darDeBajaUnidad(unidad);
                }
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona unidad", "Para dar de baja una unidad, debe seleccionarla de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(Unidad unidad) {
        System.out.println(Constantes.PG_FORMULARIO_UNIDADES);
        nav.navegar(Constantes.PG_FORMULARIO_UNIDADES, unidad);
    }

    private void darDeBajaUnidad(Unidad unidad) {
        Respuesta respuesta = UnidadImp.darDeBaja(unidad);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Unidad dada de baja", "La unidad fue dada de baja correctamente", Alert.AlertType.INFORMATION);
            cargarInformaciónUnidades();
        } else {
            Utilidades.mostrarAlertaSimple("Error al dar de baja", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void configurarTabla() {
        colIdentificador.setCellValueFactory(new PropertyValueFactory("numIdInterno"));
        colVIN.setCellValueFactory(new PropertyValueFactory("vin"));
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colNombreColaborador.setCellValueFactory(new PropertyValueFactory("nombreColaborador"));
    }

    private void cargarInformaciónUnidades() {
        HashMap<String, Object> respuesta = UnidadImp.obtenerTodos();
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

    private void asignarConductor(Unidad unidad) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(Constantes.MODAL_ASIGNAR_CONDUCTOR));
            Parent vista = cargador.load();
            FXMLModalAsignarConductorController controlador = cargador.getController();
            controlador.cargarInformacion(unidad);
            Stage context = (Stage) tfBusqueda.getScene().getWindow();
            Scene escenaPrincipal = new Scene(vista);

            Stage stModal = new Stage();
            stModal.setScene(escenaPrincipal);
            stModal.setWidth(560);
            stModal.setHeight(360);
            stModal.setResizable(false);
            stModal.setTitle("Asignar conductor");
            stModal.initOwner(context);
            stModal.initModality(Modality.WINDOW_MODAL);
            stModal.initStyle(StageStyle.UTILITY);

            stModal.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrio un error al cargar la ventana de buscar colaborador", Alert.AlertType.ERROR);
        }

    }
}
