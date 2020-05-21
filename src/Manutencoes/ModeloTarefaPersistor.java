package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe ModeloTarefa
 * @author Jonatha
 */
public class ModeloTarefaPersistor
{    
    /**
     * Insere no banco um modelo de tarefa
     * @param modeloTarefa ModeloTarefa - Objeto da classe ModeloTarefa
     * @return boolean
     * @throws MyException 
     */
    public static boolean insereModeloTarefa(ModeloTarefa modeloTarefa) throws MyException
    {
        StringBuilder sql = new StringBuilder( 200 );
        sql.append( " INSERT INTO tarefa_modelo (descricao, complexa) VALUES " );
        sql.append( " ( ?,? ) " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setString(1, modeloTarefa.getDescricao());
            statement.setBoolean(2, modeloTarefa.isComplexa());

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Modelo inserido com sucesso!");
           
            ModeloTarefaLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com esta descrição!" );
            else
                throw new MyException( e );
        }
        
    }
    
    /**
     * Altera um modelo de tarefa no banco
     * @param modeloTarefa ModeloTarefa - Objeto da classe ModeloTarefa
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraModeloTarefa(ModeloTarefa modeloTarefa) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE tarefa_modelo SET " );
        sql.append( " descricao = ?, " ); 
        sql.append( " complexa = ? " ); 
        sql.append( " WHERE id_tarefa = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setString(1, modeloTarefa.getDescricao());
            statement.setBoolean(2, modeloTarefa.isComplexa());
            statement.setInt(3, modeloTarefa.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Modelo alterado com sucesso!");
            
            ModeloTarefaLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com esta descrição!" );
            else
                throw new MyException( e );
        }
    }
    
    /**
     * Exclui um modelo de tarefa do banco
     * @param id int - Código do modelo
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiModeloTarefa(int id) throws MyException
    {
        if(TarefaLocalizador.buscaModeloTarefaTotal(id) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não é possível excluir! Existem tarefas cadastradas para este modelo!");
            
            return false;
        }
        else
        {
            StringBuilder sql = new StringBuilder( 50 );
            sql.append( "DELETE FROM tarefa_modelo WHERE id_tarefa = ?" ); 

            Conexao conexao = Conexao.getInstance();
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );

            try {
                statement.setInt( 1, id );
                statement.execute();

                JOptionPane.showMessageDialog(null, "Modelo excluído com sucesso!");

                ModeloTarefaLocalizador.carregaModelo();

                return true;
            } catch (SQLException e){
                throw new MyException( e );
            }
        }
    }
    
}