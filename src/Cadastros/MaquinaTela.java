package Cadastros;

import Banco.MyException;
import Principal.Botao;
import Principal.Formulario;
import Principal.TelaInterna;
import Modelos.MaquinaTableModel;
import Relatorios.MaquinaPDF;
import Tabelas.TableLista;
import com.itextpdf.text.DocumentException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Classe que controla as telas e componentes do menu Maquina
 * @author Jonatha
 */
public class MaquinaTela extends TelaInterna
{
    /**
     * Construtor da classe, que cria o painel e seus componentes.
     * @throws MyException 
     */
    public MaquinaTela() throws MyException, ParseException
    {
        super(getTitulo(), getArquivo());
        
        this.criaPainel();
        this.criaFormulario();
        this.criaLista();
        
        this.exibeLista();
        
        getContentPane().add(this.painel);
    }
    
    private JPanel painel;
    
    private JPanel painelListaBotoes;
    private JPanel painelListaFiltro;
    private JScrollPane painelListaTabela;
    
    private TableLista tabela;
    private MaquinaTableModel modelo;
    
    private JButton botaoInserir;
    private JButton botaoAlterar;
    private JButton botaoExcluir;
    private JButton botaoRelatorio;
    
    private JComboBox filtroModelo;
    private JTextField filtroNome;
    private JTextField filtroUsuario;
    private JComboBox filtroLocalizacao;  
    private JTextField filtroSistema;
    private JTextField filtroHd;
    private JTextField filtroRam;
    private JTextField filtroProc;
    
    private JButton botaoPesquisar;
    
    ///////////////////////////////////////////////////////////////////
    
    private JPanel painelFormularioCampos;
    private JPanel painelFormularioBotoes;
    
    private JButton botaoSalvar;
    private JButton botaoLimpar;
    private JButton botaoCancelar;

    private JTextField codigo;
    private JComboBox modeloM;
    private JTextField nome;
    private JTextField usuario;
    private JComboBox localizacao;
    private JTextField sistema;
    private JTextField hd;
    private JTextField ram;
    private JTextField proc;
    private JTextArea observacao;
    
    ///////////////////////////////////////////////////////////////////

    private ArrayList<String> sqlCondicoes = new ArrayList<String>();
    private String sqlOrdem = "";
    private int sqlAnterior;
    
    private boolean alteracao;
    
    ///////////////////////////////////////////////////////////////////

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
     * Chama os métodos que criam os componentes que exibem a lista/pesquisa
     * @throws MyException 
     */
    private void criaLista() throws MyException, ParseException
    {      
        this.criaListaFiltro();
        this.criaListaTabela();
        this.criaListaBotoes();
    }
    
    /**
     * Cria os componentes que exibem o filtro.
     */    
    private void criaListaFiltro() throws MyException
    {
        this.painelListaFiltro = new JPanel();
        this.painelListaFiltro.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.filtroNome = Formulario.retornaCaixaTexto(150);
        this.filtroModelo = Formulario.retornaComboBox(ModeloMaquinaLocalizador.getModelo(), 150);
        this.filtroUsuario = Formulario.retornaCaixaTexto(150);
        this.filtroLocalizacao = Formulario.retornaComboBox(LocalizacaoLocalizador.getModelo(), 100);
        this.filtroSistema = Formulario.retornaCaixaTexto(100);    
        this.filtroHd = Formulario.retornaCaixaNumeros(50);
        this.filtroRam = Formulario.retornaCaixaNumeros(50);
        this.filtroProc = Formulario.retornaCaixaTexto(100);
        
        this.botaoPesquisar = Botao.retornaBotao("Pesquisar", 140);
        this.botaoPesquisar.addActionListener(new SearchListener());
        
        this.painelListaFiltro.add(this.botaoPesquisar);
        this.painelListaFiltro.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(1)+": "));
        this.painelListaFiltro.add(this.filtroModelo);
        this.painelListaFiltro.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(2)+": "));
        this.painelListaFiltro.add(this.filtroNome);
        this.painelListaFiltro.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(3)+": "));
        this.painelListaFiltro.add(this.filtroUsuario);
        this.painelListaFiltro.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(4)+": "));
        this.painelListaFiltro.add(this.filtroLocalizacao);        
        this.painelListaFiltro.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(5)+": "));
        this.painelListaFiltro.add(this.filtroSistema);
        this.painelListaFiltro.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(6)+": "));
        this.painelListaFiltro.add(this.filtroHd);
        this.painelListaFiltro.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(7)+": "));
        this.painelListaFiltro.add(this.filtroRam);
        this.painelListaFiltro.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(8)+": "));
        this.painelListaFiltro.add(this.filtroProc);
    }
    
    /**
     * Cria os botões de ações na lista.
     */
    private void criaListaBotoes()
    {
        this.painelListaBotoes = new JPanel();
        
        this.botaoInserir = Botao.retornaBotao("Inserir", 120);
        this.botaoAlterar = Botao.retornaBotao("Alterar", 120);
        this.botaoExcluir = Botao.retornaBotao("Excluir", 120);
        this.botaoRelatorio = Botao.retornaBotao("Relatório", "Relatorio", 120);
        
        this.botaoInserir.addActionListener(new InsertListener());
        this.botaoAlterar.addActionListener(new UpdateListener());
        this.botaoExcluir.addActionListener(new DeleteListener());
        this.botaoRelatorio.addActionListener(new ReportListener());

        this.painelListaBotoes.add(this.botaoInserir);
        this.painelListaBotoes.add(this.botaoAlterar);
        this.painelListaBotoes.add(this.botaoExcluir);
        this.painelListaBotoes.add(this.botaoRelatorio);
    }

    /**
     * Cria a tabela que exibe os registros.
     */
    private void criaListaTabela() throws MyException, ParseException
    {
        this.modelo = new MaquinaTableModel();
        
        this.tabela = new TableLista(this.modelo); 
        
        this.tabela.getTableHeader().addMouseListener(new MaquinaTela.HeaderListener());
        
        this.painelListaTabela = new JScrollPane(this.tabela);
    }

    private void carregaListaTabela() throws MyException
    {
        this.modelo.clear();
        
        this.modelo.addMaquinaLista(MaquinaLocalizador.buscaMaquinas(this.sqlCondicoes, this.sqlOrdem));
    }
    
    /**
     * Exibe os componentes da lista/pesquisa no painel
     * @throws MyException 
     */
    private void exibeLista() throws MyException
    {
        this.painel.remove(this.painelFormularioCampos);        
        this.painel.remove(this.painelFormularioBotoes);
        
        this.painel.add(BorderLayout.NORTH, this.painelListaFiltro);
        this.painel.add(BorderLayout.CENTER, this.painelListaTabela);        
        this.painel.add(BorderLayout.SOUTH, this.painelListaBotoes);
        
        carregaListaTabela();
        
        this.painel.revalidate();
        this.painel.repaint();
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
    private void criaFormularioCampos() throws MyException
    {
        this.painelFormularioCampos = new JPanel();
        this.painelFormularioCampos.setLayout(null);
        
        this.codigo = Formulario.retornaCaixaTexto(50, 20);
        
        this.modeloM = Formulario.retornaComboBox(ModeloMaquinaLocalizador.getModelo(), 150, 60);
        this.nome = Formulario.retornaCaixaTexto(150, 100);
        this.usuario = Formulario.retornaCaixaTexto(200, 140);
        this.localizacao = Formulario.retornaComboBox(LocalizacaoLocalizador.getModelo(), 100, 180);
        this.sistema = Formulario.retornaCaixaTexto(200, 220);    
        this.hd = Formulario.retornaCaixaNumeros(50, 260);
        this.ram = Formulario.retornaCaixaNumeros(50, 300);
        this.proc = Formulario.retornaCaixaTexto(150, 340);
        this.observacao = Formulario.retornaAreaTexto(300, 380);

        this.codigo.setEnabled(false);
        
        this.painelFormularioCampos.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(0)+": ", 20));
        this.painelFormularioCampos.add(this.codigo);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(MaquinaLocalizador.getNomeTela(1)+": ", 60));
        this.painelFormularioCampos.add(this.modeloM);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(MaquinaLocalizador.getNomeTela(2)+": ", 100));
        this.painelFormularioCampos.add(this.nome);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(MaquinaLocalizador.getNomeTela(3)+": ", 140));
        this.painelFormularioCampos.add(this.usuario);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(MaquinaLocalizador.getNomeTela(4)+": ", 180));
        this.painelFormularioCampos.add(this.localizacao);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(MaquinaLocalizador.getNomeTela(5)+": ", 220));
        this.painelFormularioCampos.add(this.sistema);        
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(MaquinaLocalizador.getNomeTela(6)+": ", 260));
        this.painelFormularioCampos.add(this.hd);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("Tamanho em GB", 250, 260, "black"));
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(MaquinaLocalizador.getNomeTela(7)+": ", 300));
        this.painelFormularioCampos.add(this.ram);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("Tamanho em GB", 250, 300, "black"));
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(MaquinaLocalizador.getNomeTela(8)+": ", 340));
        this.painelFormularioCampos.add(this.proc);
        this.painelFormularioCampos.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(9)+": ", 380));
        this.painelFormularioCampos.add(this.observacao);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("* Campos obrigatórios", 20, 520, "red"));
    }
    
    /**
     * Limpa os campos do formulário.
     * Caso esteja numa alteração, o código não é limpo.
     * @throws MyException
     */
    public void limpaFormulario() throws MyException
    {
        if(! this.alteracao)
            this.codigo.setText("");
        
        this.modeloM.setSelectedIndex(0);
        this.localizacao.setSelectedIndex(0);        
        this.nome.setText("");
        this.usuario.setText("");
        this.sistema.setText("");
        this.hd.setText("");
        this.ram.setText("");
        this.proc.setText("");
        this.observacao.setText("");
    }
    
    /**
     * Cria os botões de ações no formulário.
     */
    private void criaFormularioBotoes()
    {
        this.painelFormularioBotoes = new JPanel();
        
        this.botaoSalvar = Botao.retornaBotao("Salvar", 120);
        this.botaoLimpar = Botao.retornaBotao("Limpar", 120);
        this.botaoCancelar = Botao.retornaBotao("Cancelar", 120);
        
        this.botaoSalvar.addActionListener(new SaveListener());
        this.botaoLimpar.addActionListener(new CleanListener());
        this.botaoCancelar.addActionListener(new CancelListener());

        this.painelFormularioBotoes.add(this.botaoSalvar);
        this.painelFormularioBotoes.add(this.botaoLimpar);
        this.painelFormularioBotoes.add(this.botaoCancelar);
    }
    
    /**
     * Exibe os componentes do formulário no painel
     */
    private void exibeFormulario()
    {
        this.painel.remove(this.painelListaFiltro);
        this.painel.remove(this.painelListaTabela);
        this.painel.remove(this.painelListaBotoes);
        
        this.painel.add(BorderLayout.CENTER, this.painelFormularioCampos);        
        this.painel.add(BorderLayout.SOUTH, this.painelFormularioBotoes);
        
        this.painel.revalidate();
        this.painel.repaint();
    }
    
    /**
     * Ações executadas quando o botão "Pesquisar" do filtro é clicado
     */
    private class SearchListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            sqlCondicoes.clear();
            
            if(filtroModelo.getSelectedIndex() != 0)
                sqlCondicoes.add("id_modelo = " + ( (ModeloMaquina) filtroModelo.getSelectedItem() ).getId());

            if(! filtroNome.getText().equals("") )
                sqlCondicoes.add("nome_maquina LIKE '%" + filtroNome.getText() + "%'");
            
            if(! filtroUsuario.getText().equals("") )
                sqlCondicoes.add("nome_usuario LIKE '%" + filtroUsuario.getText() + "%'");
            
            if(filtroLocalizacao.getSelectedIndex() != 0)
                sqlCondicoes.add("id_localizacao = " + ( (Localizacao) filtroLocalizacao.getSelectedItem() ).getId());
  
            if(! filtroSistema.getText().equals("") ) 
                sqlCondicoes.add("sistema LIKE '%" + filtroSistema.getText() + "%'");
            
            if(! filtroHd.getText().equals("") ) 
                sqlCondicoes.add("hd = " + filtroHd.getText());
            
            if(! filtroRam.getText().equals("") ) 
                sqlCondicoes.add("ram = " + filtroRam.getText());
            
            if(! filtroProc.getText().equals("") ) 
                sqlCondicoes.add("proc LIKE '%" + filtroProc.getText() + "%'");
            
            try {
                carregaListaTabela();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        } 
    }
    
    /**
     * Ações executadas quando o botão "Inserir" da tabela é clicado
     */
    private class InsertListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            alteracao = false;
            try {
                limpaFormulario();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
            exibeFormulario();
        }
    }

    /**
     * Ações executadas quando o botão "Alterar" da tabela é clicado
     */
    private class UpdateListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int linhaSelecionada = -1;
            
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0)
            {
                int id = (int) tabela.getValueAt(linhaSelecionada, 0);
                
                try {
                    Maquina m = MaquinaLocalizador.buscaMaquina(id);
                    
                    alteracao = true;
                    limpaFormulario();
                    
                    codigo.setText( Integer.toString( m.getId() ) );
                    modeloM.setSelectedIndex( ModeloMaquinaLocalizador.getIndexModelo(m.getModeloMaquina()) );
                    nome.setText( m.getNome() );
                    usuario.setText( m.getUsuario() );
                    localizacao.setSelectedIndex( LocalizacaoLocalizador.getIndexModelo(m.getLocalizacao()) );
                    sistema.setText( m.getSistema() );
                    hd.setText( Integer.toString( m.getHd()) );
                    ram.setText( Integer.toString( m.getRam()) );
                    proc.setText( m.getProc() );
                    observacao.setText( m.getObservacao() );
                                    
                    exibeFormulario();
                    
                } catch (MyException ex) {
                    ex.printStackTrace();
                }
            } else
            {
                JOptionPane.showMessageDialog(painel, "É necesário selecionar uma linha!");
            }
        } 
    }

    /**
     * Ações executadas quando o botão "Excluir" da tabela é clicado
     */
    private class DeleteListener implements ActionListener
    {
         public void actionPerformed(ActionEvent e)
         {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0)
            {
                int id = (int) tabela.getValueAt(linhaSelecionada, 0);
                String nome = (String) tabela.getValueAt(linhaSelecionada, 1);
                
                int opcao = JOptionPane.showConfirmDialog(
                        null,
                        "Deseja excluir definitivamente "+nome+"?", 
                        "Excluir",
                        JOptionPane.YES_NO_OPTION);
                
                if(opcao == JOptionPane.YES_OPTION)
                {
                    try {
                        if(MaquinaPersistor.excluiMaquina(id))
                            modelo.removeMaquina(linhaSelecionada);
                    } catch (MyException ex) {
                        ex.printStackTrace();
                    }
                }
            } else
            {
                JOptionPane.showMessageDialog(painel, "É necesário selecionar uma linha!");
            }
        }
    }
    
    private class ReportListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                MaquinaPDF.gerarRelatorioMaquina(modelo);
            } catch (DocumentException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Ações executadas quando o botão "Salvar" do formulário é clicado
     */
    private class SaveListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {            
            if(modeloM.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(null, "Selecione um dos modelos");
                modeloM.grabFocus();
            }            
            else if(nome.getText().trim().equals("") || nome.getText().trim().length() <= 1)
            {
                JOptionPane.showMessageDialog(null, "Informe um nome com pelo menos 2 caracteres");
                nome.grabFocus();
            }
            else if(nome.getText().trim().length() > 20)
            {
                JOptionPane.showMessageDialog(null, "Informe um nome com no máximo 20 caracteres");
                nome.grabFocus();
            }
            else if(usuario.getText().trim().equals("") || usuario.getText().trim().length() <= 3)
            {
                JOptionPane.showMessageDialog(null, "Informe um usuário com pelo menos 4 caracteres");
                usuario.grabFocus();
            }
            else if(usuario.getText().trim().length() > 100)
            {
                JOptionPane.showMessageDialog(null, "Informe um usuário com no máximo 100 caracteres");
                usuario.grabFocus();
            }
            else if(localizacao.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(null, "Selecione uma das localizações");
                localizacao.grabFocus();
            }
            else if(sistema.getText().trim().equals("") || sistema.getText().trim().length() <= 4)
            {
                JOptionPane.showMessageDialog(null, "Informe o sistema com pelo menos 5 caracteres");
                sistema.grabFocus();
            }
            else if(sistema.getText().trim().length() > 100)
            {
                JOptionPane.showMessageDialog(null, "Informe o sistema com no máximo 100 caracteres");
                sistema.grabFocus();
            }
            else if ( hd.getText().trim().equals("") )
            {
                JOptionPane.showMessageDialog(null, "Informe o tamanho do HD");
                hd.grabFocus();
            }
            else if( Integer.parseInt(hd.getText()) < 20 || Integer.parseInt(hd.getText()) > 20000 )
            {
                JOptionPane.showMessageDialog(null, "O HD deve ter entre 20 e 20000 GB");
                hd.grabFocus();
            }
            else if ( ram.getText().trim().equals("") )
            {
                JOptionPane.showMessageDialog(null, "Informe o tamanho da memória RAM");
                ram.grabFocus();
            }
            else if( Integer.parseInt(ram.getText()) < 1 || Integer.parseInt(ram.getText()) > 32 )
            {
                JOptionPane.showMessageDialog(null, "A memória RAM deve ter entre 1 e 32 GB");
                ram.grabFocus();
            }
            else if(proc.getText().trim().equals("") || proc.getText().trim().length() <= 3)
            {
                JOptionPane.showMessageDialog(null, "Informe um processador com pelo menos 4 caracteres");
                proc.grabFocus();
            }
            else if(proc.getText().trim().length() > 50)
            {
                JOptionPane.showMessageDialog(null, "Informe um processador com no máximo 50 caracteres");
                proc.grabFocus();
            }
            else
            {
                try {
                    Maquina m = new Maquina();
                    
                    if(codigo.getText().equals(""))
                        m.setId(0);
                    else
                        m.setId(Integer.parseInt(codigo.getText()));
                    
                    m.setModeloMaquina(( (ModeloMaquina) modeloM.getSelectedItem() ).getId());
                    m.setNome(nome.getText());
                    m.setUsuario(usuario.getText());
                    m.setLocalizacao( ((Localizacao) localizacao.getSelectedItem()).getId() );
                    m.setSistema(sistema.getText());
                    m.setHd(Integer.parseInt(hd.getText()));
                    m.setRam(Integer.parseInt(ram.getText()));
                    m.setProc(proc.getText());
                    m.setObservacao(observacao.getText());
                    
                    if(codigo.getText().equals(""))
                    {                        
                        if( MaquinaPersistor.insereMaquina(m) )
                            exibeLista();
                    }
                    else
                    {
                        if( MaquinaPersistor.alteraMaquina(m) )
                            exibeLista();
                    }
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
            try {
                limpaFormulario();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Ações executadas quando o botão "Cancelar" do formulário é clicado
     */
    private class CancelListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                exibeLista();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Ações executadas quando o cabeçalho de uma das colunas da tabela é clicado
     */
    private class HeaderListener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                int col = tabela.columnAtPoint(e.getPoint());
                String nomeAtual = tabela.getColumnName(col);
                String nomeAnterior = tabela.getColumnName(sqlAnterior);
                
                tabela.getTableHeader().getColumnModel().getColumn(sqlAnterior).setHeaderValue(nomeAnterior);
                tabela.getTableHeader().getColumnModel().getColumn(col).setHeaderValue("[ "+nomeAtual+" ]");
                
                sqlAnterior = col;
                sqlOrdem = MaquinaLocalizador.getConversaoSQL(nomeAtual);           
                carregaListaTabela();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //
        }
        
    }
    
    /**
     * Retorna o título da janela/menu
     * @return String
     */    
    public static String getTitulo()
    {
        return "Máquinas";
    }
    
    /**
     * Retorna o nome do arquivo do ícone
     * @return String
     */
    public static String getArquivo()
    {
        return "Maquinas";
    }
    
}
