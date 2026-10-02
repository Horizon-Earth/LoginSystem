package loginsystem;

/**
 * Classe responsável pela lógica de autenticação do sistema.
 * Credenciais padrão do projeto: root / toor
 */
public class Autenticacao {

    private static final String USUARIO_PADRAO = "root";
    private static final String SENHA_PADRAO   = "toor";

    // Construtor privado: impede que alguém instancie a classe
    // (ela só tem métodos estáticos)
    private Autenticacao() {
    }

    /**
     * Verifica se o usuário e a senha informados correspondem
     * às credenciais cadastradas no sistema.
     *
     * @param usuario nome de usuário digitado
     * @param senha   senha digitada
     * @return true se as credenciais estiverem corretas
     */
    public static boolean autenticar(String usuario, String senha) {
        return USUARIO_PADRAO.equals(usuario) && SENHA_PADRAO.equals(senha);
    }
}
