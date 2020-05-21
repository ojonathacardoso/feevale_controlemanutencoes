package Cadastros;

import Banco.Conexao;
import Banco.MyException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Executa as operações de busca no banco para a classe Localizacao
 * @author Jonatha
 */
public class LocalizacaoLocalizador
{
    private static String[][] telaSQL;    
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    
    /**
     * Retorna uma localização conforme o código pesquisado
     * @param id int - Código da localização
     * @return Localizacao
     * @throws MyException 
     */
    public static Localizacao buscaLocalizacao( int id ) throws MyException
    {
        String sql = " SELECT * FROM localizacao WHERE id_localizacao = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                Localizacao localizacao = new Localizacao();
                
                localizacao.setId( resultado.getInt( "id_localizacao" ) );                 
                localizacao.setNome( resultado.getString( "nome" ) ); 

                return localizacao;
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
     * Retorna todas as localizações, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<Localizacao> buscaLocalizacoes( ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM localizacao ");
        
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
            
            ArrayList<Localizacao> localizacoes = new ArrayList<Localizacao>();
            
            while( resultado.next() )
            {
                Localizacao localizacao = new Localizacao();
                
                localizacao.setId( resultado.getInt( "id_localizacao" ) );                 
                localizacao.setNome( resultado.getString("nome" ) ); 

                localizacoes.add(localizacao);
            }
            
            return localizacoes;
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
    }
    
    /**
     * Retorna o nome de uma localização conforme o código pesquisado
     * @param id int - Código da localização
     * @return String
     * @throws MyException 
     */
    public static String buscaLocalizacaoNome( int id ) throws MyException
    {
        String sql = " SELECT nome FROM localizacao WHERE id_localizacao = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getString( "nome" );
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
     * Retorna a lista de localizações para uma combobox
     * @return 
     * @throws MyException 
     */
    public static ArrayList<Localizacao> buscaLocalizacaoLista() throws MyException
    {
        String sql = " SELECT id_localizacao, nome FROM localizacao";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Localizacao> localizacoes = new ArrayList<Localizacao>();
            
            Localizacao inicial = new Localizacao();
            inicial.setId(0);             
            inicial.setNome( "" ); 
            
            localizacoes.add(inicial);
            
            while( resultado.next() )
            {
                Localizacao localizacao = new Localizacao();
                
                localizacao.setId( resultado.getInt( "id_localizacao" ) );                 
                localizacao.setNome( resultado.getString( "nome" ) ); 

                localizacoes.add(localizacao);
            }
            
            return localizacoes;
            
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
        if(modelo.getSize() > 0)
        {
            modelo.removeAllElements(); 
        }
        
        ArrayList opcoes = buscaLocalizacaoLista();
        
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
            if(id == ((Localizacao) modelo.getElementAt(x)).getId())
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
        
        telaSQL[0][0] = "id_localizacao";
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