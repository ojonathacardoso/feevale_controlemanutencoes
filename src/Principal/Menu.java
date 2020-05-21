
package Principal;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Classe que auxilia na criação da barra de menus do sistema
 * @author Jonatha
 */
public class Menu
{
    /**
     * Adiciona um menu principal à barra
     * @param barra JMenuBar - Barra de menus
     * @param menu JMenu - Menu principal
     * @param localizacao String - Nome do menu a ser adicionado
     */
    public void adicionarMenu(JMenuBar barra, JMenu menu, String nome, String localizacao)
    {
        barra.add(menu);
        menu.setText(nome);
        menu.setIcon(TelaPrincipal.icone.retornarIconeMenu(localizacao));
    }
    
    /**
     * Adiciona um submenu a um menu principal
     * @param menu JMenu - Menu principal
     * @param menuItem JMenuItem - Submenu
     * @param localizacao String - Nome do menu a ser adicionado
     */
    public void adicionarMenu(JMenu menu, JMenuItem menuItem, String nome, String localizacao)
    {
        menu.add(menuItem);
        menuItem.setText(nome);
        menuItem.setIcon(TelaPrincipal.icone.retornarIconeMenu(localizacao));
    }

}
