package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import Principal.Data;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe Atividade
 * @author Jonatha
 */
public class TarefaPersistor
{    
    public static boolean insereTarefas(ArrayList<Tarefa> tarefas) throws MyException, SQLException
    {
        StringBuilder sql = new StringBuilder( 500 );
        sql.append( " INSERT INTO tarefa ( " );
        sql.append( " id_atividade, id_tarefa, data_planejada, data_realizada, observacoes, concluida " ); 
        sql.append( " ) VALUES " );
        sql.append( " ( ?,?,?,?,?,? ) " );

        Conexao conexao = Conexao.getInstance();
        conexao.setAutoCommit(false);
        
        try {
            for(Tarefa t : tarefas)
            {
                PreparedStatement statement = conexao.prepareStatement( sql.toString() );

                statement.setInt( 1, t.getAtividade());
                statement.setInt( 2, t.getTarefa());

                if(t.getDataPlanejada()!= null)
                    statement.setString( 3, Data.convertDataSQL( t.getDataPlanejada() ) );
                else
                    statement.setString( 3, null );

                if(t.getDataRealizada()!= null)
                    statement.setString( 4, Data.convertDataSQL( t.getDataRealizada() ) );
                else
                    statement.setString( 4, null );

                statement.setString( 5, t.getObservacoes() );
                statement.setBoolean( 6, t.isConcluida() );
                
                try{
                    statement.execute();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                    
                    conexao.rollback();
                    conexao.setAutoCommit(true);
                    
                    JOptionPane.showMessageDialog(null, "Erro ao inserir a tarefa "+t.getTarefaTexto());                    
                    
                    return false;
                }

            }
            
            conexao.commit();
            conexao.setAutoCommit(true);
            
            JOptionPane.showMessageDialog(null, "Tarefas inseridas com sucesso!");
            
            return true;
        } catch (SQLException | ParseException | HeadlessException e){
            throw new MyException( e );
        }
        
    }
    
    /*public static boolean alteraAtividades(ArrayList<Atividade> atividades) throws MyException, SQLException
    {
        StringBuilder sql = new StringBuilder( 500 );
        sql.append( " UPDATE atividade SET " );
        sql.append( " id_operador = ?, " ); 
        sql.append( " data_inicial = ?, " ); 
        sql.append( " data_final = ?, " ); 
        sql.append( " observacoes = ?, " ); 
        sql.append( " concluida = ? " ); 
        sql.append( " WHERE id_atividade = ? " );

        Conexao conexao = Conexao.getInstance();
        conexao.setAutoCommit(false);
        
        try {
            for(Atividade a : atividades)
            {
                PreparedStatement statement = conexao.prepareStatement( sql.toString() );

                if(a.getOperador() == 0)
                    statement.setNull( 1, a.getOperador() );
                else
                    statement.setInt( 1, a.getOperador() );

                if(a.getDataInicial() != null)
                    statement.setString( 2, Data.convertDataSQL( a.getDataInicial() ) );
                else
                    statement.setString( 2, null );

                if(a.getDataFinal() != null)
                    statement.setString( 3, Data.convertDataSQL( a.getDataFinal() ) );
                else
                    statement.setString( 3, null );

                statement.setString( 4, a.getObservacoes() );
                statement.setBoolean( 5, a.isConcluida() );
                
                statement.setInt( 6, a.getId() );
                
                try{
                    statement.execute();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                    conexao.rollback();
                    JOptionPane.showMessageDialog(null, "Erro ao alterar a atividade "+a.getMaquinaTexto());                    
                    return false;
                }

            }
            
            conexao.commit();
            conexao.setAutoCommit(true);
            
            JOptionPane.showMessageDialog(null, "Atividades alteradas com sucesso!");
            
            return true;
        } catch (SQLException | ParseException | HeadlessException e){
            throw new MyException( e );
        }
        
    }*/
    
    /**
     * Exclui uma Atividade do banco
     * @param id int - Código da atividade
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiTarefas(int atividade) throws MyException
    {
        StringBuilder sql = new StringBuilder( 50 );
        sql.append( "DELETE FROM tarefa WHERE id_atividade = ?" ); 

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setInt( 1, atividade );
            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Tarefas excluídas com sucesso!");
            
            return true;
        } catch (SQLException e){
            throw new MyException( e );
        }
    }
    
}