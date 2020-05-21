package Icones;

import java.awt.Image;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Jonatha
 */
public class Icone
{
    public ImageIcon retornarIconeMenu(String localizacao)
    {
        try{
            InputStream input = getClass().getResourceAsStream("/Icones/"+localizacao+".png");
            Image image = ImageIO.read(input);
            ImageIcon imageIcon = new ImageIcon(image);
            return imageIcon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
    
    public static Icon retornarIconeBotao()
    {
        Icon redIcon = new ImageIcon("/Icones/Tarefas.png");
        return redIcon;
    }
}
