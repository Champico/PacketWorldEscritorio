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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModalSeleccionarConductorController implements Initializable {

    @FXML
    private TableView<Colaborador> tvColaboradores;
    @FXML
    private TableColumn colNoPersonal;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApPaterno;
    @FXML
    private TableColumn colApMaterno;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnBusqueda;

    private Colaborador colaboradorSeleccionado;

    private ObservableList<Colaborador> colaboradores;
    private FilteredList<Colaborador> filteredData;
    private SortedList<Colaborador> sortedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónColaboradores();
        configurarTextFieldBusqueda();
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        colaboradorSeleccionado = tvColaboradores.getSelectionModel().getSelectedItem();
        cerrar();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        cerrar();
    }

    private void configurarTabla() {
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));

        tvColaboradores.setRowFactory(tv -> {
            TableRow<Colaborador> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    colaboradorSeleccionado = row.getItem();
                    cerrar();
                }
            });
            return row;
        });
    }

    private void cargarInformaciónColaboradores() {
        HashMap<String, Object> respuesta = ColaboradorImp.obtenerConductores();
        boolean esError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!esError) {
            List<Colaborador> colaboradoresAPI = (List<Colaborador>) respuesta.get(Constantes.KEY_LISTA);
            colaboradores = FXCollections.observableArrayList();
            colaboradores.addAll(colaboradoresAPI);

            filteredData = new FilteredList<>(colaboradores, c -> c.getIdUnidad() == null );
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tvColaboradores.comparatorProperty());
            tvColaboradores.setItems(sortedData);

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

    public void cerrar() {
        Stage stage = (Stage) tvColaboradores.getScene().getWindow();
        stage.close();
    }

    public Colaborador getColaboradorSeleccionado() {
        return colaboradorSeleccionado;
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
        filteredData.setPredicate(col -> {
            col = (Colaborador) col;
            boolean coincideTexto = true;

            if (textoDeBusqueda != null && !textoDeBusqueda.isEmpty()) {
                String filtro = textoDeBusqueda.toLowerCase();

                String nombre = Utilidades.normalizar(col.getNombre());
                String apellido = Utilidades.normalizar(col.getApellidoPaterno());
                String noPersonal = String.valueOf(col.getNoPersonal()).toLowerCase();

                coincideTexto = 
                        nombre.contains(filtro)
                        || apellido.contains(filtro)
                        || noPersonal.contains(filtro);
            }

            return coincideTexto;

        });

    }

}
