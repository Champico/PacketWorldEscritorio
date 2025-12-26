/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.utilidad;

public class Constantes {

    //Servicios
    public static final String URL_WS = "http://localhost:8084/ProyectoWS/api/";
    public static final String WS_AUTENTICACION_COLABORADOR = "autenticacion/colaborador";
    public static final String WS_CATALOGO_ROLES = "catalogo/obtener-roles";
    public static final String WS_CATALOGO_SUCURSALES = "catalogo/obtener-sucursales";
    public static final String WS_CATALOGO_TIPOS_UNIDAD = "catalogo/obtener-tipos-unidad";
    public static final String WS_COLABORADOR_OBTENER_TODOS = "colaborador/obtener";
    public static final String WS_COLABORADOR_REGISTRAR = "colaborador/registrar";
    public static final String WS_COLABORADOR_EDITAR = "colaborador/editar";
    public static final String WS_COLABORADOR_ELIMINAR = "colaborador/eliminar";
    public static final String WS_COLABORADOR_SUBIR_FOTO = "colaborador/subirFoto";
    public static final String WS_COLABORADOR_OBTENER_FOTO = "colaborador/obtenerFoto";
    public static final String WS_COLABORADOR_BUSCAR_POR_NUM_PERSONAL = "colaborador/buscarPorNumPersonal";
    public static final String WS_COLABORADOR_OBTENER_CONDUCTORES = "colaborador/obtenerConductores";
    public static final String WS_SUCURSAL_OBTENER_TODOS = "sucursal/obtener";
    public static final String WS_SUCURSAL_REGISTRAR = "sucursal/registrar";
    public static final String WS_SUCURSAL_EDITAR = "sucursal/editar";
    public static final String WS_SUCURSAL_DAR_DE_BAJA = "sucursal/darBaja";
    public static final String WS_UNIDAD_OBTENER_TODOS = "unidad/obtener";
    public static final String WS_UNIDAD_REGISTRAR = "unidad/registrar";
    public static final String WS_UNIDAD_EDITAR = "unidad/editar";
    public static final String WS_UNIDAD_DAR_DE_BAJA= "unidad/darBaja";
    public static final String WS_CLIENTE_OBTENER_TODOS = "cliente/obtener";
    public static final String WS_CLIENTE_REGISTRAR = "cliente/registrar";
    public static final String WS_CLIENTE_EDITAR = "cliente/editar";
    public static final String WS_CLIENTE_ELIMINAR = "cliente/eliminar";

    //Peticiones
    public static final String PETICION_GET = "GET";
    public static final String PETICION_POST = "POST";
    public static final String PETICION_PUT = "PUT";
    public static final String PETICION_DELETE = "DELETE";

    //Content type
    public static final String CT_APPLICATION_JSON = "application/json";
    public static final String CT_APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String CT_IMAGE_JPEG = "image/jpeg";

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
    public static final String KEY_COLABORADOR = "colaborador";
    public static final String KEY_CONFIRMACION = "confirmacion";
    public static final String KEY_MOTIVO = "motivo";

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
    public static final String PG_FORMULARIO_UNIDADES = "FXMLFormularioUnidad.fxml";
    public static final String MODAL_BUSCAR_CONDUCTOR = "FXMLBuscadorConductor.fxml";
    
    //Roles
    public static final String ROL_CONDUCTOR = "Conductor";
    public static final int ID_ROL_CONDUCTOR = 3;
    public static final int ID_ROL_TODOS = 999;
    
    public static final String UNIDAD_ESTATUS_INACTIVA = "inactiva";
    public static final String UNIDAD_ESTATUS_ACTIVA = "activa";

    //Otros
    public static final int MAX_SIZE_MB_IMAGE = 2;

}
