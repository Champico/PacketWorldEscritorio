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
import packetworldescritorio.pojo.Sucursal;


public class CatalogoImp {
    public static HashMap<String, Object> obtenerRolesSistema(){
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS+Constantes.WS_CATALOGO_ROLES;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if(respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Rol>>() {}.getType();
                List<Rol> roles = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put(Constantes.KEY_LISTA, roles);
        }else{
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
    
        public static HashMap<String, Object> obtenerSucursales(){
        HashMap<String, Object> respuesta = new HashMap<>();
        String URL = Constantes.URL_WS+Constantes.WS_CATALOGO_SUCURSALES;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if(respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Sucursal>>() {}.getType();
                List<Sucursal> sucursales = gson.fromJson(respuestaAPI.getContenido(), tipoLista);
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put(Constantes.KEY_LISTA, sucursales);
        }else{
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
}
