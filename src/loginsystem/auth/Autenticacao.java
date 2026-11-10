package loginsystem.auth;

import loginsystem.database.UsuarioDAO;
import loginsystem.model.Usuario;
import loginsystem.util.Senhas;
import loginsystem.util.Validador;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Lógica de autenticação e cadastro, usando o MySQL.
 * - autenticar: só aceita usuários que JÁ estão cadastrados (usuário + senha).
 * - cadastrar: só aceita usuários que AINDA NÃO estão cadastrados.
 * - entrarComGoogle: entra ou cria a conta a partir de uma conta do Google.
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

    /**
     * Entra com uma conta do Google, criando-a se ainda não existir.
     * - Se o ID do Google já estiver no banco, entra nessa conta.
     * - Se o e-mail já estiver cadastrado com senha, não mistura as contas
     *   (avisa para entrar com usuário e senha).
     * - Caso contrário, cria uma nova conta com provedor "google", sem senha.
     *
     * @return o Usuario logado, ou null se o fluxo for cancelado.
     * @throws Exception se houver falha no banco ou nos dados do Google.
     */
    public static Usuario entrarComGoogle(DadosGoogle dados) throws Exception {
        if (dados == null || dados.email == null || dados.email.isBlank()) {
            return null;
        }

        String email = dados.email.trim().toLowerCase();

        // Já tem conta com esse ID do Google? Entra direto.
        Usuario peloGoogle = DAO.buscarPorGoogleId(dados.id);
        if (peloGoogle != null) {
            DAO.atualizarUltimoAcesso(peloGoogle.getId());
            return peloGoogle;
        }

        // O e-mail já é de uma conta com senha? Não mistura: avisa para entrar pelo jeito tradicional.
        Usuario peloEmail = DAO.buscarPorEmail(email);
        if (peloEmail != null && DAO.temSenha(peloEmail.getId())) {
            throw new Exception("Este e-mail já está cadastrado com senha. "
                    + "Entre com usuário e senha na tela de login.");
        }

        // Cria a conta com os dados do Google (sem senha).
        String usuario = novoNomeDeUsuario(email);
        Usuario novo = DAO.inserirGoogle(usuario, email, dados.id, dados.fotoUrl);
        return novo;
    }

    /** Deriva um nome de usuário disponível a partir do e-mail da conta do Google. */
    private static String novoNomeDeUsuario(String email) throws SQLException {
        String base = (email.indexOf('@') > 0 ? email.substring(0, email.indexOf('@')) : "usuario")
                .toLowerCase(java.util.Locale.ROOT)
                .replaceAll("[^a-z0-9._]", "_")
                .replaceAll("^[._]+|[._]+$", "");
        if (base.length() < 3) {
            base = "google" + base;
        }
        if (base.length() > 30) {
            base = base.substring(0, 30);
        }
        base = base.replaceAll("[._]+$", "");
        if (base.length() < 3) {
            base = "usuario";
        }

        String candidato = base;
        int sufixo = 1;
        while (DAO.existeUsuario(candidato)) {
            String sufixoStr = String.valueOf(sufixo);
            int limite = 30 - sufixoStr.length();
            candidato = (limite > 0 ? base.substring(0, Math.min(base.length(), limite)) : "")
                    + sufixoStr;
            sufixo++;
        }
        return candidato;
    }
}
