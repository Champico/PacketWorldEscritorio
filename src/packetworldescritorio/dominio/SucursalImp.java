
package packetworldescritorio.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldescritorio.conexion.ConexionAPI;
import packetworldescritorio.pojo.Colaborador;
import packetworldescritorio.pojo.RespuestaHTTP;
import packetworldescritorio.pojo.Sucursal;
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
    
    
}
