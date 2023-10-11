package it.informatica.configuratore.threads;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import it.informatica.configuratore.others.Common;
import it.informatica.configuratore.model.CarHandler;
import it.informatica.configuratore.model.UserHandler;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.FileOutputStream;

public class PdfGenerator extends Thread{

    private final File file;
    private final int colore, cerchi, interni;
    private final String prezzo;
    private final ProgressBar progressIndicator;
    private final Button export;

    private final String insideN = CarHandler.getInstance().getCar().getInside();
    private final String OutsideImagePath = CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + CarHandler.getInstance().getCar().getColour() + File.separator + CarHandler.getInstance().getCar().getWheels() + File.separator;
    private final String insideImagePath = CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + "interni" + File.separator + insideN + File.separator;
    private final String FirstOutsideImagePath = OutsideImagePath + "1" + Common.IMAGE_EXTENSION;
    private final String FirstinsideImagePath = insideImagePath+ "1" + Common.IMAGE_EXTENSION;
    private int space = 0;


    public PdfGenerator(File file, int colore, int cerchi, int interni, String prezzo, ProgressBar progress, Button export) {
        this.file = file;
        this.colore = colore;
        this.cerchi = cerchi;
        this.interni = interni;
        this.prezzo = prezzo;
        this.progressIndicator = progress;
        this.export = export;
    }

    @Override
    public void run() {
        try {
            Platform.runLater(() -> progressIndicator.setVisible(true));
            Platform.runLater(() -> progressIndicator.setCursor(Cursor.WAIT));
            Platform.runLater(() -> progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS));

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            document.addAuthor(Common.NAME);
            document.addCreationDate();
            document.addCreator(Common.NAME);
            document.addTitle("Configurazione");
            document.addSubject(CarHandler.getInstance().getCar().getMarca() + " " + CarHandler.getInstance().getCar().getModello());

            if(UserHandler.getInstance().isLogged()){
                Paragraph user = new Paragraph(Common.NAME + " | " + UserHandler.getInstance().getU().getUsername());
                user.setAlignment(Element.ALIGN_RIGHT);
                document.add(user) ;
                space = 30;
            }
            Paragraph p = new Paragraph("LA TUA CONFIGURAZIONE", new Font(Font.FontFamily.HELVETICA, 20));
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            Font f = new Font();
            f.setStyle( Font.BOLD) ;
            f.setSize(8);

            com.itextpdf.text.Image a = com.itextpdf.text.Image.getInstance(FirstOutsideImagePath);
            com.itextpdf.text.Image b = com.itextpdf.text.Image.getInstance(FirstinsideImagePath);

            Paragraph p1 = new Paragraph("Scelte effettuate:", new Font(Font.FontFamily.HELVETICA, 13));

            com.itextpdf.text.Image c = switch (CarHandler.getInstance().getCar().getColour()) {
                case "colore-1" -> com.itextpdf.text.Image.getInstance(CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + "colori" + File.separator + "1.png");
                case "colore-2" -> com.itextpdf.text.Image.getInstance(CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + "colori" + File.separator + "2.png");
                case "colore-3" -> com.itextpdf.text.Image.getInstance(CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + "colori" + File.separator + "3.png");
                default -> null;
            };
            assert c != null;
            c.scalePercent(30);

            //regola le dimensioni delle immagini più piccole(di Audi)
            if("Audi".equals(CarHandler.getInstance().getCar().getMarca())){
                a.scalePercent(50);
                a.setAbsolutePosition((document.getPageSize().getRight()-a.getWidth()*0.5f)/2, document.getPageSize().getTop()-(a.getHeight()*0.5f)-80-space);

                b.scalePercent(50);
                b.setAbsolutePosition((document.getPageSize().getRight()-b.getWidth()*0.5f)/2, document.getPageSize().getTop()-(a.getHeight()*0.5f)-80-space-b.getHeight()*0.5f);

                p1.setSpacingBefore((a.getHeight()*0.5f)+(b.getHeight()*0.5f)+40);

                c.setAbsolutePosition((document.getPageSize().getLeft() + 35), document.getPageSize().getTop() - (a.getHeight()*0.5f) - (b.getHeight()*0.5f) - 185 - space);
            }
            //altrimenti dimensioni standard per le immagini
            else {
                a.scalePercent(22);
                a.setAbsolutePosition((document.getPageSize().getRight()-a.getWidth()*0.22f)/2, document.getPageSize().getTop()-(a.getHeight()*0.22f)-80-space);

                b.scalePercent(22);
                b.setAbsolutePosition((document.getPageSize().getRight()-b.getWidth()*0.22f)/2, document.getPageSize().getTop()-(a.getHeight()*0.22f)-80-space-b.getHeight()*0.22f);

                p1.setSpacingBefore((a.getHeight()*0.22f)+(b.getHeight()*0.22f)+40);

                c.setAbsolutePosition((document.getPageSize().getLeft() + 35), document.getPageSize().getTop() - (a.getHeight()*0.22f) - (b.getHeight()*0.22f) - 185 - space);
            }

            p1.setSpacingAfter(30);

            document.add(a);
            document.add(b);

            document.add(p1);


            document.add(c);

            com.itextpdf.text.Image d = switch (CarHandler.getInstance().getCar().getWheels()) {
                case "cerchio-1" -> com.itextpdf.text.Image.getInstance(CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + "cerchi" + File.separator + "cerchio-1.png");
                case "cerchio-2" -> com.itextpdf.text.Image.getInstance(CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + "cerchi" + File.separator + "cerchio-2.png");
                case "cerchio-3" -> com.itextpdf.text.Image.getInstance(CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + "cerchi" + File.separator + "cerchio-3.png");
                default -> null;
            };
            assert d != null;
            d.scalePercent(60);
            d.setAbsolutePosition((c.getAbsoluteX() + (c.getWidth()*0.3f) + 25), c.getAbsoluteY());

            document.add(d);

            com.itextpdf.text.Image e = com.itextpdf.text.Image.getInstance(CarHandler.getInstance().getCar().getGlobalImgPath() + File.separator + "interni" + File.separator + insideN + Common.IMAGE_EXTENSION);
            e.scalePercent(25);
            e.setAbsolutePosition((d.getAbsoluteX() + d.getWidth() + 5), d.getAbsoluteY());

            document.add(e);

            Paragraph p2 = new Paragraph(colore + "_" + cerchi + "_" + interni, new Font(Font.FontFamily.HELVETICA, 7));
            p2.setAlignment(Element.ALIGN_RIGHT);
            p2.setSpacingBefore(0);
            document.add(p2);

            Paragraph prezzoC = new Paragraph(prezzo, new Font(Font.FontFamily.HELVETICA, 30));
            prezzoC.setAlignment(Element.ALIGN_CENTER);
            prezzoC.setSpacingBefore(20);
            document.add(prezzoC);

            document.close();

            Thread.sleep(3000);

            Platform.runLater(() -> progressIndicator.setVisible(false));
            Platform.runLater(() -> export.setText("Esportato"));

            FontIcon check = new FontIcon(Common.CHECK_ICON);
            check.setIconSize(20);

            Platform.runLater(() -> export.setGraphic(check));
            Platform.runLater(() -> export.setOnAction(null));
            Platform.runLater(() -> export.setCursor(Cursor.NONE));


            Thread.sleep(3000);
            export.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
