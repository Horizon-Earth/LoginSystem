package loginsystem.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Abre conexões com o MySQL, lendo resources/db.properties.
 * Pode ser sobrescrito por variáveis de ambiente:
 * LOGINSYSTEM_DB_URL, LOGINSYSTEM_DB_USER e LOGINSYSTEM_DB_PASSWORD.
 */
public final class ConexaoBD {

    private static final String ARQUIVO_CONFIG = "resources/db.properties";
    private static final String URL_PADRAO =
            "jdbc:mysql://localhost:3306/loginsystem"
            + "?serverTimezone=America/Fortaleza&connectTimeout=5000&socketTimeout=10000";

    private static Properties config;

    private ConexaoBD() {
    }

    /** Abre uma nova conexão. Quem chamar deve fechá-la (try-with-resources). */
    public static Connection obter() throws SQLException {
        Properties p = config();
        String url     = valor(p, "db.url", "LOGINSYSTEM_DB_URL", URL_PADRAO);
        String usuario = valor(p, "db.user", "LOGINSYSTEM_DB_USER", "root");
        String senha   = valor(p, "db.password", "LOGINSYSTEM_DB_PASSWORD", "");
        return DriverManager.getConnection(url, usuario, senha);
    }

    /** Testa se o banco está acessível (usado na inicialização). */
    public static boolean testar() {
        try (Connection c = obter()) {
            return c.isValid(3);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static synchronized Properties config() {
        if (config == null) {
            config = new Properties();
            try (FileInputStream fis = new FileInputStream(ARQUIVO_CONFIG)) {
                config.load(fis);
            } catch (IOException e) {
                System.err.println("Aviso: " + ARQUIVO_CONFIG
                        + " não encontrado. Usando valores padrão.");
            }
        }
        return config;
    }

    // Prioridade: variável de ambiente > db.properties > padrão
    private static String valor(Properties p, String chave, String variavel, String padrao) {
        String ambiente = System.getenv(variavel);
        if (ambiente != null && !ambiente.isBlank()) {
            return ambiente;
        }
        return p.getProperty(chave, padrao);
    }
}
