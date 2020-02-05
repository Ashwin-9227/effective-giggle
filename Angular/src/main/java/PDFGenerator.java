import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import boot.model.AptitudeValidateanswers;

public class PDFGenerator {

	public static void main(String[] args) throws IOException, DocumentException, URISyntaxException 
	{
		
		System.out.println(Paths.get(Paths.get("iTextTable.pdf").toAbsolutePath().normalize().toString()));
		
		AptitudeValidateanswers object = new AptitudeValidateanswers();
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("Field name = " + field.getName());
        }
		
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));

		document.open();

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		addTableHeader(table);
		addRows(table);
		//addCustomRows(table);

		Font f=new Font(FontFamily.TIMES_ROMAN,25.0f,Font.BOLD,BaseColor.BLUE);
		Paragraph p=new Paragraph("Aptitude survey Report",f);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		document.add(new Paragraph(" "));
		document.add(table);
		document.close();	
	}

	private static void addTableHeader(PdfPTable table) {
		Stream.of("column header 1", "column header 2", "column header 3")
		.forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
	}

	private static void addRows(PdfPTable table) {
		table.addCell("row 1, col 1");
	    table.addCell("row 1, col 2");
	    table.addCell("row 1, col 3");
	}

	private static void addCustomRows(PdfPTable table) 
			throws URISyntaxException, BadElementException, IOException {
		Path path = Paths.get(Paths.get("logo.jpg").toAbsolutePath().normalize().toString());
		Image img = Image.getInstance(path.toAbsolutePath().toString());
		img.scalePercent(2);

		PdfPCell imageCell = new PdfPCell(img);
		table.addCell(imageCell);

		PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
		horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell);

		PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
		verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell);
	}

}
