package Modelos;

import Banco.MyException;
import Manutencoes.Trabalho;
import Manutencoes.TrabalhoLocalizador;
import Principal.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class TrabalhoTableModel extends AbstractTableModel
{
    private ArrayList<Trabalho> trabalhos;
    private String[] colunas;
    private int colunasEditaveis[] = {0,5,6};
    
    public TrabalhoTableModel()
    {
        this.trabalhos = new ArrayList<Trabalho>();
        setColumnName();
    }
    
    public TrabalhoTableModel(ArrayList<Trabalho> trabalhos)
    {
        this.trabalhos = new ArrayList<Trabalho>(trabalhos);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[6];
        colunas[0] = TrabalhoLocalizador.getNomeTela(0);
        colunas[1] = TrabalhoLocalizador.getNomeTela(1);
        colunas[2] = TrabalhoLocalizador.getNomeTela(2);
        colunas[3] = TrabalhoLocalizador.getNomeTela(3);
        colunas[4] = TrabalhoLocalizador.getNomeTela(4);
        colunas[5] = TrabalhoLocalizador.getNomeTela(5);
    }

    @Override
    public int getRowCount()
    {
        return trabalhos.size();
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
        Trabalho trabalho = trabalhos.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return trabalho.getId();
                case 1:
                    return trabalho.getDescricao();
                case 2:
                    return trabalho.isProgramadoTexto();
                case 3:
                    return trabalho.isConcluidoTexto();
                case 4:
                    return Data.convertDataString(trabalho.getDataInicial());
                case 5:
                    return Data.convertDataString(trabalho.getDataFinal());
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(ParseException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(Trabalho trabalho, int rowIndex, int columnIndex)
    {
        Trabalho trabalhoAtual = trabalhos.get(rowIndex);
        
        switch(columnIndex)
        {
             case 0:
                 trabalhoAtual.setId(trabalho.getId());
                 break;
             case 1:
                 trabalhoAtual.setDescricao(trabalho.getDescricao());
                 break;
             case 2:
                 trabalhoAtual.setProgramado(trabalho.isProgramado());
                 break;
             case 3:
                 trabalhoAtual.setConcluido(trabalho.isConcluido()); 
                 break;
             case 4:
                 trabalhoAtual.setDataInicial(trabalho.getDataInicial());
                 break;
             case 5:
                 trabalhoAtual.setDataFinal(trabalho.getDataFinal());
                 break;
             default:
                 throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public Trabalho getTrabalho(int rowIndex)
    {
        return trabalhos.get(rowIndex);
    }

    public void addTrabalho(Trabalho trabalho)
    {
        trabalhos.add(trabalho);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeTrabalho(int indiceLinha)
    {
        trabalhos.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addTrabalhoLista(ArrayList<Trabalho> trabalhos)
    {
        int indice = getRowCount();

        this.trabalhos.addAll(trabalhos);

        fireTableRowsInserted(indice, indice + trabalhos.size());
    }

    public void clear()
    {
        trabalhos.clear();

        fireTableDataChanged();
    }
    
}