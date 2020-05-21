package Configuracoes;

import Banco.Conexao;
import Banco.MyException;
import Manutencoes.Trabalho;
import Principal.Criptografia;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Executa as operações de busca no banco para a classe Operador
 * @author Jonatha
 */
public class OperadorLocalizador 
{
    private static String[][] telaSQL;
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    
    /**
     * Retorna um operador conforme o código pesquisado
     * @param id int - Código do operador
     * @return Operador
     * @throws MyException 
     */
    public static Operador buscaOperador( int id ) throws MyException
    {
        String sql = " SELECT * FROM operador WHERE id_operador = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                Operador operador = new Operador();
                
                operador.setId( resultado.getInt( "id_operador" ) ); 
                
                operador.setNome( resultado.getString( "nome" ) ); 
                operador.setUsuario(resultado.getString( "usuario" ) ); 
                operador.setSenha(resultado.getString( "senha" ) ); 
                operador.setTipo(resultado.getInt( "tipo" ) ); 

                return operador;
            }
            else
            {
                return null;
            }
        } catch ( SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * Retorna todos os operadores, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<Operador> buscaOperadores( ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM operador ");
        
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
            
            ArrayList<Operador> operadores = new ArrayList<Operador>();
            
            while( resultado.next() )
            {
                Operador operador = new Operador();
                
                operador.setId( resultado.getInt( "id_operador" ) ); 
                
                operador.setNome( resultado.getString( "nome" ) ); 
                operador.setUsuario(resultado.getString( "usuario" ) ); 
                operador.setSenha(resultado.getString( "senha" ) ); 
                operador.setTipo(resultado.getInt( "tipo" ) );

                operadores.add(operador);
            }
            
            return operadores;
        } catch ( SQLException e ) {
            throw new MyException(e);
        }
    }
    
    /**
     * Retorna o nome do operador conforme o código pesquisado
     * @param id int - Código do operador
     * @return String
     * @throws MyException 
     */
    public static String buscaOperadorNome( int id ) throws MyException
    {
        String sql = " SELECT nome FROM operador WHERE id_operador = ? ";
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
    
    public static int buscaOperadorId( String usuario ) throws MyException
    {
        String sql = " SELECT id_operador FROM operador WHERE usuario = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString( 1, usuario );

            ResultSet resultado = statement.executeQuery();
            resultado.next();

            return resultado.getInt("id_operador");
            
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static boolean buscaOperadorPermissao( int id ) throws MyException
    {
        String sql = " SELECT tipo FROM operador WHERE id_operador = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );

            ResultSet resultado = statement.executeQuery();
            resultado.next();

            return resultado.getInt("tipo") == 1;

        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * Retorna a lista de localizações para uma combobox
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<Operador> buscaOperadorLista() throws MyException
    {
        String sql = " SELECT id_operador, nome FROM operador ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Operador> operadores = new ArrayList<Operador>();
            
            Operador inicial = new Operador();
            inicial.setId(0);             
            inicial.setNome( "" ); 
            
            operadores.add(inicial);
            
            while( resultado.next() )
            {
                Operador operador = new Operador();
                
                operador.setId( resultado.getInt( "id_operador" ) );                 
                operador.setNome( resultado.getString( "nome" ) ); 

                operadores.add(operador);
            }
            
            return operadores;
            
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static boolean buscaOperadorLogin(String usuario, String senha) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM operador WHERE usuario = ? AND senha = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString( 1, usuario );
            statement.setString( 2, Criptografia.criptografar(senha) );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getInt("total") == 1;
            }
            else
            {
                return false;
            }
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
        
        ArrayList opcoes = buscaOperadorLista();
        
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
            if(id == ((Operador) modelo.getElementAt(x)).getId())
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
        telaSQL = new String[5][2];
        
        telaSQL[0][0] = "id_operador";
        telaSQL[0][1] = "Código";
        telaSQL[1][0] = "nome";
        telaSQL[1][1] = "Nome";
        telaSQL[2][0] = "usuario";
        telaSQL[2][1] = "Usuário";
        telaSQL[3][0] = "senha";
        telaSQL[3][1] = "Senha";
        telaSQL[4][0] = "tipo";
        telaSQL[4][1] = "Tipo";
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
