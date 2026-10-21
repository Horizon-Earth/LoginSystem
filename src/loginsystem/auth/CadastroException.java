package loginsystem.auth;

/** Erro de regra de negócio no cadastro (dados inválidos, usuário já existente...). */
public class CadastroException extends Exception {

    public CadastroException(String mensagem) {
        super(mensagem);
    }
}
