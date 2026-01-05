/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldescritorio.conexion.ConexionAPI;
import packetworldescritorio.dto.CaracteristicasPaquete;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Envio;
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
        String URL = Constantes.URL_WS + Constantes.WS_PAQUETE_REGISTRAR;
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
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_PAQUETE_EDITAR;
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL, "PUT", parametrosJSON, Constantes.CT_APPLICATION_JSON);
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
                    respuesta.setMensaje("Lo sentimos hay problemas para editar la informacíon en este momento, porfavor intentelo más tarde");
            }
        }

        return respuesta;
    }

    public static HashMap<String, Object> obtenerConLimite(Integer idSucursal, Integer limite) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_PAQUETE_OBTENER_ULTIMOS + "/" + idSucursal + "/" + limite;
        
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Paquete>>() {
            }.getType();
            List<Paquete> paquetes = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, paquetes);
        } else {
            respuesta.put(Constantes.KEY_ERROR, true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put("mensaje", Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put("mensaje", Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put("mensaje", "Lo sentimos hay problemas para obtener la informacíon en este momento, porfavor intentelo más tarde");
            }
        }
        return respuesta;
    }

    public static Respuesta eliminar(int idPaquete) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_PAQUETE_ELIMINAR + "/" + idPaquete;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionSinBody(URL, Constantes.PETICION_DELETE);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
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
                    respuesta.setMensaje("Ocurrio un error al eliminar el paquete");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hay problemas para eliminar la informacíon en este momento, porfavor intentelo más tarde");
            }
        }

        return respuesta;
    }

}
