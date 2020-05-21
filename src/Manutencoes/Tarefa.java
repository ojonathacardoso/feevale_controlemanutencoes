package Manutencoes;

import Banco.MyException;
import Cadastros.ModeloMaquinaLocalizador;
import java.util.Date;

/**
 * Classe que controla os atributos de Trabalho 
 * @author Jonatha
 */
public class Tarefa
{
    private int atividade;
    private int tarefa;
    private boolean concluida;
    private Date dataPlanejada;
    private Date dataRealizada;
    private String observacoes;

    /**
     * Retorna a descrição
     * @return String
     */
    public int getAtividade()
    {
        return atividade;
    }

    /**
     * Define a descrição
     * @param descricao String - Descrição
     */
    public void setAtividade(int atividade)
    {
        this.atividade = atividade;
    }
    
    public int getTarefa()
    {
        return tarefa;
    }

    public String getTarefaTexto() throws MyException
    {
        return ModeloTarefaLocalizador.buscaModeloTarefa(tarefa).getDescricao();
    }
    
    /**
     * Define a descrição
     * @param descricao String - Descrição
     */
    public void setTarefa(int tarefa)
    {
        this.tarefa = tarefa;
    }

    /**
     * Retorna se foi ou não programado
     * @return boolean
     */
    public boolean isConcluida()
    {
        return concluida;
    }
    
    /**
     * Retorna se foi ou não programado, em texto
     * @return String
     */
    public String isConcluidaTexto()
    {
        if(concluida)
            return "Sim";
        else
            return "Não";
    }
    
    public void setConcluida(boolean concluida)
    {
        this.concluida = concluida;
    }
    
    /**
     * Retorna os tipos para serem usados em uma combobox
     * @param todos boolean - Exibe ou não a palavra "Todos" na primeira opção
     * @return String[]
     */
    public static String[] isConcluidaLista(boolean todos)
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
     * Retorna a data inicial
     * @return Date
     */
    public Date getDataPlanejada()
    {
        return dataPlanejada;
    }
    
    /**
     * Define a data inicial
     * @param dataInicial Date - Data inicial
     */
    public void setDataPlanejada(Date dataPlanejada)
    {
        this.dataPlanejada = dataPlanejada;
    }

    /**
     * Retorna a data final
     * @return Date
     */
    public Date getDataRealizada()
    {
        return dataRealizada;
    }
    
    /**
     * Define a data final
     * @param dataFinal Date - Data final
     */
    public void setDataRealizada(Date dataRealizada)
    {
        this.dataRealizada = dataRealizada;
    }

    public String getObservacoes()
    {
        return observacoes;
    }

    public void setObservacoes(String observacoes)
    {
        this.observacoes = observacoes;
    }    
    
}
