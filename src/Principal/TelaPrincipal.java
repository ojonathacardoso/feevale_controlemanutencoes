package Principal;

import Banco.MyException;
import Cadastros.LocalizacaoTela;
import Cadastros.MaquinaTela;
import Cadastros.ModeloMaquinaTela;
import Configuracoes.ExpedienteTela;
import Configuracoes.OperadorLocalizador;
import Configuracoes.OperadorTela;
import Configuracoes.SenhaTela;
import Configuracoes.SobreTela;
import Icones.Icone;
import Manutencoes.AtividadeTela;
import Manutencoes.TipoAtividadeTela;
import Manutencoes.TarefaTela;
import Manutencoes.ModeloTarefaTela;
import Manutencoes.TrabalhoTela;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * Classe que cria a tela principal usada no sistema
 * @author Jonatha
 */
public class TelaPrincipal extends JFrame implements ActionListener
{
    public static int largura = 1280;
    public static int altura = 720;
    
    public static Icone icone = new Icone();
    
    private Menu menu = new Menu();

    private JMenuBar barra = new JMenuBar();
    
    private JMenu manutencaoMenu = new JMenu();
    private JMenu cadastroMenu = new JMenu();
    private JMenu configuracaoMenu = new JMenu();
    
    private JMenuItem atividadeMenu = new JMenuItem();
    private JMenuItem tipoAtividadeMenu = new JMenuItem();
    private JMenuItem trabalhoMenu = new JMenuItem();
    private JMenuItem tarefaMenu = new JMenuItem();
    private JMenuItem modeloTarefaMenu = new JMenuItem();
    
    private JMenuItem maquinaMenu = new JMenuItem();
    private JMenuItem modeloMaquinaMenu = new JMenuItem();
    private JMenuItem localizacaoMenu = new JMenuItem();
    
    private JMenuItem operadorMenu = new JMenuItem();
    private JMenuItem senhaMenu = new JMenuItem();
    private JMenuItem expedienteMenu = new JMenuItem();
    private JMenuItem sobreMenu = new JMenuItem(); 
    
    private JDesktopPane painel = new JDesktopPane();
    
    private AtividadeTela atividadeTela;    
    private TipoAtividadeTela tipoAtividadeTela;    
    private TrabalhoTela trabalhoTela;   
    private TarefaTela tarefaTela;    
    private ModeloTarefaTela modeloTarefaTela;

    private MaquinaTela maquinaTela;   
    private ModeloMaquinaTela modeloMaquinaTela;    
    private LocalizacaoTela localizacaoTela;   
    
    private OperadorTela operadorTela;    
    private SenhaTela senhaTela;   
    private ExpedienteTela expedienteTela;  
    private SobreTela sobreTela;
    
    private static int idOperador;
    
    /**
     * Método construtor
     * @throws UnsupportedLookAndFeelException
     * @throws MyException 
     * @throws ParseException 
     */
    public TelaPrincipal() throws UnsupportedLookAndFeelException, MyException, ParseException
    {
        add(this.painel);
        setTitle("Sistema de Manutenções e Inspeções");
        setSize(1280, 720);
        setExtendedState(MAXIMIZED_BOTH);
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        getContentPane().setBackground( new Color(248, 248, 255));

        if(Login.acessar(this))
        {
            this.criarMenus();    
            this.criarTelas();
            
            this.configurarAcoesMenus();           
            this.adicionarTelas();
            
            setJMenuBar(this.barra);
            setVisible(true);
        }
        else
        {
            System.exit(0);
        }
         
    }
    
    /**
     * Cria e popula a barra de menus.
     */
    private void criarMenus() throws MyException
    {
        this.menu.adicionarMenu(this.barra, this.manutencaoMenu, "Manutenções", "Manutencoes");
        this.menu.adicionarMenu(this.barra, this.cadastroMenu, "Cadastros", "Cadastros");
        this.menu.adicionarMenu(this.barra, this.configuracaoMenu, "Configurações", "Configuracoes");
        
        this.menu.adicionarMenu(this.manutencaoMenu, this.atividadeMenu, AtividadeTela.getTitulo(), AtividadeTela.getArquivo());
        this.menu.adicionarMenu(this.manutencaoMenu, this.trabalhoMenu, TrabalhoTela.getTitulo(), TrabalhoTela.getArquivo());
        this.menu.adicionarMenu(this.manutencaoMenu, this.tarefaMenu, TarefaTela.getTitulo(), TarefaTela.getArquivo());
        this.menu.adicionarMenu(this.cadastroMenu, this.maquinaMenu, MaquinaTela.getTitulo(), MaquinaTela.getArquivo());
        this.menu.adicionarMenu(this.configuracaoMenu, this.senhaMenu, SenhaTela.getTitulo(), SenhaTela.getArquivo());   
        this.menu.adicionarMenu(this.configuracaoMenu, this.sobreMenu, SobreTela.getTitulo(), SobreTela.getArquivo());   
        
        if(OperadorLocalizador.buscaOperadorPermissao(idOperador))
        {
            this.manutencaoMenu.addSeparator();
            this.cadastroMenu.addSeparator();
            this.configuracaoMenu.addSeparator();
            
            this.menu.adicionarMenu(this.manutencaoMenu, this.modeloTarefaMenu, ModeloTarefaTela.getTitulo(), ModeloTarefaTela.getArquivo());
            this.menu.adicionarMenu(this.manutencaoMenu, this.tipoAtividadeMenu, TipoAtividadeTela.getTitulo(), TipoAtividadeTela.getArquivo());
            this.menu.adicionarMenu(this.cadastroMenu, this.modeloMaquinaMenu, ModeloMaquinaTela.getTitulo(), ModeloMaquinaTela.getArquivo());
            this.menu.adicionarMenu(this.cadastroMenu, this.localizacaoMenu, LocalizacaoTela.getTitulo(), LocalizacaoTela.getArquivo());
            this.menu.adicionarMenu(this.configuracaoMenu, this.operadorMenu, OperadorTela.getTitulo(), OperadorTela.getArquivo());
            this.menu.adicionarMenu(this.configuracaoMenu, this.expedienteMenu, ExpedienteTela.getTitulo(), ExpedienteTela.getArquivo());
        }
    }
    
    /**
     * Adiciona as ações de cada menu.
     */
    private void configurarAcoesMenus()
    {
        this.atividadeMenu.addActionListener(this);
        this.trabalhoMenu.addActionListener(this);
        this.tarefaMenu.addActionListener(this);
        this.tipoAtividadeMenu.addActionListener(this);
        this.modeloTarefaMenu.addActionListener(this);
        
        this.maquinaMenu.addActionListener(this);
        this.modeloMaquinaMenu.addActionListener(this);
        this.localizacaoMenu.addActionListener(this);
        
        this.operadorMenu.addActionListener(this);
        this.senhaMenu.addActionListener(this);
        this.expedienteMenu.addActionListener(this);
        this.sobreMenu.addActionListener(this);
    }
    
    /**
     * Método que cria as telas de cada menu.
     * @throws MyException 
     */
    private void criarTelas() throws MyException, ParseException
    {
        this.atividadeTela = new AtividadeTela();
        this.trabalhoTela = new TrabalhoTela();
        this.tarefaTela = new TarefaTela();        
        this.maquinaTela = new MaquinaTela();
        this.expedienteTela = new ExpedienteTela();
        this.senhaTela = new SenhaTela();
        this.sobreTela = new SobreTela();
        
        if(OperadorLocalizador.buscaOperadorPermissao(idOperador))
        {
            this.tipoAtividadeTela = new TipoAtividadeTela();
            this.modeloTarefaTela = new ModeloTarefaTela();
            this.modeloMaquinaTela = new ModeloMaquinaTela();
            this.localizacaoTela = new LocalizacaoTela();
            this.operadorTela = new OperadorTela();
            this.expedienteTela = new ExpedienteTela();
        }
        else
        {
            System.out.println("uyyyy");
        }
    }
    
    /**
     * Método que adiciona as telas ao painel principal
     */
    private void adicionarTelas() throws MyException
    {
        this.painel.add(this.atividadeTela);
        this.painel.add(this.trabalhoTela);
        this.painel.add(this.tarefaTela);
        this.painel.add(this.maquinaTela);
        this.painel.add(this.expedienteTela);
        this.painel.add(this.senhaTela);
        this.painel.add(this.sobreTela);
        
        if(OperadorLocalizador.buscaOperadorPermissao(idOperador))
        {
            this.painel.add(this.tipoAtividadeTela);
            this.painel.add(this.modeloTarefaTela);
            this.painel.add(this.modeloMaquinaTela);
            this.painel.add(this.localizacaoTela);
            this.painel.add(this.operadorTela);
        }
    }

    /**
     * Metodo que define as ações de cada menu
     * @param e ActionEvent - Evento
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JMenuItem menuItem = (JMenuItem) e.getSource(); 
        String opcao = menuItem.getText();
        
        if(opcao.equals(atividadeMenu.getText()))
            atividadeTela.setVisible(true);
        else if(opcao.equals(trabalhoMenu.getText()))
            trabalhoTela.setVisible(true);
        else if(opcao.equals(tarefaMenu.getText()))
            tarefaTela.setVisible(true);
        else if(opcao.equals(tipoAtividadeMenu.getText()))
            tipoAtividadeTela.setVisible(true);
        else if(opcao.equals(modeloTarefaMenu.getText()))
            modeloTarefaTela.setVisible(true);
        else if(opcao.equals(maquinaMenu.getText()))
            maquinaTela.setVisible(true);
        else if(opcao.equals(modeloMaquinaMenu.getText()))
            modeloMaquinaTela.setVisible(true);
        else if(opcao.equals(localizacaoMenu.getText()))
            localizacaoTela.setVisible(true);
        else if(opcao.equals(operadorMenu.getText()))
            operadorTela.setVisible(true);
        else if(opcao.equals(expedienteMenu.getText()))
            expedienteTela.setVisible(true);
        else if(opcao.equals(sobreMenu.getText()))
            sobreTela.setVisible(true);
        else if(opcao.equals(senhaMenu.getText()))
            senhaTela.setVisible(true);
    }

    public static int getIdOperador()
    {
        return idOperador;
    }
    
    public static void setIdOperador(int t)
    {
        idOperador = t;
    }

}