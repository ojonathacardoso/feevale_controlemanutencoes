package Modelos;

import Cadastros.ModeloMaquina;
import Cadastros.ModeloMaquinaLocalizador;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class ModeloMaquinaTableModel extends AbstractTableModel
{
    private ArrayList<ModeloMaquina> modelos;
    private String[] colunas;
    private int colunasEditaveis[] = {0,5,6};
    
    public ModeloMaquinaTableModel()
    {
        this.modelos = new ArrayList<ModeloMaquina>();
        setColumnName();
    }
    
    public ModeloMaquinaTableModel(ArrayList<ModeloMaquina> modelos)
    {
        this.modelos = new ArrayList<ModeloMaquina>(modelos);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[7];
        colunas[0] = ModeloMaquinaLocalizador.getNomeTela(0);
        colunas[1] = ModeloMaquinaLocalizador.getNomeTela(1);
        colunas[2] = ModeloMaquinaLocalizador.getNomeTela(2);
        colunas[3] = ModeloMaquinaLocalizador.getNomeTela(3);
        colunas[4] = ModeloMaquinaLocalizador.getNomeTela(4);
        colunas[5] = ModeloMaquinaLocalizador.getNomeTela(5);
        colunas[6] = ModeloMaquinaLocalizador.getNomeTela(6);
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
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
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
        ModeloMaquina modelo = modelos.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return modelo.getId();
                case 1:
                    return modelo.getMarca();
                case 2:
                    return modelo.getModelo();
                case 3:
                    return modelo.getTipoTexto();
                case 4:
                    return modelo.getHdPadrao();
                case 5:
                    return modelo.getRamPadrao();
                case 6:    
                    return modelo.getProcPadrao();
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(ModeloMaquina modelo, int rowIndex, int columnIndex)
    {
        ModeloMaquina modeloAtual = modelos.get(rowIndex);
        
        switch(columnIndex)
        {
             case 0:
                 modeloAtual.setId(modelo.getId());
                 break;
             case 1:
                 modeloAtual.setMarca(modelo.getMarca());
                 break;
             case 2:
                 modeloAtual.setModelo(modelo.getModelo());
                 break;
             case 3:
                 modeloAtual.setTipo(modelo.getTipo()); 
                 break;
             case 4:
                 modeloAtual.setHdPadrao(modelo.getHdPadrao());
                 break;
             case 5:
                 modeloAtual.setRamPadrao(modelo.getRamPadrao());
                 break;
             case 6:    
                 modeloAtual.setProcPadrao(modelo.getProcPadrao());
                 break;
             default:
                 throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public ModeloMaquina getModeloMaquina(int rowIndex)
    {
        return modelos.get(rowIndex);
    }

    public void addModeloMaquina(ModeloMaquina modelo)
    {
        modelos.add(modelo);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeModeloMaquina(int indiceLinha)
    {
        modelos.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addModeloMaquinaLista(ArrayList<ModeloMaquina> modelos)
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