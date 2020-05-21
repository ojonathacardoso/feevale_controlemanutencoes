package Configuracoes;

import Principal.TelaInterna;

/**
 *
 * @author Jonatha
 */
public class SobreTela extends TelaInterna
{
    public SobreTela()
    {
        super(getTitulo(), getArquivo());
    }
    
    public static String getTitulo()
    {
        return "Sobre o sistema";
    }
    
    public static String getArquivo()
    {
        return "Sobre";
    }
}