<div align="center">

# 🔐 LoginSystem

**Sistema de Login da startup Horizon Earth**

![Versão](https://img.shields.io/badge/vers%C3%A3o-v1.1.1-blue)
![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-2C5AA0)
![Licença](https://img.shields.io/badge/licen%C3%A7a-MIT-green)

Projeto Final de Programação Orientada a Objetos (POO) – 2026.2
Instituto Federal de Educação, Ciência e Tecnologia do Ceará – IFCE

</div>

---

## 📝 Sobre o projeto

O **LoginSystem** é o módulo de autenticação do **Horizon Earth**. Ele controla o acesso do usuário e, após o login, direciona para a tela de seleção dos demais módulos da aplicação.

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
| CaioStack | Full Stack | [CaioStack](https://github.com/CaioStack) |
| MiguelStack | Design | [MiguelStack](https://github.com/MiguelStack) |

## 🗂 Versões

| Versão | Descrição |
|---|---|
| **v1.1.1** | Ajustes visuais e de usabilidade na tela de login: novo tema escuro (azul-marinho com detalhes em ciano) definido em `resources/style.css`, janela mais compacta, campos que preenchem toda a caixa (sem cortar o texto digitado) e botão de mostrar/ocultar senha posicionado no final do campo, com ícone de olho aberto/fechado e animação de transição. |
| **v1.1.0** | Redesign completo da tela de login com a identidade visual do **Horizon Earth**: logo com globo, campos com rótulos e ícones, botão para mostrar/ocultar a senha, opção "Lembrar de mim" (apenas visual por enquanto), links "Esqueceu a senha?" e "Criar conta" (exibem orientações em caixas de diálogo) e barra de status com a versão do Java e do sistema. Estilos movidos para uma folha de estilos externa (`resources/style.css`). A janela agora é redimensionável, com tamanho mínimo definido. |
| **v1.0.0** | Primeira versão funcional. Autenticação com usuário e senha fixos (`root` / `toor`) na classe `Autenticacao`, mensagens de erro em caixas de diálogo (campos vazios e credenciais incorretas) e **Painel Principal** exibido após o login, com boas-vindas, data e hora da sessão e botão **Sair**, que retorna à tela de login. Tela de login redesenhada em formato de cartão, com subtítulo "Horizon Earth". Código separado em três classes. |
| **v0.2.0** | Melhorias visuais na tela de login (tema verde, fundo claro, botão **Entrar** estilizado e janela centralizada) e validação de campos vazios, com mensagem de erro exibida abaixo do botão. A senha não é mais exibida no console. A autenticação com `root`/`toor` ainda não foi implementada. |
| **v0.1.0** | Tela de login em JavaFX com título, campos de usuário e senha (com ícones) e botão **Entrar**. Os valores digitados eram apenas exibidos no console. |
| **v0.0.0** | Painel simples em branco e estrutura básica do projeto, com `.gitkeep` nas pastas "vazias". |

## 📂 Organização do projeto

```
LoginSystem/
├── README.md
├── LICENSE
├── .gitignore
├── src/
│   └── loginsystem/
│       ├── Main.java             # Tela de login (JavaFX)
│       ├── Autenticacao.java     # Lógica de autenticação
│       └── PainelPrincipal.java  # Painel exibido após o login
├── resources/
│   ├── icons/            # Ícones da interface (globo, pessoa, cadeado, olho, olho-fechado)
│   ├── images/           # Imagens da interface
│   └── style.css         # Folha de estilos da interface
├── docs/
│   ├── uml/              # Diagramas UML
│   ├── ui-ux/
│   │   ├── wireframes/   # Esboços das telas
│   │   ├── mockups/      # Aparência detalhada das telas
│   │   └── prototypes/   # Navegação entre telas
│   ├── diagrams/         # Fluxogramas e outros diagramas
│   └── presentations/    # Slides das apresentações
└── support/
    ├── documents/        # Materiais de consulta
    ├── videos/           # Links de videoaulas
    ├── tutorials/        # Tutoriais e passo a passo
    └── references/       # Documentações e referências
```

| Pasta | Finalidade |
|---|---|
| `src/` | Código-fonte da aplicação. |
| `resources/` | Recursos usados pela aplicação em execução (ícones, imagens e folha de estilos). |
| `docs/` | Documentação produzida pela própria equipe. |
| `support/` | Materiais externos usados como apoio no desenvolvimento. |

> O projeto não utiliza a pasta `database/` por enquanto. Ela será criada quando o banco de dados for adicionado.
>
> Os arquivos `.gitkeep` servem apenas para o Git versionar pastas vazias e podem ser removidos quando a pasta receber conteúdo.

## 🚀 Como executar

```bash
# 1. Clonar o repositório
git clone https://github.com/Horizon-Earth/LoginSystem.git

# 2. Importar o projeto no Eclipse
#    File > Import > Existing Projects into Workspace

# 3. Executar a classe principal
#    src/loginsystem/Main.java > Run As > Java Application

# 4. Entrar com as credenciais padrão
#    Usuário: root | Senha: toor
```

**Requisitos**
- JDK instalado e configurado no Eclipse.
- JavaFX SDK adicionado ao projeto (Build Path e argumentos de VM).

> Execute a aplicação a partir da **raiz do projeto**. Os ícones e a folha de estilos são carregados por caminhos relativos (`resources/icons/` e `resources/style.css`). Se os ícones não aparecerem, a aplicação continua funcionando normalmente. Verifique se a aplicação está sendo executada a partir da raiz do projeto e se os arquivos `globo.png`, `pessoa.png`, `cadeado.png`, `olho.png` e `olho-fechado.png` estão em `resources/icons/`. Se a tela aparecer sem estilo, confira se o arquivo `resources/style.css` existe.

## 🌿 Fluxo de trabalho

O desenvolvimento é feito com commits pequenos, frequentes e mensagens claras, seguindo o padrão **Conventional Commits** (`feat:`, `fix:`, `docs:`, `chore:`).

## 📄 Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE).

---

<div align="center">

**Horizon Earth** · IFCE · POO 2026.2

</div>