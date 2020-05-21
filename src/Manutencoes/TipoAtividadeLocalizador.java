package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Executa as operações de busca no banco para a classe TipoAtividade
 * @author Jonatha
 */
public class TipoAtividadeLocalizador 
{
    private static String[][] telaSQL;
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    
    /**
     * Retorna um tipo de atividade conforme o código pesquisado
     * @param id int - Código do tipo
     * @return TipoAtividade
     * @throws MyException 
     */
    public static TipoAtividade buscaTipoAtividade( int id ) throws MyException
    {
        String sql = " SELECT * FROM tipo WHERE id_tipo = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                TipoAtividade tipoAtividade = new TipoAtividade();
                
                tipoAtividade.setId( resultado.getInt( "id_tipo" ) );                 
                tipoAtividade.setNome( resultado.getString( "nome" ) ); 

                return tipoAtividade;
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
     * Retorna todos os tipos de atividades, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<TipoAtividade> buscaTiposAtividades( ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM tipo ");
        
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
            
            ArrayList<TipoAtividade> tiposAtividades = new ArrayList<TipoAtividade>();
            
            while( resultado.next() )
            {
                TipoAtividade tipoAtividade = new TipoAtividade();
                
                tipoAtividade.setId( resultado.getInt( "id_tipo" ) );                 
                tipoAtividade.setNome( resultado.getString("nome" ) ); 

                tiposAtividades.add(tipoAtividade);
            }
            
            return tiposAtividades;
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
    }
    
    /**
     * Retorna o nome do tipo de atividade conforme o código pesquisado
     * @param id int - Código do tipo de atividade
     * @return String
     * @throws MyException 
     */
    public static String buscaTipoAtividadeNome( int id ) throws MyException
    {
        String sql = " SELECT nome FROM tipo WHERE id_tipo = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getString( "nome" ) ;
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
     * Retorna a lista de tipos de atividades
     * @return String[]
     * @throws MyException 
     */
    public static ArrayList<TipoAtividade> buscaTipoAtividadeLista() throws MyException
    {
        String sql = " SELECT id_tipo, nome FROM tipo";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<TipoAtividade> tipos = new ArrayList<TipoAtividade>();
            
            TipoAtividade inicial = new TipoAtividade();
            inicial.setId(0);             
            inicial.setNome( "" ); 
            
            tipos.add(inicial);
            
            while( resultado.next() )
            {
                TipoAtividade tipo = new TipoAtividade();
                
                tipo.setId( resultado.getInt( "id_tipo" ) );                 
                tipo.setNome( resultado.getString( "nome" ) ); 

                tipos.add(tipo);
            }
            
            return tipos;

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
        
        ArrayList opcoes = buscaTipoAtividadeLista();
        
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
    
    public static int getIndexModelo(int id)
    {
        for(int x = 0; x < modelo.getSize(); x++)
        {
            if(id == ((TipoAtividade) modelo.getElementAt(x)).getId())
            {
                return x;
            }
        }
        return 0;
    }
    
    /**
     * Popula o vetor com as combinações entre colunas no banco e na tela
     */
    private static void criaConversaoTelaSQL()
    {
        telaSQL = new String[2][2];
        
        telaSQL[0][0] = "id_tipo";
        telaSQL[0][1] = "Código";
        telaSQL[1][0] = "nome";
        telaSQL[1][1] = "Nome";
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
