package Manutencoes;

import Banco.MyException;
import Principal.Botao;
import Principal.Data;
import Principal.Formulario;
import Principal.TelaInterna;
import Tabelas.TableLista;
import Modelos.TrabalhoTableModel;
import Relatorios.TarefaPDF;
import Relatorios.TrabalhoPDF;
import com.itextpdf.text.DocumentException;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Classe que controla as telas e componentes do menu Trabalho
 * @author Jonatha
 */
public class TrabalhoTela extends TelaInterna
{
    /**
     * Construtor da classe, que cria o painel e seus componentes.
     * @throws MyException 
     */
    public TrabalhoTela() throws MyException, ParseException
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
    private TrabalhoTableModel modelo;
    
    private JButton botaoInserir;
    private JButton botaoAlterar;
    private JButton botaoExcluir;
    private JButton botaoRelatorio;
    private JButton botaoFormularios;
    
    private JTextField filtroCodigo;
    private JTextField filtroDescricao;    
    private JComboBox filtroProgramado;
    private JComboBox filtroConcluido;
    private JDateChooser filtroDataInicial;
    private JDateChooser filtroDataFinal;
    
    private JButton botaoPesquisar;
    
    ///////////////////////////////////////////////////////////////////
    
    private JPanel painelFormularioCampos;
    private JPanel painelFormularioBotoes;
    
    private JButton botaoSalvar;
    private JButton botaoLimpar;
    private JButton botaoCancelar;

    private JTextField codigo;
    private JTextField descricao;
    private JComboBox programado;
    private JComboBox concluido;
    private JDateChooser dataInicial;
    private JDateChooser dataFinal;
    
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
    private void criaListaFiltro()
    {
        this.painelListaFiltro = new JPanel();
        this.painelListaFiltro.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.filtroCodigo = Formulario.retornaCaixaTexto(50);
        this.filtroDescricao = Formulario.retornaCaixaTexto(150);
        
        this.filtroProgramado = Formulario.retornaComboBox(Trabalho.isProgramadoLista(true), 70);
        this.filtroConcluido = Formulario.retornaComboBox(Trabalho.isConcluidoLista(true), 70);
        
        this.filtroDataInicial = Formulario.retornaCaixaData();
        this.filtroDataFinal = Formulario.retornaCaixaData();

        this.botaoPesquisar = Botao.retornaBotao("Pesquisar", 140);
        this.botaoPesquisar.addActionListener(new SearchListener());
        
        this.painelListaFiltro.add(this.botaoPesquisar);
        this.painelListaFiltro.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(0)+": "));
        this.painelListaFiltro.add(this.filtroCodigo);
        this.painelListaFiltro.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(1)+": "));
        this.painelListaFiltro.add(this.filtroDescricao);
        this.painelListaFiltro.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(2)+"? "));
        this.painelListaFiltro.add(this.filtroProgramado);
        this.painelListaFiltro.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(3)+"? "));
        this.painelListaFiltro.add(this.filtroConcluido);
        this.painelListaFiltro.add(Formulario.retornaLabel("Início: "));
        this.painelListaFiltro.add(this.filtroDataInicial);
        this.painelListaFiltro.add(Formulario.retornaLabel("Fim: "));
        this.painelListaFiltro.add(this.filtroDataFinal);
    }
    
    /**
     * Cria os botões de ações na lista.
     */
    private void criaListaBotoes()
    {
        this.painelListaBotoes = new JPanel();
        
        this.botaoInserir = Botao.retornaBotao("Inserir", 140);
        this.botaoAlterar = Botao.retornaBotao("Alterar", 140);
        this.botaoExcluir = Botao.retornaBotao("Excluir", 140);
        this.botaoRelatorio = Botao.retornaBotao("Relatório", "Relatorio", 140);
        this.botaoFormularios = Botao.retornaBotao("Formulários", "Formularios", 140);
        
        this.botaoInserir.addActionListener(new InsertListener());
        this.botaoAlterar.addActionListener(new UpdateListener());
        this.botaoExcluir.addActionListener(new DeleteListener());
        this.botaoRelatorio.addActionListener(new ReportListener());
        this.botaoFormularios.addActionListener(new FormsListener());

        this.painelListaBotoes.add(this.botaoInserir);
        this.painelListaBotoes.add(this.botaoAlterar);
        this.painelListaBotoes.add(this.botaoExcluir);
        this.painelListaBotoes.add(this.botaoRelatorio);
        this.painelListaBotoes.add(this.botaoFormularios);
    }

    private void criaListaTabela() throws MyException
    {
        this.modelo = new TrabalhoTableModel();
        
        this.tabela = new TableLista(this.modelo); 
        
        this.tabela.getTableHeader().addMouseListener(new TrabalhoTela.HeaderListener());
        
        this.painelListaTabela = new JScrollPane(this.tabela);
    }

    private void carregaListaTabela() throws MyException
    {
        this.modelo.clear();
        
        this.modelo.addTrabalhoLista(TrabalhoLocalizador.buscaTrabalhos(this.sqlCondicoes, this.sqlOrdem));
    }
    
    /**
     * Exibe os componentes da lista/pesquisa no painel
     * @throws MyException 
     */
    private void exibeLista() throws MyException, ParseException
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
        this.descricao = Formulario.retornaCaixaTexto(180, 60);
        this.programado = Formulario.retornaComboBox(Trabalho.isProgramadoLista(false), 70, 100);
        this.concluido = Formulario.retornaComboBox(Trabalho.isConcluidoLista(false), 70, 140);
        this.dataInicial = Formulario.retornaCaixaData(180);
        this.dataFinal = Formulario.retornaCaixaData(220);
        
        this.codigo.setEnabled(false);
        
        this.painelFormularioCampos.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(0)+": ", 20));
        this.painelFormularioCampos.add(this.codigo);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(TrabalhoLocalizador.getNomeTela(1)+": ", 60));
        this.painelFormularioCampos.add(this.descricao);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(TrabalhoLocalizador.getNomeTela(2)+"? ", 100));
        this.painelFormularioCampos.add(this.programado);
        this.painelFormularioCampos.add(Formulario.retornaLabelObrigatoria(TrabalhoLocalizador.getNomeTela(3)+"? ",  140));
        this.painelFormularioCampos.add(this.concluido);
        this.painelFormularioCampos.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(4)+": ", 180));
        this.painelFormularioCampos.add(this.dataInicial);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("Data em vermelho indica vazio!", 300, 180, "black"));
        this.painelFormularioCampos.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(5)+": ", 220));
        this.painelFormularioCampos.add(this.dataFinal);
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("Data em vermelho indica vazio!", 300, 220, "black"));
        this.painelFormularioCampos.add(Formulario.retornaLabelLegenda("* Campos obrigatórios", 20, 260, "red"));
    }
    
    /**
     * Limpa os campos do formulário.
     * Caso esteja numa alteração, o código não é limpo.
     */
    public void limpaFormulario()
    {
        if(! this.alteracao)
            this.codigo.setText("");
        
        this.descricao.setText("");
        this.programado.setSelectedIndex(0);
        this.concluido.setSelectedIndex(0);
        this.dataInicial.setDate(null);
        this.dataFinal.setDate(null);
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
                sqlCondicoes.add("id_trabalho = " + filtroCodigo.getText());
            
            if(! filtroDescricao.getText().equals("") )
                sqlCondicoes.add("descricao LIKE '%" + filtroDescricao.getText() + "%'");
            
            switch(filtroProgramado.getSelectedIndex())
            {
                case 1:
                    sqlCondicoes.add("programado = 1");
                    break;
                case 2:
                    sqlCondicoes.add("programado = 0");
                    break;
            }
                    
            switch(filtroConcluido.getSelectedIndex())
            {
                case 1:
                    sqlCondicoes.add("concluido = 1");
                    break;
                case 2:
                    sqlCondicoes.add("concluido = 0");
                    break;
            }
            
            if(filtroDataInicial.getDate() != null)
            {
                try {
                    sqlCondicoes.add("data_inicial >= ' "+ Data.convertDataSQL(filtroDataInicial.getDate()) + "'");
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            
            if(filtroDataFinal.getDate() != null)
            {
                try {
                    sqlCondicoes.add("data_final <= '" + Data.convertDataSQL(filtroDataInicial.getDate()) + "'");
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
 
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
                String concluidoTabela = (String) tabela.getValueAt(linhaSelecionada, 3);
                
                if(concluidoTabela.equals("Não"))
                {
                    JOptionPane.showMessageDialog(painel, "Você não pode alterar um trabalho concluído!");
                }
                else
                {
                    int codigoTabela = (int) tabela.getValueAt(linhaSelecionada, 0);
                
                    try {
                        Trabalho o = TrabalhoLocalizador.buscaTrabalho(codigoTabela);

                        alteracao = true;
                        limpaFormulario();

                        codigo.setText(Integer.toString(o.getId()));
                        descricao.setText(o.getDescricao());

                        if(o.isProgramado())
                            programado.setSelectedIndex(1);
                        else
                            programado.setSelectedIndex(2);

                        if(o.isConcluido())
                            concluido.setSelectedIndex(1);
                        else
                            concluido.setSelectedIndex(2);

                        dataInicial.setDate(o.getDataInicial());
                        dataFinal.setDate(o.getDataFinal());

                        exibeFormulario();

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
                String concluidoTabela = (String) tabela.getValueAt(linhaSelecionada, 3);
                
                if(concluidoTabela.equals("Não"))
                {
                    JOptionPane.showMessageDialog(painel, "Você não pode excluir um trabalho concluído!");
                }
                else
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
                            if(TrabalhoPersistor.excluiTrabalho(id))
                                modelo.removeTrabalho(linhaSelecionada);
                        } catch (MyException | ParseException | SQLException ex) {
                            ex.printStackTrace();
                        }
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
                TrabalhoPDF.gerarRelatorioTrabalho(modelo);
            } catch (DocumentException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class FormsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                int linhaSelecionada = -1;
                linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada >= 0)
                {
                    int id = (int) tabela.getValueAt(linhaSelecionada, 0);
                    Trabalho trabalho = TrabalhoLocalizador.buscaTrabalho(id);
                    
                    TarefaPDF.gerarFormulariosConferencia(trabalho);
                }
                else
                {
                    JOptionPane.showMessageDialog(painel, "É necesário selecionar uma linha!");
                }
            } catch (DocumentException | IOException | MyException ex) {
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
            try{
                if(descricao.getText().trim().equals("") || descricao.getText().trim().length() < 10)
                {
                    JOptionPane.showMessageDialog(null, "Informe uma descrição com pelo menos 10 caracteres");
                    descricao.grabFocus();
                }
                else if(descricao.getText().trim().length() > 30)
                {
                    JOptionPane.showMessageDialog(null, "Informe uma descrição com no máximo 30 caracteres");
                    descricao.grabFocus();
                }
                else if(programado.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Escolha se o trabalho será ou não programado");
                    programado.grabFocus();
                }
                else if(concluido.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Escolha se o trabalho foi ou não concluído");
                    concluido.grabFocus();
                }
                else if( (programado.getSelectedIndex() == 1) && (dataInicial.getDate() == null || dataFinal.getDate() == null) )
                {
                    JOptionPane.showMessageDialog(null, "Para trabalhos programados, informe as datas inicial e final!");
                    dataInicial.grabFocus();
                }
                else if(dataInicial.getDate() == null && dataFinal.getDate() != null)
                {
                    JOptionPane.showMessageDialog(null, "Ao informar a data final, você deve informar a data inicial!");
                    dataInicial.grabFocus();
                }
                else if( (dataInicial.getDate() != null) && (dataFinal.getDate() != null) && (dataFinal.getDate().before( dataInicial.getDate() )) )
                {
                    JOptionPane.showMessageDialog(null, "A data inicial deve ser anterior à data final!");
                    dataInicial.grabFocus();
                }
                else if( (concluido.getSelectedIndex() == 1) && (dataFinal.getDate() == null) )
                {
                    JOptionPane.showMessageDialog(null, "Se o trabalho foi concluído, você deve informar a data final");
                    concluido.grabFocus();
                }
                else if( (concluido.getSelectedIndex() == 0) && (dataFinal.getDate() != null) )
                {
                    JOptionPane.showMessageDialog(null, "Se há data final, marque o trabalho como concluído");
                    concluido.grabFocus();
                }
                else if (AtividadeLocalizador.buscaTotalAtividadesTrabalho( false, Integer.parseInt( codigo.getText() ) ) > 0)
                {
                    JOptionPane.showMessageDialog(null, "Só é possível concluir um trabalho cujas atividades estão concluídas!");
                    concluido.grabFocus();
                }
                else
                {
                    try {
                        Trabalho t = new Trabalho();
                        
                        if(codigo.getText().equals(""))
                            t.setId(0);
                        else
                            t.setId(Integer.parseInt(codigo.getText()));
                        
                        t.setDescricao(descricao.getText());
                        
                        if(programado.getSelectedIndex() == 1)
                            t.setProgramado(true);
                        else if(programado.getSelectedIndex() == 2)
                            t.setProgramado(false);
                        
                        if(concluido.getSelectedIndex() == 1)
                            t.setConcluido(true);
                        else if(concluido.getSelectedIndex() == 2)
                            t.setConcluido(false);
                        
                        t.setDataInicial(dataInicial.getDate());
                        t.setDataFinal(dataFinal.getDate());
                        
                        if(codigo.getText().equals(""))
                        {
                            if( TrabalhoPersistor.insereTrabalho(t) )
                                exibeLista();
                        }
                        else
                        {
                            if( TrabalhoPersistor.alteraTrabalho(t) )
                                exibeLista();
                        }
                    } catch (MyException | ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (MyException ex) {
                ex.printStackTrace();
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
            } catch (ParseException ex) {
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
                sqlOrdem = TrabalhoLocalizador.getConversaoSQL(nomeAtual);           
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
        return "Trabalhos";
    }
    
    /**
     * Retorna o nome do arquivo do ícone
     * @return String
     */
    public static String getArquivo()
    {
        return "Trabalhos";
    }
    
}