package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import Principal.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Executa as operações de busca no banco para a classe ModeloTarefa
 * @author Jonatha
 */
public class ModeloTarefaLocalizador 
{
    private static String[][] telaSQL;
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    
    /**
     * Retorna um modelo de tarefa conforme o código pesquisado
     * @param id int - Código do modelo
     * @return ModeloTarefa
     * @throws MyException 
     */
    public static ModeloTarefa buscaModeloTarefa( int id ) throws MyException
    {
        String sql = " SELECT * FROM tarefa_modelo WHERE id_tarefa = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                ModeloTarefa modeloTarefa = new ModeloTarefa();
                
                modeloTarefa.setId( resultado.getInt( "id_tarefa" ) );                 
                modeloTarefa.setDescricao(resultado.getString( "descricao" ) ); 
                modeloTarefa.setComplexa(resultado.getBoolean( "complexa" ) ); 

                return modeloTarefa;
            }
            else
            {
                return null;
            }
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * Retorna todos os modelos de tarefas, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<ModeloTarefa> buscaModelosTarefas( ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM tarefa_modelo ");
        
        for(int x = 0; x < condicoes.size(); x++)
        {
            if (x == 0)
                sql.append(" WHERE ");
            else
                sql.append(" AND ");
            
            sql.append(condicoes.get(x));
        }
        
        if(! ordem.equals(""))
        {
            sql.append(" ORDER BY ");
            sql.append(ordem);
        }
        
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<ModeloTarefa> modelosTarefas = new ArrayList<ModeloTarefa>();
            
            while( resultado.next() )
            {
                ModeloTarefa modeloTarefa = new ModeloTarefa();
                
                modeloTarefa.setId( resultado.getInt( "id_tarefa" ) );                 
                modeloTarefa.setDescricao(resultado.getString( "descricao" ) ); 
                modeloTarefa.setComplexa(resultado.getBoolean( "complexa" ) ); 

                modelosTarefas.add(modeloTarefa);
            }
            
            return modelosTarefas;
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
    }
    
    /**
     * Retorna a lista de tipos de atividades
     * @return String[]
     * @throws MyException 
     */
    public static ArrayList<ModeloTarefa> buscaModeloTarefaLista() throws MyException
    {
        String sql = " SELECT id_tarefa, descricao FROM tarefa_modelo";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<ModeloTarefa> modelos = new ArrayList<ModeloTarefa>();
            
            ModeloTarefa inicial = new ModeloTarefa();
            inicial.setId(0);             
            inicial.setDescricao(""); 
            
            modelos.add(inicial);
            
            while( resultado.next() )
            {
                ModeloTarefa modeloT = new ModeloTarefa();
                
                modeloT.setId( resultado.getInt( "id_modelo" ) );                 
                modeloT.setDescricao(resultado.getString( "descricao" ) ); 

                modelos.add(modeloT);
            }
            
            return modelos;

        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static ArrayList<ModeloTarefa> buscaModelosTarefas() throws MyException
    {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM tarefa_modelo ");
        
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql.toString());            
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<ModeloTarefa> modelos = new ArrayList<ModeloTarefa>();
            
            while( resultado.next() )
            {
                ModeloTarefa m = new ModeloTarefa();
                
                m.setId(resultado.getInt( "id_tarefa" ) ); 
                m.setDescricao(resultado.getString( "descricao" ) ); 

                modelos.add(m);
            }
            
            return modelos;
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
    }
    
    /**
     * 
     * @throws MyException 
     */
    public static void carregaModelo() throws MyException
    {
        modelo.removeAllElements();
        
        ArrayList opcoes = buscaModeloTarefaLista();
        
        for (Object opcao : opcoes)
        {
            modelo.addElement(opcao);
        }
    }
    
    /**
     * 
     * @return DefaultComboBoxModel
     * @throws MyException 
     */
    public static DefaultComboBoxModel getModelo() throws MyException
    {
        carregaModelo();
        
        return modelo;
    }
    
    /**
     * Popula o vetor com as combinações entre colunas no banco e na tela
     */
    private static void criaConversaoTelaSQL()
    {
        telaSQL = new String[3][2];
        
        telaSQL[0][0] = "id_tarefa";
        telaSQL[0][1] = "Código";
        telaSQL[1][0] = "descricao";
        telaSQL[1][1] = "Descrição";
        telaSQL[2][0] = "complexa";
        telaSQL[2][1] = "Complexa";
    }
    
    /**
     * Retorna o nome do campo no banco de dados conforme informado o nome exibido na tela
     * @param nomeTela String - Nome do campo exibido na tela
     * @return String
     */
    public static String getConversaoSQL(String nomeTela)
    {
        if(telaSQL == null)
        {
            criaConversaoTelaSQL();
        }
        
        for(int x = 0; x < telaSQL.length; x++)
        {
            if(nomeTela.equals(telaSQL[x][1]))
                return telaSQL[x][0];
        }
        
        return null;
    }
    
    /**
     * 
     * @param indice
     * @return String
     */
    public static String getNomeTela(int indice)
    {
        if(telaSQL == null)
        {
            criaConversaoTelaSQL();
        }
        
        return telaSQL[indice][1];
    }
    
    /**
     * 
     * @return int
     */
    public static int getTotalColunas()
    {
        if(telaSQL == null)
        {
            criaConversaoTelaSQL();
        }
        
        return telaSQL.length;
    }
    
}