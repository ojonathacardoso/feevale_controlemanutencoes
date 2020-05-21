
package Principal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que auxilia nas conversões entre objetos Date e Strings
 * @author Jonatha
 */
public class Data
{
    /**
     * Extrai uma data no formato DD/MM/YYYY de um objeto Date
     * @param data Date - Objeto
     * @return String
     * @throws ParseException 
     */
    public static String convertDataString(Date data) throws ParseException
    {      
        if(data != null)
        {
            DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");        
            String dataString = formato.format(data);
            return dataString;
        }
        else
        {
            return "";
        }
    }
    
    public static Date convertStringData(String dataString) throws ParseException
    {      
        if(dataString != null)
        {
            if(validarDataString(dataString))
            {
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");        
                Date data = formato.parse(dataString);
                return data;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Extrai uma data no formato YYYY-MM-DD (usado no banco de dados) de um objeto Date
     * @param data Date - Objeto
     * @return String
     * @throws ParseException 
     */
    public static String convertDataSQL(Date data) throws ParseException
    {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String dateSQL = formato.format(data);
        return dateSQL;
    }
    
    /**
     * Cria um objeto Date a partir de uma data no formato YYYY-MM-DD, obtida no banco de dados
     * @param dateString String - Data no banco
     * @return Date
     * @throws ParseException 
     */
    public static Date convertSQLData(String dataString) throws ParseException
    {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = formato.parse(dataString);
        return data;        
    }
    
    public static String convertSQLString(String dataString) throws ParseException
    {      
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = formato.parse(dataString);

        formato = new SimpleDateFormat("dd/MM/yyyy");        
        dataString = formato.format(data);
        return dataString;
    }
    
    public static boolean validarDataString(String dataString)
    {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDate = formato.parse(dataString);
            
            return dataString.equals(formato.format(dataDate));
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static String retornarDataHoraAtual()
    {
        DateFormat formato = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        String data = formato.format(new Date());
        return data;
    }
    
    public static String retornarDataAtual()
    {
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String data = formato.format(new Date());
        return data;
    }
}