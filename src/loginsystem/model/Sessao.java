package loginsystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa a sessão de um usuário autenticado.
 *
 * O horário de acesso é capturado no momento da criação do objeto,
 * ou seja, logo após o login ser confirmado — por isso, se a
 * autenticação falhar, nenhuma sessão (nem horário) é registrada.
 */
public class Sessao {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final String usuario;
    private final LocalDateTime horarioAcesso;

    public Sessao(String usuario) {
        this.usuario = usuario;
        // Captura a data/hora do sistema no instante do login
        this.horarioAcesso = LocalDateTime.now();
    }

    public String getUsuario() {
        return usuario;
    }

    public LocalDateTime getHorarioAcesso() {
        return horarioAcesso;
    }

    /** Horário de acesso já formatado no padrão brasileiro. */
    public String getHorarioAcessoFormatado() {
        return horarioAcesso.format(FORMATO);
    }
}
