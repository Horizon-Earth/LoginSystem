package loginsystem.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Hash de senhas com sal usando PBKDF2-HMAC-SHA256 (já vem no Java).
 * Formato salvo no banco:  pbkdf2$iterações$salBase64$hashBase64
 */
public final class Senhas {

    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final String PREFIXO = "pbkdf2";
    private static final int ITERACOES = 600_000;
    private static final int TAMANHO_SAL = 16;      // bytes
    private static final int TAMANHO_HASH = 256;    // bits
    private static final SecureRandom ALEATORIO = new SecureRandom();

    private Senhas() {
    }

    /** Gera o hash (com sal novo) para guardar no banco. */
    public static String gerarHash(String senha) {
        byte[] sal = new byte[TAMANHO_SAL];
        ALEATORIO.nextBytes(sal);
        byte[] hash = derivar(senha, sal, ITERACOES, TAMANHO_HASH);
        Base64.Encoder b64 = Base64.getEncoder();
        return PREFIXO + "$" + ITERACOES + "$" + b64.encodeToString(sal)
                + "$" + b64.encodeToString(hash);
    }

    /** Confere se a senha digitada corresponde ao hash guardado. */
    public static boolean verificar(String senha, String armazenado) {
        try {
            String[] partes = armazenado.split("\\$");
            if (partes.length != 4 || !PREFIXO.equals(partes[0])) {
                return false;
            }
            int iteracoes = Integer.parseInt(partes[1]);
            byte[] sal = Base64.getDecoder().decode(partes[2]);
            byte[] esperado = Base64.getDecoder().decode(partes[3]);
            byte[] calculado = derivar(senha, sal, iteracoes, esperado.length * 8);
            // Comparação em tempo constante
            return MessageDigest.isEqual(esperado, calculado);
        } catch (RuntimeException e) {
            return false;
        }
    }

    /** Gasta o mesmo tempo de uma verificação real (usuário inexistente). */
    public static void simularVerificacao(String senha) {
        derivar(senha, new byte[TAMANHO_SAL], ITERACOES, TAMANHO_HASH);
    }

    private static byte[] derivar(String senha, byte[] sal, int iteracoes, int bits) {
        PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), sal, iteracoes, bits);
        try {
            return SecretKeyFactory.getInstance(ALGORITMO).generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Algoritmo de hash indisponível", e);
        } finally {
            spec.clearPassword();
        }
    }
}
