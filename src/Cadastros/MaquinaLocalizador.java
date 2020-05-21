package Cadastros;

import Banco.Conexao;
import Banco.MyException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Executa as operações de busca no banco para a classe Maquina
 * @author Jonatha
 */
public class MaquinaLocalizador 
{
    private static String[][] telaSQL;
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    
    /**
     * Retorna uma máquina conforme o código pesquisado
     * @param id int - Código da máquina
     * @return Maquina
     * @throws MyException 
     */
    public static Maquina buscaMaquina( int id ) throws MyException
    {
        String sql = " SELECT * FROM maquina WHERE id_maquina = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                Maquina maquina = new Maquina();
                
                maquina.setId( resultado.getInt( "id_maquina" ) ); 
                
                maquina.setModeloMaquina(resultado.getInt( "id_modelo" ) ); 
                maquina.setNome(resultado.getString( "nome_maquina" ) ); 
                maquina.setUsuario(resultado.getString( "nome_usuario" ) );
                maquina.setLocalizacao(resultado.getInt( "id_localizacao" ) );
                maquina.setSistema(resultado.getString( "sistema" ) );
                maquina.setHd(resultado.getInt( "hd" ) ); 
                maquina.setRam(resultado.getInt( "ram" ) ); 
                maquina.setProc(resultado.getString( "proc" ) );      
                maquina.setObservacao(resultado.getString( "observacao" ) );

                return maquina;
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
     * Retorna todas as máquinas, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<Maquina> buscaMaquinas( ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM maquina ");
        
        if(condicoes != null)
        {
            for(int x = 0; x < condicoes.size(); x++)
            {
                if (x == 0)
                    sql.append(" WHERE ");
                else
                    sql.append(" AND ");

                sql.append(condicoes.get(x));
            }   
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
            
            ArrayList<Maquina> maquinas = new ArrayList<Maquina>();
            
            while( resultado.next() )
            {
                Maquina maquina = new Maquina();
                
                maquina.setId( resultado.getInt( "id_maquina" ) ); 
                
                maquina.setModeloMaquina(resultado.getInt( "id_modelo" ) ); 
                maquina.setNome(resultado.getString( "nome_maquina" ) ); 
                maquina.setUsuario(resultado.getString( "nome_usuario" ) );
                maquina.setLocalizacao(resultado.getInt( "id_localizacao" ) );
                maquina.setSistema(resultado.getString( "sistema" ) );
                maquina.setHd(resultado.getInt( "hd" ) ); 
                maquina.setRam(resultado.getInt( "ram" ) ); 
                maquina.setProc(resultado.getString( "proc" ) );      
                maquina.setObservacao(resultado.getString( "observacao" ) );

                maquinas.add(maquina);
            }
            
            return maquinas;
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
    }
    
    public static ArrayList<Maquina> buscaMaquinasAtividades() throws MyException
    {
        String sql = " SELECT * FROM maquina ";
            
        Conexao conexao = Conexao.getInstance();

        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Maquina> maquinas = new ArrayList<Maquina>();
            
            while( resultado.next() )
            {
                Maquina maquina = new Maquina();
                
                maquina.setId( resultado.getInt( "id_maquina" ) );                 
                maquina.setNome(resultado.getString( "nome_maquina" ) ); 
                maquina.setUsuario(resultado.getString( "nome_usuario" ) );
                maquina.setLocalizacao(resultado.getInt( "id_localizacao" ) );

                maquinas.add(maquina);
            }
            
            return maquinas;
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
    }
    
    /**
     * Retorna o nome da máquina conforme o código pesquisado
     * @param id int - Código da máquina
     * @return String
     * @throws MyException 
     */
    public static String buscaMaquinaNome( int id ) throws MyException
    {
        String sql = " SELECT nome_maquina AS nome FROM maquina WHERE id_maquina = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getString( "nome" ) ;
            }
            else
            {
                return null;
            }
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static int buscaMaquinaId( String nome ) throws MyException
    {
        String sql = " SELECT id_maquina AS id FROM maquina WHERE nome_maquina = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString( 1, nome );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getInt( "id" ) ;
            }
            else
            {
                return 0;
            }
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static int buscaMaquinaLocalizacaoTotal( int localizacao ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM maquina WHERE id_localizacao = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, localizacao );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getInt( "total" );
            }
            else
            {
                return 0;
            }
        } catch ( SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    public static int buscaModeloMaquinaTotal( int modelo ) throws MyException
    {
        String sql = " SELECT COUNT(*) AS total FROM maquina WHERE id_modelo = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, modelo );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                return resultado.getInt( "total" );
            }
            else
            {
                return 0;
            }
        } catch ( SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * Retorna a lista de máquinas
     * @return String[]
     * @throws MyException 
     */
    public static ArrayList<Maquina> buscaMaquinaLista() throws MyException
    {
        String sql = " SELECT id_maquina, nome_maquina FROM maquina ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<Maquina> maquinas = new ArrayList<Maquina>();
            
            Maquina inicial = new Maquina();
            inicial.setId(0);             
            inicial.setNome( "" ); 
            
            maquinas.add(inicial);
            
            while( resultado.next() )
            {
                Maquina maquina = new Maquina();
                
                maquina.setId( resultado.getInt( "id_maquina" ) );                 
                maquina.setNome( resultado.getString("nome_maquina" ) ); 

                maquinas.add(maquina);
            }
            
            return maquinas;
            
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
        
    }
    
    /**
     * 
     * @throws MyException 
     */
    public static void carregaModelo() throws MyException
    {
        modelo.removeAllElements();
        
        ArrayList opcoes = buscaMaquinaLista();
        
        for (Object opcao : opcoes)
        {
            modelo.addElement(opcao);
        }
    }
    
    /**
     * 
     * @return DefaultComboBoxModel
     * @throws MyException 
     */
    public static DefaultComboBoxModel getModelo() throws MyException
    {
        carregaModelo();
        
        return modelo;
    }

    public static int getIndexModelo(int id)
    {
        for(int x = 0; x < modelo.getSize(); x++)
        {
            if(id == ((Maquina) modelo.getElementAt(x)).getId())
            {
                return x;
            }
        }
        return 0;
    }
    
    /**
     * Popula o vetor com as combinações entre colunas no banco e na tela
     */
    private static void criaConversaoTelaSQL()
    {
        telaSQL = new String[10][2];
        
        telaSQL[0][0] = "id_maquina";
        telaSQL[0][1] = "Código";
        telaSQL[1][0] = "id_modelo";
        telaSQL[1][1] = "Modelo";
        telaSQL[2][0] = "nome_maquina";
        telaSQL[2][1] = "Nome";
        telaSQL[3][0] = "nome_usuario";
        telaSQL[3][1] = "Usuário";
        telaSQL[4][0] = "id_localizacao";
        telaSQL[4][1] = "Localização";
        telaSQL[5][0] = "sistema";
        telaSQL[5][1] = "Sistema";
        telaSQL[6][0] = "hd";
        telaSQL[6][1] = "HD";
        telaSQL[7][0] = "ram";
        telaSQL[7][1] = "Memória RAM";
        telaSQL[8][0] = "proc";
        telaSQL[8][1] = "Processador";
        telaSQL[9][0] = "observacao";
        telaSQL[9][1] = "Observação";
        
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
        
        for(int x = 0; x < telaSQL.length; x++)
        {
            if(nomeTela.equals(telaSQL[x][1]))
                return telaSQL[x][0];
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
        
        return telaSQL.length-1; 
    }
    
}