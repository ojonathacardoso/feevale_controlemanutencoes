package Modelos;

import Banco.MyException;
import Manutencoes.Tarefa;
import Manutencoes.TarefaLocalizador;
import Principal.Data;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class TarefaListaTableModel extends AbstractTableModel
{
    private ArrayList<Tarefa> tarefas;
    private String[] colunas;
    private int colunasEditaveis[];
    
    public TarefaListaTableModel()
    {
        this.tarefas = new ArrayList<Tarefa>();
        setColumnName();
    }
    
    public TarefaListaTableModel(ArrayList<Tarefa> tarefas)
    {
        this.tarefas = new ArrayList<Tarefa>(tarefas);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[5];
        colunas[0] = TarefaLocalizador.getNomeTela(0);
        colunas[1] = "Tarefa";
        colunas[2] = TarefaLocalizador.getNomeTela(1);
        colunas[3] = TarefaLocalizador.getNomeTela(2);
        colunas[4] = TarefaLocalizador.getNomeTela(3);
    }

    @Override
    public int getRowCount()
    {
        return tarefas.size();
    }

    @Override
    public int getColumnCount()
    {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return colunas[columnIndex];        
    }

    @Override
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
                return String.class;
            default: 
                throw new IndexOutOfBoundsException("Número de colunas excedido");
        }        
    }
    
    /**
     * Desativa a edição das células
     * @param row int - Linha
     * @param column int - Coluna
     * @return 
     */
    @Override
    public boolean isCellEditable(int row, int column)
    {
        for(int x = 0; x < this.colunasEditaveis.length; x++)
        {
            if(column == this.colunasEditaveis[x])
            {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Tarefa tarefa = tarefas.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return tarefa.getTarefa();
                case 1:
                    return tarefa.getTarefaTexto();   
                case 2:
                    return tarefa.isConcluidaTexto();
                case 3:
                    return Data.convertDataString(tarefa.getDataPlanejada());
                case 4:
                    return Data.convertDataString(tarefa.getDataRealizada());
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(ParseException | IndexOutOfBoundsException | MyException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(Tarefa tarefa, int rowIndex, int columnIndex)
    {
        Tarefa tarefaAtual = tarefas.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0:
                tarefaAtual.setTarefa(tarefa.getTarefa());
                break;
            case 1:
                tarefaAtual.setTarefa(tarefa.getTarefa());
                break;
            case 2:
                tarefaAtual.setConcluida(tarefa.isConcluida());
                break;
            case 3:
                tarefaAtual.setDataPlanejada(tarefa.getDataPlanejada());
                break;
            case 4:
                tarefaAtual.setDataRealizada(tarefa.getDataRealizada());
                break;
            default:
                throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public Tarefa getTarefa(int rowIndex)
    {
        return tarefas.get(rowIndex);
    }

    public void addTarefa(Tarefa tarefa)
    {
        tarefas.add(tarefa);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeTarefa(int indiceLinha)
    {
        tarefas.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addTarefaLista(ArrayList<Tarefa> tarefas)
    {
        int indice = getRowCount();

        this.tarefas.addAll(tarefas);

        fireTableRowsInserted(indice, indice + tarefas.size());
    }

    public void clear()
    {
        tarefas.clear();

        fireTableDataChanged();
    }
    
}