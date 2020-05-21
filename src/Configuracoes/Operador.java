package Configuracoes;

/**
 * Classe que controla os atributos de Operador 
 * @author Jonatha
 */
public class Operador
{
    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private int tipo;

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
     * Retorna o usuário
     * @return String
     */
    public String getUsuario()
    {
        return usuario;
    }

    /**
     * Define o usuário
     * @param usuario String - Nome do usuário
     */
    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    /**
     * Retorna a senha (já criptografada)
     * @return String
     */
    public String getSenha()
    {
        return senha;
    }

    /**
     * Define a senha
     * @param senha String - Senha criptografada
     */
    public void setSenha(String senha)
    {
        this.senha = senha;
    }
    
    /**
     * Retorna o tipo
     * @return int
     */
    public int getTipo()
    {
        return tipo;
    }
    
    /**
     * Define o tipo
     * @param tipo int - Tipo
     */
    public void setTipo(int tipo)
    {
        this.tipo = tipo;
    }
    
    /**
     * Retorna o tipo em formato de texto
     * @return String
     */
    public String getTipoTexto()
    {
        switch(tipo){
            case 1:
                return getTipoLista(true)[1];
            case 2:
                return getTipoLista(true)[2];
            default:
                return "";
        }
    }

    /**
     * Retorna os tipos para serem usados em uma combobox
     * @param todos boolean - Exibe ou não a palavra "Todos" na primeira opção
     * @return String[]
     */
    public static String[] getTipoLista(boolean todos)
    {
        String tipos[] = new String[3];
        
        if(todos) 
            tipos[0] = "Todos";
        else
            tipos[0] = ""; 
        
        tipos[1] = "Técnico";
        tipos[2] = "Estagiário";
        
        return tipos;
    }
    
    public String toString()
    {
        return this.nome;
    }

}
