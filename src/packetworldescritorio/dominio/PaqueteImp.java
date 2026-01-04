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
        return new Respuesta();
    }

    public static Respuesta editar(Paquete paquete) {
        return new Respuesta();
    }

}
