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
    private static final String SQL_BUSCAR =
            "SELECT id, usuario, email, senha_hash, criado_em, ultimo_acesso "
            + "FROM usuarios WHERE usuario = ?";
    private static final String SQL_INSERIR =
            "INSERT INTO usuarios (usuario, email, senha_hash) VALUES (?, ?, ?)";
    private static final String SQL_ULTIMO_ACESSO =
            "UPDATE usuarios SET ultimo_acesso = NOW() WHERE id = ?";

    public boolean existeUsuario(String usuario) throws SQLException {
        return existe(SQL_EXISTE_USUARIO, usuario);
    }

    public boolean existeEmail(String email) throws SQLException {
        return existe(SQL_EXISTE_EMAIL, email);
    }

    /** Retorna o usuário ou null se não existir. */
    public Usuario buscarPorUsuario(String usuario) throws SQLException {
        try (Connection c = ConexaoBD.obter();
             PreparedStatement ps = c.prepareStatement(SQL_BUSCAR)) {
            ps.setString(1, usuario);
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

    /** Lança SQLIntegrityConstraintViolationException se usuário ou e-mail já existirem. */
    public void inserir(String usuario, String email, String senhaHash) throws SQLException {
        try (Connection c = ConexaoBD.obter();
             PreparedStatement ps = c.prepareStatement(SQL_INSERIR)) {
            ps.setString(1, usuario);
            ps.setString(2, email);
            ps.setString(3, senhaHash);
            ps.executeUpdate();
        }
    }

    public void atualizarUltimoAcesso(int id) throws SQLException {
        try (Connection c = ConexaoBD.obter();
             PreparedStatement ps = c.prepareStatement(SQL_ULTIMO_ACESSO)) {
            ps.setInt(1, id);
            ps.executeUpdate();
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
