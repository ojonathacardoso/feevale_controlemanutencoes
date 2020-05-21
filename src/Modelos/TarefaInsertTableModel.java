package Modelos;

import Banco.MyException;
import Configuracoes.Operador;
import Manutencoes.Atividade;
import Manutencoes.ModeloTarefa;
import Manutencoes.Tarefa;
import Manutencoes.TarefaLocalizador;
import Principal.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class TarefaInsertTableModel extends DefaultTableModel
{
    private int atividade;
    private ArrayList<ModeloTarefa> modelos;
    private String[] colunas;
    private ArrayList<Integer> colunasEditaveis = new ArrayList<Integer>();
    
    public TarefaInsertTableModel()
    {
        super();
        setColumnName();
        setColumnIdentifiers(colunas);
        setRowCount(0);
        
        colunasEditaveis.add(0);
        colunasEditaveis.add(3);
        colunasEditaveis.add(4);
    }
    
    private void setColumnName()
    {
        colunas = new String[5];
        colunas[0] = "Seleção";
        colunas[1] = TarefaLocalizador.getNomeTela(0);
        colunas[2] = "Tarefa";
        colunas[3] = TarefaLocalizador.getNomeTela(1);
        colunas[4] = TarefaLocalizador.getNomeTela(3);
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
                return JTextField.class;
            case 4:
                return JTextField.class;
            default: 
                throw new IndexOutOfBoundsException("Número de colunas excedido");
        }   
    }
    
    public boolean isCellEditable(int row, int column)
    {
        try {
            if(TarefaLocalizador.buscaTarefasAtividadeTotal(this.atividade, this.modelos.get(row).getId()) == 0)
            {
                for(int x : colunasEditaveis)
                {
                    if(column == x)
                    {  
                        return true;
                    }
                }
            }
            else
            {
                return false;
            }
        } catch (MyException ex) {
            ex.printStackTrace();
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
    
    public void carregarLista(int atividade, ArrayList<ModeloTarefa> modelos) throws MyException, ParseException
    {
        this.atividade = atividade;        
        this.modelos = modelos;
        
        this.limparLista();
        
        for (ModeloTarefa m : modelos)
        {         
            Boolean b;
            String data;
            
            if(TarefaLocalizador.buscaTarefasAtividadeTotal(atividade, m.getId()) > 0)
            {
                b = Boolean.TRUE;
                data = Data.convertDataString(TarefaLocalizador.buscaTarefa(atividade, m.getId()).getDataPlanejada());
            }
            else
            {
                b = Boolean.FALSE;
                data = "";
            }
            
            addRow(new Object[]{
                b,
                m.getId(),
                m.getDescricao(),
                data,
                ""
            });
        }
    }
    
    public boolean validarDatas(Date atividadeInicial, Date atividadeFinal) throws ParseException, MyException
    {
        Date dataPlanejada;
        int total = 0;
        
        if(getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(null, "Não há tarefas cadastradas. Cadastre antes de inserir.");
            return false;
        }
        else
        {
            for(int x=0; x< getRowCount(); x++)
            {
                if(TarefaLocalizador.buscaTarefasAtividadeTotal(atividade, modelos.get(x).getId()) == 0)
                {
                    if((getValueAt(x, 0) == Boolean.TRUE))
                    {
                        total++;
                        if(! getValueAt(x, 3).toString().equals(""))
                        {
                            if(! Data.validarDataString(getValueAt(x, 3).toString()))
                            {                    
                                JOptionPane.showMessageDialog(null, "A tarefa "+getValueAt(x, 1).toString()+" está com data planejada inválida!");
                                return false;
                            }
                            else 
                            {
                                dataPlanejada = Data.convertStringData(getValueAt(x, 3).toString());

                                if( atividadeInicial != null )
                                {
                                    if( dataPlanejada.before(atividadeInicial) )
                                    {
                                        JOptionPane.showMessageDialog(null, "A data planejada da tarefa "+getValueAt(x, 1).toString()+" é anterior ao início do trabalho!");
                                        return false;
                                    }
                                }
                                else if( atividadeFinal != null )
                                {
                                    if( dataPlanejada.after(atividadeFinal) )
                                    {
                                        JOptionPane.showMessageDialog(null, "A data planejada da tarefa "+getValueAt(x, 1).toString()+" é anterior ao fim do trabalho!");
                                        return false;
                                    }
                                }
                                                                
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "A tarefa "+getValueAt(x, 1).toString()+" está sem data planejada!");
                            return false;
                        }                                
                    }
                }
            }
            
            if(total == 0)
            {
                JOptionPane.showMessageDialog(null, "Não há tarefas a serem incluídas!");
                return false;
            }
            else
            {
                return true;
            }
            
        }
    }
    
    public ArrayList<Tarefa> criarTarefas() throws ParseException, MyException
    {
        ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
        
        for(int x=0; x< getRowCount(); x++)
        {
            if(TarefaLocalizador.buscaTarefasAtividadeTotal(atividade, modelos.get(x).getId()) == 0)
            {
                if((getValueAt(x, 0) == Boolean.TRUE))
                {
                    Tarefa t = new Tarefa();

                    t.setAtividade(atividade);
                    t.setTarefa(Integer.parseInt(getValueAt(x, 1).toString()));
                    t.setDataPlanejada(Data.convertStringData( getValueAt(x, 3).toString() ) );
                    t.setDataRealizada(null);
                    t.setObservacoes("");
                    t.setConcluida(false);
                    
                    tarefas.add(t);
                }
            }
        }
        
        return tarefas;
    }

}