package Cadastros;

import Banco.Conexao;
import Banco.MyException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Executa as operações de busca no banco para a classe ModeloMaquina
 * @author Jonatha
 */
public class ModeloMaquinaLocalizador 
{
    private static String[][] telaSQL;
    private static DefaultComboBoxModel modelo = new DefaultComboBoxModel();
    
    /**
     * Retorna um modelo de máquina conforme o código pesquisado
     * @param id int - Código do modelo de máquina
     * @return ModeloMaquina
     * @throws MyException 
     */
    public static ModeloMaquina buscaModeloMaquina( int id ) throws MyException
    {
        String sql = " SELECT * FROM maquina_modelos WHERE id_modelo = ? ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setInt( 1, id );
            ResultSet resultado = statement.executeQuery();
            
            if( resultado.next() )
            {
                ModeloMaquina modeloM = new ModeloMaquina();
                
                modeloM.setId( resultado.getInt( "id_modelo" ) ); 
                
                modeloM.setMarca(resultado.getString( "marca" ) ); 
                modeloM.setModelo(resultado.getString( "modelo" ) ); 
                modeloM.setTipo(resultado.getInt( "tipo" ) ); 
                modeloM.setHdPadrao(resultado.getInt( "hd_padrao" ) ); 
                modeloM.setRamPadrao(resultado.getInt( "ram_padrao" ) ); 
                modeloM.setProcPadrao(resultado.getString( "proc_padrao" ) ); 

                return modeloM;
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
     * Retorna todos os modelos de máquina, conforme condições e ordem definidas
     * @param condicoes ArrayList - Lista de condições
     * @param ordem String - Campo usado na ordenação
     * @return ArrayList
     * @throws MyException 
     */
    public static ArrayList<ModeloMaquina> buscaModelosMaquinas( ArrayList<String> condicoes, String ordem ) throws MyException
    {
        StringBuilder sql = new StringBuilder(" SELECT * FROM maquina_modelos ");
        
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
            
            ArrayList<ModeloMaquina> modelos = new ArrayList<ModeloMaquina>();
            
            while( resultado.next() )
            {
                ModeloMaquina modeloM = new ModeloMaquina();
                
                modeloM.setId( resultado.getInt( "id_modelo" ) ); 
                
                modeloM.setMarca(resultado.getString( "marca" ) ); 
                modeloM.setModelo(resultado.getString( "modelo" ) ); 
                modeloM.setTipo(resultado.getInt( "tipo" ) ); 
                modeloM.setHdPadrao(resultado.getInt( "hd_padrao" ) ); 
                modeloM.setRamPadrao(resultado.getInt( "ram_padrao" ) ); 
                modeloM.setProcPadrao(resultado.getString( "proc_padrao" ) ); 

                modelos.add(modeloM);
            }
            
            return modelos;
            
        } catch ( MyException | SQLException e ) {
            throw new MyException(e);
        }
    }

    /**
     * Retorna o nome do modelo de máquina conforme o código pesquisado
     * @param id int - Código do modelo de máquina
     * @return String
     * @throws MyException 
     */
    public static String buscaModeloMaquinaNome( int id ) throws MyException
    {
        String sql = " SELECT CONCAT(marca,' ',modelo) AS nome FROM maquina_modelos WHERE id_modelo = ? ";
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
    
    /**
     * Retorna a lista de máquinas
     * @return String[]
     * @throws MyException 
     */
    public static ArrayList<ModeloMaquina> buscaModeloMaquinaLista() throws MyException
    {
        String sql = " SELECT id_modelo, marca, modelo FROM maquina_modelos ";
        Conexao conexao = Conexao.getInstance();
        
        try {
            
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();
            
            ArrayList<ModeloMaquina> modelos = new ArrayList<ModeloMaquina>();
            
            ModeloMaquina inicial = new ModeloMaquina();
            inicial.setId(0);             
            inicial.setMarca( "" ); 
            inicial.setModelo( "" ); 
            
            modelos.add(inicial);
            
            while( resultado.next() )
            {
                ModeloMaquina modeloM = new ModeloMaquina();
                
                modeloM.setId( resultado.getInt( "id_modelo" ) );                 
                modeloM.setMarca( resultado.getString( "marca" ) ); 
                modeloM.setModelo( resultado.getString( "modelo" ) ); 

                modelos.add(modeloM);
            }
            
            return modelos;
            
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
        if(modelo.getSize() > 0)
        {
            modelo.removeAllElements(); 
        }
        
        ArrayList opcoes = buscaModeloMaquinaLista();
        
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
            if(id == ((ModeloMaquina) modelo.getElementAt(x)).getId())
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
        telaSQL = new String[7][2];
        
        telaSQL[0][0] = "id_modelo";
        telaSQL[0][1] = "Código";
        telaSQL[1][0] = "marca";
        telaSQL[1][1] = "Marca";
        telaSQL[2][0] = "modelo";
        telaSQL[2][1] = "Modelo";
        telaSQL[3][0] = "tipo";
        telaSQL[3][1] = "Tipo";
        telaSQL[4][0] = "hd_padrao";
        telaSQL[4][1] = "HD";
        telaSQL[5][0] = "ram_padrao";
        telaSQL[5][1] = "Memória RAM";
        telaSQL[6][0] = "proc_padrao";
        telaSQL[6][1] = "Processador";
        
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
        
        return telaSQL.length;
    }
    
}
