package Manutencoes;

import Banco.MyException;
import Principal.Botao;
import Principal.Formulario;
import Principal.TelaInterna;
import Tabelas.TableLista;
import Modelos.TipoAtividadeTableModel;
import Relatorios.TipoAtividadePDF;
import com.itextpdf.text.DocumentException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Classe que controla as telas e componentes do menu TipoAtividade
 * @author Jonatha
 */
public class TipoAtividadeTela extends TelaInterna
{
    /**
     * Construtor da classe, que cria o painel e seus componentes.
     * @throws MyException 
     */
    public TipoAtividadeTela() throws MyException
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
    private TipoAtividadeTableModel modelo;
    
    private JButton botaoInserir;
    private JButton botaoAlterar;
    private JButton botaoExcluir;
    private JButton botaoRelatorio;
    
    private JTextField filtroCodigo;
    private JTextField filtroNome;
    
    private JButton botaoPesquisar;
    
    ///////////////////////////////////////////////////////////////////
    
    private JPanel painelFormularioCampos;
    private JPanel painelFormularioBotoes;
    
    private JButton botaoSalvar;
    private JButton botaoLimpar;
    private JButton botaoCancelar;

    private JTextField codigo;
    private JTextField nome;
    
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
    private void criaLista() throws MyException
    {      
        this.criaListaFiltro();
        this.criaListaTabela();
        this.criaListaBotoes();
    }
    
    /**
     * Cria os componentes que exibem o filtro.
     */    
    private void criaListaFiltro()
    {
        this.painelListaFiltro = new JPanel();
        this.painelListaFiltro.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.filtroCodigo = Formulario.retornaCaixaTexto(50);
        this.filtroNome = Formulario.retornaCaixaTexto(200);

        this.botaoPesquisar = Botao.retornaBotao("Pesquisar", 140);
        this.botaoPesquisar.addActionListener(new SearchListener());
        
        this.painelListaFiltro.add(this.botaoPesquisar);
        this.painelListaFiltro.add(Formulario.retornaLabel(TipoAtividadeLocalizador.getNomeTela(0)+": "));
        this.painelListaFiltro.add(this.filtroCodigo);
        this.painelListaFiltro.add(Formulario.retornaLabel(TipoAtividadeLocalizador.getNomeTela(1)+": "));
        this.painelListaFiltro.add(this.filtroNome);
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

    private void criaListaTabela() throws MyException
    {
        this.modelo = new TipoAtividadeTableModel();
        
        this.tabela = new TableLista(this.modelo); 
        
        this.tabela.getTableHeader().addMouseListener(new TipoAtividadeTela.HeaderListener());
        
        this.painelListaTabela = new JScrollPane(this.tabela);
    }

    private void carregaListaTabela() throws MyException
    {
        this.modelo.clear();
        
        this.modelo.addTipoAtividadeLista(TipoAtividadeLocalizador.buscaTiposAtividades(this.sqlCondicoes, this.sqlOrdem));
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
    private void criaFormularioCampos()
    {
        this.painelFormularioCampos = new JPanel();
        this.painelFormularioCampos.setLayout(null);

        this.codigo = Formulario.retornaCaixaTexto(50, 20);
        this.nome = Formulario.retornaCaixaTexto(200, 60);

        this.codigo.setEnabled(false);
        
        this.painelFormularioCampos.add(Formulario.retornaLabel(TipoAtividadeLocalizador.getNomeTela(0)+": ", 20));
        this.painelFormularioCampos.add(this.codigo);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(TipoAtividadeLocalizador.getNomeTela(1)+": ", 60));
        this.painelFormularioCampos.add(this.nome);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("* Campos obrigatórios", 20, 100, "red"));
    }
    
    /**
     * Limpa os campos do formulário.
     * Caso esteja numa alteração, o código não é limpo.
     */
    public void limpaFormulario()
    {
        if(! this.alteracao)
            this.codigo.setText("");
        
        this.nome.setText("");
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
                sqlCondicoes.add("id_localizacao = " + filtroCodigo.getText());
            
            if(! filtroNome.getText().equals("") )
                sqlCondicoes.add("nome LIKE '%" + filtroNome.getText() + "%'");
            
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
                    TipoAtividade o = TipoAtividadeLocalizador.buscaTipoAtividade(id);
                    
                    alteracao = true;
                    limpaFormulario();
                    
                    codigo.setText(Integer.toString(o.getId()));
                    nome.setText(o.getNome());
                    
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
                        if(TipoAtividadePersistor.excluiTipoAtividade(id))
                            modelo.removeTipoAtividade(linhaSelecionada);
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
                TipoAtividadePDF.gerarRelatorioTipoAtividade(modelo);
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
            if(nome.getText().trim().equals("") || nome.getText().trim().length() <= 2)
            {
                JOptionPane.showMessageDialog(null, "Informe um nome com pelo menos 3 caracteres");
                nome.grabFocus();
            }
            else if(nome.getText().trim().length() > 100)
            {
                JOptionPane.showMessageDialog(null, "Informe um nome com no máximo 100 caracteres");
                nome.grabFocus();
            }
            else
            {
                try {
                    TipoAtividade t = new TipoAtividade();
                    
                    if(codigo.getText().equals(""))
                        t.setId(0);
                    else
                        t.setId(Integer.parseInt(codigo.getText()));

                    t.setNome(nome.getText());
                    
                    if(codigo.getText().equals(""))
                    {                        
                        if( TipoAtividadePersistor.insereTipoAtividade(t) )
                            exibeLista();
                    }
                    else
                    {
                        if( TipoAtividadePersistor.alteraTipoAtividade(t) )
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
                sqlOrdem = TipoAtividadeLocalizador.getConversaoSQL(nomeAtual);           
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
        return "Tipos de atividades";
    }
    
    /**
     * Retorna o nome do arquivo do ícone
     * @return String
     */
    public static String getArquivo()
    {
        return "TiposAtividades";
    }
    
}
