package loginsystem.ui;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loginsystem.auth.Autenticacao;
import loginsystem.auth.CadastroException;
import loginsystem.util.Alertas;

/**
 * Tela de cadastro de novos usuários (mesmo visual da TelaLogin).
 * Só conclui se o usuário e o e-mail ainda não existirem no banco.
 */
public class TelaCadastro {

    private final Stage palco;

    public TelaCadastro(Stage palco) {
        this.palco = palco;
    }

    public void mostrar() {

        Label titulo = new Label("Criar conta");
        titulo.getStyleClass().add("titulo-card");

        Label pergunta = new Label("Já tem uma conta? ");
        pergunta.getStyleClass().add("texto-card");

        Hyperlink linkLogin = new Hyperlink("Entrar");
        linkLogin.getStyleClass().add("link-card");
        linkLogin.setOnAction(e -> new TelaLogin(palco).mostrar());

        HBox linhaLogin = new HBox(pergunta, linkLogin);
        linhaLogin.setAlignment(Pos.CENTER_LEFT);

        // ----- Usuário -----
        Label labelUsuario = new Label("Usuário");
        labelUsuario.getStyleClass().add("label-card");
        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Escolha um usuário");
        campoUsuario.getStyleClass().add("campo-card");

        // ----- E-mail -----
        Label labelEmail = new Label("E-mail");
        labelEmail.getStyleClass().add("label-card");
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("seu@email.com");
        campoEmail.getStyleClass().add("campo-card");

        // ----- Senha e confirmação -----
        Label labelSenha = new Label("Senha");
        labelSenha.getStyleClass().add("label-card");
        CampoSenha campoSenha = new CampoSenha("Mínimo de 6 caracteres");

        Label labelConfirmar = new Label("Confirmar senha");
        labelConfirmar.getStyleClass().add("label-card");
        CampoSenha campoConfirmar = new CampoSenha("Repita a senha");

        // ----- Botão Cadastrar -----
        Button botaoCadastrar = new Button("Criar conta");
        botaoCadastrar.setMaxWidth(Double.MAX_VALUE);
        botaoCadastrar.setPrefHeight(48);
        botaoCadastrar.getStyleClass().add("botao-principal");
        botaoCadastrar.setOnAction(e -> {
            String usuario = campoUsuario.getText().trim();
            String email = campoEmail.getText().trim();
            String senha = campoSenha.getTexto();
            String confirmacao = campoConfirmar.getTexto();

            if (usuario.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmacao.isEmpty()) {
                Alertas.mostrarErro("Campos vazios", "Preencha todos os campos.");
                return;
            }

            botaoCadastrar.setDisable(true);
            botaoCadastrar.setText("Cadastrando...");

            // Validação + banco + hash rodam em segundo plano
            Task<Void> tarefa = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Autenticacao.cadastrar(usuario, email, senha, confirmacao);
                    return null;
                }
            };

            tarefa.setOnSucceeded(ev -> {
                Alertas.mostrarInfo("Cadastro realizado",
                        "Conta criada com sucesso! Faça login para continuar.");
                new TelaLogin(palco).mostrar();
            });

            tarefa.setOnFailed(ev -> {
                botaoCadastrar.setDisable(false);
                botaoCadastrar.setText("Criar conta");
                Throwable erro = tarefa.getException();
                if (erro instanceof CadastroException) {
                    Alertas.mostrarErro("Não foi possível cadastrar", erro.getMessage());
                } else {
                    erro.printStackTrace();
                    Alertas.mostrarErroBanco();
                }
            });

            Thread thread = new Thread(tarefa);
            thread.setDaemon(true);
            thread.start();
        });

        // Enter vai pulando de campo; Enter na confirmação cadastra
        campoUsuario.setOnAction(e -> campoEmail.requestFocus());
        campoEmail.setOnAction(e -> campoSenha.focar());
        campoSenha.aoConfirmar(campoConfirmar::focar);
        campoConfirmar.aoConfirmar(botaoCadastrar::fire);

        // ==================== MONTAGEM DO CARD ====================
        VBox formulario = new VBox(12,
            titulo, linhaLogin,
            new VBox(6, labelUsuario, campoUsuario),
            new VBox(6, labelEmail, campoEmail),
            new VBox(6, labelSenha, campoSenha),
            new VBox(6, labelConfirmar, campoConfirmar),
            botaoCadastrar
        );
        formulario.setAlignment(Pos.CENTER_LEFT);
        formulario.setPadding(new Insets(36, 48, 32, 48));
        formulario.setPrefWidth(460);
        formulario.getStyleClass().add("painel-formulario");

        Scene cena = PainelMarca.criarCena(formulario);
        PainelMarca.exibir(palco, cena);
    }
}
