package Cadastros;

/**
 * Classe que controla os atributos de Localizacao 
 * @author Jonatha
 */
public class Localizacao
{
    private int id;
    private String nome;

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
     * Retorna o nome
     * @return String
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * Define o nome
     * @param nome String - Nome
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    /**
     * Exibe a localização em texto dentro de uma combobox
     * @return String
     */
    @Override
    public String toString()
    {
        return this.nome;
    }
}
