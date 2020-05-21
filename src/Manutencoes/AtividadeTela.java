package Manutencoes;

import Cadastros.*;
import Banco.MyException;
import Configuracoes.Operador;
import Configuracoes.OperadorLocalizador;
import Principal.Botao;
import Principal.Data;
import Principal.Formulario;
import Tabelas.TableLista;
import Principal.TelaInterna;
import Modelos.AtividadeListaTableModel;
import Modelos.AtividadeInsertTableModel;
import Modelos.AtividadeUpdateTableModel;
import Relatorios.AtividadePDF;
import Relatorios.TarefaPDF;
import Tabelas.TableFormularioAtividadeInsert;
import Tabelas.TableFormularioAtividadeUpdate;
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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Classe que controla as telas e componentes do menu Atividade
 * @author Jonatha
 */
public class AtividadeTela extends TelaInterna
{
    /**
     * Construtor da classe, que cria o painel e seus componentes.
     * @throws MyException 
     */
    public AtividadeTela() throws MyException, ParseException
    {
        super(getTitulo(), getArquivo());
        
        this.criaPainel();
        this.criaFormularioInsert();
        this.criaFormularioUpdate();
        this.criaFormularioUnica();        
        this.criaLista();
        
        this.exibeListaTabela();
        
        getContentPane().add(this.painel);
    }
    
    private JPanel painel;

    private JScrollPane painelListaTabela;    
    
    private TableLista tabela;
    private AtividadeListaTableModel modelo;
    
    private JPanel painelListaBotoes;
    
    private JButton botaoInserirUnica;
    private JButton botaoInserirLote;
    private JButton botaoAlterarUnica;
    private JButton botaoAlterarLote;
    private JButton botaoExcluir;
    private JButton botaoRelatorio;
    private JButton botaoFormulario;
    private JButton botaoFormularios;
    
    private JPanel painelListaFiltro;
    
    private JComboBox filtroTrabalho;
    private JComboBox filtroMaquina;
    private JComboBox filtroTipo;
    private JComboBox filtroOperador;
    private JDateChooser filtroDataInicial;
    private JDateChooser filtroDataFinal;
    private JComboBox filtroConcluida;
    
    private JButton botaoPesquisar;
    
    ///////////////////////////////////////////////////////////////////
    
    private JPanel painelFormularioCamposUnica;
    
    private JTextField codigo;
    private JComboBox trabalho;
    private JComboBox maquina;
    private JComboBox tipo;
    private JComboBox operador;
    private JDateChooser dataInicial;
    private JDateChooser dataFinal;
    private JTextArea observacoes;
    private JComboBox concluida;
    
    private JPanel painelFormularioBotoesUnica;
    
    private JButton botaoSalvarUnica;
    private JButton botaoLimparUnica;
    private JButton botaoCancelarUnica;
    
    private JPanel painelFormularioCamposInsert;
    
    private JComboBox trabalhoInsert;
    private JTextField distribuicao;
    
    private JPanel painelFormularioBotoesInsert;
    
    private JButton botaoDistribuirInsert;
    private JButton botaoSalvarInsert;   
    private JButton botaoLimparInsert;
    private JButton botaoCancelarInsert;
    
    private JPanel painelFormularioCamposUpdate;
    
    private JComboBox trabalhoUpdate;
    
    private JPanel painelFormularioBotoesUpdate;
    
    private JButton botaoSalvarUpdate;   
    private JButton botaoCancelarUpdate;
    
    private JButton carregar;
    
    private TableFormularioAtividadeInsert tabelaInsert;
    private TableFormularioAtividadeUpdate tabelaUpdate;
    
    private JScrollPane painelInsert;
    private JScrollPane painelUpdate;
    
    public static AtividadeInsertTableModel modeloInsert;
    public static AtividadeUpdateTableModel modeloUpdate;
    
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
        
        this.filtroTrabalho = Formulario.retornaComboBox(TrabalhoLocalizador.getModelo(false), 150);
        this.filtroMaquina = Formulario.retornaComboBox(MaquinaLocalizador.getModelo(), 100);
        this.filtroTipo = Formulario.retornaComboBox(TipoAtividadeLocalizador.getModelo(), 100);
        this.filtroOperador = Formulario.retornaComboBox(OperadorLocalizador.getModelo(), 100);        
        this.filtroDataInicial = Formulario.retornaCaixaData();
        this.filtroDataFinal = Formulario.retornaCaixaData();        
        this.filtroConcluida = Formulario.retornaComboBox(Atividade.isConcluidaLista(true), 70);
        
        this.botaoPesquisar = Botao.retornaBotao("Pesquisar", 140);
        this.botaoPesquisar.addActionListener(new SearchListener());
        
        this.painelListaFiltro.add(this.botaoPesquisar);
        this.painelListaFiltro.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(1)+": "));
        this.painelListaFiltro.add(this.filtroTrabalho);
        this.painelListaFiltro.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(2)+": "));
        this.painelListaFiltro.add(this.filtroMaquina);
        this.painelListaFiltro.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(3)+": "));
        this.painelListaFiltro.add(this.filtroTipo);
        this.painelListaFiltro.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(4)+": "));
        this.painelListaFiltro.add(this.filtroOperador);        
        this.painelListaFiltro.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(5)+": "));
        this.painelListaFiltro.add(this.filtroDataInicial);
        this.painelListaFiltro.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(6)+": "));
        this.painelListaFiltro.add(this.filtroDataFinal);
        this.painelListaFiltro.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(8)+": "));
        this.painelListaFiltro.add(this.filtroConcluida);
    }
    
    /**
     * Cria os botões de ações na lista.
     */
    private void criaListaBotoes()
    {
        this.painelListaBotoes = new JPanel();
        
        this.botaoInserirUnica = Botao.retornaBotao("Inserir única", "Inserir1", 140);
        this.botaoInserirLote = Botao.retornaBotao("Inserir lote", "InserirN", 140);
        this.botaoAlterarUnica = Botao.retornaBotao("Alterar única", "Alterar1", 140);
        this.botaoAlterarLote = Botao.retornaBotao("Alterar lote", "AlterarN", 140);
        this.botaoExcluir = Botao.retornaBotao("Excluir", 140);
        this.botaoRelatorio = Botao.retornaBotao("Relatório", "Relatorio", 140);
        this.botaoFormulario = Botao.retornaBotao("Formulário", "Formulario", 140);
        this.botaoFormularios = Botao.retornaBotao("Formulários", "Formularios", 140);
        
        this.botaoInserirUnica.addActionListener(new InsertUnicaListener());
        this.botaoInserirLote.addActionListener(new InsertLoteListener());
        this.botaoAlterarUnica.addActionListener(new UpdateUnicaListener());
        this.botaoAlterarLote.addActionListener(new UpdateLoteListener());
        this.botaoExcluir.addActionListener(new DeleteListener());
        this.botaoRelatorio.addActionListener(new ReportListener());
        this.botaoFormulario.addActionListener(new FormListener());
        this.botaoFormularios.addActionListener(new FormsListener());

        this.painelListaBotoes.add(this.botaoInserirUnica);
        this.painelListaBotoes.add(this.botaoInserirLote);
        this.painelListaBotoes.add(this.botaoAlterarUnica);
        this.painelListaBotoes.add(this.botaoAlterarLote);
        this.painelListaBotoes.add(this.botaoExcluir);
        this.painelListaBotoes.add(this.botaoRelatorio);
        this.painelListaBotoes.add(this.botaoFormulario);
        this.painelListaBotoes.add(this.botaoFormularios);
        
    }

    /**
     * Cria a tabela que exibe os registros.
     */
    private void criaListaTabela() throws MyException
    {
        this.modelo = new AtividadeListaTableModel();
        
        this.tabela = new TableLista(this.modelo); 
        
        this.tabela.getTableHeader().addMouseListener(new AtividadeTela.HeaderListener());
        
        this.painelListaTabela = new JScrollPane(this.tabela);
    }

    private void carregaListaTabela() throws MyException
    {
        this.modelo.clear();
        
        this.modelo.addAtividadeLista(AtividadeLocalizador.buscaAtividades(this.sqlCondicoes, this.sqlOrdem));
    }
    
    /**
     * Exibe os componentes da lista/pesquisa no painel
     * @throws MyException 
     */
    private void exibeListaTabela() throws MyException, ParseException
    {
        this.painel.remove(this.painelFormularioCamposUnica);     
        this.painel.remove(this.painelFormularioBotoesUnica);
        this.painel.remove(this.painelFormularioCamposInsert);
        this.painel.remove(this.painelFormularioBotoesInsert);
        this.painel.remove(this.painelFormularioCamposUpdate);
        this.painel.remove(this.painelFormularioBotoesUpdate);
        
        this.painel.add(BorderLayout.NORTH, this.painelListaFiltro);
        this.painel.add(BorderLayout.CENTER, this.painelListaTabela);        
        this.painel.add(BorderLayout.SOUTH, this.painelListaBotoes);
        
        this.carregaListaTabela();
        
        this.painel.revalidate();
        this.painel.repaint();
    }
    
    ///////////////////////////////////////////////////////////////////
    
    private void criaListaInsert() throws MyException
    {
        this.modeloInsert = new AtividadeInsertTableModel();
        
        ArrayList<Object> componentes = new ArrayList<Object>();
        componentes.add(new JComboBox(TipoAtividadeLocalizador.getModelo()));
        componentes.add(new JTextField());       
        
        this.tabelaInsert = new TableFormularioAtividadeInsert(this.modeloInsert, componentes); 
                
        this.modeloInsert.carregarLista(MaquinaLocalizador.buscaMaquinasAtividades());
        
        this.painelInsert = new JScrollPane(tabelaInsert);

    }
    
    public void criaFormularioInsert() throws MyException, ParseException
    {     
        this.criaListaInsert();
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
        
        this.trabalhoInsert = Formulario.retornaComboBox(TrabalhoLocalizador.getModelo(true), 250, 20);
        this.distribuicao = Formulario.retornaCaixaNumeros(50, 60);
        
        this.painelInsert.setBounds(160, 100, 900, 600);

        this.painelFormularioCamposInsert.add(Formulario.retornaLabelObrigatoria(AtividadeLocalizador.getNomeTela(1)+": ", 20));
        this.painelFormularioCamposInsert.add(this.trabalhoInsert);
        
        this.painelFormularioCamposInsert.add(Formulario.retornaLabelObrigatoria("Máquinas por dia: ", 60));
        this.painelFormularioCamposInsert.add(this.distribuicao);
        
        this.painelFormularioCamposInsert.add(Formulario.retornaLabelObrigatoria("Máquinas: ", 100));
        this.painelFormularioCamposInsert.add(this.painelInsert);
    }
    
    private void criaFormularioBotoesInsert()
    {
        this.painelFormularioBotoesInsert = new JPanel();
        
        this.botaoSalvarInsert = Botao.retornaBotao("Salvar", 120);
        this.botaoDistribuirInsert = Botao.retornaBotao("Distribuir", 120);
        this.botaoLimparInsert = Botao.retornaBotao("Limpar", 120);
        this.botaoCancelarInsert = Botao.retornaBotao("Cancelar", 120);
        
        this.botaoSalvarInsert.addActionListener(new SaveInsertListener());
        this.botaoDistribuirInsert.addActionListener(new DistributeListener());
        this.botaoLimparInsert.addActionListener(new CleanInsertListener());
        this.botaoCancelarInsert.addActionListener(new CancelListener());
    }
    
    public void limpaFormularioInsert() throws MyException
    {
        this.trabalhoInsert.setSelectedIndex(0);
        this.distribuicao.setText("");
        AtividadeTela.modeloInsert.resetarLista();
    }
    
    /**
     * Exibe os componentes do formulário no painel
     */
    private void exibeFormularioInsert()
    {
        this.painel.remove(this.painelListaFiltro);
        this.painel.remove(this.painelListaTabela);
        this.painel.remove(this.painelListaBotoes);
        
        this.painel.add(BorderLayout.CENTER, this.painelFormularioCamposInsert);        
        this.painel.add(BorderLayout.SOUTH, this.painelFormularioBotoesInsert);
        
        this.habilitarFormularioInsertSelecao();
        
        this.painel.revalidate();
        this.painel.repaint();
    }
    
    private void habilitarFormularioInsertSelecao()
    {
        this.painelFormularioBotoesInsert.remove(botaoSalvarInsert);
        this.painelFormularioBotoesInsert.remove(botaoCancelarInsert);
        
        this.painelFormularioBotoesInsert.add(botaoDistribuirInsert);
        this.painelFormularioBotoesInsert.add(botaoLimparInsert);
        this.painelFormularioBotoesInsert.add(botaoCancelarInsert);  
        
        this.trabalhoInsert.setEnabled(true);
        this.distribuicao.setEnabled(true);
        
        painelFormularioBotoesInsert.revalidate();
        painelFormularioBotoesInsert.repaint();
        
        this.modeloInsert.ativarColunasSelecao();
    }
    
    private void habilitarFormularioInsertData()
    {
        this.painelFormularioBotoesInsert.remove(botaoDistribuirInsert);
        this.painelFormularioBotoesInsert.remove(botaoLimparInsert);
        this.painelFormularioBotoesInsert.remove(botaoCancelarInsert);  
        
        this.painelFormularioBotoesInsert.add(botaoSalvarInsert);
        this.painelFormularioBotoesInsert.add(botaoCancelarInsert);
        
        this.trabalhoInsert.setEnabled(false);
        this.distribuicao.setEnabled(false);
        
        painelFormularioBotoesInsert.revalidate();
        painelFormularioBotoesInsert.repaint();
        
        this.modeloInsert.ativarColunasDatas();
    }
    
    ///////////////////////////////////////////////////////////////////
    
    private void criaListaUpdate() throws MyException
    {
        this.modeloUpdate = new AtividadeUpdateTableModel();
        
        ArrayList<Object> componentes = new ArrayList<Object>();
        componentes.add(new JTextField());
        componentes.add(new JTextField());
        componentes.add(new JComboBox(OperadorLocalizador.getModelo()));        
        componentes.add(new JTextField());        

        this.tabelaUpdate = new TableFormularioAtividadeUpdate(this.modeloUpdate, componentes); 
       
        this.painelUpdate = new JScrollPane(tabelaUpdate);

    }
    
    public void criaFormularioUpdate() throws MyException, ParseException
    {     
        this.criaListaUpdate();
        this.criaFormularioCamposUpdate();
        this.criaFormularioBotoesUpdate();
    }
    
    private void criaFormularioCamposUpdate() throws MyException, ParseException
    {
        this.painelFormularioCamposUpdate = new JPanel();
        this.painelFormularioCamposUpdate.setLayout(null);
        
        this.trabalhoUpdate = Formulario.retornaComboBox(TrabalhoLocalizador.getModelo(true), 250, 20);
        
        this.carregar = new JButton("Carregar");
        this.carregar.addActionListener(new CarregarListener());
        this.carregar.setBounds(325, 25, 100, 25);
        
        this.painelUpdate.setBounds(160, 60, 1100, 600);

        this.painelFormularioCamposUpdate.add(Formulario.retornaLabelObrigatoria(AtividadeLocalizador.getNomeTela(1)+": ", 20));
        this.painelFormularioCamposUpdate.add(this.trabalhoUpdate);
        this.painelFormularioCamposUpdate.add(this.carregar);
        
        this.painelFormularioCamposUpdate.add(Formulario.retornaLabelObrigatoria("Máquinas: ", 60));
        this.painelFormularioCamposUpdate.add(this.painelUpdate);
    }
    
    private void criaFormularioBotoesUpdate()
    {
        this.painelFormularioBotoesUpdate = new JPanel();
        
        this.botaoSalvarUpdate = Botao.retornaBotao("Salvar", 120);
        this.botaoCancelarUpdate = Botao.retornaBotao("Cancelar", 120);
        
        this.botaoSalvarUpdate.addActionListener(new SaveUpdateListener());
        this.botaoCancelarUpdate.addActionListener(new CancelListener());
        
        this.painelFormularioBotoesUpdate.add(this.botaoSalvarUpdate);
        this.painelFormularioBotoesUpdate.add(this.botaoCancelarUpdate);
    }
    
    public void limpaFormularioUpdate() throws MyException
    {
        this.trabalhoUpdate.setSelectedIndex(0);
        AtividadeTela.modeloUpdate.limparLista();
    }
    
    private void exibeFormularioUpdate()
    {
        this.painel.remove(this.painelListaFiltro);
        this.painel.remove(this.painelListaTabela);
        this.painel.remove(this.painelListaBotoes);
        
        this.painel.add(BorderLayout.CENTER, this.painelFormularioCamposUpdate);        
        this.painel.add(BorderLayout.SOUTH, this.painelFormularioBotoesUpdate);
        
        this.painel.revalidate();
        this.painel.repaint();
    }
    
    ///////////////////////////////////////////////////////////////////
    
    /**
     * Chama os métodos que criam os componentes que exibem o formulário
     * @throws MyException 
     */
    public void criaFormularioUnica() throws MyException, ParseException
    {     
        this.criaFormularioCamposUnica();
        this.criaFormularioBotoesUnica();
    }
        
    /**
     * Cria os campos do formulário
     */
    private void criaFormularioCamposUnica() throws MyException, ParseException
    {
        this.painelFormularioCamposUnica = new JPanel();
        this.painelFormularioCamposUnica.setLayout(null);
        
        this.codigo = Formulario.retornaCaixaTexto(50, 20);
        this.trabalho = Formulario.retornaComboBox(TrabalhoLocalizador.getModelo(false), 250, 60);
        this.maquina = Formulario.retornaComboBox(MaquinaLocalizador.getModelo(), 100, 100);
        this.tipo = Formulario.retornaComboBox(TipoAtividadeLocalizador.getModelo(), 150, 140);
        this.operador = Formulario.retornaComboBox(OperadorLocalizador.getModelo(), 150, 180);        
        this.dataInicial = Formulario.retornaCaixaData(220);
        this.dataFinal = Formulario.retornaCaixaData(260);        
        this.concluida = Formulario.retornaComboBox(Atividade.isConcluidaLista(false), 70, 300);
        this.observacoes = Formulario.retornaAreaTexto(300, 340);
        
        this.codigo.setEnabled(false);
        
        this.painelFormularioCamposUnica.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(0)+": ", 20));
        this.painelFormularioCamposUnica.add(this.codigo);
        this.painelFormularioCamposUnica.add(Formulario.retornaLabelObrigatoria(AtividadeLocalizador.getNomeTela(1)+": ", 60));
        this.painelFormularioCamposUnica.add(this.trabalho);
        this.painelFormularioCamposUnica.add(Formulario.retornaLabelObrigatoria(AtividadeLocalizador.getNomeTela(2)+": ", 100));
        this.painelFormularioCamposUnica.add(this.maquina);
        this.painelFormularioCamposUnica.add(Formulario.retornaLabelObrigatoria(AtividadeLocalizador.getNomeTela(3)+": ", 140));
        this.painelFormularioCamposUnica.add(this.tipo);
        this.painelFormularioCamposUnica.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(4)+": ", 180));
        this.painelFormularioCamposUnica.add(this.operador);        
        this.painelFormularioCamposUnica.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(5)+": ", 220));
        this.painelFormularioCamposUnica.add(this.dataInicial);
        this.painelFormularioCamposUnica.add(Formulario.retornaLabel(AtividadeLocalizador.getNomeTela(6)+": ", 260));
        this.painelFormularioCamposUnica.add(this.dataFinal);
        this.painelFormularioCamposUnica.add(Formulario.retornaLabelObrigatoria(AtividadeLocalizador.getNomeTela(8)+": ", 300));
        this.painelFormularioCamposUnica.add(this.concluida);
        this.painelFormularioCamposUnica.add(Formulario.retornaLabel(MaquinaLocalizador.getNomeTela(9)+": ", 340));
        this.painelFormularioCamposUnica.add(this.observacoes);        
        this.painelFormularioCamposUnica.add(Formulario.retornaLabelLegenda("* Campos obrigatórios", 20, 480, "red"));
    }
    
    /**
     * Cria os botões de ações no formulário.
     */
    private void criaFormularioBotoesUnica()
    {
        this.painelFormularioBotoesUnica = new JPanel();
        
        this.botaoSalvarUnica = Botao.retornaBotao("Salvar", 120);
        this.botaoLimparUnica = Botao.retornaBotao("Limpar", 120);
        this.botaoCancelarUnica = Botao.retornaBotao("Cancelar", 120);
        
        this.botaoSalvarUnica.addActionListener(new SaveUnicaListener());
        this.botaoLimparUnica.addActionListener(new CleanUnicaListener());
        this.botaoCancelarUnica.addActionListener(new CancelListener());

        this.painelFormularioBotoesUnica.add(this.botaoSalvarUnica);
        this.painelFormularioBotoesUnica.add(this.botaoLimparUnica);
        this.painelFormularioBotoesUnica.add(this.botaoCancelarUnica);
    }
    
    /**
     * Limpa os campos do formulário.
     * Caso esteja numa alteração, o código não é limpo.
     */
    public void limpaFormularioUnica() throws MyException
    {
        if(! this.alteracao)
            this.codigo.setText("");
        
        this.trabalho.setSelectedIndex(0);
        this.maquina.setSelectedIndex(0);
        this.tipo.setSelectedIndex(0);
        this.operador.setSelectedIndex(0);
        this.dataInicial.setDate(null);
        this.dataFinal.setDate(null);
        this.concluida.setSelectedIndex(0);
    }
    
    /**
     * Exibe os componentes do formulário no painel
     */
    private void exibeFormularioUnica()
    {
        this.painel.remove(this.painelListaFiltro);
        this.painel.remove(this.painelListaTabela);
        this.painel.remove(this.painelListaBotoes);
        
        this.painel.add(BorderLayout.CENTER, this.painelFormularioCamposUnica);        
        this.painel.add(BorderLayout.SOUTH, this.painelFormularioBotoesUnica);
        
        this.painel.revalidate();
        this.painel.repaint();
    }
    
    /////////////////////////////////////////////////////////
    
    /**
     * Ações executadas quando o botão "Pesquisar" do filtro é clicado
     */
    private class SearchListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            sqlCondicoes.clear();
            
            if(filtroTrabalho.getSelectedIndex() != 0)
                sqlCondicoes.add("id_trabalho = " + ( (Trabalho) filtroTrabalho.getSelectedItem() ).getId());

            if(filtroMaquina.getSelectedIndex() != 0)
                sqlCondicoes.add("id_maquina = " + ( (Maquina) filtroMaquina.getSelectedItem() ).getId());
            
            if(filtroTipo.getSelectedIndex() != 0)
                sqlCondicoes.add("id_tipo = " + ( (TipoAtividade) filtroTipo.getSelectedItem() ).getId());
            
            if(filtroOperador.getSelectedIndex() != 0)
                sqlCondicoes.add("id_operador = " + ( (Operador) filtroOperador.getSelectedItem() ).getId());
            
            switch(filtroConcluida.getSelectedIndex())
            {
                case 1:
                    sqlCondicoes.add("concluida = 1");
                    break;
                case 2:
                    sqlCondicoes.add("concluida = 0");
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
    private class InsertUnicaListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                alteracao = false;
                limpaFormularioUnica();
                exibeFormularioUnica();
            } catch (MyException ex) {
                ex.printStackTrace();
            }            
        }
    }
    
    /**
     * Ações executadas quando o botão "Inserir" da tabela é clicado
     */
    private class InsertLoteListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                alteracao = false;
                limpaFormularioInsert();
                exibeFormularioInsert();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Ações executadas quando o botão "Alterar" da tabela é clicado
     */
    private class UpdateUnicaListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0)
            {
                String concluidoTabela = (String) tabela.getValueAt(linhaSelecionada, 7);
                
                if(concluidoTabela.equals("Não"))
                {
                    JOptionPane.showMessageDialog(painel, "Você não pode alterar uma atividade concluída!");
                }
                else
                {
                    int id = (int) tabela.getValueAt(linhaSelecionada, 0);

                    try {
                        Atividade a = AtividadeLocalizador.buscaAtividadeId(id);

                        alteracao = true;
                        limpaFormularioUnica();

                        codigo.setText( Integer.toString( a.getId() ) );
                        trabalho.setSelectedIndex( TrabalhoLocalizador.getIndexModelo(false, a.getTrabalho()) );
                        maquina.setSelectedIndex( MaquinaLocalizador.getIndexModelo(a.getMaquina()) );
                        tipo.setSelectedIndex( TipoAtividadeLocalizador.getIndexModelo(a.getTipo()) );
                        operador.setSelectedIndex( OperadorLocalizador.getIndexModelo(a.getOperador()) );

                        dataInicial.setDate(a.getDataInicial());
                        dataFinal.setDate(a.getDataFinal());

                        if(a.isConcluida())
                            concluida.setSelectedIndex(1);
                        else
                            concluida.setSelectedIndex(2);

                        observacoes.setText(a.getObservacoes());

                        exibeFormularioUnica();

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

    private class UpdateLoteListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                alteracao = true;
                limpaFormularioUpdate();
                exibeFormularioUpdate();
            } catch (MyException ex) {
                ex.printStackTrace();
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
                String concluidoTabela = (String) tabela.getValueAt(linhaSelecionada, 7);
                
                if(concluidoTabela.equals("Não"))
                {
                    JOptionPane.showMessageDialog(painel, "Você não pode excluir uma atividade concluída!");
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
                            if(AtividadePersistor.excluiAtividade(id))
                            {
                                JOptionPane.showMessageDialog(null, "Atividade excluída com sucesso!");
                                modelo.removeAtividade(linhaSelecionada);
                            }
                        } catch (MyException ex) {
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
                AtividadePDF.gerarRelatorioAtividade(modelo);
            } catch (DocumentException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class FormListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                int linhaSelecionada = -1;
                linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada >= 0)
                {
                    int id = (int) tabela.getValueAt(linhaSelecionada, 0);
                    Atividade atividade = AtividadeLocalizador.buscaAtividadeId(id);
                    
                    TarefaPDF.gerarFormularioConferencia(atividade);
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
    
    private class FormsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                if(filtroTrabalho.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(painel, "Selecione o trabalho!");
                }
                else
                {
                    TarefaPDF.gerarFormulariosConferencia((Trabalho) filtroTrabalho.getSelectedItem());
                }
            } catch (DocumentException | IOException | MyException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class CarregarListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (trabalhoUpdate.getSelectedIndex() != 0)
            {
                try {
                    Trabalho t = (Trabalho) trabalhoUpdate.getSelectedItem();

                    ArrayList<String> condicoes = new ArrayList<String>();
                    condicoes.add(" id_trabalho = "+ t.getId()+" ");

                    String ordem = " data_inicial ASC ";
                
                    modeloUpdate.carregarLista(AtividadeLocalizador.buscaAtividades(condicoes, ordem));
                } catch (MyException | ParseException ex) {
                    Logger.getLogger(AtividadeTela.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else
            {
                JOptionPane.showMessageDialog(painel, "É necesário selecionar um trabalho!");
            }
        }
    }
    
    private class DistributeListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(trabalhoInsert.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(null, "Selecione um dos trabalhos");
                trabalhoInsert.grabFocus();
            }
            else if(distribuicao.getText().trim().equals("") || Integer.parseInt(distribuicao.getText()) < 1)
            {
                JOptionPane.showMessageDialog(null, "Informe a quantidade de máquinas por dia");
                distribuicao.grabFocus();
            }
            else
            {
                try {
                    if( modeloInsert.validarSelecao( ( (Trabalho) trabalhoInsert.getSelectedItem() ).getId() ) )
                    {
                        if (modeloInsert.distribuirDatas(
                                Integer.parseInt(distribuicao.getText()),
                                (Trabalho) trabalhoInsert.getSelectedItem()
                        ))
                        {
                            habilitarFormularioInsertData();                            
                        }
                    
                    }
                } catch (ParseException | MyException ex) {
                    ex.printStackTrace();
                }
            }           
            
        }
    }
    
    /**
     * Ações executadas quando o botão "Salvar" do formulário é clicado
     */
    private class SaveUnicaListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {    
            try{ 
                if(trabalho.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Selecione um dos trabalhos");
                    trabalho.grabFocus();
                }
                else if(maquina.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Selecione uma das máquinas");
                    maquina.grabFocus();
                }
                else if(AtividadeLocalizador.buscaAtividadeTrabalhoTotal(((Trabalho) trabalho.getSelectedItem()).getId(), ((Maquina) maquina.getSelectedItem()).getId()) > 0)
                {
                    JOptionPane.showMessageDialog(null, "Já há atividade criada para a máquina neste trabalho");
                    maquina.grabFocus();
                }
                else if(tipo.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Selecione um dos tipos");
                    tipo.grabFocus();
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
                else if(
                        (dataInicial.getDate() != null) &&
                        (((Trabalho) trabalho.getSelectedItem() ).getDataInicial() != null) &&
                        (dataInicial.getDate().before( ( (Trabalho) trabalho.getSelectedItem() ).getDataInicial() ) )
                        ){
                    JOptionPane.showMessageDialog(null, "A data inicial deve ser posterior ao início do trabalho!");
                    dataInicial.grabFocus();
                }
                else if(
                        (dataFinal.getDate() != null) &&
                        (((Trabalho) trabalho.getSelectedItem() ).getDataFinal()!= null) &&
                        (dataFinal.getDate().after( ( (Trabalho) trabalho.getSelectedItem() ).getDataFinal() ) )
                        ){
                    JOptionPane.showMessageDialog(null, "A data final deve ser anterior ao final do trabalho!");
                    dataFinal.grabFocus();
                }
                else if(concluida.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Escolha se a atividade foi ou não concluída");
                    concluida.grabFocus();
                }
                else if( (dataFinal.getDate() != null) && (operador.getSelectedIndex() == 0) )
                {
                    JOptionPane.showMessageDialog(null, "Se há data final, você deve selecionar um dos operadores");
                    operador.grabFocus();
                }
                else if( (concluida.getSelectedIndex() == 1) && (dataFinal.getDate() == null) )
                {
                    JOptionPane.showMessageDialog(null, "Se a tarefa foi concluída, você deve informar a data final");
                    dataFinal.grabFocus();
                }
                else if(TarefaLocalizador.buscaTotalTarefasAtividade(false, Integer.parseInt( codigo.getText() ) ) > 0)
                {
                    JOptionPane.showMessageDialog(null, "Só é possível concluir uma atividade cujas tarefas estão concluídas!");
                    concluida.grabFocus();
                }
                else
                {
                    try {
                        Atividade a = new Atividade();
                        
                        if(codigo.getText().equals(""))
                            a.setId(0);
                        else
                            a.setId(Integer.parseInt(codigo.getText()));
                        
                        a.setTrabalho(( (Trabalho) trabalho.getSelectedItem() ).getId());
                        a.setMaquina(( (Maquina) maquina.getSelectedItem() ).getId());
                        a.setTipo(( (TipoAtividade) tipo.getSelectedItem() ).getId());
                        a.setOperador(( (Operador) operador.getSelectedItem() ).getId());
                        
                        a.setDataInicial(dataInicial.getDate());
                        a.setDataFinal(dataFinal.getDate());
                        
                        if(concluida.getSelectedIndex() == 1)
                            a.setConcluida(true);
                        else if(concluida.getSelectedIndex() == 2)
                            a.setConcluida(false);
                        
                        a.setObservacoes(observacoes.getText());
                        
                        if(codigo.getText().equals(""))
                        {
                            if( AtividadePersistor.insereAtividade(a) )
                                exibeListaTabela();
                        }
                        else
                        {
                            if( AtividadePersistor.alteraAtividade(a) )
                                exibeListaTabela();
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
    
    private class SaveInsertListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Trabalho trabalhoSelecionado = (Trabalho) trabalhoInsert.getSelectedItem();
            
            try {                
                Date dataInicial = trabalhoSelecionado.getDataInicial();
                Date dataFinal = trabalhoSelecionado.getDataFinal();
            
                if(modeloInsert.validarDatas(dataInicial, dataFinal))
                {
                    if( AtividadePersistor.insereAtividades(modeloInsert.criarAtividades( trabalhoSelecionado.getId() ) ) )
                        exibeListaTabela();                 
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
            if(trabalhoUpdate.getSelectedIndex() == 0)
            {
                JOptionPane.showMessageDialog(null, "Selecione um trabalho e clique em carregar!");
                trabalhoUpdate.grabFocus();
            }
            else
            {
                
                Trabalho trabalhoSelecionado = (Trabalho) trabalhoUpdate.getSelectedItem();

                try {                
                    Date dataInicial = trabalhoSelecionado.getDataInicial();
                    Date dataFinal = trabalhoSelecionado.getDataFinal();

                    if(modeloUpdate.validarDatas(dataInicial, dataFinal))
                    {
                        if( AtividadePersistor.alteraAtividades(modeloUpdate.atualizarAtividades() ) )
                            exibeListaTabela();                 
                    }
                } catch (ParseException | SQLException | MyException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Ações executadas quando o botão "Limpar" do formulário é clicado
     */
    private class CleanUnicaListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                limpaFormularioUnica();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class CleanInsertListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                limpaFormularioInsert();
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
                exibeListaTabela();
            } catch (MyException | ParseException ex) {
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
                sqlOrdem = AtividadeLocalizador.getConversaoSQL(nomeAtual);           
                carregaListaTabela();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            //
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            //
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            //
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            //
        }
        
    }
    
    /**
     * Retorna o título da janela/menu
     * @return String
     */    
    public static String getTitulo()
    {
        return "Atividades";
    }

    /**
     * Retorna o nome do arquivo do ícone
     * @return String
     */
    public static String getArquivo()
    {
        return "Atividades";
    }
    
}
