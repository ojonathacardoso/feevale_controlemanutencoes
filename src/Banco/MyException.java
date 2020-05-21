package Banco;

import javax.swing.JOptionPane;

/**
 * Classe que trata e exibe as exceções via JOptionPane
 * @author Jonatha
 */
public class MyException extends Exception
{	
    /** Versão do utilizada pelo serializador da classe */
    private static final long serialVersionUID = -6003018715297216583L;
    
    /**
     * Construtor que cria a exceção e exibe uma mensagem enviada
     * @param mensagem String - Mensagem da exceção
     */
    public MyException( String mensagem )
    {
        super( mensagem );
        JOptionPane.showMessageDialog(null, "O seguinte erro foi encontrado: \n " + mensagem);
    }

    /**
     * Construtor que cria a exceção e exibe seu erro
     * @param e Exception - Exceção
     */
    public MyException( Exception e )
    {
        super( e );
        JOptionPane.showMessageDialog(null, "O seguinte erro foi encontrado: \n " + e.getMessage());
    }
}