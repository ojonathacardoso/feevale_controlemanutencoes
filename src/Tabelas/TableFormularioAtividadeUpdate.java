
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
public class TableFormularioAtividadeUpdate extends JTable
{    
    ArrayList<TableCellEditor> editors = new ArrayList<TableCellEditor>();
    
    /**
     * Método construtor, que cria a tabela
     * @param modelo AbstractTableModel - Dados usados para popular a tabela
     */
    public TableFormularioAtividadeUpdate(TableModel modelo, ArrayList<Object> componentes)
    {
        super(modelo);
        
        for(Object o : componentes)
        {
            DefaultCellEditor dce = null;
            
            if(o.getClass().toString().contains("JComboBox"))
            {
                dce = new DefaultCellEditor( (JComboBox) o );
            }
            else if(o.getClass().toString().contains("JTextField"))
            {
                dce = new DefaultCellEditor( (JTextField) o );
            }
            
            editors.add(dce);
        }
        
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        getTableHeader().setPreferredSize(new Dimension(getTableHeader().getWidth(),25));

        getColumnModel().getColumn(0).setMaxWidth(60);
        getColumnModel().getColumn(8).setMaxWidth(80);
        setRowHeight(20); 
    }
    
    public TableCellEditor getCellEditor(int row, int column)
    {
        int modelColumn = convertColumnIndexToModel( column );
        
        switch(modelColumn)
        {
            case 4:
                return editors.get(0);
            case 5:
                return editors.get(1);
            case 6:
                return editors.get(2);
            case 7:
                return editors.get(3);
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
