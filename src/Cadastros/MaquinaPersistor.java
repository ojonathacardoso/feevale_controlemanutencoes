package Cadastros;

import Banco.Conexao;
import Banco.MyException;
import Manutencoes.AtividadeLocalizador;
import Manutencoes.AtividadeTela;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe Maquina
 * @author Jonatha
 */
public class MaquinaPersistor
{    
    /**
     * Insere no banco uma Maquina
     * @param maquina Maquina - Objeto da classe Maquina
     * @return boolean
     * @throws MyException 
     */
    public static boolean insereMaquina(Maquina maquina) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        sql.append( " INSERT INTO maquina ( " );
        sql.append( " id_modelo, nome_maquina, nome_usuario, id_localizacao, sistema, hd, ram, proc, observacao " ); 
        sql.append( " ) VALUES " );
        sql.append( " ( ?,?,?,?,?,?,?,?,? ) " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setInt( 1, maquina.getModeloMaquina() );
            statement.setString( 2, maquina.getNome() );
            statement.setString( 3, maquina.getUsuario() );
            statement.setInt( 4, maquina.getLocalizacao() );
            statement.setString( 5, maquina.getSistema() );            
            statement.setInt( 6, maquina.getHd() );
            statement.setInt( 7, maquina.getRam() );
            statement.setString( 8, maquina.getProc() );
            statement.setString( 9, maquina.getObservacao() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Máquina inserida com sucesso!");

            MaquinaLocalizador.carregaModelo();
            
            if(AtividadeTela.modeloInsert != null)
            {
                AtividadeTela.modeloInsert.carregarLista(MaquinaLocalizador.buscaMaquinas(null, ""));
            }
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome!" );
            else
                throw new MyException( e );
        }
        
    }
    
    /**
     * Altera uma Maquina no banco
     * @param maquina Maquina - Objeto da classe Maquina
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraMaquina(Maquina maquina) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE maquina SET " );
        sql.append( " id_modelo = ?, " ); 
        sql.append( " nome_maquina = ?, " ); 
        sql.append( " nome_usuario = ?, " ); 
        sql.append( " id_localizacao = ?, " ); 
        sql.append( " sistema = ?, " ); 
        sql.append( " hd = ?, " ); 
        sql.append( " ram = ?, " ); 
        sql.append( " proc = ?, " ); 
        sql.append( " observacao = ? " ); 
        sql.append( " WHERE id_maquina = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setInt( 1, maquina.getModeloMaquina() );
            statement.setString( 2, maquina.getNome() );
            statement.setString( 3, maquina.getUsuario() );
            statement.setInt( 4, maquina.getLocalizacao() );
            statement.setString( 5, maquina.getSistema() );            
            statement.setInt( 6, maquina.getHd() );
            statement.setInt( 7, maquina.getRam() );
            statement.setString( 8, maquina.getProc() );
            statement.setString( 9, maquina.getObservacao() );
            
            statement.setInt( 10, maquina.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Máquina alterada com sucesso!");
            
            MaquinaLocalizador.carregaModelo();
            
            if(AtividadeTela.modeloInsert != null)
            {
                AtividadeTela.modeloInsert.carregarLista(MaquinaLocalizador.buscaMaquinas(null, ""));
            }
            
            return true;
        } catch (SQLException e){
            if(e.getClass().getName().contains("ConstraintViolationException"))
                throw new MyException( "Já existe um registro com este nome!" );
            else
                throw new MyException( e );
        }
    }
    
    /**
     * Exclui um Maquina do banco
     * @param id int - Código da máquina
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiMaquina(int id) throws MyException
    {
        if(AtividadeLocalizador.buscaAtividadeMaquinaTotal(id) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não é possível excluir! Existem atividades cadastradas para esta máquina!");
            
            return false;
        }
        else
        {
            StringBuilder sql = new StringBuilder( 50 );
            sql.append( "DELETE FROM maquina WHERE id_maquina = ?" ); 

            Conexao conexao = Conexao.getInstance();
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );

            try {
                statement.setInt( 1, id );
                statement.execute();

                JOptionPane.showMessageDialog(null, "Máquina excluída com sucesso!");

                MaquinaLocalizador.carregaModelo();
                
                if(AtividadeTela.modeloInsert != null)
                {
                    AtividadeTela.modeloInsert.carregarLista(MaquinaLocalizador.buscaMaquinas(null, ""));
                }

                return true;
            } catch (SQLException e){
                throw new MyException( e );
            }
        }
        
        
    }
    
}