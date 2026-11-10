package loginsystem.database;

import loginsystem.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/** DAO da tabela "usuarios". Todo o SQL fica aqui, sempre com PreparedStatement. */
public class UsuarioDAO {

    private static final String SQL_EXISTE_USUARIO =
            "SELECT 1 FROM usuarios WHERE usuario = ? LIMIT 1";
    private static final String SQL_EXISTE_EMAIL =
            "SELECT 1 FROM usuarios WHERE email = ? LIMIT 1";
    private static final String SQL_BUSCAR_BASE =
            "SELECT id, usuario, email, senha_hash, criado_em, ultimo_acesso "
            + "FROM usuarios";
    private static final String SQL_BUSCAR_USUARIO =
            SQL_BUSCAR_BASE + " WHERE usuario = ?";
    private static final String SQL_BUSCAR_EMAIL =
            SQL_BUSCAR_BASE + " WHERE email = ?";
    private static final String SQL_BUSCAR_GOOGLE_ID =
            SQL_BUSCAR_BASE + " WHERE google_id = ?";
    private static final String SQL_INSERIR =
            "INSERT INTO usuarios (usuario, email, senha_hash) VALUES (?, ?, ?)";
    private static final String SQL_INSERIR_GOOGLE =
            "INSERT INTO usuarios (usuario, email, senha_hash, provedor, google_id, foto_url) "
            + "VALUES (?, ?, NULL, 'google', ?, ?)";
    private static final String SQL_ULTIMO_ACESSO =
            "UPDATE usuarios SET ultimo_acesso = NOW() WHERE id = ?";
    private static final String SQL_TEM_SENHA =
            "SELECT 1 FROM usuarios WHERE id = ? AND senha_hash IS NOT NULL LIMIT 1";

    public boolean existeUsuario(String usuario) throws SQLException {
        return existe(SQL_EXISTE_USUARIO, usuario);
    }

    public boolean existeEmail(String email) throws SQLException {
        return existe(SQL_EXISTE_EMAIL, email);
    }

    /** Retorna o usuário ou null se não existir. */
    public Usuario buscarPorUsuario(String usuario) throws SQLException {
        return buscar(SQL_BUSCAR_USUARIO, usuario);
    }

    /** Retorna o usuário por e-mail, ou null se não existir. */
    public Usuario buscarPorEmail(String email) throws SQLException {
        return buscar(SQL_BUSCAR_EMAIL, email);
    }

    /** Retorna o usuário pelo ID da conta do Google, ou null se não existir. */
    public Usuario buscarPorGoogleId(String googleId) throws SQLException {
        return buscar(SQL_BUSCAR_GOOGLE_ID, googleId);
    }

    /**
     * Cadastra um novo usuário vindo do Google (sem senha).
     * Retorna o usuário já com o id gerado pelo banco.
     */
    public Usuario inserirGoogle(String usuario, String email, String googleId, String fotoUrl)
            throws SQLException {
        try (Connection c = ConexaoBD.obter();
             PreparedStatement ps = c.prepareStatement(SQL_INSERIR_GOOGLE)) {
            ps.setString(1, usuario);
            ps.setString(2, email);
            ps.setString(3, googleId);
            ps.setString(4, fotoUrl);
            ps.executeUpdate();
        }
        return buscarPorGoogleId(googleId);
    }

    /** Cadastra um novo usuário com senha (login tradicional). */
    public void inserir(String usuario, String email, String senhaHash) throws SQLException {
        try (Connection c = ConexaoBD.obter();
             PreparedStatement ps = c.prepareStatement(SQL_INSERIR)) {
            ps.setString(1, usuario);
            ps.setString(2, email);
            ps.setString(3, senhaHash);
            ps.executeUpdate();
        }
    }

    /** Diz se o usuário já tem senha cadastrada (conta criada pelo login tradicional). */
    public boolean temSenha(int id) throws SQLException {
        return existe(SQL_TEM_SENHA, String.valueOf(id));
    }

    public void atualizarUltimoAcesso(int id) throws SQLException {
        try (Connection c = ConexaoBD.obter();
             PreparedStatement ps = c.prepareStatement(SQL_ULTIMO_ACESSO)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Usuario buscar(String sql, String valor) throws SQLException {
        try (Connection c = ConexaoBD.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Timestamp ultimo = rs.getTimestamp("ultimo_acesso");
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("email"),
                        rs.getString("senha_hash"),
                        rs.getTimestamp("criado_em").toLocalDateTime(),
                        ultimo == null ? null : ultimo.toLocalDateTime());
            }
        }
    }

    private boolean existe(String sql, String valor) throws SQLException {
        try (Connection c = ConexaoBD.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
