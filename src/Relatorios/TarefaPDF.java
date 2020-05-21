
package Relatorios;

import Banco.MyException;
import Cadastros.Maquina;
import Cadastros.MaquinaLocalizador;
import Manutencoes.Atividade;
import Manutencoes.AtividadeLocalizador;
import Manutencoes.Tarefa;
import Manutencoes.TarefaLocalizador;
import Manutencoes.Trabalho;
import Manutencoes.TrabalhoLocalizador;
import Principal.Data;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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

public class TarefaPDF
{
    private static JFileChooser arquivoJanela;
    private static Document arquivoPDF;
    private static String arquivoLocalizacao;
    private static String nome = "Formulário de conferência";
    
    public static void gerarFormularioConferencia(Atividade atividade) throws FileNotFoundException, DocumentException, IOException, MyException
    {
        if(abrirJanela())
        {
            arquivoPDF = new Document(PageSize.A4, 56, 56, 56, 56);
            
            String arquivoNome = "/Formulario_Conferencia_" + Data.retornarDataHoraAtual() + ".pdf";
            arquivoLocalizacao = arquivoJanela.getSelectedFile().getAbsolutePath() + arquivoNome;
            
            try{            
                OutputStream os = new FileOutputStream(arquivoLocalizacao);
                
                PdfWriter writer = PdfWriter.getInstance(arquivoPDF, os);
                writer.setPageEvent( new RodapePDF( nome ) );
                
                arquivoPDF.open();

                carregarArquivo(atividade);

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
    
    public static void gerarFormulariosConferencia(Trabalho trabalho) throws FileNotFoundException, DocumentException, IOException, MyException
    {
        if(abrirJanela())
        {
            arquivoPDF = new Document(PageSize.A4, 56, 56, 56, 56);
            
            String arquivoNome = "/Formulario_Conferencias_" + Data.retornarDataHoraAtual() + ".pdf";
            arquivoLocalizacao = arquivoJanela.getSelectedFile().getAbsolutePath() + arquivoNome;
            
            try{            
                OutputStream os = new FileOutputStream(arquivoLocalizacao);
                
                PdfWriter writer = PdfWriter.getInstance(arquivoPDF, os);
                writer.setPageEvent( new RodapePDF( nome ) );
                
                arquivoPDF.open();

                for(Atividade a : AtividadeLocalizador.buscaProFormulario(trabalho.getId()))
                {
                    carregarArquivo(a);
                    arquivoPDF.newPage();
                }                

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
    
    private static void carregarArquivo(Atividade atividade) throws DocumentException, MyException
    {
        Font tituloFonte = new Font(FontFamily.HELVETICA, 11, Font.BOLD);
        Font subtituloFonte = new Font(FontFamily.HELVETICA, 11, Font.NORMAL);
        
        Font textoFonteNormal = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
        Font textoFonteNegrito = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
        
        Paragraph titulo = new Paragraph( nome, tituloFonte );
        titulo.setAlignment(Element.ALIGN_CENTER);
        
        Paragraph subtitulo = new Paragraph( atividade.getTrabalhoTexto(), subtituloFonte );
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(20);
        
        Maquina maquina = MaquinaLocalizador.buscaMaquina(atividade.getMaquina());
        
        Paragraph maquinaNome = new Paragraph();
        maquinaNome.add(new Phrase("Máquina: ", textoFonteNegrito));
        maquinaNome.add(new Phrase(maquina.getNome(), textoFonteNormal));

        Paragraph maquinaModelo = new Paragraph();
        maquinaModelo.add(new Phrase("Marca/modelo: ", textoFonteNegrito));
        maquinaModelo.add(new Phrase(maquina.getModeloMaquinaTexto(), textoFonteNormal));
        
        Paragraph maquinaLocalizacao = new Paragraph();
        maquinaLocalizacao.add(new Phrase("Localização: ", textoFonteNegrito));
        maquinaLocalizacao.add(new Phrase(maquina.getLocalizacaoTexto(), textoFonteNormal));
        
        Paragraph maquinaUsuario = new Paragraph();
        maquinaUsuario.add(new Phrase("Usuário: ", textoFonteNegrito));
        maquinaUsuario.add(new Phrase(maquina.getUsuario(), textoFonteNormal));
        maquinaUsuario.setSpacingAfter(30);

        PdfPTable tabela = new PdfPTable(new float[] { 0.07f, 0.93f });
        tabela.setWidthPercentage(100);
        tabela.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabela.setSpacingAfter(40);

        for(Tarefa t : TarefaLocalizador.buscaTarefasAtividade(atividade.getId()))
        {
            PdfPCell c1 = new PdfPCell(new Paragraph() );
            c1.setPadding(5);            
            tabela.addCell(c1);
            
            PdfPCell c2 = new PdfPCell(new Paragraph( t.getTarefaTexto(), textoFonteNormal ) );
            c2.setPadding(5);            
            tabela.addCell(c2);
        }
        
        Paragraph linhaAssinatura = new Paragraph( "Técnico: ________________________________", textoFonteNormal );
        linhaAssinatura.setSpacingAfter(10);
        Paragraph linhaData = new Paragraph( "Data da atividade: _____/_____/_________", textoFonteNormal );        
        
        arquivoPDF.add(titulo);
        arquivoPDF.add(subtitulo);
        
        arquivoPDF.add(maquinaNome);
        arquivoPDF.add(maquinaModelo);
        arquivoPDF.add(maquinaLocalizacao);
        arquivoPDF.add(maquinaUsuario);
        
        arquivoPDF.add(tabela);
        
        arquivoPDF.add(linhaAssinatura);
        arquivoPDF.add(linhaData);        
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
