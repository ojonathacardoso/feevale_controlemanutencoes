package Configuracoes;

import Principal.TelaInterna;

/**
 *
 * @author Jonatha
 */
public class ExpedienteTela extends TelaInterna
{
    public ExpedienteTela()
    {
        super(getTitulo(), getArquivo());
    }
    
    public static String getTitulo()
    {
        return "Expedientes";
    }
    
    public static String getArquivo()
    {
        return "Expedientes";
    }
}