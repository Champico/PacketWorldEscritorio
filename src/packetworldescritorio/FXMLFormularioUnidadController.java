/** @authores  Pipe, Kevin, champ */
package packetworldescritorio;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import packetworldescritorio.dominio.CatalogoImp;
import packetworldescritorio.interfaz.INavegableChild;
import packetworldescritorio.interfaz.INavegacion;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.pojo.TipoUnidad;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;

public class FXMLFormularioUnidadController implements Initializable, INavegableChild {

    private INavegacion nav;

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private ComboBox<Integer> cbAno;
    @FXML
    private TextField tfVin;
    @FXML
    private Label lbErrorVin;
    @FXML
    private Label lbErrorMarca;
    @FXML
    private Label lbErrorModelo;
    @FXML
    private Label lbErrorAno;
    @FXML
    private Label lbErrorTipo;
    @FXML
    private ComboBox<TipoUnidad> cbTipo;

    private Unidad unidadEdicion = null;
    private ObservableList<TipoUnidad> tiposDeUnidad;
    private ObservableList<Colaborador> conductores;
    private ObservableList<Integer> anos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarComboBoxAno();
        cargarComboBoxTiposUnidad();
    }

    @Override
    public void setNavegador(INavegacion nav) {
        this.nav = nav;
    }

    @Override
    public void setObject(Object object) {
        if (object instanceof Unidad) {
            Unidad unidad = (Unidad) object;
            inicializarDatos(unidad);
        }
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        regresar();
    }

    private void regresar() {
        nav.navegar(Constantes.MODULO_UNIDADES);
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
    }

    public void inicializarDatos(Unidad unidadEdicion) {
        this.unidadEdicion = unidadEdicion;
        if (unidadEdicion != null) {

            lbTitulo.setText("Editar colaborador");

            tfVin.setText(unidadEdicion.getVin());
            tfMarca.setText(unidadEdicion.getMarca());
            tfModelo.setText(unidadEdicion.getModelo());

            tfVin.setDisable(true);

            int posicionAno = obtenerPosicionAno(unidadEdicion.getAnio());
            cbAno.getSelectionModel().select(posicionAno);

        }
    }

    private void cargarComboBoxAno() {
        int anoActual = LocalDate.now().getYear();

        ArrayList<Integer> anosTemp = new ArrayList<>();
        for (int ano = anoActual + 3; ano > anoActual -50; ano--) {
            anosTemp.add(ano);
        }

        anos = FXCollections.observableArrayList();
        anos.addAll(anosTemp);
        cbAno.setItems(anos);
    }

    private int obtenerPosicionAno(String ano) {
        try {
            int numero = Integer.parseInt(ano);
            for (int i = 0; i < anos.size(); i++) {
                if (anos.get(i) == numero) {
                    return i;
                }
            }
        } catch (NumberFormatException ex) {
            return -1;
        }
        return -1;
    }

    private void cargarComboBoxTiposUnidad() {
           HashMap<String, Object> respuesta = CatalogoImp.obtenerTiposUnidad();
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            List<TipoUnidad> sucursalAPI = (List<TipoUnidad>) respuesta.get(Constantes.KEY_LISTA);
            tiposDeUnidad = FXCollections.observableArrayList();
            tiposDeUnidad.addAll(sucursalAPI);
            cbTipo.setItems(tiposDeUnidad);
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.get(Constantes.KEY_MENSAJE).toString(), Alert.AlertType.ERROR);
            nav.navegar(Constantes.MODULO_COLABORADORES);
        }
    }

}
