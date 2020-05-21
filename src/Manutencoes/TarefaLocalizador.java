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
 * Executa as operações de busca no banco para a classe Trabalho
 * @author Jonatha
 */
public class TarefaLocalizador 
{
    private static String[][] telaSQL;
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    
    /**
     * Retorna todos os trabalhos, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    
    public static Tarefa buscaTarefa(int idAtividade, int idTarefa ) throws MyException
    {
        String sql = " SELECT * FROM tarefa WHERE id_atividade = ? AND id_tarefa = ? ";
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, idAtividade );
            statement.setInt( 2, idTarefa );

            ResultSet resultado = statement.executeQuery();

            if( resultado.next() )
            {
                Tarefa tarefa = new Tarefa();
                
                tarefa.setAtividade(resultado.getInt( "id_atividade" ));
                tarefa.setTarefa(resultado.getInt( "id_tarefa" ) ); 
                tarefa.setConcluida(resultado.getBoolean( "concluida" ) ); 
                
                if ( resultado.getString( "data_planejada" ) != null )
                    tarefa.setDataPlanejada(Data.convertSQLData( resultado.getString( "data_planejada" ) ) );
                else
                    tarefa.setDataPlanejada(null);
                
                if ( resultado.getString( "data_realizada" ) != null )
                    tarefa.setDataRealizada(Data.convertSQLData( resultado.getString( "data_realizada" ) ) );
                else
                    tarefa.setDataRealizada(null);
                
                return tarefa;
            }
            else
            {
                return null;
            }
            
        } catch ( MyException | SQLException | ParseException e ) {
            throw new MyException(e);
        }
    }
    
    
    public static ArrayList<Tarefa> buscaTarefas(int atividade, ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM tarefa WHERE id_atividade = ? ");

        for (String c : condicoes) {
            sql.append(" AND ");
            sql.append(c);
        }
        
        if(! ordem.equals(""))
        {
            sql.append(" ORDER BY ");
            sql.append(ordem);
        }
        
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );
            statement.setInt( 1, atividade );
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
            
            while( resultado.next() )
            {
                Tarefa tarefa = new Tarefa();
                
                tarefa.setTarefa(resultado.getInt( "id_tarefa" ) ); 
                tarefa.setConcluida(resultado.getBoolean( "concluida" ) ); 
                
                if ( resultado.getString( "data_planejada" ) != null )
                    tarefa.setDataPlanejada(Data.convertSQLData( resultado.getString( "data_planejada" ) ) );
                else
                    tarefa.setDataPlanejada(null);
                
                if ( resultado.getString( "data_realizada" ) != null )
                    tarefa.setDataRealizada(Data.convertSQLData( resultado.getString( "data_realizada" ) ) );
                else
                    tarefa.setDataRealizada(null);
                
                tarefas.add(tarefa);
            }
            
            return tarefas;
        } catch ( MyException | SQLException | ParseException e ) {
            throw new MyException(e);
        }
    }
    
    public static ArrayList<Tarefa> buscaTarefasAtividade(int atividade) throws MyException
    {
        String sql = " SELECT * FROM tarefa WHERE id_atividade = ? ";
        
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, atividade );
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
            
            while( resultado.next() )
            {
                Tarefa tarefa = new Tarefa();
                
                tarefa.setTarefa(resultado.getInt( "id_tarefa" ) ); 
                tarefa.setConcluida(resultado.getBoolean( "concluida" ) ); 
                
                if ( resultado.getString( "data_planejada" ) != null )
                    tarefa.setDataPlanejada(Data.convertSQLData( resultado.getString( "data_planejada" ) ) );
                else
                    tarefa.setDataPlanejada(null);
                
                if ( resultado.getString( "data_realizada" ) != null )
                    tarefa.setDataRealizada(Data.convertSQLData( resultado.getString( "data_realizada" ) ) );
                else
                    tarefa.setDataRealizada(null);
                
                tarefa.setObservacoes( resultado.getString( "observacoes" ) );
                
                tarefas.add(tarefa);
            }
            
            return tarefas;
        } catch ( MyException | SQLException | ParseException e ) {
            throw new MyException(e);
        }
    }
    
    public static int buscaModeloTarefaTotal( int modelo ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM tarefa WHERE id_tarefa = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, modelo );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getInt( "total" );
            }
            else
            {
                return 0;
            }
        } catch ( SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static int buscaTarefasAtividadeTotal( int atividade, int tarefa ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM tarefa WHERE id_atividade = ? AND id_tarefa = ? ";
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, atividade );
            statement.setInt( 2, tarefa );
            
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getInt( "total" );
            }
            else
            {
                return 0;
            }
        } catch ( SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static int buscaTotalTarefasAtividade( boolean concluida, int atividade ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM tarefa WHERE id_atividade = ? AND concluida = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, atividade );
            
            if(concluida)
                statement.setInt( 1, 1 );
            else
                statement.setInt( 1, 0 );
            
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getInt( "total" ) ;
            }
            else
            {
                return 0;
            }
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }

    /**
     * Popula o vetor com as combinações entre colunas no banco e na tela
     */
    private static void criaConversaoTelaSQL()
    {
        telaSQL = new String[6][2];
        
        telaSQL[0][0] = "id_tarefa";
        telaSQL[0][1] = "Código";        
        telaSQL[1][0] = "data_planejada";
        telaSQL[1][1] = "Data planejada";
        telaSQL[2][0] = "data_realizada";
        telaSQL[2][1] = "Data realizada";
        telaSQL[3][0] = "observacoes";
        telaSQL[3][1] = "Observações";
        telaSQL[4][0] = "concluida";
        telaSQL[4][1] = "Concluída";
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
        
        for (String[] telaSQL1 : telaSQL)
        {
            if (nomeTela.equals(telaSQL1[1])) {
                return telaSQL1[0];
            }
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
