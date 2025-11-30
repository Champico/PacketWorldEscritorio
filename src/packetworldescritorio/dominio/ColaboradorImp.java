/** @author champ */
package packetworldescritorio.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import packetworldescritorio.conexion.ConexionAPI;
import packetworldescritorio.dto.RSAutenticacionColaborador;
import packetworldescritorio.dto.Respuesta;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.RespuestaHTTP;
import packetworldescritorio.utilidad.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ColaboradorImp {

    public static HashMap<String, Object> obtenerTodos() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_COLABORADOR_OBTENER_TODOS;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Colaborador>>() {
            }.getType();
            List<Colaborador> colaboradores = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put("error", false);
            respuesta.put("colaboradores", colaboradores);
        } else {
            respuesta.put("error", true);
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
/*
    public static Respuesta registrar(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "/profesor/registrar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(profesor);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL, Constantes.PETICION_POST, parametrosJSON, Constantes.APPLICATION_JSON);

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

    public static Respuesta verificarNoPersonal(String noPersonal) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "/profesor/verificar/noPersonal";

        String parametros = "noPersonal=" + noPersonal;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL, "POST", parametros, "application/x-www-form-urlencoded");

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
            } catch (Exception ex) {
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

    public static Respuesta editar(Profesor profesor) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "/profesor/editar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(profesor);
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL, "PUT", parametrosJSON, Constantes.APPLICATION_JSON);
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

    public static Respuesta eliminar(int idProfesor) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "/profesor/eliminar/" + idProfesor;

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
                    respuesta.setMensaje("Campos en formato incorrecto, por favor verifica la información enviada");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hay problemas para eliminar la informacíon en este momento, porfavor intentelo más tarde");
            }
        }

        return respuesta;
    }*/

}
