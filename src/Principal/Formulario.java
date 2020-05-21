
package Principal;

import Banco.MyException;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 * Classe que auxilia na criação de campos usados nos formulários
 * @author Jonatha
 */
public class Formulario
{    
    private static int xLabel = 20;
    private static int xCampo = 160;
    
    /**
     * Cria e retorna um campo de texto
     * @param largura int - Largura do campo
     * @return JTextField
     */
    public static JTextField retornaCaixaTexto(int largura)
    {
        JTextField caixaTexto = new JTextField();
        caixaTexto.setPreferredSize(new Dimension(largura, 25));        
        return caixaTexto;
    }
    
    /**
     * Cria e retorna um campo de texto posicionado na tela
     * @param largura int - Largura do campo
     * @param y int - Posição do campo na vertical
     * @return JTextField
     */
    public static JTextField retornaCaixaTexto(int largura, int y)
    {
        JTextField caixaTexto = new JTextField();
        caixaTexto.setPreferredSize(new Dimension(largura, 25));
        caixaTexto.setBounds(xCampo, y+5, largura, 25);
        return caixaTexto;
    }
    
    /**
     * Cria e retorna um campo de texto posicionado na tela
     * @param largura int - Largura do campo
     * @param y int - Posição do campo na vertical
     * @return JTextField
     */
    public static JTextArea retornaAreaTexto(int largura, int y)
    {
        JTextArea areaTexto = new JTextArea();
        areaTexto.setPreferredSize(new Dimension(largura, 100));
        areaTexto.setBounds(xCampo, y+5, largura, 100);
        areaTexto.setLineWrap(true);
        areaTexto.setBorder(new EtchedBorder());
        return areaTexto;
    }
    
    /**
     * 
     * @param opcoes
     * @param largura int - Largura do campo
     * @return JComboBox
     * @throws MyException
     */
    public static JComboBox retornaComboBox(String[] opcoes, int largura)
    {
        JComboBox comboBox = new JComboBox(opcoes);
        comboBox.setPreferredSize(new Dimension(largura, 25));
        return comboBox;
    }
    
    /**
     * 
     * @param opcoes
     * @param largura
     * @param y
     * @return
     * @throws MyException 
     */
    public static JComboBox retornaComboBox(String[] opcoes, int largura, int y)
    {
        JComboBox comboBox = new JComboBox(opcoes);
        comboBox.setPreferredSize(new Dimension(largura, 25));
        comboBox.setBounds(xCampo, y+5, 150, 25);
        return comboBox;
    }
    
    /**
     * Cria e retorna uma caixa de seleção usando um ArrayList
     * @param modelo
     * @param largura int - Largura do campo
     * @return 
     * @throws MyException
     */
    public static JComboBox retornaComboBox(DefaultComboBoxModel modelo, int largura) throws MyException
    {
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(modelo);
        comboBox.setPreferredSize(new Dimension(largura, 25));
        return comboBox;
    }
    
    /**
     * 
     * @param modelo
     * @param largura
     * @param y
     * @return
     * @throws MyException 
     */
    public static JComboBox retornaComboBox(DefaultComboBoxModel modelo, int largura, int y) throws MyException
    {
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(modelo);
        comboBox.setPreferredSize(new Dimension(largura, 25));
        comboBox.setBounds(xCampo, y+5, 150, 25);
        return comboBox;
    }
    
    /**
     * Cria e retorna um campo de números
     * @param largura int - Largura do campo
     * @return JTextField
     */
    public static JTextField retornaCaixaNumeros(int largura)
    {
        JTextField caixaNumeros = new JTextField();
        caixaNumeros.setPreferredSize(new Dimension(largura, 25));
        caixaNumeros.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent ev)
            {
                String caracteres = "0987654321";
                if(! caracteres.contains(ev.getKeyChar()+""))
                {
                    ev.consume();
                }
            }
        });
        
        return caixaNumeros;
    }
    
    /**
     * Cria e retorna um campo de números posicionado na tela
     * @param largura int - Largura do campo
     * @param y int - Posição do campo na vertical
     * @return JTextField
     */
    public static JTextField retornaCaixaNumeros(int largura, int y)
    {
        JTextField caixaNumeros = new JTextField();
        caixaNumeros.setPreferredSize(new Dimension(largura, 25));        
        caixaNumeros.setBounds(xCampo, y+5, largura, 25);
        caixaNumeros.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent ev)
            {
                String caracteres = "0987654321";
                if(! caracteres.contains(ev.getKeyChar()+""))
                {
                    ev.consume();
                }
            }
        });
        return caixaNumeros;
    }
    
    /**
     * Cria e retorna um campo de senha
     * @param largura int - Largura do campo
     * @return JPasswordField
     */
    public static JPasswordField retornaCaixaSenha(int largura)
    {
        JPasswordField caixaSenha = new JPasswordField();
        caixaSenha.setPreferredSize(new Dimension(largura, 25));        
        return caixaSenha;
    }
    
    /**
     * Cria e retorna um campo de senha posicionado na tela
     * @param largura int - Largura do campo
     * @param y int - Posição do campo na vertical
     * @return JPasswordField
     */
    public static JPasswordField retornaCaixaSenha(int largura, int y)
    {
        JPasswordField caixaSenha = new JPasswordField();
        caixaSenha.setPreferredSize(new Dimension(largura, 25));
        caixaSenha.setBounds(xCampo, y+5, largura, 25);
        return caixaSenha;
    }

    /**
     * Cria e retorna uma label para um campo 
     * @param texto String - Texto da label
     * @return JLabel
     */
    public static JLabel retornaLabel(String texto)
    {
        JLabel label = new JLabel("<html><font color='blue'>"+texto+"</font></html>");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
    
    /**
     * Cria e retorna uma label para um campo posicionada na tela
     * @param texto String - Texto da label
     * @param y int - Posição do campo na vertical
     * @return JLabel
     */
    public static JLabel retornaLabel(String texto, int y)
    {
        JLabel label = new JLabel("<html><font color='blue'>"+texto+"</font></html>");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setBounds(xLabel, y, 100, 30);
        return label;
    }
    
    /**
     * Cria e retorna uma label para um campo obrigatório
     * @param texto String - Texto da label
     * @param y int - Posição do campo na vertical
     * @return JLabel
     */
    public static JLabel retornaLabelObrigatoria(String texto, int y)
    {
        JLabel label = new JLabel("<html><font color='red'>"+texto+"</font></html>");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setBounds(xLabel, y, 100, 30);
        return label;
    }
    
    /**
     * Cria e retorna uma label para uso como legenda
     * @param texto String - Texto da label
     * @param x int - Posição do campo na horizontal
     * @param y int - Posição do campo na vertical
     * @param cor String - Cor do campo
     * @return JLabel
     */
    public static JLabel retornaLabelLegenda(String texto, int x, int y, String cor)
    {
        JLabel label = new JLabel("<html><font color='"+cor+"'>"+texto+"</font></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 11));
        label.setBounds(x, y, 200, 30);
        return label;
    }
    
    /**
     * Cria e retorna um campo para seleção de data
     * @return JDateChooser
     */
    public static JDateChooser retornaCaixaData()
    {
        JDateChooser data = new JDateChooser();
        return data;
    }
    
    /**
     * Cria e retorna um campo para seleção de data posicionado na tela
     * @param y int - Posição do campo na vertical
     * @return JDateChooser
     */
    public static JDateChooser retornaCaixaData(int y)
    {
        JDateChooser data = new JDateChooser();
        data.setBounds(xCampo, y, 100, 25);
        return data;
    }
}