package com.infosaudepro.exemplo.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

/**
 * Classe utilitária para aplicar criptografia AES a dados sensíveis.
 */
public class CriptografiaUtil {

    // ➡️ CORREÇÃO CRÍTICA: Nome completo do algoritmo para evitar erros JCE
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    // 🔑 CHAVE DE 16 BYTES (128 bits): Usada para criptografia simétrica.
    private static final byte[] KEY = "infosaude-chave!".getBytes(StandardCharsets.UTF_8);

    /**
     * Criptografa um valor de texto usando AES.
     * Método 'static' para ser chamado diretamente pela classe.
     */
    public static String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedValue = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    /**
     * Descriptografa um valor cifrado de volta para texto simples.
     * Método 'static' para ser chamado diretamente pela classe.
     */
    public static String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedByteValue = Base64.getDecoder().decode(encryptedValue);
        byte[] decryptedValue = cipher.doFinal(decryptedByteValue);
        return new String(decryptedValue, StandardCharsets.UTF_8);
    }
}