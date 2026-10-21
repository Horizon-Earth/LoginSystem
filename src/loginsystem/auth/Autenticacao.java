package loginsystem.auth;

import loginsystem.database.UsuarioDAO;
import loginsystem.model.Usuario;
import loginsystem.util.Senhas;
import loginsystem.util.Validador;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Lógica de autenticação e cadastro, usando o MySQL.
 * - autenticar: só aceita usuários que JÁ estão cadastrados.
 * - cadastrar: só aceita usuários que AINDA NÃO estão cadastrados.
 * Acessam o banco, então devem rodar fora da thread da interface.
 */
public class Autenticacao {

    private static final UsuarioDAO DAO = new UsuarioDAO();

    private Autenticacao() {
    }

    /** Retorna o Usuario autenticado, ou null se usuário/senha estiverem errados. */
    public static Usuario autenticar(String usuario, String senha) throws SQLException {
        Usuario encontrado = DAO.buscarPorUsuario(usuario);

        if (encontrado == null) {
            // Gasta o mesmo tempo de um login real, para não revelar se o usuário existe
            Senhas.simularVerificacao(senha);
            return null;
        }
        if (!Senhas.verificar(senha, encontrado.getSenhaHash())) {
            return null;
        }

        DAO.atualizarUltimoAcesso(encontrado.getId());
        return encontrado;
    }

    /** Cadastra um novo usuário (valida os dados e checa duplicidade). */
    public static void cadastrar(String usuario, String email, String senha, String confirmacao)
            throws CadastroException, SQLException {

        usuario = usuario == null ? "" : usuario.trim();
        email = email == null ? "" : email.trim().toLowerCase();

        String erro = Validador.validarUsuario(usuario);
        if (erro == null) erro = Validador.validarEmail(email);
        if (erro == null) erro = Validador.validarSenha(senha);
        if (erro != null) {
            throw new CadastroException(erro);
        }
        if (!senha.equals(confirmacao)) {
            throw new CadastroException("As senhas não coincidem.");
        }

        if (DAO.existeUsuario(usuario)) {
            throw new CadastroException("Este nome de usuário já está em uso.");
        }
        if (DAO.existeEmail(email)) {
            throw new CadastroException("Este e-mail já está cadastrado.");
        }

        try {
            DAO.inserir(usuario, email, Senhas.gerarHash(senha));
        } catch (SQLIntegrityConstraintViolationException e) {
            // Garantia final: a restrição UNIQUE do banco
            throw new CadastroException("Usuário ou e-mail já cadastrado.");
        }
    }
}
