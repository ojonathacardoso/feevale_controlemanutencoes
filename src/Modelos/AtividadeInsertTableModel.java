package Modelos;

import Banco.MyException;
import Cadastros.Maquina;
import Cadastros.MaquinaLocalizador;
import Manutencoes.Atividade;
import Manutencoes.AtividadeLocalizador;
import Manutencoes.TipoAtividade;
import Manutencoes.Trabalho;
import Principal.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class AtividadeInsertTableModel extends DefaultTableModel
{
    private ArrayList<Maquina> maquinas;
    private String[] colunas;
    private ArrayList<Integer> colunasEditaveis = new ArrayList<Integer>();
    
    public AtividadeInsertTableModel()
    {
        super();
        setColumnName();
        setColumnIdentifiers(colunas);
        setRowCount(0);
    }
    
    public void ativarColunasSelecao()
    {
        this.colunasEditaveis.clear();
        
        this.colunasEditaveis.add(0);
        this.colunasEditaveis.add(4);
    }
    
    public void ativarColunasDatas()
    {       
        this.colunasEditaveis.clear();
        
        this.colunasEditaveis.add(5);
    }
    
    private void setColumnName()
    {
        colunas = new String[6];
        colunas[0] = "Seleção";
        colunas[1] = MaquinaLocalizador.getNomeTela(2);
        colunas[2] = MaquinaLocalizador.getNomeTela(3);
        colunas[3] = MaquinaLocalizador.getNomeTela(4);
        colunas[4] = "Tipo";
        colunas[5] = "Data";
    }
    
    public Class<?> getColumnClass(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
                return Boolean.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return JComboBox.class;
            case 5:
                return JTextField.class;
            default: 
                throw new IndexOutOfBoundsException("Número de colunas excedido");
        }   
    }
    
    public boolean isCellEditable(int row, int column)
    {
        for(int x : colunasEditaveis)
        {
            if(column == x)
            {
                return true;
            }  
        }
        
        return false;
    }
    
    public void carregarLista(ArrayList<Maquina> maquinas) throws MyException
    {
        this.maquinas = maquinas;
        
        int rows = getRowCount(); 
        for(int i = rows - 1; i >=0; i--)
        {
            removeRow(i); 
        }
        
        for (Maquina m : maquinas)
        {
            addRow(new Object[]{
                Boolean.FALSE,
                m.getNome(),
                m.getUsuario(), 
                m.getLocalizacaoTexto(),
                "",
                "Aguarde a distribuição..."
            });
        }
    }
    
    public void resetarLista() throws MyException
    {
        for(int x = 0; x < getRowCount(); x++)
        {
            setValueAt(Boolean.FALSE, x, 0);
            setValueAt(maquinas.get(x).getNome(), x, 1);
            setValueAt(maquinas.get(x).getUsuario(), x, 2);
            setValueAt(maquinas.get(x).getLocalizacaoTexto(), x, 3);
            setValueAt("", x, 4);
            setValueAt("Aguarde a distribuição...", x, 5);
        }
    }
    
    public boolean validarSelecao(int trabalho) throws MyException
    {
        int total = 0;
        
        for(int x=0; x< getRowCount(); x++)
        {
            if(getValueAt(x, 0) == Boolean.TRUE)
            {
                total++;
                
                int maquina = MaquinaLocalizador.buscaMaquinaId( getValueAt(x, 1).toString() );
                
                if(AtividadeLocalizador.buscaAtividadeTrabalhoTotal(trabalho, maquina) > 0)
                {
                    JOptionPane.showMessageDialog(null, "Já há atividade criada para a máquina "+getValueAt(x, 1).toString()+" neste trabalho");
                    return false;
                }
                else if(getValueAt(x, 4).toString().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Escolha o tipo de atividade para o computador "+getValueAt(x, 1).toString());
                    return false;
                }
            }
        }
        
        if(total < 1)
        {
            JOptionPane.showMessageDialog(null, "Selecione ao menos um computador para criar a atividade");
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean distribuirDatas(int porDia, Trabalho trabalho) throws ParseException, MyException
    {
        Calendar calInicial = Calendar.getInstance();        
        calInicial.setTime(trabalho.getDataInicial());
        
        Calendar calFinal = Calendar.getInstance();        
        calFinal.setTime(trabalho.getDataFinal());
        
        int disponibilidade = 0;
        
        while(calInicial.before(calFinal))
        {
            if(
                    calInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                    calInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
            )
            {
                int total = AtividadeLocalizador.buscaAtividadeDiasTotal(new Date(calInicial.getTimeInMillis()), trabalho.getId());
                
                int diferenca = porDia - total;
                if(diferenca > 0)
                {
                    disponibilidade += diferenca;
                }
            }
            
            calInicial.add(Calendar.DATE, 1);
        }
        
        int selecao = 0;
        
        for(int x=0; x< getRowCount(); x++)
        {
            if(getValueAt(x, 0) == Boolean.TRUE)
            {
                selecao++;
            }
        }
        
        // Com base nos dias úteis disponíveis e a quantidade de computadores por dia,
        // ele informa se a quantidade que o usuário selecionou é ou não maior que esta disponibilidade. 
        // Se for, ele pede pra diminuir a seleção ou aumentar a quantidade por dia.
        if( selecao > disponibilidade )
        {
            JOptionPane.showMessageDialog(null, "A quantidade de computadores selecionados é maior do que a disponibilidade de dias. "
                    + "Selecione menos computadores e/ou aumente a quantidade por dia.");
            
            return false;
        }
        else
        {
            // Percorre todos os dias do trabalho...
            calInicial.setTime(trabalho.getDataInicial());
            
            int linha = 0;
            int total = AtividadeLocalizador.buscaAtividadeDiasTotal(new Date(calInicial.getTimeInMillis()), trabalho.getId());

            // ...até chegar ao final
            while(calInicial.before(calFinal))
            {
                // Se o dia for sábado ou domingo, ele passa
                // Se for dia útil, ele testa
                if(
                    calInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                    calInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                )
                {
                    // Caso a quantidade de atividades marcadas pra aquele dia seja igual
                    // a quantidade diária determinada, ele avança um dia
                    if(total == porDia)
                    {
                        calInicial.add(Calendar.DATE, 1);
                        total = AtividadeLocalizador.buscaAtividadeDiasTotal(new Date(calInicial.getTimeInMillis()), trabalho.getId());
                    }
                    else
                    {
                        boolean avancar = false;
                    
                        // O laço funciona até que a quantidade de atividades marcadas pra aquele dia
                        // seja igual à quantidade diária determinada
                        while(! avancar)
                        {
                            // Testa se não chegou ao final da tabela
                            if(linha < getRowCount())
                            {
                                // Se o dia foi selecionado, recebe a data.
                                // Enquanto a quantidade de atividades marcadas pra aquele dia for menor que
                                // a quantidade diária determinada, ele distribui o mesmo dia para as atividades seguintes
                                if(getValueAt(linha, 0) == Boolean.TRUE)
                                {
                                    setValueAt(Data.convertDataString( new Date( calInicial.getTimeInMillis() ) ), linha, 5); 
                                    total++;
                                    
                                    if(total == porDia)
                                    {
                                        avancar = true;
                                    }
                                }
                                else
                                {
                                    // Caso o dia não foi selecionado, ele limpa o conteúdo da célula,
                                    // deixando apenas o nome da máquina
                                    setValueAt("", linha, 2);
                                    setValueAt("", linha, 3);
                                    setValueAt("", linha, 4);
                                    setValueAt("", linha, 5);
                                }

                                linha++;
                            }
                            else
                            {
                                // Se chegou ao final da tabela, poupa o calendário e vai direto pro último dia.
                                calInicial = calFinal;                                
                                avancar = true;
                            }                       
                        }
                    }
                }
                else
                {
                    calInicial.add(Calendar.DATE, 1);
                    total = AtividadeLocalizador.buscaAtividadeDiasTotal(new Date(calInicial.getTimeInMillis()), trabalho.getId());
                }
            }
            
            return true;
        }
    }
    
    public boolean validarDatas(Date dataInicial, Date dataFinal) throws ParseException, MyException
    {
        for(int x=0; x< getRowCount(); x++)
        {
            if(getValueAt(x, 0) == Boolean.TRUE)
            {      
                if(! getValueAt(x, 5).toString().equals(""))
                {
                    if(! Data.validarDataString(getValueAt(x, 5).toString()))
                    {                    
                        JOptionPane.showMessageDialog(null, "O computador "+getValueAt(x, 1).toString()+" está com data inválida!");
                        return false;
                    }
                    else 
                    {
                        Date data = Data.convertStringData(getValueAt(x, 5).toString());

                        if( data.before(dataInicial) || data.after(dataFinal) )
                        {
                            JOptionPane.showMessageDialog(null, "A data do "+getValueAt(x, 1).toString()+" deve estar dentro do intervalo de datas do trabalho!");
                            return false;
                        }
                    }
                }                
            }
        }
        
        return true;
    }
    
    public ArrayList<Atividade> criarAtividades(int trabalho) throws ParseException, MyException
    {
        ArrayList<Atividade> atividades = new ArrayList<Atividade>();
        
        for(int x=0; x< getRowCount(); x++)
        {
            if(getValueAt(x, 0) == Boolean.TRUE)
            { 
                Atividade a = new Atividade();
                
                a.setTrabalho(trabalho);
                a.setMaquina( MaquinaLocalizador.buscaMaquinaId( getValueAt(x, 1).toString() ) );
                a.setTipo( ( (TipoAtividade) getValueAt(x, 4) ).getId() );
                a.setDataInicial( Data.convertStringData( getValueAt(x, 5).toString() ) );
                a.setConcluida(false);
                
                atividades.add(a);
            }   
        }
        
        return atividades;
    }

}