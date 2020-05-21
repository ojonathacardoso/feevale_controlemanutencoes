package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import Cadastros.MaquinaLocalizador;
import Principal.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe Trabalho
 * @author Jonatha
 */
public class TrabalhoPersistor
{    
    /**
     * Insere no banco um Trabalho
     * @param trabalho Trabalho - Objeto da classe Trabalho
     * @return boolean
     * @throws MyException 
     */
    public static boolean insereTrabalho(Trabalho trabalho) throws MyException, ParseException
    {
        StringBuilder sql = new StringBuilder( 500 );
        sql.append( " INSERT INTO trabalho ( " );
        sql.append( " descricao, programado, concluido, data_inicial, data_final " ); 
        sql.append( " ) VALUES " );
        sql.append( " ( ?,?,?,?,? ) " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setString( 1, trabalho.getDescricao() );
            statement.setBoolean( 2, trabalho.isProgramado() );
            statement.setBoolean( 3, trabalho.isConcluido() );
            
            if(trabalho.getDataInicial() != null)
                statement.setString( 4, Data.convertDataSQL( trabalho.getDataInicial() ) );
            else
                statement.setString( 4, null );
            
            if(trabalho.getDataFinal() != null)
                statement.setString( 5, Data.convertDataSQL( trabalho.getDataFinal() ) );
            else
                statement.setString( 5, null );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Trabalho inserido com sucesso!");
            
            TrabalhoLocalizador.carregaModelo(false);
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com esta descrição!" );
            else
                throw new MyException( e );
        }
        
    }
    
    /**
     * Altera um Trabalho no banco
     * @param trabalho Trabalho - Objeto da cla
     * @throws ParseException
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraTrabalho(Trabalho trabalho) throws MyException, ParseException
    {
        if(AtividadeLocalizador.buscaDataLimiteTrabalho(1, trabalho.getId()).before(trabalho.getDataInicial()))
        {
            JOptionPane.showMessageDialog(null, "Não é possível alterar! Há uma atividade concluída que iniciou em data anterior à data inicial que você informou. ");
            
            return false;
        }
        else if(AtividadeLocalizador.buscaDataLimiteTrabalho(2, trabalho.getId()).after(trabalho.getDataFinal()))
        {
            JOptionPane.showMessageDialog(null, "Não é possível alterar! Existe uma atividade concluída em data posterior à data final que você informou. ");
            
            return false;
        }
        else if(AtividadeLocalizador.buscaTotalAtividadesTrabalho(false, trabalho.getId()) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não é possível alterar! Existem atividades vinculadas a este trabalho que ainda não foram concluídas.");
            
            return false;
        }
        else
        {
            StringBuilder sql = new StringBuilder( 2000 );

            sql.append( " UPDATE trabalho SET " );
            sql.append( " descricao = ?, " ); 
            sql.append( " programado = ?, " ); 
            sql.append( " concluido = ?, " ); 
            sql.append( " data_inicial = ?, " ); 
            sql.append( " data_final = ? " ); 
            sql.append( " WHERE id_trabalho = ? " );

            Conexao conexao = Conexao.getInstance();
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );

            try {
                statement.setString( 1, trabalho.getDescricao());
                statement.setBoolean( 2, trabalho.isProgramado());
                statement.setBoolean(3, trabalho.isConcluido());

                if(trabalho.getDataInicial() != null)
                    statement.setString( 4, Data.convertDataSQL( trabalho.getDataInicial() ) );
                else
                    statement.setString( 4, null );

                if(trabalho.getDataFinal() != null)
                    statement.setString( 5, Data.convertDataSQL( trabalho.getDataFinal() ) );
                else
                    statement.setString( 5, null );

                statement.setInt( 6, trabalho.getId() );

                statement.execute();

                JOptionPane.showMessageDialog(null, "Trabalho alterado com sucesso!");

                TrabalhoLocalizador.carregaModelo(false);

                return true;
            } catch (SQLException e){
                if(e.getClass().getName().contains("ConstraintViolationException"))
                    throw new MyException( "Já existe um registro com esta descrição!" );
                else
                    throw new MyException( e );
            }
        }
        
    }
    
    /**
     * Exclui um Trabalho do banco
     * @param id int - Código do trabalho
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiTrabalho(int id) throws MyException, ParseException, SQLException
    {
        if(AtividadeLocalizador.buscaTotalAtividadesTrabalho(true, id) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o trabalho! Há atividades concluídas vinculadas à ele.");
            return false;
        }
        else
        {
            if(AtividadePersistor.excluiAtividades(id))
            {
                StringBuilder sql = new StringBuilder( 50 );
                sql.append( "DELETE FROM trabalho WHERE id_trabalho = ?" ); 

                Conexao conexao = Conexao.getInstance();
                PreparedStatement statement = conexao.prepareStatement( sql.toString() );

                try {
                    statement.setInt( 1, id );
                    statement.execute();

                    JOptionPane.showMessageDialog(null, "Trabalho excluído com sucesso!");

                    TrabalhoLocalizador.carregaModelo(false);

                    return true;
                } catch (SQLException e){
                    throw new MyException( e );
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Não foi possível excluir o trabalho!");
                return false;
            }
        }
    }
    
}