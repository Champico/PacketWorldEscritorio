package packetworldescritorio.utilidad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Utilidades {

    public static String streamToString(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String inputLine;
        StringBuffer respuestaEntrada = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            respuestaEntrada.append(inputLine);
        }
        in.close();
        return respuestaEntrada.toString();
    }

    public static void mostrarAlertaSimple(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    public static boolean mostrarAlertaConfirmacion(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        Optional<ButtonType> btnSeleccion = alerta.showAndWait();
        return (btnSeleccion.get() == ButtonType.OK);
    }

    /**
     * Normaliza un texto eliminando acentos y marcas diacríticas, convirtiendo
     * caracteres como á, é, í, ó, ú, ü, ñ, Á, É, Í, Ó, Ú en sus equivalentes
     * sin acento. También transforma la cadena resultante a minúsculas.
     *
     * <p>
     * Esta función utiliza la normalización Unicode en la forma NFD
     * (Normalization Form Decomposition), la cual separa cada carácter
     * acentuado en su forma base más las marcas de acento. Posteriormente, se
     * eliminan todas las marcas combinadas de tipo {@code \p{M}}, que
     * representan los acentos, diéresis, tildes y otros signos diacríticos.
     * </p>
     *
     * @param texto la cadena original que puede contener caracteres con
     * acentos. Si es {@code null}, devuelve una cadena vacía.
     * @return una versión normalizada del texto, sin acentos y en minúsculas.
     */
    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String normal = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normal.replaceAll("\\p{M}", "").toLowerCase();
    }

}
