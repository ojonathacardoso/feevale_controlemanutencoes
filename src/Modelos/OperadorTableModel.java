
package Modelos;

import Configuracoes.Operador;
import Configuracoes.OperadorLocalizador;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class OperadorTableModel extends AbstractTableModel
{
    private ArrayList<Operador> operadores;
    private String[] colunas;
    private int colunasEditaveis[] = {};
    
    public OperadorTableModel()
    {
        this.operadores = new ArrayList<Operador>();
        setColumnName();
    }
    
    public OperadorTableModel(ArrayList<Operador> operadores)
    {
        this.operadores = new ArrayList<Operador>(operadores);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[4];
        colunas[0] = OperadorLocalizador.getNomeTela(0);
        colunas[1] = OperadorLocalizador.getNomeTela(1);
        colunas[2] = OperadorLocalizador.getNomeTela(2);
        colunas[3] = OperadorLocalizador.getNomeTela(4);
    }

    @Override
    public int getRowCount()
    {
        return operadores.size();
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
        Operador operador = operadores.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return operador.getId();
                case 1:
                    return operador.getNome();
                case 2:
                    return operador.getUsuario();
                case 3:
                    return operador.getTipoTexto();
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(Operador operador, int rowIndex, int columnIndex)
    {
        Operador operadorAtual = operadores.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0:
                operadorAtual.setId(operador.getId());
                break;
            case 1:
                operadorAtual.setNome(operador.getNome());
                break;    
            case 2:
                operadorAtual.setUsuario(operador.getUsuario());
                break; 
            case 3:
                operadorAtual.setTipo(operador.getTipo());
                break; 
            default:
                throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public Operador getOperador(int rowIndex)
    {
        return operadores.get(rowIndex);
    }

    public void addOperador(Operador operador)
    {
        operadores.add(operador);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeOperador(int indiceLinha)
    {
        operadores.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addOperadorLista(ArrayList<Operador> operadores)
    {
        int indice = getRowCount();

        this.operadores.addAll(operadores);

        fireTableRowsInserted(indice, indice + operadores.size());
    }

    public void clear()
    {
        operadores.clear();

        fireTableDataChanged();
    }
    
}