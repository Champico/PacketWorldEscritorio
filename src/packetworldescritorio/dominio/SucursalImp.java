package packetworldescritorio.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldescritorio.conexion.ConexionAPI;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.RespuestaHTTP;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.pojo.Unidad;
import packetworldescritorio.utilidad.Constantes;

public class SucursalImp {

    public static HashMap<String, Object> obtenerTodos() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_SUCURSAL_OBTENER_TODOS;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Sucursal>>() {
            }.getType();
            List<Sucursal> sucursales = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, sucursales);
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

    public static Respuesta editar(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_SUCURSAL_EDITAR;

        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(sucursal);
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
                    respuesta.setMensaje("Ocurrio un error al editar la sucursal");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hay problemas para dar editar en este momento, porfavor intentelo más tarde");
            }
        }

        return respuesta;
    }

    public static Respuesta registrar(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_SUCURSAL_REGISTRAR;
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(sucursal);

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

    public static Respuesta darDeBaja(Integer idSucursal) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_SUCURSAL_DAR_DE_BAJA + "/" + idSucursal;
         System.out.println(URL);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

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
                    respuesta.setMensaje("Ocurrio un error al dar de baja la sucursal");
                    break;
                default:
                    System.out.println(respuestaAPI.getCodigo());
                    respuesta.setMensaje("Lo sentimos hay problemas para dar de baja en este momento, porfavor intentelo más tarde");
            }
        }

        return respuesta;
    }

}
