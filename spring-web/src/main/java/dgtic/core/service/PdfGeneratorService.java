package dgtic.core.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import dgtic.core.model.Asiento;
import dgtic.core.model.Boleto;
import dgtic.core.model.Evento;
import dgtic.core.model.Zona;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class PdfGeneratorService {

    public byte[] generarBoletosPdf(List<Boleto> boletos) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
        Document doc = new Document(pdfDoc);
        DeviceRgb azulClaro = new DeviceRgb(220, 235, 255);


        for (int i = 0; i < boletos.size(); i++) {
            Boleto boleto = boletos.get(i);
            Asiento asiento = boleto.getAsientoEvento().getAsiento();
            Zona zona = asiento.getZona();
            Evento evento = boleto.getAsientoEvento().getEvento();
            String qrData = UUID.randomUUID().toString().substring(0, 12);
            BitMatrix matrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, 100, 100);
            ByteArrayOutputStream qrOutput = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", qrOutput);
            Image qrImage = new Image(ImageDataFactory.create(qrOutput.toByteArray()))
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            String logoPath = new ClassPathResource("static/images/logo_Eventia.png").getFile().getAbsolutePath();
            Image logo = new Image(ImageDataFactory.create(logoPath)).scaleToFit(60, 60)
                    .setHorizontalAlignment(HorizontalAlignment.LEFT);

            String posterPath = new ClassPathResource("static/images/" + evento.getPoster()).getFile().getAbsolutePath();
            Image poster = new Image(ImageDataFactory.create(posterPath)).scaleToFit(180, 180)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);

            Paragraph titulo = new Paragraph("BOLETO")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(16)
                    .setBold()
                    .setFontColor(ColorConstants.BLUE);
            Paragraph info = new Paragraph()
                    .add("Evento: " + evento.getNombreEvento() + "\n")
                    .add("Fecha: " + evento.getFecha() + "\n")
                    .add("Zona: " + zona.getNombreZona() + "\n")
                    .add("Asiento: " + asiento.getNumeroAsiento() + "\n")
                    .add("Precio: $" + zona.getPrecio())
                    .setFontSize(12)
                    .setMarginTop(10);

            Paragraph codigo = new Paragraph("Código: " + qrData)
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(10);

            Div contenedor = new Div()
                    .setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 1))
                    .setBackgroundColor(azulClaro)
                    .setPadding(15)
                    .setMarginBottom(20)
                    .setWidth(UnitValue.createPercentValue(100));

            contenedor.add(logo);
            contenedor.add(titulo);
            contenedor.add(info);
            contenedor.add(poster);
            contenedor.add(qrImage);
            contenedor.add(codigo);

            doc.add(contenedor);

            if (i < boletos.size() - 1) {
                doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }
        }


        doc.close();
        return outputStream.toByteArray();
    }
}
