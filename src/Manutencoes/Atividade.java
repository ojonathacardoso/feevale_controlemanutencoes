package Manutencoes;

import Cadastros.*;
import Banco.MyException;
import Configuracoes.OperadorLocalizador;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que controla os atributos de Atividade 
 * @author Jonatha
 */
public class Atividade
{
    private int id;
    private int trabalho;
    private int maquina;
    private int tipo;
    private int operador;
    private Date dataInicial;
    private Date dataFinal;
    private String observacoes;
    private boolean concluida;
    
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
     * Retorna o código do trabalho
     * @return int
     */
    public int getTrabalho()
    {
        return trabalho;
    }
    
    /**
     * Retorna o trabalho em texto
     * @return String
     * @throws Banco.MyException
     */
    public String getTrabalhoTexto() throws MyException
    {
        return TrabalhoLocalizador.buscaTrabalhoDescricao(trabalho);
    }
    
    /**
     * Define o código do trabalho
     * @param trabalho int - Código
     */
    public void setTrabalho(int trabalho)
    {
        this.trabalho = trabalho;
    }

    /**
     * Retorna o código da máquina
     * @return int
     */
    public int getMaquina()
    {
        return maquina;
    }
    
    /**
     * Retorna a máquina em texto
     * @return String
     * @throws Banco.MyException
     */
    public String getMaquinaTexto() throws MyException
    {
        return MaquinaLocalizador.buscaMaquinaNome(maquina);
    }

    /**
     * Define o código da maquina
     * @param maquina int - Código
     */
    public void setMaquina(int maquina)
    {
        this.maquina = maquina;
    }

    /**
     * Retorna o código do tipo de atividade
     * @return int
     */
    public int getTipo()
    {
        return tipo;
    }
    
    /**
     * Retorna o tipo de atividade em texto
     * @return String
     * @throws Banco.MyException
     */
    public String getTipoTexto() throws MyException
    {
        return TipoAtividadeLocalizador.buscaTipoAtividadeNome(tipo);
    }

    /**
     * Define o código do tipo
     * @param tipo int - Código
     */
    public void setTipo(int tipo)
    {
        this.tipo = tipo;
    }

    /**
     * Retorna o código do operador
     * @return int
     */
    public int getOperador()
    {
        return operador;
    }

    /**
     * Retorna o operador em texto
     * @return String
     * @throws Banco.MyException
     */
    public String getOperadorTexto() throws MyException
    {
        return OperadorLocalizador.buscaOperadorNome(operador);
    }
    
    /**
     * Define o código do operador
     * @param operador int - Código
     */
    public void setOperador(int operador)
    {
        this.operador = operador;
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

    /**
     * Retorna as observações anotadas da máquina
     * @return String
     */
    public String getObservacoes()
    {
        return observacoes;
    }

    /**
     * Define as observações anotadas
     * @param observacoes String - Observações
     */
    public void setObservacoes(String observacoes)
    {
        this.observacoes = observacoes;
    }

    /**
     * Retorna se foi ou não concluida
     * @return boolean
     */
    public boolean isConcluida()
    {
        return concluida;
    }
    
    /**
     * Retorna se foi ou não concluída, em texto
     * @return String
     */
    public String isConcluidaTexto()
    {
        if(concluida)
            return "Sim";
        else
            return "Não";
    }
    
    /**
     * Retorna os tipos para serem usados em uma combobox
     * @param todos boolean - Exibe ou não a palavra "Todos" na primeira opção
     * @return String[]
     */
    public static String[] isConcluidaLista(boolean todos)
    {
        String tipos[] = new String[3];
        
        if(todos) 
            tipos[0] = "Todos";
        else
            tipos[0] = ""; 
        
        tipos[1] = "Sim";
        tipos[2] = "Não";
        
        return tipos;
    }

    /**
     * Define se foi concluída ou não
     * @param concluida boolean - Concluído ou não
     */
    public void setConcluida(boolean concluida)
    {
        this.concluida = concluida;
    }

    public String toString()
    {
        try {
            if(this.id == 0)
                return "";
            else
                return this.id+"-"+this.getMaquinaTexto();
        } catch (MyException ex) {
            ex.printStackTrace();
            return "";
        }
        
    }
    
}