/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldescritorio.dominio.CatalogoImp;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.interfaz.INotificador;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModuloColaboradoresController implements Initializable, INavegableChild {

    private INavegacion nav;

    @FXML
    private TextField tfBusqueda;
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
    private TableColumn colCorreo;
    @FXML
    private TableColumn colRol;
    @FXML
    private Button btnBusqueda;
    @FXML
    private ComboBox<Rol> cbRol;

    private ObservableList<Colaborador> colaboradores;
    private FilteredList<Colaborador> filteredData;
    private SortedList<Colaborador> sortedData;
    private ObservableList<Rol> roles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónColaboradores();
        configurarComboBoxRoles();
        configurarTextFieldBusqueda();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        nav.navegar(Constantes.PG_PRINCIPAL);
    }

    private void configurarTabla() {
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
    }

    private void cargarInformaciónColaboradores() {
        HashMap<String, Object> respuesta = ColaboradorImp.obtenerTodos();
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

    @FXML
    private void clickIrRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clickIrEditar(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            irFormulario(colaborador);
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona un colaborador", "Para editar la información de un colaborador, debes seleciconarlo primero de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Eliminar colaborador", "¿Estas seguro de que quieres eliminar el registro del colaborador " + (colaborador.getNombre() != null ? colaborador.getNombre() : "") + " " + (colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "") + " " + (colaborador.getApellidoMaterno() != null ? colaborador.getApellidoMaterno() : "") + " ?" + "\n Al eliminar un registro no podras recuperar la información posteriormente");
            if (confirmarOperacion) {
                eliminarColaborador(colaborador.getIdColaborador());
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona profesor", "Para eliminar un profesor, debe seleccionarlo de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(Colaborador colaborador) {
        nav.navegar(Constantes.PG_FORMULARIO_COLABORADOR, colaborador);
    }

 

    private void eliminarColaborador(int idColaborador) {
        Respuesta respuesta = ColaboradorImp.eliminar(idColaborador);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Registro eliminado", "El registro del colaborador fue eliminado correctamente", Alert.AlertType.INFORMATION);
            cargarInformaciónColaboradores();
        } else {
            Utilidades.mostrarAlertaSimple("Error al eliminar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void setObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarRolesProfesor() {
        HashMap<String, Object> respuesta = CatalogoImp.obtenerRolesSistema();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<Rol> rolesAPI = (List<Rol>) respuesta.get(Constantes.KEY_LISTA);
            roles = FXCollections.observableArrayList();
            Rol rolTodos = new Rol(Constantes.ID_ROL_TODOS, "Todos");
            roles.add(rolTodos);
            roles.addAll(rolesAPI);
            cbRol.setItems(roles);
            cbRol.getSelectionModel().select(rolTodos);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
            nav.navegar(Constantes.MODULO_COLABORADORES);
        }
    }

    private void configurarComboBoxRoles() {
        cargarRolesProfesor();
        agregarEventoCambioRol();
    }

    private void agregarEventoCambioRol() {
        cbRol.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                aplicarFiltros();
            }
        });
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
        int idRolSeleccionado = cbRol.getSelectionModel().getSelectedItem().getIdRol();

        /*
        Un predicado es una función que recibe un objeto de tipo T y devuelve true o false
        En este caso es un colaborador (col)}
         */
        filteredData.setPredicate(col -> {

            col = (Colaborador) col;
            
            // Filtro por texto
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

            //Filtrado por rol
            boolean coincideRol = true;

            if (idRolSeleccionado != Constantes.ID_ROL_TODOS) {

                int idRol = 0;
                if (col.getRol() != null) {
                    idRol = col.getIdRol();
                }

                coincideRol = idRol == idRolSeleccionado;
            }


            //Cumple dos condiciones | texto de busqueda y rol
            return coincideTexto && coincideRol;

        });

    }

}
