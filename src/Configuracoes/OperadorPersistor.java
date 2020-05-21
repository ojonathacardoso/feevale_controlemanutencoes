package Configuracoes;

import Banco.Conexao;
import Banco.MyException;
import Manutencoes.AtividadeLocalizador;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe Operador
 * @author Jonatha
 */
public class OperadorPersistor
{    
    /**
     * Insere no banco um Operador
     * @param operador Operador - Objeto da classe Operador
     * @return boolean
     * @throws MyException 
     */
    public static boolean insereOperador(Operador operador) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        sql.append( " INSERT INTO operador ( " );
        sql.append( " nome, usuario, senha, tipo " ); 
        sql.append( " ) VALUES " );
        sql.append( " ( ?,?,?,? ) " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setString( 1, operador.getNome() );
            statement.setString( 2, operador.getUsuario());
            statement.setString( 3, operador.getSenha() );
            statement.setInt(4, operador.getTipo());

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Operador inserido com sucesso!");
            
            OperadorLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome de usuário!" );
            else
                throw new MyException( e );
        }
        
    }
    
    /**
     * Altera um Operador no banco
     * @param operador Operador - Objeto da classe Operador
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraOperador(Operador operador) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE operador SET " );
        sql.append( " nome = ?, " ); 
        sql.append( " usuario = ?, " ); 
        sql.append( " senha = ?, " ); 
        sql.append( " tipo = ? " ); 
        sql.append( " WHERE id_operador = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setString( 1, operador.getNome() );
            statement.setString( 2, operador.getUsuario() );
            statement.setString( 3, operador.getSenha() );
            statement.setInt( 4, operador.getTipo() );
            statement.setInt( 5, operador.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Operador alterado com sucesso!");
            
            OperadorLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome de usuário!" );
            else
                throw new MyException( e );
        }
    }
    
    public static boolean alteraSenhaOperador(Operador operador) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE operador SET " );
        sql.append( " senha = ? " ); 
        sql.append( " WHERE id_operador = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setString( 1, operador.getSenha() );
            statement.setInt( 2, operador.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
            
            OperadorLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome de usuário!" );
            else
                throw new MyException( e );
        }
    }
    
    /**
     * Exclui um Operador do banco
     * @param id int - Código do operador
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiOperador(int id) throws MyException
    {
        if(AtividadeLocalizador.buscaAtividadeOperadorTotal(id) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não é possível excluir! Existem atividades cadastradas para este operador!");
            
            return false;
        }
        else
        {
            StringBuilder sql = new StringBuilder( 50 );
            sql.append( "DELETE FROM operador WHERE id_operador = ?" ); 

            Conexao conexao = Conexao.getInstance();
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );

            try {
                statement.setInt( 1, id );
                statement.execute();

                JOptionPane.showMessageDialog(null, "Operador excluído com sucesso!");

                OperadorLocalizador.carregaModelo();

                return true;
            } catch (SQLException e){
                throw new MyException( e );
            }   
        }
    }
    
}