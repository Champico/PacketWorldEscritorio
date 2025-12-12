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
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldescritorio.dominio.ColaboradorImp;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.interfaz.INotificador;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLModuloColaboradoresController implements Initializable, INotificador, INavegableChild  {

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

    private ObservableList<Colaborador> colaboradores;
    @FXML
    private Button btnBusqueda;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformaciónProfesores();
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

    private void cargarInformaciónProfesores() {
        HashMap<String, Object> respuesta = ColaboradorImp.obtenerTodos();
        boolean esError = (boolean) respuesta.get("error");
        if (!esError) {
            List<Colaborador> colaboradoresAPI = (List<Colaborador>) respuesta.get("colaboradores");
            colaboradores = FXCollections.observableArrayList();
            colaboradores.addAll(colaboradoresAPI);
            tvColaboradores.setItems(colaboradores);
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
            Boolean confirmarOperacion = Utilidades.mostrarAlertaConfirmacion("Eliminar colaborador", "¿Estas seguro de que quieres eliminar el registro del colaborador " + (colaborador.getNombre() != null ? colaborador.getNombre() : "") + " " + (colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno(): "") + " " + (colaborador.getApellidoMaterno() != null ? colaborador.getApellidoMaterno() : "") + " ?" + "\n Al eliminar un registro no podras recuperar la información posteriormente");
            if (confirmarOperacion) {
               eliminarProfesor(colaborador.getIdColaborador());
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona profesor", "Para eliminar un profesor, debe seleccionarlo de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(Colaborador colaborador) {
        nav.navegar(Constantes.PG_FORMULARIO_COLABORADOR, colaborador);
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarInformaciónProfesores();
    }

    private void eliminarProfesor(int idProfesor) {
        Respuesta respuesta = ColaboradorImp.eliminar(idProfesor);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Registro eliminado", "El registro del profesor(a) fue eliminado correctamente", Alert.AlertType.INFORMATION);
            cargarInformaciónProfesores();
        } else {
            Utilidades.mostrarAlertaSimple("Error al eliminar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }


    private void irSeleccionFoto(Colaborador colaborador) {
        /*try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFotoProfesor.fxml"));
            Parent vista = cargador.load();
            FXMLFotoProfesorController controlador = cargador.getController();
            controlador.inicializarValores(profesor.getIdProfesor());
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Selección de foto profesor");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
*/
    }

    @Override
    public void setObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
