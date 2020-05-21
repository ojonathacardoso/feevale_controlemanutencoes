package Cadastros;

import Banco.Conexao;
import Banco.MyException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Executa as operações de inclusão, alteração e exclusão no banco para a classe ModeloMaquina
 * @author Jonatha
 */
public class ModeloMaquinaPersistor
{    
    /**
     * Insere no banco um ModeloMaquina
     * @param modelo ModeloMaquina - Objeto da classe ModeloMaquina
     * @return boolean
     * @throws MyException 
     */
    public static boolean insereModeloMaquina(ModeloMaquina modelo) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        sql.append( " INSERT INTO maquina_modelos ( " );
        sql.append( " marca, modelo, tipo, hd_padrao, ram_padrao, proc_padrao " ); 
        sql.append( " ) VALUES " );
        sql.append( " ( ?,?,?,?,?,? ) " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );

        try {
            statement.setString( 1, modelo.getMarca());
            statement.setString( 2, modelo.getModelo());
            statement.setInt( 3, modelo.getTipo() );
            statement.setInt( 4, modelo.getHdPadrao());
            statement.setInt( 5, modelo.getRamPadrao());
            statement.setString( 6, modelo.getProcPadrao());

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Modelo inserido com sucesso!");
            
            ModeloMaquinaLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            throw new MyException( e );
        }
        
    }
    
    /**
     * Altera um ModeloMaquina no banco
     * @param modelo ModeloMaquina - Objeto da classe ModeloMaquina
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraModeloMaquina(ModeloMaquina modelo) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE maquina_modelos SET " );
        sql.append( " marca = ?, " ); 
        sql.append( " modelo = ?, " ); 
        sql.append( " tipo = ?, " ); 
        sql.append( " hd_padrao = ?, " ); 
        sql.append( " ram_padrao = ?, " ); 
        sql.append( " proc_padrao = ? " ); 
        sql.append( " WHERE id_modelo = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setString( 1, modelo.getMarca());
            statement.setString( 2, modelo.getModelo());
            statement.setInt( 3, modelo.getTipo() );
            statement.setInt( 4, modelo.getHdPadrao());
            statement.setInt( 5, modelo.getRamPadrao());
            statement.setString( 6, modelo.getProcPadrao());
            
            statement.setInt( 7, modelo.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Modelo alterado com sucesso!");
            
            ModeloMaquinaLocalizador.carregaModelo();
            
            int opcao = JOptionPane.showConfirmDialog(
                        null,
                        "Deseja atualizar os dados de HD, RAM e processador para as máquinas baseadas neste modelo?", 
                        "Atualizar máquinas",
                        JOptionPane.YES_NO_OPTION);
                
            if(opcao == JOptionPane.YES_OPTION)
            {
                try {
                    alteraMaquinas(modelo);                  
                } catch (MyException ex) {
                    ex.printStackTrace();
                }
            }
  
            return true;
        } catch (SQLException e){
            throw new MyException( e );
        }
    }
    
     /**
     * Altera uma Maquina no banco
     * @param maquina Maquina - Objeto da classe Maquina
     * @return boolean
     * @throws MyException 
     */
    public static boolean alteraMaquinas(ModeloMaquina modelo) throws MyException
    {
        StringBuilder sql = new StringBuilder( 500 );
        
        sql.append( " UPDATE maquina SET " );
        sql.append( " hd = ?, " ); 
        sql.append( " ram = ?, " ); 
        sql.append( " proc = ? " ); 
        sql.append( " WHERE id_modelo = ? " );

        Conexao conexao = Conexao.getInstance();
        PreparedStatement statement = conexao.prepareStatement( sql.toString() );
        
        try {
            statement.setInt( 1, modelo.getHdPadrao() );
            statement.setInt( 2, modelo.getRamPadrao() );
            statement.setString( 3, modelo.getProcPadrao() );            
            statement.setInt( 4, modelo.getId() );

            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Máquinas alteradas com sucesso!");
            
            MaquinaLocalizador.carregaModelo();
            
            return true;
        } catch (SQLException e){
            throw new MyException( e );
        }
    }
    
    /**
     * Exclui um ModeloMaquina do banco
     * @param id int - Código do modelo
     * @return boolean
     * @throws MyException 
     */
    public static Boolean excluiModeloMaquina(int id) throws MyException
    {
        if(MaquinaLocalizador.buscaModeloMaquinaTotal(id) > 0)
        {
            JOptionPane.showMessageDialog(null, "Não é possível excluir! Existem máquinas cadastradas para este modelo!");
            
            return false;
        }
        else
        {
            StringBuilder sql = new StringBuilder( 50 );
            sql.append( "DELETE FROM maquina_modelos WHERE id_modelo = ?" ); 

            Conexao conexao = Conexao.getInstance();
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );

            try {
                statement.setInt( 1, id );
                statement.execute();

                JOptionPane.showMessageDialog(null, "Modelo excluído com sucesso!");

                ModeloMaquinaLocalizador.carregaModelo();

                return true;
            } catch (SQLException e){
                throw new MyException( e );
            }   
        }
    }
    
}