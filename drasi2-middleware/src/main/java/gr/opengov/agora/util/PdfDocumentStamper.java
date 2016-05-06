package gr.opengov.agora.util;

import gr.opengov.agora.exceptions.InternalErrorException;
import gr.opengov.agora.exceptions.PdfPasswordException;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.exceptions.BadPasswordException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PdfDocumentStamper implements IDocumentStamper {
	private static final Logger logger = LoggerFactory.getLogger(PdfDocumentStamper.class);

	private static int alignment = Element.ALIGN_RIGHT;

	private String fontName = "/fonts/arial.ttf";
	private String fontEncoding = "Identity-H";
	private Integer fontSize = 12;
	private Integer marginRight = 26;
	private Integer marginTop = 22+fontSize*2; //added 24 so as not to override the ADA from diavgeia in case a document is stamped also in diavgeia
	private Boolean addBackground = true;

	private String fortifyStamp(String txt) {
		// return "#" + txt + "#";
		return txt;
	}

	private String processStamp(String txt) {
		return "ΑΔΑ ΜΗΤΡΩΟΥ: " + txt;
	}

	private void addBackground(PdfContentByte page, Rectangle rect,
			BaseFont font, String text) {
		final float paddingX = 2;
		final float paddingY = 2;
		float width = font.getWidthPointKerned(text, fontSize);
		float height = 2 * font.getAscentPoint(text, fontSize)
				- font.getDescentPoint(text, fontSize) + paddingY;
		float x = rect.getWidth() - marginRight - width;
		float y = rect.getHeight() - marginTop;
		page.setColorFill(Color.white);
		page.rectangle(x - paddingX, y - paddingY, width + 2 * paddingX, height
				+ 2 * paddingY);
		page.fill();
	}

	@Override
	public byte[] stamp(byte[] data, String stamp) {
		try {
			PdfReader reader = new PdfReader(data);
			stamp = processStamp(fortifyStamp(stamp));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, baos);
			int pages = reader.getNumberOfPages();
			BaseFont bf = BaseFont.createFont(fontName, fontEncoding, true);
			for (int n = 1; n <= pages; n++) {
				PdfContentByte page = stamper.getOverContent(n);
				Rectangle rect = reader.getPageSizeWithRotation(n);
				if (addBackground) {
					addBackground(page, rect, bf, stamp);
				}
				page.beginText();
				page.setFontAndSize(bf, fontSize);
				page.setColorFill(Color.black);
				page.showTextAligned(alignment, stamp,
						rect.getRight(marginRight), rect.getTop(marginTop), 0);
				page.endText();
			}
			stamper.close();
			byte[] output = baos.toByteArray();
			return output;
		} catch ( BadPasswordException e ) {
			throw new PdfPasswordException( "PDF has a password: " + stamp );
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalErrorException("Error stamping PDF document: " + stamp );
		}
	}
}
