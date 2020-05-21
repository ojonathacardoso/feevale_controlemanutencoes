package Modelos;

import Banco.MyException;
import Cadastros.Maquina;
import Cadastros.MaquinaLocalizador;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que personaliza a DefaultTableModel, desativando a edição das células
 * @author Jonatha
 */
public class MaquinaTableModel extends AbstractTableModel
{
    private ArrayList<Maquina> maquinas;
    private String[] colunas;
    private int colunasEditaveis[] = {};
    
    public MaquinaTableModel()
    {
        this.maquinas = new ArrayList<Maquina>();
        setColumnName();
    }
    
    public MaquinaTableModel(ArrayList<Maquina> maquinas)
    {
        this.maquinas = new ArrayList<Maquina>(maquinas);
        setColumnName();
    }
    
    private void setColumnName()
    {
        colunas = new String[9];
        colunas[0] = MaquinaLocalizador.getNomeTela(0);
        colunas[1] = MaquinaLocalizador.getNomeTela(1);
        colunas[2] = MaquinaLocalizador.getNomeTela(2);
        colunas[3] = MaquinaLocalizador.getNomeTela(3);
        colunas[4] = MaquinaLocalizador.getNomeTela(4);
        colunas[5] = MaquinaLocalizador.getNomeTela(5);
        colunas[6] = MaquinaLocalizador.getNomeTela(6);
        colunas[7] = MaquinaLocalizador.getNomeTela(7);
        colunas[8] = MaquinaLocalizador.getNomeTela(8);
    }

    @Override
    public int getRowCount()
    {
        return maquinas.size();
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
            case 8:
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
        Maquina maquina = maquinas.get(rowIndex);
        
        try
        {
           switch(columnIndex)
            {
                case 0:
                    return maquina.getId();
                case 1:
                    return maquina.getModeloMaquinaTexto();
                case 2:
                    return maquina.getNome();
                case 3:
                    return maquina.getUsuario();
                case 4:
                    return maquina.getLocalizacaoTexto();
                case 5:
                    return maquina.getSistema();
                case 6:    
                    return maquina.getHd();
                case 7:
                    return maquina.getRam();
                case 8:
                    return maquina.getProc();
                default:
                    throw new IndexOutOfBoundsException("Índice de coluna inexistente");
            } 
        } catch(MyException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setValueAt(Maquina maquina, int rowIndex, int columnIndex)
    {
        Maquina maquinaAtual = maquinas.get(rowIndex);
        
        switch(columnIndex)
        {
             case 0:
                 maquinaAtual.setId(maquina.getId());
                 break;
             case 1:
                 maquinaAtual.setModeloMaquina(maquina.getModeloMaquina());
                 break;
             case 2:
                 maquinaAtual.setNome(maquina.getNome());
                 break;
             case 3:
                 maquinaAtual.setUsuario(maquina.getUsuario()); 
                 break;
             case 4:
                 maquinaAtual.setLocalizacao(maquina.getLocalizacao());
                 break;
             case 5:
                 maquinaAtual.setSistema(maquina.getSistema());
                 break;
             case 6:    
                 maquinaAtual.setHd(maquina.getHd());
                 break;
             case 7:
                 maquinaAtual.setRam(maquina.getRam());
                 break;
             case 8:
                 maquinaAtual.setProc(maquina.getProc());
                 break;
             default:
                 throw new IndexOutOfBoundsException("Índice de coluna inexistente");
         }
    }
    
    public Maquina getMaquina(int rowIndex)
    {
        return maquinas.get(rowIndex);
    }

    public void addMaquina(Maquina maquina)
    {
        maquinas.add(maquina);

        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeMaquina(int indiceLinha)
    {
        maquinas.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addMaquinaLista(ArrayList<Maquina> maquinas)
    {
        int indice = getRowCount();

        this.maquinas.addAll(maquinas);

        fireTableRowsInserted(indice, indice + maquinas.size());
    }

    public void clear()
    {
        maquinas.clear();

        fireTableDataChanged();
    }
    
}