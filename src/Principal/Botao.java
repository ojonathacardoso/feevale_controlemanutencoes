
package Principal;

import Icones.Icone;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Classe que auxilia na criação de botões usados nas telas
 * @author Jonatha
 */
public class Botao
{
    private static Icone icone = new Icone();
    
    /***
     * Cria e retorna um botão 
     * @param texto String - Texto exibido no botão
     * @param largura int - Largura do botão
     * @return JButton
     */
    public static JButton retornaBotao(String texto, int largura)
    {
        JButton botao = new JButton();
        botao.setLayout(new FlowLayout());
        botao.add(new JLabel(icone.retornarIconeMenu(texto)));
        botao.add(new JLabel(texto));
        botao.setPreferredSize(new Dimension(largura, 45));
        
        return botao;
    }
    
    public static JButton retornaBotao(String texto, String imagem, int largura)
    {
        JButton botao = new JButton();
        botao.setLayout(new FlowLayout());
        botao.add(new JLabel(icone.retornarIconeMenu(imagem)));
        botao.add(new JLabel(texto));
        botao.setPreferredSize(new Dimension(largura, 45));
        
        return botao;
    }
    
}
