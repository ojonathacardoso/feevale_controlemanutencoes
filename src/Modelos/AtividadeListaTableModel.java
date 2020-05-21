
package Modelos;

import Banco.MyException;
import Manutencoes.Atividade;
import Manutencoes.AtividadeLocalizador;
import Principal.Data;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class AtividadeListaTableModel extends AbstractTableModel
{
    private ArrayList<Atividade> atividades;
    private String[] colunas;
    private int colunasEditaveis[] = {};
    
    public AtividadeListaTableModel()
    {
        this.atividades = new ArrayList<Atividade>();
        setColumnName();
    }
    
    public AtividadeListaTableModel(ArrayList<Atividade> atividades)
    {
        this.atividades = new ArrayList<Atividade>(atividades);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[8];
        colunas[0] = AtividadeLocalizador.getNomeTela(0);
        colunas[1] = AtividadeLocalizador.getNomeTela(1);
        colunas[2] = AtividadeLocalizador.getNomeTela(2);
        colunas[3] = AtividadeLocalizador.getNomeTela(3);
        colunas[4] = AtividadeLocalizador.getNomeTela(4);
        colunas[5] = AtividadeLocalizador.getNomeTela(5);
        colunas[6] = AtividadeLocalizador.getNomeTela(6);
        colunas[7] = AtividadeLocalizador.getNomeTela(8);
    }

    @Override
    public int getRowCount()
    {
        return atividades.size();
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
            case 7:
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
        Atividade atividade = atividades.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return atividade.getId();
                case 1:
                    return atividade.getTrabalhoTexto();
                case 2:
                    return atividade.getMaquinaTexto();
                case 3:
                    return atividade.getTipoTexto();
                case 4:
                    return atividade.getOperadorTexto();
                case 5:
                    return Data.convertDataString(atividade.getDataInicial());
                case 6:    
                    return Data.convertDataString(atividade.getDataFinal());
                case 7:
                    return atividade.isConcluidaTexto();
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(MyException | ParseException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(Atividade atividade, int rowIndex, int columnIndex)
    {
        Atividade atividadeAtual = atividades.get(rowIndex);
        
        switch(columnIndex)
        {
             case 0:
                 atividadeAtual.setId(atividade.getId());
                 break;
             case 1:
                 atividadeAtual.setTrabalho(atividade.getTrabalho());
                 break;
             case 2:
                 atividadeAtual.setMaquina(atividade.getMaquina());
                 break;
             case 3:
                 atividadeAtual.setTipo(atividade.getTipo()); 
                 break;
             case 4:
                 atividadeAtual.setOperador(atividade.getOperador());
                 break;
             case 5:
                 atividadeAtual.setDataInicial(atividade.getDataInicial());
                 break;
             case 6:    
                 atividadeAtual.setDataFinal(atividade.getDataFinal());
                 break;
             case 7:
                 atividadeAtual.setConcluida(atividade.isConcluida());
                 break;
             default:
                 throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public Atividade getAtividade(int rowIndex)
    {
        return atividades.get(rowIndex);
    }

    public void addAtividade(Atividade atividade)
    {
        atividades.add(atividade);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeAtividade(int indiceLinha)
    {
        atividades.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addAtividadeLista(ArrayList<Atividade> atividades)
    {
        int indice = getRowCount();

        this.atividades.addAll(atividades);

        fireTableRowsInserted(indice, indice + atividades.size());
    }

    public void clear()
    {
        atividades.clear();

        fireTableDataChanged();
    }
    
}