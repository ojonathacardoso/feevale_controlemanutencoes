package Principal;

import Banco.MyException;
import java.text.ParseException;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe principal, que inicia o programa
 * @author Jonatha
 */
public class Principal
{    
    public static void main(String[] args) throws UnsupportedLookAndFeelException, MyException, ParseException
    {        
        new TelaPrincipal();
        
    }
}
