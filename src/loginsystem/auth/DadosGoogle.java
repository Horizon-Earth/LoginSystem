package loginsystem.auth;

/**
 * Dados de uma conta do Google obtidos após o login.
 *
 * O id é o campo "sub" retornado pela API oficial do Google.
 */
public final class DadosGoogle {

    public final String id;      // identificador da conta no Google (sub)
    public final String email;   // e-mail da conta
    public final String nome;    // nome da conta (pode ser null)
    public final String fotoUrl; // URL da foto de perfil (pode ser null)

    public DadosGoogle(String id, String email, String nome, String fotoUrl) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.fotoUrl = fotoUrl;
    }
}
