package Modelos;

import Banco.MyException;
import Cadastros.Maquina;
import Cadastros.MaquinaLocalizador;
import Configuracoes.Operador;
import Configuracoes.OperadorLocalizador;
import Manutencoes.Atividade;
import Manutencoes.AtividadeLocalizador;
import Principal.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class AtividadeUpdateTableModel extends DefaultTableModel
{
    private ArrayList<Atividade> atividades;
    private String[] colunas;
    private ArrayList<Integer> colunasEditaveis = new ArrayList<Integer>();
    
    public AtividadeUpdateTableModel()
    {
        super();
        setColumnName();
        setColumnIdentifiers(colunas);
        setRowCount(0);
        
        this.colunasEditaveis.add(4);
        this.colunasEditaveis.add(5);
        this.colunasEditaveis.add(6);
        this.colunasEditaveis.add(7);
        this.colunasEditaveis.add(8);
    }
    
    private void setColumnName()
    {
        colunas = new String[9];
        colunas[0] = AtividadeLocalizador.getNomeTela(0);
        colunas[1] = MaquinaLocalizador.getNomeTela(2);
        colunas[2] = MaquinaLocalizador.getNomeTela(3);
        colunas[3] = MaquinaLocalizador.getNomeTela(4);
        colunas[4] = AtividadeLocalizador.getNomeTela(5);
        colunas[5] = AtividadeLocalizador.getNomeTela(6);
        colunas[6] = AtividadeLocalizador.getNomeTela(4);
        colunas[7] = AtividadeLocalizador.getNomeTela(7);
        colunas[8] = AtividadeLocalizador.getNomeTela(8);
    }
    
    public Class<?> getColumnClass(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return JTextField.class;
            case 5:
                return JTextField.class;
            case 6:
                return JComboBox.class;
            case 7:
                return JTextField.class;
            case 8:
                return Boolean.class;
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
    
    public void limparLista()
    {
        int rows = getRowCount(); 
        
        for(int i = rows - 1; i >=0; i--)
        {
            removeRow(i); 
        }
    }
    
    public void carregarLista(ArrayList<Atividade> atividades) throws MyException, ParseException
    {
        this.atividades = atividades;
        
        this.limparLista();
        
        for (Atividade a : atividades)
        {          
            Maquina m = MaquinaLocalizador.buscaMaquina(a.getMaquina());
            
            addRow(new Object[]{
                a.getId(),
                m.getNome(),
                m.getUsuario(),
                m.getLocalizacaoTexto(), 
                Data.convertDataString(a.getDataInicial()),
                Data.convertDataString(a.getDataFinal()),
                OperadorLocalizador.buscaOperador(a.getOperador()),
                a.getObservacoes(),
                a.isConcluida()
            });
        }
    }
    
    public boolean validarDatas(Date trabalhoInicial, Date trabalhoFinal) throws ParseException, MyException
    {
        Date dataInicial;
        Date dataFinal;
        
        if(getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(null, "Não há atividades para o trabalho selecionado. Tente carregar novamente.");
            return false;
        }
        else
        {
            for(int x=0; x< getRowCount(); x++)
            {
                try {
                    if(! getValueAt(x, 4).toString().equals(""))
                    {
                        if(! Data.validarDataString(getValueAt(x, 4).toString()))
                        {                    
                            JOptionPane.showMessageDialog(null, "O computador "+getValueAt(x, 1).toString()+" está com data inicial inválida!");
                            return false;
                        }
                        else 
                        {
                            dataInicial = Data.convertStringData(getValueAt(x, 4).toString());

                            if( dataInicial.before(trabalhoInicial) || dataInicial.after(trabalhoFinal) )
                            {
                                JOptionPane.showMessageDialog(null, "A data inicial do "+getValueAt(x, 1).toString()+" deve estar dentro do intervalo de datas do trabalho!");
                                return false;
                            }
                            else
                            {
                                if(! getValueAt(x, 5).toString().equals(""))
                                {
                                    if (! Data.validarDataString(getValueAt(x, 5).toString()) )
                                    {
                                        JOptionPane.showMessageDialog(null, "O computador "+getValueAt(x, 1).toString()+" está com data final inválida!");
                                        return false;
                                    }
                                    else
                                    {
                                        dataFinal = Data.convertStringData(getValueAt(x, 5).toString());

                                        if( dataFinal.before(trabalhoInicial) || dataFinal.after(trabalhoFinal) )
                                        {
                                            JOptionPane.showMessageDialog(null, "A data final do "+getValueAt(x, 1).toString()+" deve estar dentro do intervalo de datas do trabalho!");
                                            return false;
                                        }
                                        else if(dataInicial == null && dataFinal != null)
                                        {
                                            JOptionPane.showMessageDialog(null, "Ao informar a data final do "+getValueAt(x, 1).toString()+", você deve informar a data inicial!");
                                            return false;
                                        }
                                        else if( (dataInicial != null) && (dataFinal != null) && (dataFinal.before(dataInicial)) )
                                        {
                                            JOptionPane.showMessageDialog(null, "A data inicial do "+getValueAt(x, 1).toString()+" deve ser anterior à data final!");
                                            return false;
                                        }
                                        else if( (dataFinal != null) && (getValueAt(x, 6) == null) )
                                        {
                                            JOptionPane.showMessageDialog(null, "Se há data final, você deve selecionar um dos operadores");
                                            return false;
                                        }
                                        else if( (getValueAt(x, 8) == Boolean.TRUE) && (dataFinal == null) )
                                        {
                                            JOptionPane.showMessageDialog(null, "Se a tarefa foi concluída, você deve informar a data final");
                                            return false;
                                        }     
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "O computador "+getValueAt(x, 1).toString()+" está sem data inicial!");
                        return false;
                    }
                } catch(ParseException e) {
                    new MyException(e.getMessage());
                }
                
            }
            
            return true;
        }
    }
    
    public ArrayList<Atividade> atualizarAtividades() throws ParseException, MyException
    {
        ArrayList<Atividade> atividades = new ArrayList<Atividade>();
        
        for(int x=0; x< getRowCount(); x++)
        {
            Atividade a = new Atividade();

            a.setId( Integer.parseInt( getValueAt(x, 0).toString() ) );
            
            a.setDataInicial( Data.convertStringData( getValueAt(x, 4).toString() ) );
            
            if(! getValueAt(x, 5).toString().equals(""))
                a.setDataFinal( Data.convertStringData( getValueAt(x, 5).toString() ) );
            else
                a.setDataFinal(null);
            
            if(getValueAt(x, 6) != null)
                a.setOperador(((Operador) getValueAt(x, 6)).getId());
            else
                a.setOperador(0);
  
            if(getValueAt(x, 7) != null)
                a.setObservacoes( getValueAt(x, 7).toString() );
            else
                a.setObservacoes("");

            a.setConcluida( (Boolean) getValueAt(x, 8) );

            atividades.add(a);
        }
        
        return atividades;
    }

}