package Cadastros;

import Banco.Conexao;
import Banco.MyException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe Localizacao
 * @author Jonatha
 */
public class LocalizacaoPersistor
{    
    /**
     * Insere no banco uma localizacao
     * @param localizacao Localizacao - Objeto da classe Localizacao
     * @return boolean
     * @throws MyException 
     */
    public static boolean insereLocalizacao(Localizacao localizacao) throws MyException
    {
        StringBuilder sql = new StringBuilder( 200 );
        sql.append( " INSERT INTO localizacao (nome) VALUES " );
        sql.append( " ( ? ) " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setString( 1, localizacao.getNome() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Localização inserida com sucesso!");
            
            LocalizacaoLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome!" );
            else
                throw new MyException( e );
        }
        
    }
    
    /**
     * Altera uma localização no banco
     * @param localizacao Localizacao - Objeto da classe Localizacao
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraLocalizacao(Localizacao localizacao) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE localizacao SET " );
        sql.append( " nome = ? " ); 
        sql.append( " WHERE id_localizacao = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setString( 1, localizacao.getNome() );
            statement.setInt( 2, localizacao.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Localização alterada com sucesso!");
            
            LocalizacaoLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome!" );
            else
                throw new MyException( e );
        }
    }
    
    /**
     * Exclui uma localização do banco
     * @param id int - Código da localização
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiLocalizacao(int id) throws MyException
    {
        if(MaquinaLocalizador.buscaMaquinaLocalizacaoTotal(id) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não é possível excluir! Existem máquinas cadastradas para esta localização!");
            
            return false;
        }
        else
        {
            StringBuilder sql = new StringBuilder( 50 );
            sql.append( "DELETE FROM localizacao WHERE id_localizacao = ?" ); 

            Conexao conexao = Conexao.getInstance();
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );

            try {
                statement.setInt( 1, id );
                statement.execute();

                JOptionPane.showMessageDialog(null, "Localização excluída com sucesso!");

                LocalizacaoLocalizador.carregaModelo();

                return true;
            } catch (SQLException e){
                throw new MyException( e );
            }   
        }
    }
    
}