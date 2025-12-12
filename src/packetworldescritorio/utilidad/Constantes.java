/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.utilidad;

public class Constantes {

    //Servicios
    public static final String URL_WS = "http://localhost:8084/ProyectoWS/api/";
    public static final String WS_AUTENTICACION_COLABORADOR = "autenticacion/colaborador";
    public static final String WS_CATALOGO_ROLES = "catalogo/obtener-roles";
    public static final String WS_COLABORADOR_OBTENER_TODOS = "colaborador/obtener";
    public static final String WS_COLABORADOR_REGISTRAR = "colaborador/registrar";
    public static final String WS_COLABORADOR_EDITAR = "colaborador/editar";
    public static final String WS_COLABORADOR_ELIMINAR = "colaborador/eliminar";
    public static final String WS_COLABORADOR_SUBIR_FOTO = "colaborador/subir-foto";
    public static final String WS_COLABORADOR_OBTENER_FOTO = "colaborador/obtener-foto";
    

    //Peticiones
    public static final String PETICION_GET = "GET";
    public static final String PETICION_POST = "POST";
    public static final String PETICION_PUT = "PUT";
    public static final String PETICION_DELETE = "DELETE";

    //Content type
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    //Errores
    public static final int ERROR_MALFORMED_URL = 1001;
    public static final int ERROR_PETICION = 1002;
    public static final int ERROR_BAD_REQUEST = 1003;
    public static final String MSJ_ERROR_URL = "Lo sentimos su solicitud no puede ser realizada en este momento. porfavor inténtelo más tarde";
    public static final String MSJ_ERROR_PETICION = "Lo sentimos tenemos problemas de conexión en este momento, por favor inténtelo más tarde.";

    //Llaves Hash
    public static final String KEY_ERROR = "error";
    public static final String KEY_MENSAJE = "mensaje";
    public static final String KEY_LISTA = "lista_valores";
    public static final String KEY_OBJETO = "objeto";

    //Archivos fxml
    public static final String PG_APLICACION = "FXMLAplicacion.fxml";
    public static final String PG_INICIO_SESION = "FXMLInicioSesion.fxml";
    public static final String MODULO_COLABORADORES = "FXMLModuloColaboradores.fxml";
    public static final String MODULO_SUCURSALES = "FXMLModuloSucursales.fxml";
    public static final String MODULO_UNIDADES = "FXMLModuloUnidades.fxml";
    public static final String MODULO_CLIENTES = "FXMLModuloClientes.fxml";
    public static final String MODULO_PAQUETES = "FXMLModuloPaquetes.fxml";
    public static final String MODULO_ENVIOS = "FXMLModuloEnvios.fxml";
    public static final String PG_PRINCIPAL = "FXMLPrincipal.fxml";
    public static final String PG_FORMULARIO_COLABORADOR = "FXMLFormularioColaborador.fxml";

    
    //Roles
    public static final String ROL_CONDUCTOR = "Conductor";
    public static final int ID_ROL_CONDUCTOR = 3;
    public static final int ID_ROL_TODOS = 999;
    
}
