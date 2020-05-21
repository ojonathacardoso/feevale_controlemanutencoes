package Manutencoes;

/**
 * Classe que controla os atributos de TipoAtividade 
 * @author Jonatha
 */
public class TipoAtividade
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
    
    public String toString()
    {
        return this.nome;
    }

}
