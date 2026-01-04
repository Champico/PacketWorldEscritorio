/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.dominio;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import packetworldescritorio.conexion.ConexionAPI;
import packetworldescritorio.dto.CaracteristicasPaquete;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Paquete;
import packetworldescritorio.pojo.RespuestaHTTP;
import packetworldescritorio.utilidad.Constantes;

public class PaqueteImp {

    public static CaracteristicasPaquete obtenerCaracteristicasMaximas() {
        CaracteristicasPaquete caracteristicasPaquete = new CaracteristicasPaquete();
        String URL = Constantes.URL_WS + Constantes.WS_PAQUETE_OBTENER_CARACTERISTICAS;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            caracteristicasPaquete = gson.fromJson(respuestaAPI.getContenido(), CaracteristicasPaquete.class);
        }

        return caracteristicasPaquete;
    }

    public static Respuesta registrar(Paquete paquete) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_ENVIO_REGISTRAR;
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL, Constantes.PETICION_POST, parametrosJSON, Constantes.CT_APPLICATION_JSON);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_BAD_REQUEST:
                    respuesta.setMensaje("Campos en formato incorrecto, por favor verifica la información enviada");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hay problemas para registrar la informacíon en este momento, porfavor intentelo más tarde");
            }
        }

        return respuesta;
    }

    public static Respuesta editar(Paquete paquete) {
        return new Respuesta();
    }

}
