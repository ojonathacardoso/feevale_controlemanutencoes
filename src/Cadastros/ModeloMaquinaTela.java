package Cadastros;

import Banco.MyException;
import Principal.Botao;
import Principal.Formulario;
import Principal.TelaInterna;
import Modelos.ModeloMaquinaTableModel;
import Relatorios.ModeloMaquinaPDF;
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
import javax.swing.JTextField;

/**
 * Classe que controla as telas e componentes do menu ModeloMaquina
 * @author Jonatha
 */
public class ModeloMaquinaTela extends TelaInterna
{
    /**
     * Construtor da classe, que cria o painel e seus componentes.
     * @throws MyException 
     */
    public ModeloMaquinaTela() throws MyException, ParseException
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
    private ModeloMaquinaTableModel modelo;
    
    private JButton botaoInserir;
    private JButton botaoAlterar;
    private JButton botaoExcluir;
    private JButton botaoRelatorio;
    
    private JTextField filtroCodigo;
    private JTextField filtroMarca;
    private JTextField filtroModelo;
    private JComboBox filtroTipo;
    private JTextField filtroHdPadrao;
    private JTextField filtroRamPadrao;
    private JTextField filtroProcPadrao;
    
    private JButton botaoPesquisar;
    
    ///////////////////////////////////////////////////////////////////
    
    private JPanel painelFormularioCampos;
    private JPanel painelFormularioBotoes;
    
    private JButton botaoSalvar;
    private JButton botaoLimpar;
    private JButton botaoCancelar;

    private JTextField codigo;
    private JTextField marca;
    private JTextField modeloM;
    private JComboBox tipo;
    private JTextField hdPadrao;
    private JTextField ramPadrao;
    private JTextField procPadrao;
    
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
        
        this.filtroCodigo = Formulario.retornaCaixaTexto(50);
        this.filtroMarca = Formulario.retornaCaixaTexto(150);
        this.filtroModelo = Formulario.retornaCaixaTexto(150);        
        this.filtroTipo = Formulario.retornaComboBox(ModeloMaquina.getTipoLista(true), 90);        
        this.filtroHdPadrao = Formulario.retornaCaixaNumeros(50);
        this.filtroRamPadrao = Formulario.retornaCaixaNumeros(50);
        this.filtroProcPadrao = Formulario.retornaCaixaTexto(150);
        
        this.botaoPesquisar = Botao.retornaBotao("Pesquisar", 140);
        this.botaoPesquisar.addActionListener(new SearchListener());
        
        this.painelListaFiltro.add(this.botaoPesquisar);
        this.painelListaFiltro.add(Formulario.retornaLabel(ModeloMaquinaLocalizador.getNomeTela(0)+": "));
        this.painelListaFiltro.add(this.filtroCodigo);
        this.painelListaFiltro.add(Formulario.retornaLabel(ModeloMaquinaLocalizador.getNomeTela(1)+": "));
        this.painelListaFiltro.add(this.filtroMarca);
        this.painelListaFiltro.add(Formulario.retornaLabel(ModeloMaquinaLocalizador.getNomeTela(2)+": "));
        this.painelListaFiltro.add(this.filtroModelo);
        this.painelListaFiltro.add(Formulario.retornaLabel(ModeloMaquinaLocalizador.getNomeTela(3)+": "));
        this.painelListaFiltro.add(this.filtroTipo);        
        this.painelListaFiltro.add(Formulario.retornaLabel(ModeloMaquinaLocalizador.getNomeTela(4)+": "));
        this.painelListaFiltro.add(this.filtroHdPadrao);
        this.painelListaFiltro.add(Formulario.retornaLabel(ModeloMaquinaLocalizador.getNomeTela(5)+": "));
        this.painelListaFiltro.add(this.filtroRamPadrao);
        this.painelListaFiltro.add(Formulario.retornaLabel(ModeloMaquinaLocalizador.getNomeTela(6)+": "));
        this.painelListaFiltro.add(this.filtroProcPadrao);
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

    private void criaListaTabela() throws MyException, ParseException
    {
        this.modelo = new ModeloMaquinaTableModel();
        
        this.tabela = new TableLista(this.modelo); 
        
        this.tabela.getTableHeader().addMouseListener(new HeaderListener());
        
        this.painelListaTabela = new JScrollPane(this.tabela);
    }

    private void carregaListaTabela() throws MyException
    {
        this.modelo.clear();
        
        this.modelo.addModeloMaquinaLista(ModeloMaquinaLocalizador.buscaModelosMaquinas(this.sqlCondicoes, this.sqlOrdem));
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
        this.marca = Formulario.retornaCaixaTexto(200, 60);
        this.modeloM = Formulario.retornaCaixaTexto(200, 100);        
        this.tipo = Formulario.retornaComboBox(ModeloMaquina.getTipoLista(false), 90, 140);        
        this.hdPadrao = Formulario.retornaCaixaNumeros(50, 180);
        this.ramPadrao = Formulario.retornaCaixaNumeros(50, 220);
        this.procPadrao = Formulario.retornaCaixaTexto(150, 260);
        
        this.codigo.setEnabled(false);
        
        this.painelFormularioCampos.add(Formulario.retornaLabel(ModeloMaquinaLocalizador.getNomeTela(0)+": ", 20));
        this.painelFormularioCampos.add(this.codigo);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(ModeloMaquinaLocalizador.getNomeTela(1)+": ", 60));
        this.painelFormularioCampos.add(this.marca);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(ModeloMaquinaLocalizador.getNomeTela(2)+": ", 100));
        this.painelFormularioCampos.add(this.modeloM);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(ModeloMaquinaLocalizador.getNomeTela(3)+": ", 140));
        this.painelFormularioCampos.add(this.tipo);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(ModeloMaquinaLocalizador.getNomeTela(4)+": ", 180));
        this.painelFormularioCampos.add(this.hdPadrao);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("Tamanho em GB", 250, 180, "black"));
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(ModeloMaquinaLocalizador.getNomeTela(5)+": ", 220));
        this.painelFormularioCampos.add(this.ramPadrao);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("Tamanho em GB", 250, 220, "black"));
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(ModeloMaquinaLocalizador.getNomeTela(6)+": ", 260));
        this.painelFormularioCampos.add(this.procPadrao);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("* Campos obrigatórios", 20, 300, "red"));
    }
    
    /**
     * Limpa os campos do formulário.
     * Caso esteja numa alteração, o código não é limpo.
     */
    public void limpaFormulario()
    {
        if(! this.alteracao)
            this.codigo.setText("");
        
        this.marca.setText("");
        this.modeloM.setText("");
        this.tipo.setSelectedIndex(0);
        this.hdPadrao.setText("");
        this.ramPadrao.setText("");
        this.procPadrao.setText("");
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
            
            if(! filtroCodigo.getText().equals("") )
                sqlCondicoes.add("id_modelo = " + filtroCodigo.getText());
            
            if(! filtroMarca.getText().equals("") )
                sqlCondicoes.add("marca LIKE '%" + filtroMarca.getText() + "%'");
            
            if(! filtroModelo.getText().equals("") ) 
                sqlCondicoes.add("modelo LIKE '%" + filtroModelo.getText() + "%'");
            
            if(filtroTipo.getSelectedIndex() != 0)
                sqlCondicoes.add("tipo = " + filtroTipo.getSelectedIndex());
            
            if(! filtroHdPadrao.getText().equals("") ) 
                sqlCondicoes.add("hd_padrao = " + filtroHdPadrao.getText());
            
            if(! filtroRamPadrao.getText().equals("") ) 
                sqlCondicoes.add("ram_padrao = " + filtroRamPadrao.getText());
            
            if(! filtroProcPadrao.getText().equals("") ) 
                sqlCondicoes.add("proc_padrao LIKE '%" + filtroProcPadrao.getText() + "%'");
            
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
            limpaFormulario();
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
                    ModeloMaquina m = ModeloMaquinaLocalizador.buscaModeloMaquina(id);
                    
                    alteracao = true;
                    limpaFormulario();
                    
                    codigo.setText( Integer.toString( m.getId() ) );
                    marca.setText( m.getMarca());
                    modeloM.setText( m.getModelo());
                    tipo.setSelectedIndex( m.getTipo() );
                    hdPadrao.setText( Integer.toString( m.getHdPadrao()) );
                    ramPadrao.setText( Integer.toString( m.getRamPadrao()) );
                    procPadrao.setText( m.getProcPadrao());
                    
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
                        if(ModeloMaquinaPersistor.excluiModeloMaquina(id))
                            modelo.removeModeloMaquina(linhaSelecionada);
                        
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
                ModeloMaquinaPDF.gerarRelatorioModeloMaquina(modelo);
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
            if(marca.getText().trim().equals("") || marca.getText().trim().length() <= 1)
            {
                JOptionPane.showMessageDialog(null, "Informe uma marca com pelo menos 2 caracteres");
                marca.grabFocus();
            }
            else if(marca.getText().trim().length() > 30)
            {
                JOptionPane.showMessageDialog(null, "Informe uma marca com no máximo 30 caracteres");
                marca.grabFocus();
            }
            else if(modeloM.getText().trim().equals("") || modeloM.getText().trim().length() <= 2)
            {
                JOptionPane.showMessageDialog(null, "Informe um modelo com pelo menos 3 caracteres");
                modeloM.grabFocus();
            }
            else if(modeloM.getText().trim().length() > 100)
            {
                JOptionPane.showMessageDialog(null, "Informe um modelo com no máximo 100 caracteres");
                modeloM.grabFocus();
            }
            else if(tipo.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(null, "Selecione um dos tipos");
                tipo.grabFocus();
            }
            else if ( hdPadrao.getText().trim().equals("") )
            {
                JOptionPane.showMessageDialog(null, "Informe o tamanho do HD");
                hdPadrao.grabFocus();
            }
            else if( Integer.parseInt(hdPadrao.getText()) < 20 || Integer.parseInt(hdPadrao.getText()) > 20000 )
            {
                JOptionPane.showMessageDialog(null, "O HD deve ter entre 20 e 20000 GB");
                hdPadrao.grabFocus();
            }
            else if ( ramPadrao.getText().trim().equals("") )
            {
                JOptionPane.showMessageDialog(null, "Informe o tamanho da memória RAM");
                ramPadrao.grabFocus();
            }
            else if( Integer.parseInt(ramPadrao.getText()) < 1 || Integer.parseInt(ramPadrao.getText()) > 32 )
            {
                JOptionPane.showMessageDialog(null, "A memória RAM deve ter entre 1 e 32 GB");
                ramPadrao.grabFocus();
            }
            else if(procPadrao.getText().trim().equals("") || procPadrao.getText().trim().length() <= 3)
            {
                JOptionPane.showMessageDialog(null, "Informe um processador com pelo menos 4 caracteres");
                procPadrao.grabFocus();
            }
            else if(procPadrao.getText().trim().length() > 50)
            {
                JOptionPane.showMessageDialog(null, "Informe um processador com no máximo 50 caracteres");
                procPadrao.grabFocus();
            }            
            else
            {
                try {
                    ModeloMaquina m = new ModeloMaquina();
                    
                    if(codigo.getText().equals(""))
                        m.setId(0);
                    else
                        m.setId(Integer.parseInt(codigo.getText()));

                    m.setMarca(marca.getText());
                    m.setModelo(modeloM.getText());
                    m.setTipo(tipo.getSelectedIndex());
                    m.setHdPadrao(Integer.parseInt(hdPadrao.getText()));
                    m.setRamPadrao(Integer.parseInt(ramPadrao.getText()));
                    m.setProcPadrao(procPadrao.getText());
                    
                    if(codigo.getText().equals(""))
                    {                        
                        if( ModeloMaquinaPersistor.insereModeloMaquina(m) )
                            exibeLista();
                    }
                    else
                    {
                        if( ModeloMaquinaPersistor.alteraModeloMaquina(m) )
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
            limpaFormulario();
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
                sqlOrdem = ModeloMaquinaLocalizador.getConversaoSQL(nomeAtual);           
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
        return "Modelos de máquinas";
    }
    
    /**
     * Retorna o nome do arquivo do ícone
     * @return String
     */
    public static String getArquivo()
    {
        return "ModelosMaquinas";
    }
    
}
