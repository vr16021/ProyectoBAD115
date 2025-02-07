
package com.proyecto.encuestas.Library;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;


public class Encrypt {
    // Definicion del tipo de algoritmo a utilizar (AES, DES, RSA)

    private final static String alg = "AES";
    // Definicion del modo de cifrado a utilizar
    private final static String cI = "AES/CBC/PKCS5Padding";
    private static String key = "92AE31A79FEEB2A3"; //llave
    private static String iv = "0123456789ABCDEF"; // vector de inicializacion

    public static String encrypt(String cleartext) throws Exception {
        //Esta clase proporciona la funcionalidad de un cifrado 
        //criptográfico para el cifrado y descifrado
        Cipher cipher = Cipher.getInstance(cI);
        //Esta clase especifica una clave secreta de forma independiente del proveedor.
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
        //Esta clase especifica un vector de inicialización (IV). Los ejemplos que usan 
        //IV son cifrados en modo de retroalimentación
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        return new String(encodeBase64(encrypted));
    }
    public static String decrypt(String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(cI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        byte[] enc = decodeBase64(encrypted);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
         byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }
}
