/** @authores  Pipe, Kevin, champ */
package packetworldescritorio.utilidad;

import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;

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

        String valor = input.getText();

        if (valor == null || valor.trim().isEmpty()) {
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
     * Limita la cantidad máxima de caracteres de tipo numerico (1-9) que puede
     * ingresar el usuario en un campo de texto de JavaFX.
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

    /**
     * Configura un campo de texto para escribir numeros de telefono Los
     * caracteres permitidos son los números (0-9) Espacios, guiones y el
     * simbolo de +
     *
     * @param field El campo de texto (TextInputControl) al que se aplicará el
     * la configuración.
     */
    public static void setPhoneNumberFormatter(TextInputControl field) {

        field.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText();

            if (nuevoTexto.length() > 20) {
                return null;
            }
            if (!nuevoTexto.matches("[0-9+\\- ]*")) {
                return null;
            }

            return change;
        }));
    }

    /**
     * Configura un campo de texto para escribir numeros decimales configurando
     * un maximo de numeros para cada lado del punto
     *
     * @param field El campo de texto (TextInputControl) al que se aplicará el
     * la configuración.
     * @param maxIzquierda número maximo de dígitos del lado izquierdo
     * @param maxDerecha número maximo de dígitos del lado derecho
     *
     */
    public static void aplicarFormatoNumericoDecimal(TextInputControl field, int maxIzquierda, int maxDerecha) {

        field.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText();

            if (nuevoTexto.isEmpty()) {
                return change;
            }

            String regex = "^\\d{0," + maxIzquierda + "}(\\.\\d{0," + maxDerecha + "})?$";

            if (nuevoTexto.matches(regex)) {
                return change;
            }

            return null;
        }));
    }

    /**
     * Limita la cantidad máxima de caracteres permitidos a letras A-Z, a-z y
     * números del 1 al 9 en un campo de texto de JavaFX.
     *
     * No permite ñ, acentos, espacios ni símbolos.
     *
     * @param field El campo de texto (TextInputControl) al que se aplicará el
     * límite.
     * @param max El número máximo de caracteres permitidos.
     */
    public static void limitarAlfanumericoBasicoSinEspacios(TextInputControl field, int max) {
        field.setTextFormatter(new TextFormatter<>(change -> {

            String nuevoTexto = change.getControlNewText();

            if (nuevoTexto.length() > max) {
                return null;
            }

            // Solo letras A-Z, a-z y números 0-9
            if (!nuevoTexto.matches("[A-Za-z0-9]*")) {
                return null;
            }

            return change;
        }));
    }

    /**
     * Limita la cantidad máxima de caracteres permitidos a letras A-Z
     * (incluyendo Ñ) y números del 0 al 9 en un campo de texto de JavaFX.
     *
     * Convierte automáticamente las letras a MAYÚSCULAS. No permite espacios ni
     * símbolos.
     *
     * @param field El campo de texto (TextInputControl) al que se aplicará el
     * límite.
     * @param max El número máximo de caracteres permitidos.
     */
    public static void aplicarFormatoBasicoMayusculasSinEspacios(TextInputControl field, int max) {
        field.setTextFormatter(new TextFormatter<>(change -> {

            String nuevoTexto = change.getControlNewText();

            if (nuevoTexto.length() > max) {
                return null;
            }

            // Convertir a mayúsculas
            String textoMayusculas = nuevoTexto.toUpperCase();

            // Solo A-Z, Ñ y números 0-9
            if (!textoMayusculas.matches("[A-Z0-9]*")) {
                return null;
            }

            // Aplicar el texto transformado
            change.setText(textoMayusculas.substring(
                    change.getRangeStart(),
                    change.getRangeStart() + change.getText().length()
            ));

            return change;
        }));
    }

    /**
     * Aplica un formato que permite letras del español (incluyendo ñ, acentos y
     * ü) y números del 0 al 9 en un campo de texto de JavaFX.
     *
     * No permite espacios ni símbolos.
     *
     * @param field El campo de texto (TextInputControl) al que se aplicará el
     * formato.
     * @param max El número máximo de caracteres permitidos.
     */
    public static void aplicarAlfanumericoEspanolSinEspacios(TextInputControl field, int max) {
        field.setTextFormatter(new TextFormatter<>(change -> {

            String nuevoTexto = change.getControlNewText();

            // Validar longitud máxima
            if (nuevoTexto.length() > max) {
                return null;
            }

            // Letras españolas + números
            if (!nuevoTexto.matches("[A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9]*")) {
                return null;
            }

            return change;
        }));
    }

}
