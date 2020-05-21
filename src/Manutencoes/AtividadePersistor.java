package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import Principal.Data;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe Atividade
 * @author Jonatha
 */
public class AtividadePersistor
{    
    /**
     * Insere no banco uma Atividade
     * @param atividade Atividade - Objeto da classe Atividade
     * @return boolean
     * @throws MyException 
     */
    public static boolean insereAtividade(Atividade atividade) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        sql.append( " INSERT INTO atividade ( " );
        sql.append( " id_trabalho, id_maquina, id_tipo, id_operador, data_inicial, data_final, observacoes, concluida " ); 
        sql.append( " ) VALUES " );
        sql.append( " ( ?,?,?,?,?,?,?,? ) " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setInt( 1, atividade.getTrabalho());
            statement.setInt( 2, atividade.getMaquina());
            statement.setInt( 3, atividade.getTipo());
            
            if(atividade.getOperador() == 0)
                statement.setNull(4, atividade.getOperador());
            else
                statement.setInt( 4, atividade.getOperador());
            
            if(atividade.getDataInicial() != null)
                statement.setString( 5, Data.convertDataSQL( atividade.getDataInicial() ) );
            else
                statement.setString( 5, null );
            
            if(atividade.getDataFinal() != null)
                statement.setString( 6, Data.convertDataSQL( atividade.getDataFinal() ) );
            else
                statement.setString( 6, null );

            statement.setString( 7, atividade.getObservacoes() );
            statement.setBoolean( 8, atividade.isConcluida() );

            statement.execute();
   
            JOptionPane.showMessageDialog(null, "Atividade inserida com sucesso!");
            
            return true;
        } catch (SQLException | ParseException | HeadlessException e){
            throw new MyException( e );
        }
        
    }
    
    public static boolean insereAtividades(ArrayList<Atividade> atividades) throws MyException, SQLException
    {
        StringBuilder sql = new StringBuilder( 500 );
        sql.append( " INSERT INTO atividade ( " );
        sql.append( " id_trabalho, id_maquina, id_tipo, id_operador, data_inicial, data_final, observacoes, concluida " ); 
        sql.append( " ) VALUES " );
        sql.append( " ( ?,?,?,?,?,?,?,? ) " );

        Conexao conexao = Conexao.getInstance();
        conexao.setAutoCommit(false);
        
        try {
            for(Atividade a : atividades)
            {
                PreparedStatement statement = conexao.prepareStatement( sql.toString() );

                statement.setInt( 1, a.getTrabalho());
                statement.setInt( 2, a.getMaquina());
                statement.setInt( 3, a.getTipo());

                if(a.getOperador() == 0)
                    statement.setNull(4, a.getOperador());
                else
                    statement.setInt( 4, a.getOperador());

                if(a.getDataInicial() != null)
                    statement.setString( 5, Data.convertDataSQL( a.getDataInicial() ) );
                else
                    statement.setString( 5, null );

                if(a.getDataFinal() != null)
                    statement.setString( 6, Data.convertDataSQL( a.getDataFinal() ) );
                else
                    statement.setString( 6, null );

                statement.setString( 7, a.getObservacoes() );
                statement.setBoolean( 8, a.isConcluida() );
                
                try{
                    statement.execute();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                    conexao.rollback();
                    JOptionPane.showMessageDialog(null, "Erro ao inserir a atividade "+a.getMaquinaTexto());                    
                    return false;
                }

            }
            
            conexao.commit();
            conexao.setAutoCommit(true);
            
            JOptionPane.showMessageDialog(null, "Atividades inseridas com sucesso!");
            
            return true;
        } catch (SQLException | ParseException | HeadlessException e){
            throw new MyException( e );
        }
        
    }
    
    /**
     * Altera uma Atividade no banco
     * @param atividade Atividade - Objeto da classe Atividade
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraAtividade(Atividade atividade) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE atividade SET " );
        sql.append( " id_trabalho = ?, " ); 
        sql.append( " id_maquina = ?, " ); 
        sql.append( " id_tipo = ?, " ); 
        sql.append( " id_operador = ?, " ); 
        sql.append( " data_inicial = ?, " ); 
        sql.append( " data_final = ?, " ); 
        sql.append( " observacoes = ?, " ); 
        sql.append( " concluida = ? " ); 
        sql.append( " WHERE id_atividade = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setInt( 1, atividade.getTrabalho());
            statement.setInt( 2, atividade.getMaquina());
            statement.setInt( 3, atividade.getTipo());
            
            if(atividade.getOperador() == 0)
                statement.setNull(4, atividade.getOperador());
            else
                statement.setInt( 4, atividade.getOperador());
            
            if(atividade.getDataInicial() != null)
                statement.setString( 5, Data.convertDataSQL( atividade.getDataInicial() ) );
            else
                statement.setString( 5, null );
            
            if(atividade.getDataFinal() != null)
                statement.setString( 6, Data.convertDataSQL( atividade.getDataFinal() ) );
            else
                statement.setString( 6, null );

            statement.setString( 7, atividade.getObservacoes() );
            statement.setBoolean( 8, atividade.isConcluida() );
            
            statement.setInt( 9, atividade.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Atividade alterada com sucesso!");
            
            return true;
        } catch (SQLException | ParseException | HeadlessException e){
            throw new MyException( e );
        }
    }
    
    public static boolean alteraAtividades(ArrayList<Atividade> atividades) throws MyException, SQLException
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
        
    }
    
    /**
     * Exclui uma Atividade do banco
     * @param id int - Código da atividade
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiAtividade(int id) throws MyException
    {
        if(AtividadeLocalizador.buscaAtividadeId(id).isConcluida())
        {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a atividade! Ela está concluída.");            
            return true;
        }
        else if(TarefaLocalizador.buscaTotalTarefasAtividade(true, id) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a atividade! Há tarefas concluídas vinculadas à ela.");            
            return true;
        }
        else
        {
            if(TarefaPersistor.excluiTarefas(id))
            {
                StringBuilder sql = new StringBuilder( 50 );
                sql.append( "DELETE FROM atividade WHERE id_atividade = ?" ); 

                Conexao conexao = Conexao.getInstance();
                PreparedStatement statement = conexao.prepareStatement( sql.toString() );

                try {
                    statement.setInt( 1, id );
                    statement.execute();
        
                    return true;
                } catch (SQLException e){
                    throw new MyException( e );
                }                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Não foi possível excluir a atividade."); 
                return false;
            }
        }
    }
    
    public static Boolean excluiAtividades(int trabalho) throws MyException, SQLException
    {
        StringBuilder sql = new StringBuilder( 50 );
        
        sql.append( "SELECT id FROM atividade WHERE id_trabalho = ?" ); 

        Conexao conexao = Conexao.getInstance();        
        conexao.setAutoCommit(false);
        
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        statement.setInt( 1, trabalho );

        ResultSet resultado = statement.executeQuery();
            
        while( resultado.next() )
        {
            int id = resultado.getInt("id");
            
            if(! excluiAtividade(id))
            {
                conexao.rollback();
                return false; 
            }
        }
        
        conexao.commit();
        conexao.setAutoCommit(true);
        
        JOptionPane.showMessageDialog(null, "Atividades excluídas com sucesso!");            
        
        return true;
    }
    
}