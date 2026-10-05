<div align="center">

# 🔐 LoginSystem

**Sistema de Login da startup Horizon Earth**

![Versão](https://img.shields.io/badge/vers%C3%A3o-v1.0.0-blue)
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
│   ├── icons/            # Ícones da interface (pessoa.png, cadeado.png)
│   └── images/           # Imagens da interface
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
| `resources/` | Recursos usados pela aplicação em execução (ícones e imagens). |
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

> Execute a aplicação a partir da **raiz do projeto**. Os ícones são carregados pelo caminho relativo `resources/icons/`. Se os ícones não aparecerem, a aplicação continua funcionando normalmente. Verifique se a aplicação está sendo executada a partir da raiz do projeto e se os arquivos `pessoa.png` e `cadeado.png` estão em `resources/icons/`.

## 🌿 Fluxo de trabalho

O desenvolvimento é feito com commits pequenos, frequentes e mensagens claras, seguindo o padrão **Conventional Commits** (`feat:`, `fix:`, `docs:`, `chore:`).

## 📄 Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE).

---

<div align="center">

**Horizon Earth** · IFCE · POO 2026.2

</div>

<!-- estrutura-guia:inicio -->
## 📂 Estrutura de arquivos

Os caminhos abaixo descrevem as pastas deste repositório. Cada pasta possui um README com finalidade e estado do conteúdo.

| Pasta | Finalidade |
| --- | --- |
| `docs/` | Documentação produzida pela equipe sobre este projeto. Materiais externos de consulta pertencem a support/. |
| `docs/diagrams/` | Fluxogramas e diagramas de arquitetura; UML fica em docs/uml/ e modelagem de banco em database/. |
| `docs/presentations/` | Slides e materiais de apresentação e demonstração do projeto. |
| `docs/ui-ux/` | Planejamento das interfaces e da experiência do usuário. |
| `docs/ui-ux/mockups/` | Representações visuais detalhadas da aparência das telas. |
| `docs/ui-ux/prototypes/` | Protótipos e registros da navegação e interação entre telas. |
| `docs/ui-ux/wireframes/` | Esboços da disposição dos componentes e da estrutura das telas. |
| `docs/uml/` | Diagramas UML de classes, casos de uso, sequência e atividades. |
| `resources/` | Recursos consumidos pela aplicação durante sua execução, como imagens, ícones, FXML e CSS. Documentos de consulta pertencem a support/. |
| `resources/icons/` | Ícones utilizados nas interfaces e nas janelas da aplicação. |
| `resources/images/` | Imagens e texturas utilizadas na interface ou na cena 3D. |
| `src/` | Código-fonte da aplicação, organizado em pacotes e classes. |
| `src/loginsystem/` | Organização dos pacotes e arquivos do projeto. |
| `support/` | Materiais externos e auxiliares usados como apoio ao desenvolvimento. |
| `support/documents/` | Guias da disciplina, apostilas, artigos e manuais utilizados para consulta. |
| `support/references/` | Links de documentações oficiais, exemplos e outras referências técnicas consultadas. |
| `support/tutorials/` | Tutoriais e passos de instalação, configuração e execução das tecnologias do projeto. |
| `support/videos/` | Links de vídeos e videoaulas usados como apoio. Registre título e URL no README, evitando arquivos de vídeo grandes. |

Arquivos da raiz: `.gitignore`, `LICENSE`, `README.md`.
<!-- estrutura-guia:fim -->
