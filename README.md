<div align="center">

# 🔐 LoginSystem

**Sistema de Login da startup Horizon Earth**

![Versão](https://img.shields.io/badge/vers%C3%A3o-v0.0.0-blue)
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

**Objetivo inicial:** implementar um login simples com usuário e senha fixos:

| Campo | Valor |
|---|---|
| Usuário | `root` |
| Senha | `toor` |

**Visão futura:** substituir as credenciais fixas por autenticação com **banco de dados**.

## 🛠 Tecnologias

- Java
- JavaFX
- Git e GitHub
- Eclipse IDE

## 🚀 Como executar

```bash
# 1. Clonar o repositório
git clone https://github.com/Horizon-Earth/LoginSystem.git

# 2. Importar o projeto no Eclipse
#    File > Import > Existing Projects into Workspace

# 3. Executar a classe principal
```

> Requer JDK e JavaFX SDK configurados no Eclipse.

## 🗂 Versões

| Versão | Descrição |
|---|---|
| **v0.0.0** | Painel simples em branco e estrutura básica do projeto, com `.gitkeep` nas pastas "vazias". |

## 👥 Equipe

| Integrante | Função | GitHub |
|---|---|---|
| CaioStack | Full Stack | [@CaioStack](https://github.com/CaioStack) |
| MiguelStack | Design | [@MiguelStack](https://github.com/MiguelStack) |

## 📂 Organização do projeto

```
LoginSystem/
├── README.md
├── LICENSE
├── .gitignore
├── src/                  # Código-fonte Java
├── resources/
│   ├── icons/            # Ícones da interface
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

## 🌿 Fluxo de trabalho

O desenvolvimento é feito na branch `main`, com commits pequenos, frequentes e mensagens claras.

## 📄 Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE).

---

<div align="center">

**Horizon Earth** · IFCE · POO 2026.2

</div>