package Modelos;

import Banco.MyException;
import Manutencoes.Tarefa;
import Manutencoes.TarefaLocalizador;
import Principal.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class TarefaUpdateTableModel extends DefaultTableModel
{
    private int atividade;
    private ArrayList<Tarefa> tarefas;
    private String[] colunas;
    private ArrayList<Integer> colunasEditaveis = new ArrayList<Integer>();
    
    public TarefaUpdateTableModel()
    {
        super();
        setColumnName();
        setColumnIdentifiers(colunas);
        setRowCount(0);
        
        colunasEditaveis.add(2);
        colunasEditaveis.add(3);
        colunasEditaveis.add(4);
        colunasEditaveis.add(5);
    }
    
    private void setColumnName()
    {
        colunas = new String[6];
        colunas[0] = TarefaLocalizador.getNomeTela(0);
        colunas[1] = "Tarefa";
        colunas[2] = TarefaLocalizador.getNomeTela(1);
        colunas[3] = TarefaLocalizador.getNomeTela(2);
        colunas[4] = TarefaLocalizador.getNomeTela(3);
        colunas[5] = TarefaLocalizador.getNomeTela(4);
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
                return JTextField.class;
            case 3:
                return JTextField.class;            
            case 4:
                return JTextField.class;
            case 5:
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
    
    public void carregarLista(ArrayList<Tarefa> tarefas) throws MyException, ParseException
    {
        this.tarefas = tarefas;
        
        this.limparLista();
        
        for (Tarefa t : tarefas)
        {       
            addRow(new Object[]{
                t.getTarefa(),
                t.getTarefaTexto(),
                Data.convertDataString(t.getDataPlanejada()),
                Data.convertDataString(t.getDataRealizada()),                               
                t.getObservacoes(),
                t.isConcluida() 
            });
        }
    }
    
//    public boolean validarDatas(Date atividadeInicial, Date atividadeFinal) throws ParseException, MyException
//    {
//        Date dataInicial;
//        Date dataFinal;
//        
//        if(getRowCount() == 0)
//        {
//            JOptionPane.showMessageDialog(null, "Não há atividades para o trabalho selecionado. Tente carregar novamente.");
//            return false;
//        }
//        else
//        {
//            for(int x=0; x< getRowCount(); x++)
//            {
//                try {
//                    if(! getValueAt(x, 3).toString().equals(""))
//                    {
//                        if(! Data.validarDataString(getValueAt(x, 3).toString()))
//                        {                    
//                            JOptionPane.showMessageDialog(null, "O computador "+getValueAt(x, 1).toString()+" está com data inicial inválida!");
//                            return false;
//                        }
//                        else 
//                        {
//                            dataInicial = Data.convertStringData(getValueAt(x, 3).toString());
//
//                            if( dataInicial.before(trabalhoInicial) || dataInicial.after(trabalhoFinal) )
//                            {
//                                JOptionPane.showMessageDialog(null, "A data inicial do "+getValueAt(x, 1).toString()+" deve estar dentro do intervalo de datas do trabalho!");
//                                return false;
//                            }
//                            else
//                            {
//                                if(! getValueAt(x, 4).toString().equals(""))
//                                {
//                                    if (! Data.validarDataString(getValueAt(x, 4).toString()) )
//                                    {
//                                        JOptionPane.showMessageDialog(null, "O computador "+getValueAt(x, 1).toString()+" está com data final inválida!");
//                                        return false;
//                                    }
//                                    else
//                                    {
//                                        dataFinal = Data.convertStringData(getValueAt(x, 4).toString());
//
//                                        if( dataFinal.before(trabalhoInicial) || dataFinal.after(trabalhoFinal) )
//                                        {
//                                            JOptionPane.showMessageDialog(null, "A data final do "+getValueAt(x, 1).toString()+" deve estar dentro do intervalo de datas do trabalho!");
//                                            return false;
//                                        }
//                                        else if(dataInicial == null && dataFinal != null)
//                                        {
//                                            JOptionPane.showMessageDialog(null, "Ao informar a data final do "+getValueAt(x, 1).toString()+", você deve informar a data inicial!");
//                                            return false;
//                                        }
//                                        else if( (dataInicial != null) && (dataFinal != null) && (dataFinal.before(dataInicial)) )
//                                        {
//                                            JOptionPane.showMessageDialog(null, "A data inicial do "+getValueAt(x, 1).toString()+" deve ser anterior à data final!");
//                                            return false;
//                                        }
//                                        else if( (dataFinal != null) && (getValueAt(x, 5) == null) )
//                                        {
//                                            JOptionPane.showMessageDialog(null, "Se há data final, você deve selecionar um dos operadores");
//                                            return false;
//                                        }
//                                        else if( (getValueAt(x, 8) == Boolean.TRUE) && (dataFinal == null) )
//                                        {
//                                            JOptionPane.showMessageDialog(null, "Se a tarefa foi concluída, você deve informar a data final");
//                                            return false;
//                                        }     
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    else
//                    {
//                        JOptionPane.showMessageDialog(null, "O computador "+getValueAt(x, 1).toString()+" está sem data inicial!");
//                        return false;
//                    }
//                } catch(ParseException e) {
//                    new MyException(e.getMessage());
//                }
//                
//            }
//            
//            return true;
//        }
//    }
    
//    public ArrayList<Tarefa> atualizarTarefas() throws ParseException, MyException
//    {
//        ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
//        
//        for(int x=0; x< getRowCount(); x++)
//        {
//            Atividade a = new Atividade();
//
//            a.setId( Integer.parseInt( getValueAt(x, 0).toString() ) );
//            
//            a.setDataInicial( Data.convertStringData( getValueAt(x, 4).toString() ) );
//            
//            if(! getValueAt(x, 5).toString().equals(""))
//                a.setDataFinal( Data.convertStringData( getValueAt(x, 5).toString() ) );
//            else
//                a.setDataFinal(null);
//            
//            if(getValueAt(x, 6) != null)
//                a.setOperador(((Operador) getValueAt(x, 6)).getId());
//            else
//                a.setOperador(0);
//  
//            if(getValueAt(x, 7) != null)
//                a.setObservacoes( getValueAt(x, 7).toString() );
//            else
//                a.setObservacoes("");
//
//            a.setConcluida( (Boolean) getValueAt(x, 8) );
//
//            atividades.add(a);
//        }
//        
//        return atividades;
//    }

}