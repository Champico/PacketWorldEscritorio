package packetworldescritorio.utilidad;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;

import java.io.FileOutputStream;
import javafx.stage.FileChooser;
import packetworldescritorio.pojo.Cliente;
import packetworldescritorio.pojo.Envio;
import packetworldescritorio.pojo.Sucursal;

public class PdfCreator {

    private static final float ANCHO_TICKET_CM = 8.0f;
    private static final float ALTO_TICKET_CM = 16.0f;
    private static final float PUNTOS_POR_CM = 28.35f;

    public static void generarTicket(Envio envio, Cliente cliente, Sucursal sucursal) {

        try {
            //Paso 1: Configurar tamaño del ticket
            float anchoTicket = ANCHO_TICKET_CM * PUNTOS_POR_CM;
            float altoTicket = ALTO_TICKET_CM * PUNTOS_POR_CM;
            Rectangle pageSize = new Rectangle(anchoTicket, altoTicket);
            Document document = new Document(pageSize, 10, 10, 10, 10);

            
            //Paso 2: Buscar la dirección para guardar el archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar ticket");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
            fileChooser.setInitialFileName("ticket_envio.pdf");
            File archivo = fileChooser.showSaveDialog(null);

            if (archivo == null) {
                return;
            }

            //Paso 3: Crear instancia del PdfWrriter
            PdfWriter.getInstance(document, new FileOutputStream(archivo));

            
            //Paso 4: Abrir el flujo de datos para construir el pdf
            document.open();

            //Paso 5: Definimos las tipografias
            Font tituloEmpresa = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font subtitulo = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
            Font normal = new Font(Font.FontFamily.HELVETICA, 8);
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            Paragraph empresa = new Paragraph("PACKET WORLD", tituloEmpresa);
            empresa.setAlignment(Element.ALIGN_CENTER);
            document.add(empresa);

            //document.add(new Paragraph(" "));

            Paragraph suc = new Paragraph(
                    sucursal.getNombre() + "\n"
                    + sucursal.getCalle() + " " + sucursal.getNumero() + ", "
                    + sucursal.getColonia() + "\n"
                    + sucursal.getCiudad() + ", " + sucursal.getEstado(),
                    normal
            );
            suc.setAlignment(Element.ALIGN_CENTER);
            document.add(suc);

            //document.add(new Paragraph(" "));

            Paragraph clienteTxt = new Paragraph(
                    "Cliente:"
                    + cliente.getNombre() + " "
                    + cliente.getApellidoPaterno() + " "
                    + cliente.getApellidoMaterno(),
                    normal
            );
            document.add(clienteTxt);

            //document.add(new Paragraph(" "));
            
             Paragraph guiaTxt = new Paragraph(
                    "Envío:"
                    + envio.getNumeroGuia() + " ",
                    normal
            );
            document.add(guiaTxt);

            
            //document.add(new Paragraph(" "));
            
            LineSeparator linea = new LineSeparator();
            linea.setLineWidth(0.5f);
            document.add(new Chunk(linea));

            /*
            .format de un String
                %s              String
                %d              Entero (int, Integer)
                %f              Decimal (float, double)
                %.2f            Decimal con 2 decimales
                %n              Salto de línea
            */
            Paragraph costo = new Paragraph(
                    "Costo de envío: $ " + String.format("%.2f", envio.getCosto()),
                    subtitulo
            );
            costo.setAlignment(Element.ALIGN_CENTER);
            document.add(costo);

            document.add(new Paragraph(" "));

            Paragraph total = new Paragraph(
                    "TOTAL\n$ " + String.format("%.2f", envio.getCosto()),
                    totalFont
            );
            total.setAlignment(Element.ALIGN_CENTER);
            document.add(total);

            document.add(new Paragraph(" "));
            document.add(new Chunk(linea));
            document.add(new Paragraph(" "));

            Paragraph gracias = new Paragraph(
                    "Gracias por su preferencia",
                    normal
            );
            gracias.setAlignment(Element.ALIGN_CENTER);
            document.add(gracias);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
