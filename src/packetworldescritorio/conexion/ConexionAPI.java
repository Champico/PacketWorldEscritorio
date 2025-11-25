/**  @authores  Pipe, Kevin, champ */

package packetworldescritorio.conexion;

import packetworldescritorio.pojo.RespuestaHTTP;
import packetworldescritorio.utilidad.Constantes;
import packetworldescritorio.utilidad.Utilidades;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexionAPI {

/**
 * Genera una petición HTTP de tipo GET
 *
 * @param URL Es la URL a la que va la petición
 * @return Retorna un objeto de tipo RespuestaHTTP que incluye si hubo un error, un mensaje y un objeto
 */
    public static RespuestaHTTP peticionGET(String URL) {
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            int codigo = conexionHTTP.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexionHTTP.getInputStream()));
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(e.getMessage());
        }

        return respuesta;
    }

    /**
 * Genera una petición HTTP con un body
 *
 * @param URL Es la URL a la que va la petición
 * @param metodoHTTP es el método: PUT, POST, DELETE
 * @return Retorna un objeto de tipo RespuestaHTTP que incluye si hubo un error, un mensaje y un objeto
 */
    public static RespuestaHTTP peticionBody(String URL, String metodoHTTP, String parametros, String contentType) {
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            conexionHTTP.setRequestMethod(metodoHTTP);
            conexionHTTP.setRequestProperty("Content-Type", contentType);
            conexionHTTP.setDoOutput(true);
            OutputStream os = conexionHTTP.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            int codigo = conexionHTTP.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexionHTTP.getInputStream()));
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(e.getMessage());
        }

        return respuesta;

    }
    
    
    
     public static RespuestaHTTP peticionSinBody(String URL, String metodoHTTP) {
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlWS = new URL(URL);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlWS.openConnection();
            conexionHTTP.setRequestMethod(metodoHTTP);
            int codigo = conexionHTTP.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexionHTTP.getInputStream()));
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(e.getMessage());
        }

        return respuesta;
    }


}
