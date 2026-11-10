<div align="center">

# 🔐 LoginSystem

**Sistema de Login da startup Horizon Earth**

![Versão](https://img.shields.io/badge/vers%C3%A3o-v3.0.0-blue)
![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-2C5AA0)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)
![Licença](https://img.shields.io/badge/licen%C3%A7a-MIT-green)

Projeto Final de Programação Orientada a Objetos (POO) – 2026.2  
Instituto Federal de Educação, Ciência e Tecnologia do Ceará – IFCE

</div>

---

## 📝 Sobre o projeto

O **LoginSystem** é o módulo de autenticação do **Horizon Earth**. Ele controla o acesso do usuário e, após o login, direciona para o painel principal da aplicação.

## 🎯 Objetivos

**Objetivo inicial (concluído na v1.0.0 ✅):** implementar um login simples com usuário e senha fixos (`root` / `toor`). Essas credenciais fixas foram usadas da v1.0.0 até a v1.4.0.

**Objetivo seguinte (concluído na v2.0.0 ✅):** substituir as credenciais fixas por autenticação com **banco de dados MySQL**, com cadastro de usuários e senhas protegidas por hash.

**Objetivo atual (concluído na v3.0.0 ✅):** permitir a autenticação com uma conta **Google** por OAuth 2.0, criando a conta local vinculada ao Google na primeira entrada.

**Pendências:** login com Apple (o botão ainda exibe a mensagem de recurso indisponível).

## 🛠 Tecnologias

- Java
- JavaFX
- MySQL 8+ e JDBC (MySQL Connector/J)
- Google OAuth 2.0 com PKCE
- Git e GitHub
- Eclipse IDE

## 👥 Equipe

| Integrante | Função | GitHub |
|---|---|---|
| CaioStack | Full Stack | [@CaioStack](https://github.com/CaioStack) |
| MiguelStack | Design | [@MiguelStack](https://github.com/MiguelStack) |

## 🗂 Versões

| Versão | Descrição |
|---|---|
| **v3.0.0** | Login com o **Google** via OAuth 2.0, PKCE, navegador externo e callback local. Na primeira entrada, o sistema cria uma conta vinculada ao identificador Google; nas demais, autentica a conta já vinculada. O banco passa a armazenar `provedor`, `google_id` e `foto_url`, com `senha_hash` nulo para contas Google. A configuração local exige Client ID e Client Secret de uma credencial OAuth do tipo Desktop. O botão Apple continua indisponível. |
| **v2.0.0** | Integração com **banco de dados MySQL**. O login deixa de usar credenciais fixas (`root` / `toor`) e passa a consultar a tabela `usuarios`: só entra quem já está cadastrado. Nova tela de **cadastro** (`TelaCadastro.java`), aberta pelo link "Cadastre-se", que só conclui se o usuário e o e-mail ainda não existirem. As senhas são guardadas com hash e sal (PBKDF2-HMAC-SHA256) em `Senhas.java`, e os dados do cadastro são validados em `Validador.java`. Novo pacote `loginsystem.database`, com `ConexaoBD.java` (conexão configurada em `resources/db.properties`, que não vai para o GitHub) e `UsuarioDAO.java` (todo o SQL, com `PreparedStatement`). Novos `Usuario.java` (modelo) e `CadastroException.java`. As consultas ao banco rodam em segundo plano, sem travar a janela, e o aplicativo avisa se o MySQL estiver inacessível. Componentes visuais reaproveitados em `CampoSenha.java` e `PainelMarca.java`. Script de criação do banco em `database/schema.sql`. Os botões Google e Apple continuam indisponíveis. |
| **v1.4.0** | Organização do código em pacotes por responsabilidade: `loginsystem.auth`, `loginsystem.model`, `loginsystem.ui` e `loginsystem.util`. A autenticação fica isolada em `Autenticacao.java`, e os dados da sessão são representados por `Sessao.java`. O `PainelPrincipal` recebe a sessão autenticada e exibe o nome do usuário e o horário de acesso formatado. O botão **Sair** fecha o painel e retorna à tela de login. Foram centralizados os alertas de erro, informação e recurso indisponível em `Alertas.java`, e o carregamento de imagens e ícones em `Recursos.java`, com tratamento para arquivos não encontrados. A classe `Main.java` permanece responsável por iniciar a aplicação JavaFX. |
| **v1.3.0** | Nova tela de login em formato de **card central flutuante** sobre fundo acinzentado, com tema escuro em tons de roxo. À esquerda do card, a imagem da marca (`resources/images/logo.png`) com o nome **Horizon Earth**, o slogan e indicadores decorativos de carrossel; à direita, o formulário de acesso com botão **Entrar no Sistema** e botão de mostrar/ocultar senha com animação. Inclui o link "Cadastre-se" e os botões "Google" e "Apple", que ainda não estão implementados e exibem a mensagem de recurso indisponível. Janela com tamanho mínimo definido. |
| **v1.2.0** | Nova tela de login em **layout dividido**: à esquerda, o mapa do planeta (`resources/images/mapa.png`) com o nome **Horizon Earth** e o slogan "Monitoramento de clima e riscos do planeta."; à direita, o formulário de acesso (usuário, senha e botão **Entrar**) com rodapé do projeto. Interface simplificada: foram removidos o botão de mostrar/ocultar senha, a opção "Lembrar de mim", os links de apoio e a barra de status. Janela maior (1200x750), com tamanho mínimo definido. |
| **v1.1.1** | Ajustes visuais e de usabilidade na tela de login: novo tema escuro (azul-marinho com detalhes em ciano) definido em `resources/style.css`, janela mais compacta, campos que preenchem toda a caixa (sem cortar o texto digitado) e botão de mostrar/ocultar senha posicionado no final do campo, com ícone de olho aberto/fechado e animação de transição. |
| **v1.1.0** | Redesign completo da tela de login com a identidade visual do **Horizon Earth**: logo com globo, campos com rótulos e ícones, botão para mostrar/ocultar a senha, opção "Lembrar de mim" (apenas visual por enquanto), links "Esqueceu a senha?" e "Criar conta" (exibem orientações em caixas de diálogo) e barra de status com a versão do Java e do sistema. Estilos movidos para uma folha de estilos externa (`resources/style.css`). A janela agora é redimensionável, com tamanho mínimo definido. |
| **v1.0.0** | Primeira versão funcional. Autenticação com usuário e senha fixos (`root` / `toor`) na classe `Autenticacao`, mensagens de erro em caixas de diálogo (campos vazios e credenciais incorretas) e **Painel Principal** exibido após o login, com boas-vindas, data e hora da sessão e botão **Sair**, que retorna à tela de login. Tela de login redesenhada em formato de cartão, com subtítulo "Horizon Earth". Código separado em três classes. |
| **v0.2.0** | Melhorias visuais na tela de login (tema verde, fundo claro, botão **Entrar** estilizado e janela centralizada) e validação de campos vazios, com mensagem de erro exibida abaixo do botão. A senha não é mais exibida no console. A autenticação com `root`/`toor` ainda não foi implementada. |
| **v0.1.0** | Tela de login em JavaFX com título, campos de usuário e senha (com ícones) e botão **Entrar**. Os valores digitados eram apenas exibidos no console. |
| **v0.0.0** | Painel simples em branco e estrutura básica do projeto, com `.gitkeep` nas pastas vazias. |

## 📂 Organização do projeto

```text
LoginSystem/
├── README.md
├── LICENSE
├── .gitignore
├── lib/
│   └── mysql-connector-j-X.X.X.jar
├── database/
│   ├── schema.sql
│   └── migrar-v2-0-para-v2-1-google.sql
├── src/
│   └── loginsystem/
│       ├── Main.java
│       ├── auth/
│       │   ├── Autenticacao.java
│       │   ├── AutenticacaoGoogle.java
│       │   ├── CadastroException.java
│       │   └── DadosGoogle.java
│       ├── database/
│       │   ├── ConexaoBD.java
│       │   └── UsuarioDAO.java
│       ├── model/
│       │   ├── Sessao.java
│       │   └── Usuario.java
│       ├── ui/
│       │   ├── TelaLogin.java
│       │   ├── TelaCadastro.java
│       │   ├── CampoSenha.java
│       │   ├── PainelMarca.java
│       │   └── PainelPrincipal.java
│       └── util/
│           ├── Alertas.java
│           ├── Recursos.java
│           ├── Senhas.java
│           └── Validador.java
├── resources/
│   ├── icons/
│   │   ├── globo.png
│   │   ├── olho.png
│   │   └── olho-fechado.png
│   ├── images/
│   │   └── logo.png
│   ├── db.properties.example
│   ├── google.properties.example
│   └── style.css
├── docs/
│   ├── uml/
│   ├── ui-ux/
│   │   ├── wireframes/
│   │   ├── mockups/
│   │   └── prototypes/
│   ├── diagrams/
│   └── presentations/
└── support/
    ├── documents/
    ├── videos/
    ├── tutorials/
    └── references/
```

| Pasta/arquivo | Finalidade |
|---|---|
| `src/loginsystem/Main.java` | Inicializa a aplicação JavaFX, abre a tela de login e testa a conexão com o banco. |
| `src/loginsystem/auth/` | Contém a autenticação local, o fluxo OAuth 2.0 do Google e o cadastro. |
| `src/loginsystem/database/` | Contém o código de acesso ao MySQL (conexão e consultas). |
| `src/loginsystem/model/` | Contém os modelos de dados: usuário cadastrado e sessão. |
| `src/loginsystem/ui/` | Contém as telas da aplicação (login, cadastro e painel) e componentes visuais. |
| `src/loginsystem/util/` | Reúne utilitários para alertas, recursos visuais, hash de senhas e validação. |
| `database/` | Script de criação do banco (`schema.sql`) e migração de bancos v2.0.0 para v3.0.0. |
| `lib/` | Bibliotecas externas, como o driver JDBC do MySQL (Connector/J). |
| `resources/` | Recursos visuais, folha de estilos e modelos de configuração do banco e do Google (`*.properties.example`). |
| `docs/` | Documentação produzida pela equipe. |
| `support/` | Materiais externos usados como apoio no desenvolvimento. |

> Os arquivos `.gitkeep` servem apenas para o Git versionar pastas vazias e podem ser removidos quando a pasta receber conteúdo.

## 🚀 Como executar

### 1. Clonar e importar

```bash
# Clonar o repositório
git clone https://github.com/Horizon-Earth/LoginSystem.git

# Importar o projeto no Eclipse
# File > Import > Existing Projects into Workspace
```

### 2. Criar o banco de dados

Com o MySQL 8+ instalado e em execução, crie o banco e a tabela:

```bash
# Linux (Ubuntu): o root do MySQL entra via sudo
sudo mysql < database/schema.sql

# Windows / macOS
mysql -u root -p < database/schema.sql
```

Depois, crie um usuário próprio para o sistema (troque a senha) e libere as permissões:

```sql
CREATE USER 'loginsystem'@'localhost' IDENTIFIED BY 'SUA_SENHA';
GRANT SELECT, INSERT, UPDATE ON loginsystem.usuarios TO 'loginsystem'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar a conexão

Copie `resources/db.properties.example` para `resources/db.properties` e preencha o usuário e a senha do banco. Esse arquivo está no `.gitignore` e **nunca deve ser enviado ao GitHub**.

### 4. Adicionar o driver JDBC

Baixe o **MySQL Connector/J** (*Platform Independent*) em [dev.mysql.com/downloads/connector/j](https://dev.mysql.com/downloads/connector/j/), coloque o `.jar` na pasta `lib/` e, no Eclipse, clique com o botão direito no arquivo > **Build Path > Add to Build Path**.

### 5. Executar

```bash
# src/loginsystem/Main.java > Run As > Java Application
```

Na primeira vez, crie uma conta em **Cadastre-se** para entrar com usuário e senha ou use
**Google** para criar automaticamente uma conta vinculada à sua conta Google.

### Requisitos

- JDK instalado e configurado no Eclipse.
- JavaFX SDK adicionado ao projeto (Build Path e argumentos de VM).
- MySQL 8+ em execução e MySQL Connector/J no Build Path.

> Execute a aplicação a partir da **raiz do projeto**. Os ícones, as imagens, a folha de estilos e o arquivo `db.properties` são carregados por caminhos relativos, como `resources/icons/`, `resources/images/`, `resources/style.css` e `resources/db.properties`. Se algum recurso visual não for encontrado, a aplicação possui métodos auxiliares que retornam um elemento visual vazio ou nulo, conforme o tipo de recurso. Confira se os arquivos esperados existem e se a aplicação está sendo executada a partir do diretório correto.

## 🔑 Login com o Google

O botão **Google** na tela de login usa OAuth 2.0 real. Configure as credenciais abaixo antes de usá-lo.

O login não tem modo de demonstração: sem validar a identidade no Google, não é seguro aceitar um e-mail digitado pelo usuário.

1. No [Google Cloud Console](https://console.cloud.google.com), configure a tela de
   consentimento e crie uma credencial OAuth 2.0 do tipo **Aplicativo para computador
   (Desktop)**. Se a tela de consentimento estiver em teste, inclua as contas permitidas em
   **Test users**.
2. Copie `resources/google.properties.example` para `resources/google.properties` e informe
   `google.client.id` e `google.client.secret` da mesma credencial Desktop.
3. Ao clicar em Google, o app abre o navegador padrão. A autenticação usa OAuth 2.0 com PKCE, state anti-CSRF e um callback loopback temporário em porta local dinâmica.
4. Após o consentimento, o app consulta o perfil da conta Google e só aceita e-mails
   verificados. A primeira entrada cria a conta local; as próximas usam o `google_id`
   armazenado no banco.

O arquivo `resources/google.properties` é local e está no `.gitignore`; não envie o Client
Secret para o GitHub.

Contas do Google ficam armazenadas na tabela `usuarios` com `provedor = 'google'`, sem
`senha_hash`. Se um e-mail já estiver cadastrado com senha, o login com Google **não mistura
as contas** e avisa para entrar com usuário e senha.

Bancos criados na v2.0.0 precisam do script `database/migrar-v2-0-para-v2-1-google.sql` (executado com privilégios de `ALTER`, ex.: `root`) antes de usar o login com Google.

## 🔒 Segurança

- As senhas **nunca** são guardadas em texto puro: usam hash PBKDF2-HMAC-SHA256 com sal aleatório por usuário.
- Todas as consultas usam `PreparedStatement`, o que protege contra SQL Injection.
- A mensagem de falha no login é genérica ("Usuário ou senha incorretos"), sem revelar se o usuário existe.
- As credenciais do banco ficam em `resources/db.properties`, fora do controle de versão.
- As credenciais do Google ficam em `resources/google.properties`, fora do controle de versão.
- O login com Google não mistura contas: um e-mail já cadastrado com senha segue acessando apenas pelo login tradicional.

## 🌿 Fluxo de trabalho

O desenvolvimento é feito com commits pequenos, frequentes e mensagens claras, seguindo o padrão **Conventional Commits** (`feat:`, `fix:`, `docs:`, `chore:`).

## 📄 Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE).

---

<div align="center">

**Horizon Earth** · IFCE · POO 2026.2

</div>
