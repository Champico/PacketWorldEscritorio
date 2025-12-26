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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLBuscadorConductorController implements Initializable {

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

    private ObservableList<Colaborador> colaboradores;
    private FilteredList<Colaborador> filteredData;
    private SortedList<Colaborador> sortedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónColaboradores();
    }

    private void configurarTabla() {
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
    }

    private void cargarInformaciónColaboradores() {
        HashMap<String, Object> respuesta = ColaboradorImp.obtenerConductores();
        boolean esError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!esError) {
            List<Colaborador> colaboradoresAPI = (List<Colaborador>) respuesta.get(Constantes.KEY_LISTA);
            colaboradores = FXCollections.observableArrayList();
            colaboradores.addAll(colaboradoresAPI);

            filteredData = new FilteredList<>(colaboradores, p -> true);
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
    
    
    

}
