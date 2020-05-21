
package Modelos;

import Manutencoes.TipoAtividade;
import Manutencoes.TipoAtividadeLocalizador;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class TipoAtividadeTableModel extends AbstractTableModel
{
    private ArrayList<TipoAtividade> tipos;
    private String[] colunas;
    private int colunasEditaveis[] = {};
    
    public TipoAtividadeTableModel()
    {
        this.tipos = new ArrayList<TipoAtividade>();
        setColumnName();
    }
    
    public TipoAtividadeTableModel(ArrayList<TipoAtividade> tipos)
    {
        this.tipos = new ArrayList<TipoAtividade>(tipos);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[2];
        colunas[0] = TipoAtividadeLocalizador.getNomeTela(0);
        colunas[1] = TipoAtividadeLocalizador.getNomeTela(1);
    }

    @Override
    public int getRowCount()
    {
        return tipos.size();
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
        TipoAtividade tipo = tipos.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return tipo.getId();
                case 1:
                    return tipo.getNome();
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(TipoAtividade tipo, int rowIndex, int columnIndex)
    {
        TipoAtividade tipoAtual = tipos.get(rowIndex);
        
        switch(columnIndex)
        {
             case 0:
                 tipoAtual.setId(tipo.getId());
                 break;
             case 1:
                 tipoAtual.setNome(tipo.getNome());
                 break;             
             default:
                 throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public TipoAtividade getTipoAtividade(int rowIndex)
    {
        return tipos.get(rowIndex);
    }

    public void addTipoAtividade(TipoAtividade tipo)
    {
        tipos.add(tipo);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeTipoAtividade(int indiceLinha)
    {
        tipos.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addTipoAtividadeLista(ArrayList<TipoAtividade> tipos)
    {
        int indice = getRowCount();

        this.tipos.addAll(tipos);

        fireTableRowsInserted(indice, indice + tipos.size());
    }

    public void clear()
    {
        tipos.clear();

        fireTableDataChanged();
    }
    
}