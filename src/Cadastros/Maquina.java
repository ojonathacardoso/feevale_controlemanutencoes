package Cadastros;

import Banco.MyException;

/**
 * Classe que controla os atributos de Maquina 
 * @author Jonatha
 */
public class Maquina
{
    private int id;
    private int modeloMaquina;
    private String nome;
    private String usuario;
    private int localizacao;
    private String sistema;
    private int hd;
    private int ram;
    private String proc;
    private String observacao;
    
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
     * Retorna o código do modelo de máquina
     * @return int
     */
    public int getModeloMaquina()
    {
        return modeloMaquina;
    }
    
    /**
     * Retorna o modelo de máquina em texto
     * @return String
     * @throws MyException
     */
    public String getModeloMaquinaTexto() throws MyException
    {
        return ModeloMaquinaLocalizador.buscaModeloMaquinaNome(modeloMaquina);
    }

    /**
     * Define o código do modelo de máquina
     * @param modeloMaquina int - Código
     */
    public void setModeloMaquina(int modeloMaquina)
    {
        this.modeloMaquina = modeloMaquina;
    }

    /**
     * Retorna o nome da máquina
     * @return String
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * Define o nome da máquina
     * @param nome String - Nome da máquina
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * Retorna o nome do usuário
     * @return String
     */
    public String getUsuario()
    {
        return usuario;
    }

    /**
     * Define o nome do usuário
     * @param usuario String - Nome do usuário
     */
    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    /**
     * Retorna o código da localização
     * @return int
     */
    public int getLocalizacao()
    {
        return localizacao;
    }
    
    /**
     * Retorna a localização em texto
     * @return String
     * @throws MyException
     */
    public String getLocalizacaoTexto() throws MyException
    {
        return LocalizacaoLocalizador.buscaLocalizacaoNome(localizacao);
    }
    
    /**
     * Define o código da localização
     * @param localizacao int - Código
     */
    public void setLocalizacao(int localizacao)
    {
        this.localizacao = localizacao;
    }

    /**
     * Retorna o sistema operacional
     * @return String
     */
    public String getSistema()
    {
        return sistema;
    }

    /**
     * Define o sistema operacional
     * @param sistema String - Sistema
     */
    public void setSistema(String sistema)
    {
        this.sistema = sistema;
    }

    /**
     * Retorna o tamanho do HD
     * @return int
     */
    public int getHd()
    {
        return hd;
    }

    /**
     * Define o tamanho do HD
     * @param hd int - Tamanho em GB
     */
    public void setHd(int hd)
    {
        this.hd = hd;
    }

    /**
     * Retorna o tamanho da memória RAM
     * @return int
     */
    public int getRam()
    {
        return ram;
    }

    /**
     * Define o tamanho da memória RAM
     * @param ram int - Tamanho em GB
     */
    public void setRam(int ram)
    {
        this.ram = ram;
    }

    /**
     * Retorna o modelo do processador
     * @return String
     */
    public String getProc()
    {
        return proc;
    }

    /**
     * Define o modelo do processador
     * @param proc String - Modelo
     */
    public void setProc(String proc)
    {
        this.proc = proc;
    }

    /**
     * Retorna as observações anotadas da máquina
     * @return String
     */
    public String getObservacao()
    {
        return observacao;
    }

    /**
     * Define as observações anotadas
     * @param observacao String - Observações
     */
    public void setObservacao(String observacao)
    {
        this.observacao = observacao;
    }
    
    /**
     * Exibe a máquina em texto dentro de uma combobox
     * @return String
     */
    @Override
    public String toString()
    {
        return this.nome;
    }

}