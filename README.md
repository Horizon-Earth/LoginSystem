<div align="center">

# 🔐 LoginSystem

**Sistema de Login da startup Horizon Earth**

![Versão](https://img.shields.io/badge/vers%C3%A3o-v1.4.0-blue)
![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-2C5AA0)
![Licença](https://img.shields.io/badge/licen%C3%A7a-MIT-green)

Projeto Final de Programação Orientada a Objetos (POO) – 2026.2  
Instituto Federal de Educação, Ciência e Tecnologia do Ceará – IFCE

</div>

---

## 📝 Sobre o projeto

O **LoginSystem** é o módulo de autenticação do **Horizon Earth**. Ele controla o acesso do usuário e, após o login, direciona para o painel principal da aplicação.

A partir da versão **v1.4.0**, o código está organizado em pacotes separados por responsabilidade: autenticação, modelo de sessão, interface e utilitários. O painel principal recebe os dados da sessão autenticada e apresenta o nome do usuário e o horário de acesso.

## 🎯 Objetivos

**Objetivo inicial (concluído na v1.0.0 ✅):** implementar um login simples com usuário e senha fixos:

| Campo | Valor |
|---|---|
| Usuário | `root` |
| Senha | `toor` |

**Próxima etapa:** substituir as credenciais fixas por autenticação com **banco de dados**.

## 🛠 Tecnologias

- Java
- JavaFX
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
| **v1.4.0** | Organização do código em pacotes por responsabilidade: `loginsystem.auth`, `loginsystem.model`, `loginsystem.ui` e `loginsystem.util`. A autenticação fica isolada em `Autenticacao.java`, e os dados da sessão são representados por `Sessao.java`. O `PainelPrincipal` recebe a sessão autenticada e exibe o nome do usuário e o horário de acesso formatado. O botão **Sair** fecha o painel e retorna à tela de login. Foram centralizados os alertas de erro, informação e recurso indisponível em `Alertas.java`, e o carregamento de imagens e ícones em `Recursos.java`, com tratamento para arquivos não encontrados. A classe `Main.java` permanece responsável por iniciar a aplicação JavaFX. |
| **v1.3.0** | Nova tela de login em formato de **card central flutuante** sobre fundo acinzentado, com tema escuro em tons de roxo. À esquerda do card, a imagem da marca (`resources/images/logo.png`) com o nome **Horizon Earth**, o slogan e indicadores decorativos de carrossel; à direita, o formulário de acesso com botão **Entrar no Sistema** e botão de mostrar/ocultar senha com animação. Inclui o link "Cadastre-se" e os botões "Google" e "Apple", que ainda não estão implementados e exibem a mensagem de recurso indisponível. Janela com tamanho mínimo definido. |
| **v1.2.1** | Nova tela de login em **layout dividido**: à esquerda, o mapa do planeta (`resources/images/mapa.png`) com o nome **Horizon Earth** e o slogan "Monitoramento de clima e riscos do planeta."; à direita, o formulário de acesso (usuário, senha e botão **Entrar**) com rodapé do projeto. Interface simplificada: foram removidos o botão de mostrar/ocultar senha, a opção "Lembrar de mim", os links de apoio e a barra de status. Janela maior (1200x750), com tamanho mínimo definido. |
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
├── src/
│   └── loginsystem/
│       ├── Main.java
│       ├── auth/
│       │   └── Autenticacao.java
│       ├── model/
│       │   └── Sessao.java
│       ├── ui/
│       │   ├── TelaLogin.java
│       │   └── PainelPrincipal.java
│       └── util/
│           ├── Alertas.java
│           └── Recursos.java
├── resources/
│   ├── icons/
│   │   ├── globo.png
│   │   ├── olho.png
│   │   └── olho-fechado.png
│   ├── images/
│   │   └── logo.png
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
| `src/loginsystem/Main.java` | Inicializa a aplicação JavaFX e abre a tela de login. |
| `src/loginsystem/auth/` | Contém a lógica de autenticação. |
| `src/loginsystem/model/` | Contém o modelo de dados da sessão. |
| `src/loginsystem/ui/` | Contém as telas da aplicação. |
| `src/loginsystem/util/` | Reúne utilitários para alertas e carregamento de recursos visuais. |
| `resources/` | Recursos usados pela interface, como ícones, imagens e folha de estilos. |
| `docs/` | Documentação produzida pela equipe. |
| `support/` | Materiais externos usados como apoio no desenvolvimento. |

> O projeto não utiliza a pasta `database/` por enquanto. Ela será criada quando o banco de dados for adicionado.
>
> Os arquivos `.gitkeep` servem apenas para o Git versionar pastas vazias e podem ser removidos quando a pasta receber conteúdo.

## 🚀 Como executar

```bash
# 1. Clonar o repositório
git clone https://github.com/Horizon-Earth/LoginSystem.git

# 2. Importar o projeto no Eclipse
# File > Import > Existing Projects into Workspace

# 3. Executar a classe principal
# src/loginsystem/Main.java > Run As > Java Application

# 4. Entrar com as credenciais padrão
# Usuário: root | Senha: toor
```

### Requisitos

- JDK instalado e configurado no Eclipse.
- JavaFX SDK adicionado ao projeto (Build Path e argumentos de VM).

> Execute a aplicação a partir da **raiz do projeto**. Os ícones, as imagens e a folha de estilos podem ser carregados por caminhos relativos, como `resources/icons/`, `resources/images/` e `resources/style.css`. Se algum recurso visual não for encontrado, a aplicação possui métodos auxiliares que retornam um elemento visual vazio ou nulo, conforme o tipo de recurso. Confira se os arquivos esperados existem e se a aplicação está sendo executada a partir do diretório correto.

## 🌿 Fluxo de trabalho

O desenvolvimento é feito com commits pequenos, frequentes e mensagens claras, seguindo o padrão **Conventional Commits** (`feat:`, `fix:`, `docs:`, `chore:`).

## 📄 Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE).

---

<div align="center">

**Horizon Earth** · IFCE · POO 2026.2

</div>
