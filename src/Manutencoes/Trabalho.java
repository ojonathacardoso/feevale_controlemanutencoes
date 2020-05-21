package Manutencoes;

import java.util.Date;

/**
 * Classe que controla os atributos de Trabalho 
 * @author Jonatha
 */
public class Trabalho
{
    private int id;
    private String descricao;
    private boolean programado;
    private boolean concluido;
    private Date dataInicial;
    private Date dataFinal;

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
     * Retorna se foi ou não programado
     * @return boolean
     */
    public boolean isProgramado()
    {
        return programado;
    }
    
    /**
     * Retorna se foi ou não programado, em texto
     * @return String
     */
    public String isProgramadoTexto()
    {
        if(programado)
            return "Sim";
        else
            return "Não";
    }
    
    /**
     * Retorna os tipos para serem usados em uma combobox
     * @param todos boolean - Exibe ou não a palavra "Todos" na primeira opção
     * @return String[]
     */
    public static String[] isProgramadoLista(boolean todos)
    {
        String opcoes[] = new String[3];
        
        if(todos) 
            opcoes[0] = "Todos";
        else
            opcoes[0] = ""; 
        
        opcoes[1] = "Sim";
        opcoes[2] = "Não";
        
        return opcoes;
    }

    /**
     * Define se foi programado ou não
     * @param programado boolean - Programado ou não
     */
    public void setProgramado(boolean programado)
    {
        this.programado = programado;
    }

    /**
     * Retorna se foi ou não concluído
     * @return boolean
     */
    public boolean isConcluido()
    {
        return concluido;
    }
    
    /**
     * Retorna se foi ou não concluído, em texto
     * @return String
     */
    public String isConcluidoTexto()
    {
        if(concluido)
            return "Sim";
        else
            return "Não";
    }
    
    /**
     * Retorna os tipos para serem usados em uma combobox
     * @param todos boolean - Exibe ou não a palavra "Todos" na primeira opção
     * @return String[]
     */
    public static String[] isConcluidoLista(boolean todos)
    {
        String opcoes[] = new String[3];
        
        if(todos) 
            opcoes[0] = "Todos";
        else
            opcoes[0] = ""; 
        
        opcoes[1] = "Sim";
        opcoes[2] = "Não";
        
        return opcoes;
    }

    /**
     * Define se foi concluído ou não
     * @param concluido boolean - Concluído ou não
     */
    public void setConcluido(boolean concluido)
    {
        this.concluido = concluido;
    }

    /**
     * Retorna a data inicial
     * @return Date
     */
    public Date getDataInicial()
    {
        return dataInicial;
    }
    
    /**
     * Define a data inicial
     * @param dataInicial Date - Data inicial
     */
    public void setDataInicial(Date dataInicial)
    {
        this.dataInicial = dataInicial;
    }

    /**
     * Retorna a data final
     * @return Date
     */
    public Date getDataFinal()
    {
        return dataFinal;
    }

    /**
     * Define a data final
     * @param dataFinal Date - Data final
     */
    public void setDataFinal(Date dataFinal)
    {
        this.dataFinal = dataFinal;
    }
    
    public String toString()
    {
        if(this.id == 0)
            return "";
        else
            return this.id+"-"+this.descricao;
        
    }

}
