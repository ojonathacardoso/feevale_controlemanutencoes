package Banco;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe que permite usar arquivo XML com parâmetros
 * @author Jonatha
 */
public class Parametros
{
    private static Parametros myself;
    private Properties parametros;
	
    /**
     * Construtor que cria o parâmetro e encaminha para ser carregado
     * @throws IOException 
     */
    private Parametros() throws MyException
    {
        parametros = new Properties();
        carregaParametros();
    }
	
    /**
     * Retorna a instância de Parametros
     * @return Parametros 
     * @throws MyException 
     */
    public static Parametros getInstance() throws MyException
    {
        if( myself == null )
        {
            myself = new Parametros();
        }
        
        return myself;
    }

    /**
     * Retorna o parâmetro solicitado
     * @param chave String - Chave do parâmetro
     * @return 
     */
    public String getParametro( String chave )
    {
        return parametros.getProperty( chave );
    }

    /**
     * Carrega os parâmetros contidos no arquivo
     * @throws IOException 
     */
    private void carregaParametros() throws MyException
    {
        File arquivo = new File("src/Banco/Parametros.xml");
        
        if( arquivo.exists() )
        {            
            try {
                FileInputStream fis = new FileInputStream( arquivo );

                parametros.loadFromXML( fis );
                
                fis.close();
            } catch (IOException ex) {
                throw new MyException(ex);
            }
        }
    }
	
}