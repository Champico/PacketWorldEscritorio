package packetworldescritorio.utilidad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

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

    public static  HashMap<String, Object> mostrarAlertaConfirmacionConMotivo(String titulo, String contenido, String mensajeBotonAceptar){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        dialog.getDialogPane().setPrefWidth(420);
        
        ButtonType  btnAceptar = new ButtonType(mensajeBotonAceptar, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAceptar, ButtonType.CANCEL);
        
        Label lbMotivo = new Label(contenido);
        
        TextArea taMotivo = new TextArea();
        taMotivo.setPromptText("Escriba aqui...");
        taMotivo.setWrapText(true);
        taMotivo.setPrefRowCount(4);
        taMotivo.setPrefWidth(400);
        
        VBox content = new VBox(10, lbMotivo, taMotivo);
        content.setPadding(new Insets(10));
        
        dialog.getDialogPane().setContent(content);
        
        Node btnOkNode = dialog.getDialogPane().lookupButton(btnAceptar);
        
        btnOkNode.setDisable(true);
        taMotivo.textProperty().addListener((obs, oldText, newText) ->{
            btnOkNode.setDisable(newText.trim().isEmpty());
        });
        
        dialog.setResultConverter(button ->{
            if(button == btnAceptar) {
                return taMotivo.getText().trim();
            }
            return null;
        });
        
        Optional<String> result = dialog.showAndWait();
        
        if(result.isPresent()){
            String motivo = result.get().trim();
            respuesta.put(Constantes.KEY_CONFIRMACION, true);
            respuesta.put(Constantes.KEY_MOTIVO, motivo);
        }else{
            respuesta.put(Constantes.KEY_CONFIRMACION, false);
            respuesta.put(Constantes.KEY_MOTIVO, "cancelado");
        }
        return respuesta;
    }
    
    
    
    
    
    /**
     * Elimina acentos y marcas diacríticas, convirtiendo caracteres como á, é,
     * í, ó, ú, ü, ñ, Á, É, Í, Ó, Ú en sus equivalentes sin acento. También
     * transforma la cadena resultante a minúsculas.
     *
     * <p>
     * Esta función utiliza la normalización Unicode en la forma NFD
     * (Normalization Form Decomposition), la cual separa cada carácter
     * acentuado en su forma base más las marcas de acento. Posteriormente, se
     * eliminan todas las marcas de tipo {@code \p{M}}, que representan los
     * acentos, diéresis, tildes y otros signos diacríticos.
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

    /**
     * Convierte un string de base de 64 a bits
     */
    public static byte[] base64ABits(String base64) {
        byte[] bytes = null;
        base64 = normalizarTextoBase64(base64);
        bytes = Base64.getDecoder().decode(base64);
        return bytes;
    }

    public static String normalizarTextoBase64(String base64) {
        return base64
                .replaceAll("\\s", "") // elimina \n \r \t y espacios
                .replaceAll("^data:[^,]*,", ""); // elimina encabezado data:image/png;base64
    }

    /**
     * Verifica si un archivo tiene un tamaño maximo en megabytes
     *
     * @param file Archivo
     * @param maxMB Tamaño maximo del archivo. Limitación de hasta 1GB
     * @throws IllegalArgumentException si el valor está fuera del rango [0,
     * 1024].
     */
    public static boolean verificarTamañoMaximoArchivo(File file, int maxMB) throws IllegalArgumentException, NullPointerException {
        if (file == null) {
            throw new NullPointerException("No se envio ningun archivo");
        }

        if (maxMB > 1024) {
            throw new IllegalArgumentException("El tamaño maximo para verificar archivos es de 1024 bytes");
        }

        final long MAX_SIZE_BYTES = maxMB * 1024 * 1024;

        return !(file.length() > MAX_SIZE_BYTES);
    }

    /**
     * Convierte una imagen PNG (que puede incluir transparencia) a una imagen
     * en formato JPG, retornando el resultado como un arreglo de bytes.
     *
     * @param pngBytes Arreglo de bytes que representa una imagen en formato
     * PNG.
     * @return Un arreglo de bytes que contiene la imagen resultante en formato
     * JPG.
     * @throws IllegalArgumentException Si {@code pngBytes} es {@code null}.
     *
     * @throws IOException Si la imagen PNG no puede decodificarse o si ocurre
     * un error al codificar la imagen JPG.
     */
    public static byte[] pngBytesToJpgBytes(byte[] pngBytes) throws IOException {
        if (pngBytes == null) {
            throw new IllegalArgumentException("pngBytes == null");
        }

        BufferedImage pngImage = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(pngBytes);
            pngImage = ImageIO.read(bais);
        } catch (Exception ex) {
        }

        if (pngImage == null) {
            throw new IOException("No se pudo decodificar la imagen PNG");
        }

        BufferedImage jpgImage = new BufferedImage(
                pngImage.getWidth(),
                pngImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = jpgImage.createGraphics();
        try {
            g.drawImage(pngImage, 0, 0, Color.WHITE, null); //Dibujar imagen con fondo blanco
        } finally {
            g.dispose(); //liberar recursos
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(jpgImage, "jpg", baos);
            return baos.toByteArray();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IOException("No se pudo codificar la imagen JPG");
        }

    }

    public static String getExtensionFileImagen(File file) throws IOException {
        String format = null;

        /* ImageInputStream es una clase que optimiza la lectura de bits de una imagen*/
        try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
            /*  ### ITERATOR #### 
            Un iterator es una interfaz que permite recorrer colecciones o streams de elementos. En este caso lo obtenemos a partir del  ImageInputStream
            La funcion hasNext() verifica si tiene un elemento y next() obtiene ese elemento
            
               ### IMAGE READER #####
            Un ImageReader es una implementación Java que sabe cómo LEER una imagen (Sabe decodificarla)
            Son parte de JDK/JVM y Image I/P
            Un lector puede reconocer el formato de la imagen sin leer toda la imagen
            Pasos:
            1. Lee los primero bytes (header / magic numbers)
            2. Pregunta a los lectores si pueden leer esa informacion
            3. El lector responde que si y extrae los metadatos
             */
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            /* Verificamos si existe almenos un elemento lector*/
            if (!readers.hasNext()) {
                return null;
            }

            ImageReader reader = readers.next();

            try {
                format = reader.getFormatName().toLowerCase();
            } finally {
                reader.dispose();
            }

        } catch (IOException e) {
            throw new IOException("El archivo no se pudo leer");
        }

        return format;
    }

    public static byte[] fileImageToBytes(File fileImage) throws IOException, NullPointerException {
        if (fileImage == null) {
            throw new NullPointerException("No se encontro el archivo");
        }

        String extension = getExtensionFileImagen(fileImage);

        if (extension == null) {
            throw new IOException("No se pudo leer el archivo");
        }

        BufferedImage bufferImg;
        try {
            bufferImg = ImageIO.read(fileImage);
            if (bufferImg == null) {
                throw new IOException("El archivo no es una imagen válida");
            }
        } catch (IOException e) {
            throw new IOException("No se pudo encontrar el archivo");
        }

        byte[] imagenBytes = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferImg, extension, baos);
            imagenBytes = baos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IOException("No se pudo leer la imagen");
        }

        if (extension.equals("png")) {
            imagenBytes = pngBytesToJpgBytes(imagenBytes);
        } else if (!extension.equals("jpg") && !extension.equals("jpeg")) {
            throw new IOException("Formato de imagen no soportado");
        }
        return imagenBytes;
    }

}
