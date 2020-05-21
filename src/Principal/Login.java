
package Principal;

import Banco.MyException;
import Configuracoes.OperadorLocalizador;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Jonatha
 */
public class Login
{
    public static boolean acessar(JFrame frame) throws MyException
    {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        
        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("Usuário:", SwingConstants.RIGHT));
        label.add(new JLabel("Senha:", SwingConstants.RIGHT));
        
        JPanel controles = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField usuario = new JTextField();
        controles.add(usuario);
        JPasswordField senha = new JPasswordField();
        controles.add(senha);
        
        painel.add(label, BorderLayout.WEST);
        painel.add(controles, BorderLayout.CENTER);
        
        String[] opcoes = {"Acessar", "Sair"};
        
        int opcao = JOptionPane.showOptionDialog(
                                frame,
                                painel,
                                "Acesso ao sistema",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                opcoes,
                                JOptionPane.OK_OPTION);
        
        boolean login;
        
        do {
            if(opcao == JOptionPane.YES_OPTION )
            {
                if(usuario.getText().trim().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Usuário em branco!");
                    login = false;
                }
                else if(new String(senha.getPassword()).trim().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Senha em branco!");
                    login = false;
                }
                else
                {
                    if(OperadorLocalizador.buscaOperadorLogin( usuario.getText(), new String( senha.getPassword() ) ))
                    {
                        TelaPrincipal.setIdOperador(OperadorLocalizador.buscaOperadorId(usuario.getText()));
                        login = true;
                        return true;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos");
                        login = false;
                    }
                }
                
                if(! login)
                {
                    opcao = JOptionPane.showOptionDialog(
                                frame,
                                painel,
                                "Acesso ao sistema",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                opcoes,
                                JOptionPane.OK_OPTION);
                }
            }
            
        } while(opcao != JOptionPane.NO_OPTION);
        
        return false;
    }
}
