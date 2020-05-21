package Manutencoes;

import Banco.MyException;
import Modelos.TarefaInsertTableModel;
import Principal.Botao;
import Principal.Data;
import Principal.Formulario;
import Principal.TelaInterna;
import Modelos.TarefaUpdateTableModel;
import Tabelas.TableLista;
import Modelos.TarefaListaTableModel;
import Relatorios.TarefaPDF;
import Tabelas.TableFormularioTarefaInsert;
import Tabelas.TableFormularioTarefaUpdate;
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
public class TarefaTela extends TelaInterna
{
    /**
     * Construtor da classe, que cria o painel e seus componentes.
     * @throws MyException 
     */
    public TarefaTela() throws MyException, ParseException
    {
        super(getTitulo(), getArquivo());
        
        this.criaPainel();
        this.criaFormularioInsert();
        this.criaFormularioUpdate();
        this.criaLista();
        
        this.exibeLista();
        
        getContentPane().add(this.painel);
    }
    
    private JPanel painel;
    
    private JPanel painelListaBotoes;
    
    private JButton botaoInserir;
    private JButton botaoGerenciar;
    private JButton botaoFormulario;
    
    private JPanel painelListaFiltro;
    
    private JComboBox selecaoTrabalho;
    private JComboBox selecaoAtividade;
    
    private JTextField filtroTarefa;
    private JComboBox filtroConcluida;    
    private JDateChooser filtroDataPlanejada;
    private JDateChooser filtroDataRealizada;
    
    private JButton botaoCarregar;
    private JButton botaoPesquisar;
    
    private JScrollPane painelListaTabela;
    private TableLista tabela;
    private TarefaListaTableModel modelo;
    
    ///////////////////////////////////////////////////////////
    
    private JPanel painelFormularioCamposInsert;
    
    private JScrollPane painelInsert;
    private TarefaInsertTableModel modeloInsert;
    private TableFormularioTarefaInsert tabelaInsert;
    
    private JPanel painelFormularioBotoesInsert;
    
    private JButton botaoSalvarInsert;
    private JButton botaoCancelarInsert;
    
    private JPanel painelFormularioCamposUpdate;
    
    private JScrollPane painelUpdate;
    private TarefaUpdateTableModel modeloUpdate;
    private TableFormularioTarefaUpdate tabelaUpdate;
    
    private JPanel painelFormularioBotoesUpdate;
    
    private JButton botaoSalvarUpdate;
    private JButton botaoExcluirUpdate;
    private JButton botaoCancelarUpdate;
    
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
    private void criaListaFiltro() throws MyException, ParseException
    {
        this.painelListaFiltro = new JPanel();
        this.painelListaFiltro.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.selecaoTrabalho = Formulario.retornaComboBox(TrabalhoLocalizador.getModelo(false), 150);
        this.selecaoAtividade = Formulario.retornaComboBox(AtividadeLocalizador.getModelo(0), 100);
        
        this.selecaoTrabalho.addActionListener(new ComboListener());
        this.botaoCarregar = Botao.retornaBotao("Carregar", 140);
        this.botaoCarregar.addActionListener(new LoadListener());
        
        this.filtroTarefa = Formulario.retornaCaixaTexto(100);
        this.filtroConcluida = Formulario.retornaComboBox(Tarefa.isConcluidaLista(true), 70);
        
        this.filtroDataPlanejada = Formulario.retornaCaixaData();
        this.filtroDataRealizada = Formulario.retornaCaixaData();

        this.botaoPesquisar = Botao.retornaBotao("Pesquisar", 140);
        this.botaoPesquisar.addActionListener(new SearchListener());
        
        this.painelListaFiltro.add(Formulario.retornaLabel("Trabalho: "));
        this.painelListaFiltro.add(this.selecaoTrabalho);
        this.painelListaFiltro.add(Formulario.retornaLabel("Atividade: "));
        this.painelListaFiltro.add(this.selecaoAtividade);
        this.painelListaFiltro.add(this.botaoCarregar);
        
        this.painelListaFiltro.add(this.botaoPesquisar);
        this.painelListaFiltro.add(Formulario.retornaLabel(TarefaLocalizador.getNomeTela(0)+": "));
        this.painelListaFiltro.add(this.filtroTarefa);
        this.painelListaFiltro.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(1)+": "));
        this.painelListaFiltro.add(this.filtroDataPlanejada);
        this.painelListaFiltro.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(2)+"? "));
        this.painelListaFiltro.add(this.filtroDataRealizada);
        this.painelListaFiltro.add(Formulario.retornaLabel(TrabalhoLocalizador.getNomeTela(3)+"? "));
        this.painelListaFiltro.add(this.filtroConcluida);
    }
    
    /**
     * Cria os botões de ações na lista.
     */
    private void criaListaBotoes()
    {
        this.painelListaBotoes = new JPanel();
        
        this.botaoInserir = Botao.retornaBotao("Inserir lote", "Inserir", 150);
        this.botaoGerenciar = Botao.retornaBotao("Gerenciar lote", "Alterar", 150);
        this.botaoFormulario = Botao.retornaBotao("Formulário", "Formulario", 150);
        
        this.botaoInserir.addActionListener(new InsertListener());
        this.botaoGerenciar.addActionListener(new ManageListener());
        this.botaoFormulario.addActionListener(new FormListener());

        this.painelListaBotoes.add(this.botaoInserir);
        //this.painelListaBotoes.add(this.botaoGerenciar);
        this.painelListaBotoes.add(this.botaoFormulario);
    }

    private void criaListaTabela() throws MyException
    {
        this.modelo = new TarefaListaTableModel();
        
        this.tabela = new TableLista(this.modelo); 
        
        this.tabela.getTableHeader().addMouseListener(new TarefaTela.HeaderListener());
        
        this.painelListaTabela = new JScrollPane(this.tabela);
    }

    private void carregaListaTabela() throws MyException
    {
        this.modelo.clear();
        
        int atividade = ((Atividade) this.selecaoAtividade.getSelectedItem()).getId();
        
        this.modelo.addTarefaLista(TarefaLocalizador.buscaTarefas(atividade, this.sqlCondicoes, this.sqlOrdem));
    }
    
    /**
     * Exibe os componentes da lista/pesquisa no painel
     * @throws MyException 
     */
    private void exibeLista() throws MyException, ParseException
    {
        this.painel.remove(this.painelFormularioCamposInsert);        
        this.painel.remove(this.painelFormularioBotoesInsert);
        this.painel.remove(this.painelFormularioCamposUpdate);        
        this.painel.remove(this.painelFormularioBotoesUpdate);
        
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
    public void criaFormularioInsert() throws MyException, ParseException
    {     
        this.criaFormularioCamposInsert();
        this.criaFormularioBotoesInsert();
    }
    
    /**
     * Cria os campos do formulário
     */
    private void criaFormularioCamposInsert() throws MyException, ParseException
    {
        this.painelFormularioCamposInsert = new JPanel();
        this.painelFormularioCamposInsert.setLayout(null);

        ArrayList<Object> componentes = new ArrayList<Object>();
        componentes.add(new JTextField());   
        componentes.add(new JTextField());
        
        this.modeloInsert = new TarefaInsertTableModel();
        this.tabelaInsert = new TableFormularioTarefaInsert(this.modeloInsert, componentes); 
        
        this.painelInsert = new JScrollPane(tabelaInsert);
        this.painelInsert.setBounds(20, 20, 1000, 600);

        this.painelFormularioCamposInsert.add(this.painelInsert);   
        
    }
    
    /**
     * Limpa os campos do formulário.
     * Caso esteja numa alteração, o código não é limpo.
     */
    public void limpaFormularioInsert()
    {
        this.modeloInsert.limparLista();
    }
    
    /**
     * Cria os botões de ações no formulário.
     */
    private void criaFormularioBotoesInsert()
    {
        this.painelFormularioBotoesInsert = new JPanel();
        
        this.botaoSalvarInsert = Botao.retornaBotao("Salvar", 120);
        this.botaoCancelarInsert = Botao.retornaBotao("Cancelar", 120);
        
        this.botaoSalvarInsert.addActionListener(new SaveInsertListener());
        this.botaoCancelarInsert.addActionListener(new CancelListener());

        this.painelFormularioBotoesInsert.add(this.botaoSalvarInsert);
        this.painelFormularioBotoesInsert.add(this.botaoCancelarInsert);
    }
    
    /**
     * Exibe os componentes do formulário no painel
     */
    private void exibeFormularioInsert() throws MyException, ParseException
    {
        this.painel.remove(this.painelListaFiltro);
        this.painel.remove(this.painelListaTabela);
        this.painel.remove(this.painelListaBotoes);

        int atividade = ((Atividade) this.selecaoAtividade.getSelectedItem()).getId();
        this.modeloInsert.carregarLista(atividade, ModeloTarefaLocalizador.buscaModelosTarefas());

        this.painel.add(BorderLayout.CENTER, this.painelFormularioCamposInsert);        
        this.painel.add(BorderLayout.SOUTH, this.painelFormularioBotoesInsert);
        
        this.painel.revalidate();
        this.painel.repaint();
    }
    
    ///////////////////////////////////////////////////////////////////
    
    /**
     * Chama os métodos que criam os componentes que exibem o formulário
     * @throws MyException 
     */
    public void criaFormularioUpdate() throws MyException, ParseException
    {     
        this.criaFormularioCamposUpdate();
        this.criaFormularioBotoesUpdate();
    }
    
    /**
     * Cria os campos do formulário
     */
    private void criaFormularioCamposUpdate() throws MyException, ParseException
    {
        this.painelFormularioCamposUpdate = new JPanel();
        this.painelFormularioCamposUpdate.setLayout(null);

        ArrayList<Object> componentes = new ArrayList<Object>();
        componentes.add(new JTextField());   
        componentes.add(new JTextField());
        componentes.add(new JTextField());
        
        this.modeloUpdate = new TarefaUpdateTableModel();
        this.tabelaUpdate = new TableFormularioTarefaUpdate(this.modeloUpdate, componentes); 
        
        this.painelUpdate = new JScrollPane(tabelaUpdate);
        this.painelUpdate.setBounds(20, 20, 1000, 600);

        this.painelFormularioCamposUpdate.add(this.painelUpdate);   
        
    }
    
    /**
     * Limpa os campos do formulário.
     * Caso esteja numa alteração, o código não é limpo.
     */
    public void limpaFormularioUpdate()
    {
        this.modeloUpdate.limparLista();
    }
    
    /**
     * Cria os botões de ações no formulário.
     */
    private void criaFormularioBotoesUpdate()
    {
        this.painelFormularioBotoesUpdate = new JPanel();
        
        this.botaoSalvarUpdate = Botao.retornaBotao("Salvar", 120);
        this.botaoExcluirUpdate = Botao.retornaBotao("Excluir", 120);
        this.botaoCancelarUpdate = Botao.retornaBotao("Cancelar", 120);
        
        this.botaoSalvarUpdate.addActionListener(new SaveUpdateListener());
        this.botaoExcluirUpdate.addActionListener(new DeleteListener());
        this.botaoCancelarUpdate.addActionListener(new CancelListener());

        this.painelFormularioBotoesUpdate.add(this.botaoSalvarUpdate);
        this.painelFormularioBotoesUpdate.add(this.botaoExcluirUpdate);        
        this.painelFormularioBotoesUpdate.add(this.botaoCancelarUpdate);
    }
    
    /**
     * Exibe os componentes do formulário no painel
     */
    private void exibeFormularioUpdate() throws MyException, ParseException
    {
        this.painel.remove(this.painelListaFiltro);
        this.painel.remove(this.painelListaTabela);
        this.painel.remove(this.painelListaBotoes);

        int atividade = ((Atividade) this.selecaoAtividade.getSelectedItem()).getId();
        this.modeloUpdate.carregarLista(TarefaLocalizador.buscaTarefasAtividade(atividade));  
        
        this.painel.add(BorderLayout.CENTER, this.painelFormularioCamposUpdate);        
        this.painel.add(BorderLayout.SOUTH, this.painelFormularioBotoesUpdate);
        
        this.painel.revalidate();
        this.painel.repaint();
    }
    
    ////////////////////////////////////////////////////////////////
    
    private class LoadListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                if(selecaoTrabalho.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(painel, "Selecione o trabalho!");
                }
                else if(selecaoAtividade.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(painel, "Selecione a atividade!");
                }
                
                carregaListaTabela();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Ações executadas quando o botão "Pesquisar" do filtro é clicado
     */
    private class SearchListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            sqlCondicoes.clear();
            
            if(! filtroTarefa.getText().equals("") )
                sqlCondicoes.add("id_tarefa IN (SELECT id_tarefa FROM tarefa_modelo WHERE descricao LIKE '%" + filtroTarefa.getText() + "%')");

            switch(filtroConcluida.getSelectedIndex())
            {
                case 1:
                    sqlCondicoes.add("concluida = 1");
                    break;
                case 2:
                    sqlCondicoes.add("concluida = 0");
                    break;
            }
            
            if(filtroDataPlanejada.getDate() != null)
            {
                try {
                    sqlCondicoes.add("data_planejada >= ' "+ Data.convertDataSQL(filtroDataPlanejada.getDate()) + "'");
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            
            if(filtroDataRealizada.getDate() != null)
            {
                try {
                    sqlCondicoes.add("data_realizada <= '" + Data.convertDataSQL(filtroDataRealizada.getDate()) + "'");
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
            if(selecaoTrabalho.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(painel, "Selecione o trabalho!");
            }
            else if(selecaoAtividade.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(painel, "Selecione a atividade!");
            }
            else
            {
                try {
                    alteracao = false;

                    limpaFormularioInsert();                    
                    exibeFormularioInsert();
                } catch (MyException | ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Ações executadas quando o botão "Alterar" da tabela é clicado
     */
    private class ManageListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(selecaoTrabalho.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(painel, "Selecione o trabalho!");
            }
            else if(selecaoAtividade.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(painel, "Selecione a atividade!");
            }
            else
            {
                try {
                    alteracao = true;
            
                    limpaFormularioUpdate();                    
                    exibeFormularioUpdate();
                } catch (MyException | ParseException ex) {
                    ex.printStackTrace();
                }
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
//            int linhaSelecionada = -1;
//            linhaSelecionada = tabela.getSelectedRow();
//            if (linhaSelecionada >= 0)
//            {
//                int atividade = ((Atividade) selecaoTrabalho.getSelectedItem()).getId();                
//                int tarefa = (int) tabela.getValueAt(linhaSelecionada, 0);                
//                String nome = (String) tabela.getValueAt(linhaSelecionada, 1);
//                
//                int opcao = JOptionPane.showConfirmDialog(
//                        null,
//                        "Deseja excluir definitivamente "+nome+"?", 
//                        "Excluir",
//                        JOptionPane.YES_NO_OPTION);
//                
//                if(opcao == JOptionPane.YES_OPTION)
//                {
//                    try {
//                        if(TarefaPersistor.excluiTrabalho(atividade, tarefa))
//                            modelo.removeTarefa(linhaSelecionada);
//                    } catch (MyException ex) {
//                        ex.printStackTrace();
//                    } catch (ParseException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//                else
//            {
//                JOptionPane.showMessageDialog(painel, "É necesário selecionar uma linha!");
//            }
        }
    }
    
    private class FormListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                if(selecaoTrabalho.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(painel, "Selecione o trabalho!");
                }
                else if(selecaoAtividade.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(painel, "Selecione a atividade!");
                }
                else
                {
                    TarefaPDF.gerarFormularioConferencia((Atividade) selecaoAtividade.getSelectedItem());
                }
            } catch (DocumentException | IOException | MyException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Ações executadas quando o botão "Salvar" do formulário é clicado
     */
    private class SaveInsertListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                
                Atividade atividade = AtividadeLocalizador.buscaAtividadeId( ( (Atividade) selecaoAtividade.getSelectedItem() ).getId() );
                
                if(modeloInsert.validarDatas(atividade.getDataInicial(), atividade.getDataFinal()))
                {
                    if(TarefaPersistor.insereTarefas(modeloInsert.criarTarefas()))
                    {
                        exibeLista();
                    }
                }
            } catch (ParseException | MyException | SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class SaveUpdateListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        { 
            
        }
    }
    
    /**
     * Ações executadas quando o botão "Limpar" do formulário é clicado
     */
    private class CleanInsertListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            limpaFormularioInsert();
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
    
    private class ComboListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                int trabalho = ((Trabalho) selecaoTrabalho.getSelectedItem()).getId();                
                AtividadeLocalizador.carregaModelo(trabalho);
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
        return "Tarefas";
    }
    
    /**
     * Retorna o nome do arquivo do ícone
     * @return String
     */
    public static String getArquivo()
    {
        return "Tarefas";
    }
    
}