package Principal;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;

/**
 * Classe que cria o JInternalFrame personalizado
 * @author Jonatha
 */
public class TelaInterna extends JInternalFrame
{
    /**
     * Método construtor
     * @param titulo String - Título da janela
     * @param icone String - Ícone a ser usado na janela
     */
    public TelaInterna(String titulo, String icone)
    {
        setTitle(titulo);
        setLayout(new BorderLayout());
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon( TelaPrincipal.icone.retornarIconeMenu(icone) );
        setSize( TelaPrincipal.largura-200, TelaPrincipal.altura-200);            
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    }
}
