package Configuracoes;

import Banco.MyException;
import Principal.Botao;
import Principal.Criptografia;
import Principal.Formulario;
import Principal.TelaInterna;
import Principal.TelaPrincipal;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * Classe que controla as telas e componentes do menu Operador
 * @author Jonatha
 */
public class SenhaTela extends TelaInterna
{
    /**
     * Construtor da classe, que cria o painel e seus componentes.
     * @throws MyException 
     */
    public SenhaTela() throws MyException
    {
        super(getTitulo(), getArquivo());
        
        this.criaPainel();
        this.criaFormulario();
        this.exibeFormulario();
        /*this.addInternalFrameListener(new InternalFrameListener() {

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });*/
        
        getContentPane().add(this.painel);
    }
    
    private JPanel painel;

    private JPanel painelFormularioCampos;
    private JPanel painelFormularioBotoes;
    
    private JButton botaoSalvar;
    private JButton botaoLimpar;

    private JTextField senhaAntiga;
    private JTextField senhaNova1;
    private JTextField senhaNova2;

    /**
     * Cria o painel aonde serão colocados os componentes.
     */
    private void criaPainel()
    {
        this.painel = new JPanel();
        this.painel.setLayout(new BorderLayout());
    }
   
    ///////////////////////////////////////////////////////////////////
    
    /**
     * Chama os métodos que criam os componentes que exibem o formulário
     * @throws MyException 
     */
    public void criaFormulario() throws MyException
    {     
        this.criaFormularioCampos();
        this.criaFormularioBotoes();
    }
    
    /**
     * Cria os campos do formulário
     */
    private void criaFormularioCampos()
    {
        this.painelFormularioCampos = new JPanel();
        this.painelFormularioCampos.setLayout(null);

        this.senhaAntiga = Formulario.retornaCaixaSenha(180, 20);    
        this.senhaNova1 = Formulario.retornaCaixaSenha(180, 60); 
        this.senhaNova2 = Formulario.retornaCaixaSenha(180, 100); 
        
        this.painelFormularioCampos.add(Formulario.retornaLabel("Senha antiga: ", 20));
        this.painelFormularioCampos.add(this.senhaAntiga);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria("Nova senha: ", 60));
        this.painelFormularioCampos.add(this.senhaNova1);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria("Confirmar: ", 100));
        this.painelFormularioCampos.add(this.senhaNova2);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("* Campos obrigatórios", 20, 140, "red"));
    }
    
    /**
     * Limpa os campos do formulário.
     * Caso esteja numa alteração, o código não é limpo.
     */
    public void limpaFormulario()
    {
        this.senhaAntiga.setText("");
        this.senhaNova1.setText("");
        this.senhaNova2.setText("");
    }
    
    /**
     * Cria os botões de ações no formulário.
     */
    private void criaFormularioBotoes()
    {
        this.painelFormularioBotoes = new JPanel();
        
        this.botaoSalvar = Botao.retornaBotao("Salvar", 120);
        this.botaoLimpar = Botao.retornaBotao("Limpar", 120);
        
        this.botaoSalvar.addActionListener(new SaveListener());
        this.botaoLimpar.addActionListener(new CleanListener());

        this.painelFormularioBotoes.add(this.botaoSalvar);
        this.painelFormularioBotoes.add(this.botaoLimpar);
    }
    
    private void exibeFormulario()
    {
        this.painel.add(BorderLayout.CENTER, this.painelFormularioCampos);        
        this.painel.add(BorderLayout.SOUTH, this.painelFormularioBotoes);
        
        this.painel.revalidate();
        this.painel.repaint();
    }
    
    /**
     * Ações executadas quando o botão "Salvar" do formulário é clicado
     */
    private class SaveListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if( senhaAntiga.getText().trim().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Informe a senha antiga");
                senhaAntiga.grabFocus();
            }
            else if( senhaNova1.getText().trim().equals("") || senhaNova1.getText().trim().length() < 8)
            {
                JOptionPane.showMessageDialog(null, "Informe a nova senha, com no mínimo 8 caracteres");
                senhaNova1.grabFocus();
            }
            else if(senhaNova1.getText().trim().length() > 45)
            {
                JOptionPane.showMessageDialog(null, "Informe a nova senha com no máximo 45 caracteres");
                senhaNova1.grabFocus();
            }
            else if( ! senhaNova2.getText().equals(senhaNova1.getText()) )
            {
                JOptionPane.showMessageDialog(null, "A confirmação está diferente da nova senha!");
                senhaNova2.grabFocus();
            }
            else
            {
                try {
                    
                    Operador o = new Operador();

                    String senha = Criptografia.criptografar(senhaNova1.getText());
                    
                    o.setId(TelaPrincipal.getIdOperador());
                    o.setSenha(senha);
                    
                    OperadorPersistor.alteraSenhaOperador(o);
                    
                    limpaFormulario();
                } catch (MyException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Ações executadas quando o botão "Limpar" do formulário é clicado
     */
    private class CleanListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            limpaFormulario();
        }
    }

    /**
     * Retorna o título da janela/menu
     * @return String
     */    
    public static String getTitulo()
    {
        return "Alterar senha";
    }
    
    /**
     * Retorna o nome do arquivo do ícone
     * @return String
     */
    public static String getArquivo()
    {
        return "Senha";
    }
    
}
