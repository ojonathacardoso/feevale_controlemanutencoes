
package Tabelas;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

/**
 * Classe que cria uma JTable personalizada a ser usada no sistema
 * @author Jonatha
 */
public class TableFormularioTarefaUpdate extends JTable
{    
    ArrayList<TableCellEditor> editors = new ArrayList<TableCellEditor>();
    
    /**
     * Método construtor, que cria a tabela
     * @param modelo AbstractTableModel - Dados usados para popular a tabela
     */
    public TableFormularioTarefaUpdate(TableModel modelo, ArrayList<Object> componentes)
    {
        super(modelo);
        
        for(Object o : componentes)
        {
            DefaultCellEditor dce = null;
            
            dce = new DefaultCellEditor( (JTextField) o );
            
            editors.add(dce);
        }
        
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        getTableHeader().setPreferredSize(new Dimension(getTableHeader().getWidth(),25));

        getColumnModel().getColumn(0).setMaxWidth(80);
        getColumnModel().getColumn(5).setMaxWidth(80);
        setRowHeight(20); 
    }
    
    public TableCellEditor getCellEditor(int row, int column)
    {
        int modelColumn = convertColumnIndexToModel( column );
        
        switch(modelColumn)
        {
            case 2:
                return editors.get(0);
            case 3:
                return editors.get(1);
            case 4:
                return editors.get(2);
            default:
                return super.getCellEditor(row, column);
        }  
    }
    
    /**
     * Alinha a coluna de uma tabela
     * @param alinhamento int - Alinhamento
     * @return DefaultTableCellRenderer
     */
    public static DefaultTableCellRenderer alinhaColuna(int alinhamento)
    {
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(alinhamento);
        return centro;
    }
    
}
