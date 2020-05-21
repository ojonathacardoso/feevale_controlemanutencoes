
package Principal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe que realiza a criptografia de um campo
 * @author Jonatha
 */
public class Criptografia {
 
    private static MessageDigest md = null;
 
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
 
    private static char[] hexCodes(byte[] text)
    {
        char[] hexOutput = new char[text.length * 2];
        String hexString;
 
        for (int i = 0; i < text.length; i++)
        {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,
                                	hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }
 
    /**
     * Realiza a criptografia de um texto usando MD5
     * @param texto String - Texto a ser criptografado
     * @return String
     */
    public static String criptografar(String texto)
    {
        if (md != null)
        {
            return new String(hexCodes(md.digest(texto.getBytes())));
        }
        return null;
    }
}