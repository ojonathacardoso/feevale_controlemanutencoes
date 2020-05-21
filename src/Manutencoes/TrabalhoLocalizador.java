package Manutencoes;

import Banco.Conexao;
import Banco.MyException;
import Principal.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Executa as operações de busca no banco para a classe Trabalho
 * @author Jonatha
 */
public class TrabalhoLocalizador 
{
    private static String[][] telaSQL;
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    private static DefaultComboBoxModel modeloProgramados = new DefaultComboBoxModel();
    
    /**
     * Retorna um trabalho conforme o código pesquisado
     * @param id int - Código do trabalho
     * @return Trabalho
     * @throws MyException 
     */
    public static Trabalho buscaTrabalho( int id ) throws MyException
    {
        String sql = " SELECT * FROM trabalho WHERE id_trabalho = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                Trabalho trabalho = new Trabalho();
                
                trabalho.setId( resultado.getInt( "id_trabalho" ) ); 
                
                trabalho.setDescricao( resultado.getString( "descricao" ) ); 
                trabalho.setProgramado( resultado.getBoolean( "programado" ) ); 
                trabalho.setConcluido( resultado.getBoolean( "concluido" ) ); 
                
                if ( resultado.getString( "data_inicial" ) != null )
                    trabalho.setDataInicial( Data.convertSQLData( resultado.getString( "data_inicial" ) ) );
                else
                    trabalho.setDataInicial(null);
                
                if ( resultado.getString( "data_final" ) != null )
                    trabalho.setDataFinal( Data.convertSQLData( resultado.getString( "data_final" ) ) );
                else
                    trabalho.setDataFinal(null);

                return trabalho;
            }
            else
            {
                return null;
            }
        } catch ( MyException | SQLException | ParseException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * Retorna todos os trabalhos, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<Trabalho> buscaTrabalhos(ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM trabalho ");
        
        for(int x = 0; x < condicoes.size(); x++)
        {
            if (x == 0)
                sql.append(" WHERE ");
            else
                sql.append(" AND ");
            
            sql.append(condicoes.get(x));
        }
        
        if(! ordem.equals(""))
        {
            sql.append(" ORDER BY ");
            sql.append(ordem);
        }
        
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement( sql.toString() );
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Trabalho> trabalhos = new ArrayList<Trabalho>();
            
            while( resultado.next() )
            {
                Trabalho trabalho = new Trabalho();
                
                trabalho.setId( resultado.getInt( "id_trabalho" ) ); 
                
                trabalho.setDescricao(resultado.getString("descricao" ) ); 
                trabalho.setProgramado(resultado.getBoolean("programado" ) ); 
                trabalho.setConcluido(resultado.getBoolean( "concluido" ) ); 
                
                if ( resultado.getString( "data_inicial" ) != null )
                    trabalho.setDataInicial( Data.convertSQLData( resultado.getString( "data_inicial" ) ) );
                else
                    trabalho.setDataInicial(null);
                
                if ( resultado.getString( "data_final" ) != null )
                    trabalho.setDataFinal( Data.convertSQLData( resultado.getString( "data_final" ) ) );
                else
                    trabalho.setDataFinal(null);

                trabalhos.add(trabalho);
            }
            
            return trabalhos;
        } catch ( MyException | SQLException | ParseException e ) {
            throw new MyException(e);
        }
    }

    /**
     * Retorna a descrição do trabalho conforme o código pesquisado
     * @param id int - Código do trabalho
     * @return String
     * @throws MyException 
     */
    public static String buscaTrabalhoDescricao( int id ) throws MyException
    {
        String sql = " SELECT descricao FROM trabalho WHERE id_trabalho = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getString( "descricao" ) ;
            }
            else
            {
                return null;
            }
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * Retorna a lista de localizações para uma combobox
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<Trabalho> buscaTrabalhoLista(boolean programado) throws MyException, ParseException
    {
        StringBuilder sql = new StringBuilder(" SELECT id_trabalho, descricao, data_inicial, data_final FROM trabalho WHERE concluido = 0 " );
        Conexao conexao = Conexao.getInstance();
        
        if(programado)
        {
            sql.append(" AND programado = 1 ");
        }
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Trabalho> trabalhos = new ArrayList<Trabalho>();
            
            Trabalho inicial = new Trabalho();
            inicial.setId(0);             
            inicial.setDescricao( "" ); 
            inicial.setDataFinal(null);
            inicial.setDataInicial(null);
            
            trabalhos.add(inicial);
            
            while( resultado.next() )
            {
                Trabalho trabalho = new Trabalho();
                
                trabalho.setId( resultado.getInt( "id_trabalho" ) );                 
                trabalho.setDescricao( resultado.getString( "descricao" ) ); 
                
                if ( resultado.getString( "data_inicial" ) != null )
                    trabalho.setDataInicial( Data.convertSQLData( resultado.getString( "data_inicial" ) ) );
                else
                    trabalho.setDataInicial(null);

                if ( resultado.getString( "data_final" ) != null )
                    trabalho.setDataFinal( Data.convertSQLData( resultado.getString( "data_final" ) ) );
                else
                    trabalho.setDataFinal(null);

                trabalhos.add(trabalho);
            }
            
            return trabalhos;
            
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * 
     * @throws MyException 
     */
    public static void carregaModelo(boolean programado) throws MyException, ParseException
    {
        if(programado)
        {
            modeloProgramados.removeAllElements();
        
            ArrayList opcoes = buscaTrabalhoLista(true);

            for (Object opcao : opcoes)
            {
                modeloProgramados.addElement(opcao);
            }
        }
        else
        {
            modelo.removeAllElements();
        
            ArrayList opcoes = buscaTrabalhoLista(false);

            for (Object opcao : opcoes)
            {
                modelo.addElement(opcao);
            }
        }
        
    }
    
    public static int getIndexModelo(boolean programado, int id)
    {
        if(programado)
        {
            for(int x = 0; x < modeloProgramados.getSize(); x++)
            {
                if(id == ((Trabalho) modeloProgramados.getElementAt(x)).getId())
                {
                    return x;
                }
            }
        }
        else
        {
            for(int x = 0; x < modelo.getSize(); x++)
            {
                if(id == ((Trabalho) modelo.getElementAt(x)).getId())
                {
                    return x;
                }
            }
        }
        
        return 0;
    }
    
    /**
     * 
     * @return DefaultComboBoxModel
     * @throws MyException 
     * @throws java.text.ParseException 
     */
    public static DefaultComboBoxModel getModelo(boolean programado) throws MyException, ParseException
    {
        carregaModelo(programado);
        
        if(programado)
            return modeloProgramados;
        else
            return modelo;
    }
    
    /**
     * Popula o vetor com as combinações entre colunas no banco e na tela
     */
    private static void criaConversaoTelaSQL()
    {
        telaSQL = new String[6][2];
        
        telaSQL[0][0] = "id_trabalho";
        telaSQL[0][1] = "Código";
        telaSQL[1][0] = "descricao";
        telaSQL[1][1] = "Descrição";
        telaSQL[2][0] = "programado";
        telaSQL[2][1] = "Programado";
        telaSQL[3][0] = "concluido";
        telaSQL[3][1] = "Concluído";
        telaSQL[4][0] = "data_inicial";
        telaSQL[4][1] = "Data inicial";
        telaSQL[5][0] = "data_final";
        telaSQL[5][1] = "Data final";
    }
    
    /**
     * Retorna o nome do campo no banco de dados conforme informado o nome exibido na tela
     * @param nomeTela String - Nome do campo exibido na tela
     * @return String
     */
    public static String getConversaoSQL(String nomeTela)
    {
        if(telaSQL == null)
        {
            criaConversaoTelaSQL();
        }
        
        for (String[] telaSQL1 : telaSQL)
        {
            if (nomeTela.equals(telaSQL1[1])) {
                return telaSQL1[0];
            }
        }
        
        return null;
    }
    
    /**
     * 
     * @param indice
     * @return String
     */
    public static String getNomeTela(int indice)
    {
        if(telaSQL == null)
        {
            criaConversaoTelaSQL();
        }
        
        return telaSQL[indice][1];
    }
    
    /**
     * 
     * @return int
     */
    public static int getTotalColunas()
    {
        if(telaSQL == null)
        {
            criaConversaoTelaSQL();
        }
        
        return telaSQL.length;
    }
    
}
