package packetworldescritorio.utilidad;

public class Constantes {

    //Servicios
    public static final String URL_WS = "http://localhost:8084/APIRest/api/";
    public static final String WS_INICIO_SESION = "autenticacion/colaborador";
    
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


}
