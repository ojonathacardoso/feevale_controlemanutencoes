package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import static Manutencoes.ModeloTarefaLocalizador.buscaModeloTarefaLista;
import Principal.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;

/**
 * Executa as operações de busca no banco para a classe Atividade
 * @author Jonatha
 */
public class AtividadeLocalizador 
{
    private static String[][] telaSQL;
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    
    /**
     * Retorna uma atividade conforme o código pesquisado
     * @param id int - Código da máquina
     * @return Atividade
     * @throws MyException 
     */
    public static Atividade buscaAtividadeId( int id ) throws MyException
    {
        String sql = " SELECT * FROM atividade WHERE id_atividade = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                Atividade atividade = new Atividade();
                
                atividade.setId( resultado.getInt( "id_atividade" ) ); 
                
                atividade.setTrabalho( resultado.getInt( "id_trabalho" ));
                atividade.setMaquina( resultado.getInt( "id_maquina" ));
                atividade.setTipo( resultado.getInt( "id_tipo" ));
                atividade.setOperador( resultado.getInt( "id_operador" ));
                
                if ( resultado.getString( "data_inicial" ) != null )
                    atividade.setDataInicial( Data.convertSQLData( resultado.getString( "data_inicial" ) ) );
                else
                    atividade.setDataInicial(null);
                
                if ( resultado.getString( "data_final" ) != null )
                    atividade.setDataFinal( Data.convertSQLData( resultado.getString( "data_final" ) ) );
                else
                    atividade.setDataFinal(null);
                   
                atividade.setObservacoes( resultado.getString( "observacoes" ) );                
                atividade.setConcluida( resultado.getBoolean( "concluida" ) ); 

                return atividade;
            }
            else
            {
                return null;
            }
        } catch ( MyException | SQLException | ParseException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * Retorna todas as atividades, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<Atividade> buscaAtividades( ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM atividade ");
        
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
            
            ArrayList<Atividade> atividades = new ArrayList<Atividade>();
            
            while( resultado.next() )
            {
                Atividade atividade = new Atividade();
                
                atividade.setId( resultado.getInt( "id_atividade" ) ); 
                
                atividade.setTrabalho( resultado.getInt( "id_trabalho" ));
                atividade.setMaquina( resultado.getInt( "id_maquina" ));
                atividade.setTipo( resultado.getInt( "id_tipo" ));
                atividade.setOperador( resultado.getInt( "id_operador" ));
                
                if ( resultado.getString( "data_inicial" ) != null )
                    atividade.setDataInicial( Data.convertSQLData( resultado.getString( "data_inicial" ) ) );
                else
                    atividade.setDataInicial(null);
                
                if ( resultado.getString( "data_final" ) != null )
                    atividade.setDataFinal( Data.convertSQLData( resultado.getString( "data_final" ) ) );
                else
                    atividade.setDataFinal(null);
                   
                atividade.setObservacoes( resultado.getString( "observacoes" ) );                
                atividade.setConcluida( resultado.getBoolean( "concluida" ) ); 

                atividades.add(atividade);
            }
            
            return atividades;
        } catch ( MyException | SQLException | ParseException e ) {
            throw new MyException(e);
        }
    }
    
    public static ArrayList<Atividade> buscaPraLista(int trabalho) throws MyException
    {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" id_atividade, ");
        sql.append(" id_maquina ");
        sql.append(" FROM atividade ");
        sql.append(" WHERE id_trabalho = ? ");
        
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setInt( 1, trabalho );
            
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Atividade> atividades = new ArrayList<Atividade>();
            
            Atividade inicial = new Atividade();
            inicial.setId(0);             
            inicial.setMaquina(0); 
            
            atividades.add(inicial);
            
            while( resultado.next() )
            {
                Atividade atividade = new Atividade();
                
                atividade.setId( resultado.getInt( "id_atividade" ) );                 
                atividade.setMaquina(resultado.getInt( "id_maquina" ) ); 

                atividades.add(atividade);
            }
            
            return atividades;

        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static ArrayList<Atividade> buscaProFormulario( int trabalho ) throws MyException
    {
        String sql = " SELECT * FROM atividade WHERE id_trabalho = ? ";
        
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt(1, trabalho);
            
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Atividade> atividades = new ArrayList<Atividade>();
            
            while( resultado.next() )
            {
                Atividade atividade = new Atividade();
                
                atividade.setId( resultado.getInt( "id_atividade" ) ); 
                
                atividade.setTrabalho( resultado.getInt( "id_trabalho" ));
                atividade.setMaquina( resultado.getInt( "id_maquina" ));
                atividade.setTipo( resultado.getInt( "id_tipo" ));
                atividade.setOperador( resultado.getInt( "id_operador" ));
                
                if ( resultado.getString( "data_inicial" ) != null )
                    atividade.setDataInicial( Data.convertSQLData( resultado.getString( "data_inicial" ) ) );
                else
                    atividade.setDataInicial(null);
                
                if ( resultado.getString( "data_final" ) != null )
                    atividade.setDataFinal( Data.convertSQLData( resultado.getString( "data_final" ) ) );
                else
                    atividade.setDataFinal(null);
                   
                atividade.setObservacoes( resultado.getString( "observacoes" ) );                
                atividade.setConcluida( resultado.getBoolean( "concluida" ) ); 

                atividades.add(atividade);
            }
            
            return atividades;
        } catch ( MyException | SQLException | ParseException e ) {
            throw new MyException(e);
        }
    }
        
    /**
     * Retorna uma atividade conforme o código pesquisado
     * @param id int - Código da máquina
     * @return Atividade
     * @throws MyException 
     */
    public static int buscaAtividadeMaquinaTotal( int maquina ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM atividade WHERE id_maquina = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, maquina );
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
    
    public static int buscaAtividadeTrabalhoTotal( int trabalho, int maquina ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM atividade WHERE id_trabalho = ? AND id_maquina = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, trabalho );
            statement.setInt( 2, maquina );
            
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
    
    public static int buscaAtividadeDiasTotal( Date data, int trabalho ) throws MyException, ParseException
    {
        String sql = " SELECT COUNT(*) AS total FROM atividade WHERE data_inicial = ? AND id_trabalho = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString( 1, Data.convertDataSQL( data ) );
            statement.setInt( 2, trabalho );
            
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
    
    /**
     * Retorna uma atividade conforme o código pesquisado
     * @param id int - Código da máquina
     * @return Atividade
     * @throws MyException 
     */
    public static int buscaAtividadeOperadorTotal( int operador ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM atividade WHERE id_operador = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, operador );
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
    
    public static int buscaAtividadeTipoAtividadeTotal( int tipo ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM atividade WHERE id_tipo = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, tipo );
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
    
    public static Date buscaDataLimiteTrabalho(int opcao, int trabalho) throws MyException, ParseException
    {
        String sql = " SELECT ? AS data FROM atividade WHERE concluida = 1 AND id_trabalho = " + trabalho;
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            
            if(opcao == 1)
            {
                statement.setString(1, " MIN(data_inicial) ");
            }
            else
            {
                statement.setString(1, " MAX(data_final) ");
            }
            
            statement.setInt(2, trabalho);
            
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Trabalho> trabalhos = new ArrayList<Trabalho>();

            if( resultado.next() )
            {
                if( resultado.getString( "data" ).equals("") )
                {
                    return null;
                }
                else
                {
                    return Data.convertSQLData( resultado.getString( "data" ) );
                }
                
            }
            else
            {
                return null;
            }            
            
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
    }
    
    public static int buscaTotalAtividadesTrabalho( boolean concluido, int trabalho ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM atividade WHERE id_trabalho = ? AND concluida = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, trabalho );
            
            if(concluido)
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
    
    public static void carregaModelo(int trabalho) throws MyException
    {
        modelo.removeAllElements();
        
        ArrayList opcoes = buscaPraLista(trabalho);
        
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
    public static DefaultComboBoxModel getModelo(int trabalho) throws MyException
    {
        carregaModelo(trabalho);
        
        return modelo;
    }
    
    /**
     * Popula o vetor com as combinações entre colunas no banco e na tela
     */
    private static void criaConversaoTelaSQL()
    {
        telaSQL = new String[9][2];
        
        telaSQL[0][0] = "id_atividade";
        telaSQL[0][1] = "Código";
        telaSQL[1][0] = "id_trabalho";
        telaSQL[1][1] = "Trabalho";
        telaSQL[2][0] = "id_maquina";
        telaSQL[2][1] = "Máquina";
        telaSQL[3][0] = "id_tipo";
        telaSQL[3][1] = "Tipo";
        telaSQL[4][0] = "id_operador";
        telaSQL[4][1] = "Operador";
        telaSQL[5][0] = "data_inicial";
        telaSQL[5][1] = "Data inicial";
        telaSQL[6][0] = "data_final";
        telaSQL[6][1] = "Data final";
        telaSQL[7][0] = "observacoes";
        telaSQL[7][1] = "Observações";
        telaSQL[8][0] = "concluida";
        telaSQL[8][1] = "Concluída";
        
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
        
        return telaSQL.length-1;
    }
    
}
