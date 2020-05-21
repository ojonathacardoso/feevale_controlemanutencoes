
package Tabelas;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 * Classe que cria uma JTable personalizada a ser usada no sistema
 * @author Jonatha
 */
public class TableLista extends JTable
{    
    /**
     * Método construtor, que cria a tabela
     * @param modelo AbstractTableModel - Dados usados para popular a tabela
     */
    public TableLista(TableModel modelo)
    {
        super(modelo);
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        getTableHeader().setPreferredSize(new Dimension(getTableHeader().getWidth(),25));

        getColumnModel().getColumn(0).setMaxWidth(75);        
        setRowHeight(20); 
        
        getColumnModel().getColumn(0).setCellRenderer(alinhaColuna(JLabel.CENTER));
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
