package Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe que realiza a conexão com o banco de dados
 * @author Jonatha
 */
public class Conexao
{
    private static Conexao myself;
    
    private Connection conexao;
    private Parametros parametros;
    
    /**
     * Construtor que cria uma instância de Conexao
     * @throws MyException 
     */
    private Conexao() throws MyException 
    {
        try {
            this.parametros = Parametros.getInstance();

            Class.forName( this.parametros.getParametro( "driver" ) );
            
            String url = this.parametros.getParametro( "url" );
            String banco = this.parametros.getParametro( "banco" );
            String usuario = this.parametros.getParametro( "usuario" );
            String senha = this.parametros.getParametro( "senha" );            
            
            this.conexao = DriverManager.getConnection( url+banco, usuario, senha );
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new MyException(e);
        }
    }

    /**
     * Retorna a instância de Conexao - se não existe, cria antes
     * @return Conexao
     * @throws MyException 
     */
    public static Conexao getInstance() throws MyException
    {
        if( myself == null )
        {
            myself = new Conexao();
        }

        return myself;
    }
    
    /**
     * Cria uma sentença SQL 
     * @return Statement
     * @throws MyException 
     */
    public Statement createStatement() throws MyException
    {
        try {
            return this.conexao.createStatement();
        } catch (SQLException e) {
            throw new MyException(e);
        }
    }

    /**
     * Cria uma sentença preparada SQL
     * @param sql String - Comando SQL inicial
     * @return Prepared Statement
     * @throws MyException 
     */
    public PreparedStatement prepareStatement(String sql) throws MyException
    {
        try {
            PreparedStatement pst = this.conexao.prepareStatement( sql );
            return pst;
        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    /**
     * Desconecta-se do banco de dados
     * @throws MyException 
     */
    public void desconecta() throws MyException
    {
        try {
            this.conexao.close();
            myself = null;
        } catch (Exception e){
            throw new MyException(e);
        }
    }

    /**
     * Retorna a conexão
     * @return Connection
     */
    public Connection getConnection()
    {
        return this.conexao;
    }
    
    public void setAutoCommit(boolean set) throws MyException
    {
        try {
            this.conexao.setAutoCommit(set);
        } catch (SQLException ex) {
            throw new MyException(ex);
        }
    }
    
    public void commit() throws MyException
    {
        try {
            this.conexao.commit();
        } catch (SQLException ex) {
            throw new MyException(ex);
        }
    }
    
    public void rollback() throws MyException
    {
        try {
            this.conexao.rollback();
        } catch (SQLException ex) {
            throw new MyException(ex);
        }
    }
}