package loginsystem.model;

import java.time.LocalDateTime;

/** Usuário cadastrado no banco de dados (diferente de Sessao, que é um login em andamento). */
public class Usuario {

    private final int id;
    private final String usuario;
    private final String email;
    private final String senhaHash;
    private final LocalDateTime criadoEm;
    private final LocalDateTime ultimoAcesso;

    public Usuario(int id, String usuario, String email, String senhaHash,
                   LocalDateTime criadoEm, LocalDateTime ultimoAcesso) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.senhaHash = senhaHash;
        this.criadoEm = criadoEm;
        this.ultimoAcesso = ultimoAcesso;
    }

    public int getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getEmail() { return email; }
    public String getSenhaHash() { return senhaHash; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getUltimoAcesso() { return ultimoAcesso; }
}
