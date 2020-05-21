
package Relatorios;

import Modelos.AtividadeListaTableModel;
import Principal.Data;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AtividadePDF
{
    private static JFileChooser arquivoJanela;
    private static Document arquivoPDF;
    private static String arquivoLocalizacao;
    private static AtividadeListaTableModel tabelaModelo;
    private static String nome = "Relatório de atividades";
    
    public static void gerarRelatorioAtividade(AtividadeListaTableModel tm) throws FileNotFoundException, DocumentException, IOException
    {
        if(abrirJanela())
        {
            arquivoPDF = new Document(PageSize.A4.rotate(), 56, 56, 56, 56);
            
            String arquivoNome = "/Relatorio_Atividade_" + Data.retornarDataHoraAtual() + ".pdf";
            arquivoLocalizacao = arquivoJanela.getSelectedFile().getAbsolutePath() + arquivoNome;
            
            try{            
                OutputStream os = new FileOutputStream(arquivoLocalizacao);
                
                PdfWriter writer = PdfWriter.getInstance(arquivoPDF, os);
                writer.setPageEvent( new RodapePDF( nome ) );
                
                arquivoPDF.open();

                tabelaModelo = tm;
                
                carregarArquivo();

                if (arquivoPDF != null) {
                    arquivoPDF.close();
                }

                if (os != null) {
                    os.close();
                }

                if(! arquivoPDF.isOpen()) {
                    abrirArquivo();
                }
            } catch(FileNotFoundException f) {
                JOptionPane.showMessageDialog(null, "Verifique se o arquivo não está aberto!");
            }
            
        }
    }
    
    private static void carregarArquivo() throws DocumentException
    {
        Font tituloFonte = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
        Font cabecalhoFonte = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
        Font tabelaFonte = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
        
        Paragraph titulo = new Paragraph( nome, tituloFonte );
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);

        PdfPTable tabela = new PdfPTable(new float[] { 0.2f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.3f });
        tabela.setWidthPercentage(100);
        tabela.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabela.setSpacingAfter(10);

        for(int x = 0; x < tabelaModelo.getColumnCount(); x++)
        {
            Paragraph frase = new Paragraph(tabelaModelo.getColumnName(x), cabecalhoFonte);
            frase.setAlignment(Element.ALIGN_CENTER);
            
            PdfPCell cabecalho = new PdfPCell();
            cabecalho.addElement(frase);
            
            tabela.addCell(cabecalho);
        }

        int contagem = 0;
        
        for(int x = 0; x < tabelaModelo.getRowCount(); x++)
        {
            for(int y = 0; y < tabelaModelo.getColumnCount(); y++)
            {
                String texto = "";
                
                if (tabelaModelo.getValueAt(x, y) != null)
                    texto = tabelaModelo.getValueAt(x, y).toString();                    
                
                tabela.addCell(new Paragraph(texto, tabelaFonte) );
            }
            contagem++;
        }

        Paragraph total = new Paragraph( "Total de registros: "+contagem, tabelaFonte );
        total.setAlignment(Element.ALIGN_LEFT);
        
        arquivoPDF.add(titulo);
        arquivoPDF.add(tabela);
        arquivoPDF.add(total);
    }
    
    private static void abrirArquivo()
    {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(arquivoLocalizacao);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível abrir o arquivo gerado. Abra manualmente.");
            }
        }
    }
    
    private static boolean abrirJanela()
    {
        arquivoJanela = new JFileChooser();

        arquivoJanela.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        arquivoJanela.setAcceptAllFileFilterUsed(true);

        int acao = arquivoJanela.showSaveDialog(null);
        
        switch(acao)
        {
            case JFileChooser.APPROVE_OPTION:
                return true;
            case JFileChooser.CANCEL_OPTION:
                return false;
            case JFileChooser.ERROR_OPTION:
                return false;
            default:
                return false;
        }

    }
}