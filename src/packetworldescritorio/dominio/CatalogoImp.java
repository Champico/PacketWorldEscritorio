/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import packetworldescritorio.conexion.ConexionAPI;
import packetworldescritorio.pojo.RespuestaHTTP;
import packetworldescritorio.pojo.Rol;
import packetworldescritorio.utilidad.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import packetworldescritorio.pojo.Asentamiento;
import packetworldescritorio.pojo.Estado;
import packetworldescritorio.pojo.EstatusEnvio;
import packetworldescritorio.pojo.Municipio;
import packetworldescritorio.pojo.Sucursal;
import packetworldescritorio.pojo.TipoUnidad;

public class CatalogoImp {

    public static HashMap<String, Object> obtenerRolesSistema() {
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_CATALOGO_ROLES;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Rol>>() {
            }.getType();
            List<Rol> roles = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, roles);
        } else {
            respuesta.put("error", true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put(Constantes.KEY_ERROR, Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ocurrio un problema al obtener la información de roles.\nPorfavor intentelo más tarde");
            }
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerSucursales() {
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_CATALOGO_SUCURSALES;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Sucursal>>() {
            }.getType();
            List<Sucursal> sucursales = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, sucursales);
        } else {
            respuesta.put("error", true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put(Constantes.KEY_ERROR, Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos hay problemas para obtener la informacíon de sucursales, porfavor intentelo más tarde");
            }
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerTiposUnidad() {
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_CATALOGO_TIPOS_UNIDAD;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<TipoUnidad>>() {
            }.getType();
            List<TipoUnidad> tiposUnidad = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, tiposUnidad);
        } else {
            respuesta.put("error", true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put(Constantes.KEY_ERROR, Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos hay problemas para obtener la informacíon de sucursales, porfavor intentelo más tarde");
            }
        }
        return respuesta;
    }
    
    
    
    public static HashMap<String, Object> obtenerEstados() {
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_CATALOGO_ESTADOS;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Estado>>() {
            }.getType();
            List<Estado> estados = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, estados);
        } else {
            respuesta.put("error", true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put(Constantes.KEY_ERROR, Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos hay problemas para obtener la informacíon de estados, porfavor intentelo más tarde");
            }
        }
        return respuesta;
    }
    
    
    
    
    public static HashMap<String, Object> obtenerMunicipios(String claveEstado) {
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_CATALOGO_MUNICIPIOS + "/" + claveEstado;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Municipio>>() {
            }.getType();
            List<Municipio> municipios = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, municipios);
        } else {
            respuesta.put("error", true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put(Constantes.KEY_ERROR, Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos hay problemas para obtener la informacíon de municipios, porfavor intentelo más tarde");
            }
        }
        return respuesta;
    }
    
    
    
    public static HashMap<String, Object> obtenerColonias(String claveEstado, String claveMunicipio) {
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_CATALOGO_COLONIAS + "/" + claveEstado + "/" + claveMunicipio;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Asentamiento>>() {
            }.getType();
            List<Asentamiento> asentamientos = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, asentamientos);
        } else {
            respuesta.put("error", true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put(Constantes.KEY_ERROR, Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos hay problemas para obtener la informacíon de colonias, porfavor intentelo más tarde");
            }
        }
        return respuesta;
    }
    
    
    public static HashMap<String, Object> obtenerEstatusEnvios() {
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS + Constantes.WS_CATALOGO_ESTATUS_ENVIO;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<EstatusEnvio>>() {
            }.getType();
            List<EstatusEnvio> estatus = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put(Constantes.KEY_ERROR, false);
            respuesta.put(Constantes.KEY_LISTA, estatus);
        } else {
            respuesta.put("error", true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put(Constantes.KEY_ERROR, Constantes.MSJ_ERROR_PETICION);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ocurrio un problema al obtener la información de los estatus de envio.\nPorfavor intentelo más tarde");
            }
        }
        return respuesta;
    }
}
