package Manutencoes;

/**
 * Classe que controla os atributos de ModeloTarefa 
 * @author Jonatha
 */
public class ModeloTarefa
{
    private int id;
    private String descricao;
    private boolean complexa;

    /**
     * Retorna o código
     * @return int
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Define o código
     * @param id int - Código
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Retorna a descrição
     * @return String
     */
    public String getDescricao()
    {
        return descricao;
    }

    /**
     * Define a descrição
     * @param descricao String - Descrição
     */
    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    /**
     * Retorna se é ou não complexa
     * @return boolean
     */
    public boolean isComplexa()
    {
        return complexa;
    }
    
    /**
     * Retorna se é ou não complexa, em texto
     * @return String
     */
    public String isComplexaTexto()
    {
        if(complexa)
            return "Sim";
        else
            return "Não";
    }

    /**
     * Define se é ou não complexa
     * @param complexa boolean - Complexa ou não
     */
    public void setComplexa(boolean complexa)
    {
        this.complexa = complexa;
    }
    
    public String toString()
    {
        return this.descricao;
    }

}