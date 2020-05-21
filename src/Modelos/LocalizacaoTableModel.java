
package Modelos;

import Cadastros.Localizacao;
import Cadastros.LocalizacaoLocalizador;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class LocalizacaoTableModel extends AbstractTableModel
{
    private ArrayList<Localizacao> localizacoes;
    private String[] colunas;
    private int colunasEditaveis[] = {};
    
    public LocalizacaoTableModel()
    {
        this.localizacoes = new ArrayList<Localizacao>();
        setColumnName();
    }
    
    public LocalizacaoTableModel(ArrayList<Localizacao> localizacoes)
    {
        this.localizacoes = new ArrayList<Localizacao>(localizacoes);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[2];
        colunas[0] = LocalizacaoLocalizador.getNomeTela(0);
        colunas[1] = LocalizacaoLocalizador.getNomeTela(1);
    }

    @Override
    public int getRowCount()
    {
        return localizacoes.size();
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
        Localizacao localizacao = localizacoes.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return localizacao.getId();
                case 1:
                    return localizacao.getNome();
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(Localizacao localizacao, int rowIndex, int columnIndex)
    {
        Localizacao localizacaoAtual = localizacoes.get(rowIndex);
        
        switch(columnIndex)
        {
             case 0:
                 localizacaoAtual.setId(localizacao.getId());
                 break;
             case 1:
                 localizacaoAtual.setNome(localizacao.getNome());
                 break;             
             default:
                 throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public Localizacao getLocalizacao(int rowIndex)
    {
        return localizacoes.get(rowIndex);
    }

    public void addLocalizacao(Localizacao localizacao)
    {
        localizacoes.add(localizacao);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeLocalizacao(int indiceLinha)
    {
        localizacoes.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addLocalizacaoLista(ArrayList<Localizacao> localizacoes)
    {
        int indice = getRowCount();

        this.localizacoes.addAll(localizacoes);

        fireTableRowsInserted(indice, indice + localizacoes.size());
    }

    public void clear()
    {
        localizacoes.clear();

        fireTableDataChanged();
    }
    
}