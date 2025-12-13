/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.dominio;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.net.HttpURLConnection;
import packetworldescritorio.conexion.ConexionAPI;
import packetworldescritorio.dto.RSAutenticacionColaborador;
import packetworldescritorio.pojo.RespuestaHTTP;
import packetworldescritorio.utilidad.Constantes;

public class InicioSesionImp {
    
        public static RSAutenticacionColaborador verificarCredenciales(String noPersonal, String password) {
        RSAutenticacionColaborador respuesta = new RSAutenticacionColaborador();
        String parametros = "noPersonal=" + noPersonal + "&password=" + password;
        String URL = Constantes.URL_WS + Constantes.WS_AUTENTICACION_COLABORADOR;
            
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL, Constantes.PETICION_GET, parametros, Constantes.CT_APPLICATION_FORM_URLENCODED);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaAPI.getContenido(), RSAutenticacionColaborador.class);
            } catch (JsonSyntaxException ex) {
                respuesta.setError(true);
                respuesta.setMensaje("Lo sentimos hubo un error al obtener la información");
            }
        } else {
            respuesta.setError(true);

            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    respuesta.setMensaje("Datos requeridos para poder realizar la operacion");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hay problemas para verificar sus credenciales");
            }
        }

        return respuesta;
    }
}
