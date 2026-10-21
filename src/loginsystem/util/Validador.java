package loginsystem.util;

import java.util.regex.Pattern;

/** Validação dos dados do cadastro. Retorna a mensagem de erro, ou null se estiver tudo certo. */
public final class Validador {

    public static final int SENHA_MINIMA = 6;
    public static final int SENHA_MAXIMA = 64;

    private static final Pattern USUARIO =
            Pattern.compile("^[A-Za-z0-9._]{3,30}$");
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private Validador() {
    }

    public static String validarUsuario(String usuario) {
        if (usuario == null || !USUARIO.matcher(usuario).matches()) {
            return "O usuário deve ter de 3 a 30 caracteres, usando apenas "
                    + "letras, números, ponto (.) ou underline (_).";
        }
        return null;
    }

    public static String validarEmail(String email) {
        if (email == null || email.length() > 120 || !EMAIL.matcher(email).matches()) {
            return "Informe um e-mail válido.";
        }
        return null;
    }

    public static String validarSenha(String senha) {
        if (senha == null || senha.length() < SENHA_MINIMA || senha.length() > SENHA_MAXIMA) {
            return "A senha deve ter entre " + SENHA_MINIMA + " e " + SENHA_MAXIMA + " caracteres.";
        }
        return null;
    }
}
