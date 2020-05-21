package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe TipoAtividade
 * @author Jonatha
 */
public class TipoAtividadePersistor
{    
    /**
     * Insere no banco um tipo de atividade
     * @param tipoAtividade TipoAtividade - Objeto da classe TipoAtividade
     * @return boolean
     * @throws MyException 
     */
    public static boolean insereTipoAtividade(TipoAtividade tipoAtividade) throws MyException
    {
        StringBuilder sql = new StringBuilder( 200 );
        sql.append( " INSERT INTO tipo (nome) VALUES " );
        sql.append( " ( ? ) " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setString(1, tipoAtividade.getNome() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Tipo inserido com sucesso!");
            
            TipoAtividadeLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome!" );
            else
                throw new MyException( e );
        }
        
    }
    
    /**
     * Altera um tipo de atividade no banco
     * @param tipoAtividade TipoAtividade - Objeto da classe TipoAtividade
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraTipoAtividade(TipoAtividade tipoAtividade) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE tipo SET " );
        sql.append( " nome = ? " ); 
        sql.append( " WHERE id_tipo = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setString(1, tipoAtividade.getNome() );
            statement.setInt(2, tipoAtividade.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Tipo alterado com sucesso!");
            
            TipoAtividadeLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome!" );
            else
                throw new MyException( e );
        }
    }
    
    /**
     * Exclui um tipo de atividade do banco
     * @param id int - Código do tipo
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiTipoAtividade(int id) throws MyException
    {
        if(AtividadeLocalizador.buscaAtividadeTipoAtividadeTotal(id) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não é possível excluir! Existem atividades cadastradas para este tipo!");
            
            return false;
        }
        else
        {
            StringBuilder sql = new StringBuilder( 50 );
            sql.append( "DELETE FROM tipo WHERE id_tipo = ?" ); 

            Conexao conexao = Conexao.getInstance();
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );

            try {
                statement.setInt( 1, id );
                statement.execute();

                JOptionPane.showMessageDialog(null, "Tipo excluído com sucesso!");

                TipoAtividadeLocalizador.carregaModelo();

                return true;
            } catch (SQLException e){
                throw new MyException( e );
            }
        }
    }
    
}