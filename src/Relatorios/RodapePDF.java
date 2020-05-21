package Relatorios;

import Principal.Data;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class RodapePDF extends PdfPageEventHelper {

    private String titulo = "";
    
    private Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL);


    /*
     * constructor
     */
    public RodapePDF(String titulo)
    {
        super();
        this.titulo = titulo;
    }


    @Override
    public void onEndPage(PdfWriter writer, Document document)
    {
        PdfContentByte cb = writer.getDirectContent();

        Phrase paginas = new Phrase( ("Página " + String.format("%d", writer.getPageNumber())), this.font );
        Phrase titulo = new Phrase( this.titulo, this.font );
        Phrase data = new Phrase( Data.retornarDataAtual(), this.font );
        
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, titulo, document.left()-30, document.bottom()-30, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, paginas, document.right()+20 , document.top()+30, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, data, document.right()+20, document.bottom()-30, 0);

    }

}