/** @authores  Pipe, Kevin, champ */
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
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldescritorio.utilidad.Utilidades;

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
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, colaboradores);
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

    public static Respuesta registrar(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_COLABORADOR_REGISTRAR;
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(colaborador);

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

    public static Respuesta editar(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_COLABORADOR_EDITAR;
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(colaborador);
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

    public static Respuesta eliminar(int idColaborador) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_COLABORADOR_ELIMINAR + "/" + idColaborador;

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
                    respuesta.setMensaje("Ocurrio un error al eliminar el colaborador");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hay problemas para eliminar la informacíon en este momento, porfavor intentelo más tarde");
            }
        }

        return respuesta;
    }

    public static byte[] obtenerFotoBytes(int idColaborador) {
        byte[] imagen = null;
        String URL = Constantes.URL_WS + Constantes.WS_COLABORADOR_OBTENER_FOTO + "/" + idColaborador;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Colaborador colaborador = gson.fromJson(respuestaAPI.getContenido(), Colaborador.class);
            if (colaborador != null && colaborador.getFotoBase64() != null && !colaborador.getFotoBase64().isEmpty()) {
                try {
                    imagen = Utilidades.base64ABits(colaborador.getFotoBase64());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return imagen;
    }

    public static Respuesta subirFoto(byte[] foto, int idColaborador) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + Constantes.WS_COLABORADOR_SUBIR_FOTO + "/" + idColaborador;
      
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBodyBinario(URL, Constantes.PETICION_PUT, foto, Constantes.CT_IMAGE_JPEG);

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
                    respuesta.setMensaje("Ocurrio un error al subir la foto");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hubo un problema para subir la foto, porfavor intente subir la foto más tarde");
            }

        }
        return respuesta;
    }
    
        public static HashMap<String, Object> buscarPorNumPersonal(String noPersonal) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_COLABORADOR_BUSCAR_POR_NUM_PERSONAL + "/" + noPersonal;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Colaborador colaborador = gson.fromJson(respuestaAPI.getContenido(), Colaborador.class);
            
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_COLABORADOR, colaborador);
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

    
}
