package Cadastros;

/**
 * Classe que controla os atributos de ModeloMaquina 
 * @author Jonatha
 */
public class ModeloMaquina
{
    private int id;
    private String marca;
    private String modelo;
    private int tipo;
    private int hdPadrao;
    private int ramPadrao;
    private String procPadrao;
    
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
     * Retorna a marca
     * @return String
     */
    public String getMarca()
    {
        return marca;
    }

    /**
     * Define a marca
     * @param marca String - Marca
     */
    public void setMarca(String marca)
    {
        this.marca = marca;
    }

    /**
     * Retorna o modelo
     * @return String
     */
    public String getModelo()
    {
        return modelo;
    }

    /**
     * Define o modelo
     * @param modelo String - Modelo
     */
    public void setModelo(String modelo)
    {
        this.modelo = modelo;
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
            case 3:
                return getTipoLista(true)[3];
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
        String tipos[] = new String[4];
        
        if(todos) 
            tipos[0] = "Todos";
        else
            tipos[0] = ""; 
        
        tipos[1] = "Computador";
        tipos[2] = "Notebook";
        tipos[3] = "AiO";
        
        return tipos;
    }

    /**
     * Retorna o tamanho padrão do HD
     * @return int
     */
    public int getHdPadrao()
    {
        return hdPadrao;
    }

    /**
     * Define o tamanho padrão do HD
     * @param hd_padrao int - Tamanho em GB
     */
    public void setHdPadrao(int hd_padrao)
    {
        this.hdPadrao = hd_padrao;
    }

    /**
     * Retorna o tamanho padrão da memória RAM
     * @return int
     */
    public int getRamPadrao()
    {
        return ramPadrao;
    }

    /**
     * Define o tamanho padrão da memória RAM
     * @param ram_padrao int - Tamanho em GB
     */
    public void setRamPadrao(int ram_padrao)
    {
        this.ramPadrao = ram_padrao;
    }

    /**
     * Retorna o processador padrão
     * @return int
     */
    public String getProcPadrao()
    {
        return procPadrao;
    }

    /**
     * Define o processador padrão
     * @param proc_padrao String - Modelo do processador
     */
    public void setProcPadrao(String proc_padrao)
    {
        this.procPadrao = proc_padrao;
    }
    
    /**
     * Exibe o modelo de máquina em texto dentro de uma combobox
     * @return String
     */
    @Override
    public String toString()
    {
        return this.marca+" "+this.modelo;
    }

}