
package Modelos;

import Manutencoes.ModeloTarefa;
import Manutencoes.ModeloTarefaLocalizador;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class ModeloTarefaTableModel extends AbstractTableModel
{
    private ArrayList<ModeloTarefa> modelos;
    private String[] colunas;
    private int colunasEditaveis[] = {};
    
    public ModeloTarefaTableModel()
    {
        this.modelos = new ArrayList<ModeloTarefa>();
        setColumnName();
    }
    
    public ModeloTarefaTableModel(ArrayList<ModeloTarefa> modelos)
    {
        this.modelos = new ArrayList<ModeloTarefa>(modelos);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[3];
        colunas[0] = ModeloTarefaLocalizador.getNomeTela(0);
        colunas[1] = ModeloTarefaLocalizador.getNomeTela(1);
        colunas[2] = ModeloTarefaLocalizador.getNomeTela(2);
    }

    @Override
    public int getRowCount()
    {
        return modelos.size();
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
        ModeloTarefa modelo = modelos.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return modelo.getId();
                case 1:
                    return modelo.getDescricao();
                case 2:
                    return modelo.isComplexaTexto();
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(ModeloTarefa modelo, int rowIndex, int columnIndex)
    {
        ModeloTarefa modeloAtual = modelos.get(rowIndex);
        
        switch(columnIndex)
        {
             case 0:
                 modeloAtual.setId(modelo.getId());
                 break;
             case 1:
                 modeloAtual.setDescricao(modelo.getDescricao());
                 break;  
             case 2:
                 modeloAtual.setComplexa(modelo.isComplexa());
                 break;
             default:
                 throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public ModeloTarefa getModeloTarefa(int rowIndex)
    {
        return modelos.get(rowIndex);
    }

    public void addModeloTarefa(ModeloTarefa modelo)
    {
        modelos.add(modelo);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeModeloTarefa(int indiceLinha)
    {
        modelos.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addModeloTarefaLista(ArrayList<ModeloTarefa> modelos)
    {
        int indice = getRowCount();

        this.modelos.addAll(modelos);

        fireTableRowsInserted(indice, indice + modelos.size());
    }

    public void clear()
    {
        modelos.clear();

        fireTableDataChanged();
    }
    
}