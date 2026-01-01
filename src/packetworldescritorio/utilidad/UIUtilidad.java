/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.utilidad;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javax.imageio.ImageIO;

public class UIUtilidad {

    /**
     * Verifica si un campo de texto está vacío y maneja el estado visual de
     * error.
     *
     * <p>
     * Esta función obtiene el valor del {@code TextInputControl}, elimina los
     * espacios en blanco al inicio y al final, y determina si el campo está
     * vacío. En caso de estar vacío:
     * <ul>
     * <li>Marca el borde del campo en color rojo.</li>
     * <li>Muestra el mensaje de error correspondiente en el {@code Label}
     * asociado.</li>
     * </ul>
     * Si el campo contiene texto:
     * <ul>
     * <li>Se limpia el estado de error visual.</li>
     * <li>Se oculta el mensaje de error relacionado.</li>
     * </ul>
     *
     * @param input El campo de texto (TextField o PasswordField) a validar.
     * @param labelError El Label donde se mostrará el mensaje de error si el
     * campo está vacío.
     * @return {@code true} si el campo está vacío, {@code false} si contiene
     * algún texto.
     */
    public static boolean esInputVacio(TextInputControl input, Label labelError) {
        if (input == null) {
            return true;
        }

        String valor = input.getText().trim();
        if (valor.isEmpty()) {
            marcarError(input, labelError, "Campo obligatorio");
            return true;
        } else {
            limpiarError(input, labelError);
            return false;
        }
    }

    /**
     * Marca un error visual en un campo de texto y muestra un mensaje de error
     * en un Label.
     *
     * <p>
     * Acciones realizadas:
     * <ul>
     * <li>Pone el borde del TextInputControl en color rojo para indicar
     * error.</li>
     * <li>Muestra el Label en color rojo con el mensaje proporcionado.</li>
     * </ul>
     * </p>
     *
     * @param input El campo de texto (TextField o PasswordField) al que se le
     * marcará el borde en rojo.
     * @param label El componente Label donde se mostrará el mensaje de error.
     * @param mensaje El texto del mensaje de error que se mostrará en el Label.
     */
    public static void marcarError(TextInputControl input, Label label, String mensaje) {
        marcarErrorTextInputControl(input);
        mostrarLabelMensajeError(label, mensaje);
    }

    /**
     * Limpia el estado de error visual en un campo de texto y oculta el mensaje
     * asociado.
     *
     * <p>
     * Acciones realizadas:
     * <ul>
     * <li>Restaura el borde del TextInputControl a su estilo normal.</li>
     * <li>Oculta el Label que contiene el mensaje de error.</li>
     * </ul>
     * </p>
     *
     * @param input El campo de texto (TextField o PasswordField) al que se le
     * quitará el borde de error.
     * @param label El componente Label cuyo mensaje de error será ocultado.
     */
    public static void limpiarError(TextInputControl input, Label label) {
        limpiarErrorTextInputControl(input);
        ocultarLabelMensajeError(label);
    }

    public static void marcarErrorTextInputControl(TextInputControl input) {
        if (input != null) {
            input.getStyleClass().add("input_error");
        }
    }

    public static void limpiarErrorTextInputControl(TextInputControl input) {
        if (input != null) {
            input.getStyleClass().remove("input_error");
        }
    }

    public static void mostrarLabelMensajeError(Label label, String mensaje) {
        if (label != null && mensaje != null) {
            label.setText(mensaje);
            label.setVisible(true);
        }
    }

    public static void ocultarLabelMensajeError(Label label) {
        if (label != null) {
            label.setText("");
            label.setVisible(false);
        }
    }

    /**
     * Limita la cantidad máxima de caracteres que puede ingresar el usuario en
     * un campo de texto de JavaFX.
     *
     * @param field El campo de texto (TextInputControl) al que se aplicará el
     * límite.
     * @param max El número máximo de caracteres permitidos.
     */
    public static void limitarCaracteres(TextInputControl field, int max) {
        field.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().length() <= max ? change : null
        ));
    }

        /**
     * Limita la cantidad máxima de caracteres de tipo numerico (1-9) que puede ingresar
     * el usuario en un campo de texto de JavaFX.
     *
     * @param field El campo de texto (TextInputControl) al que se aplicará el
     * límite.
     * @param max El número máximo de caracteres permitidos.
     */
    public static void limitarNumeros(TextInputControl field, int max) {
        field.setTextFormatter(new TextFormatter<>(change -> {

            String nuevoTexto = change.getControlNewText();

            if (nuevoTexto.length() > max) {
                return null;
            }

            if (!nuevoTexto.matches("\\d*")) {
                return null;
            }

            return change;
        }));
    }

}
