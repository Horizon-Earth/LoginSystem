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

---

<!-- estrutura-guia:inicio -->
## 🗂️ Organização do repositório

As pastas abaixo separam as responsabilidades do projeto. Abra uma delas para ver seus arquivos, suas subpastas e o estado do conteúdo.

| Pasta | O que você encontra |
| :--- | :--- |
| 📚 [docs/](docs/README.md) | Documentação produzida pela equipe sobre este projeto. Materiais externos de consulta pertencem a support/. |
| 🎨 [resources/](resources/README.md) | Recursos consumidos pela aplicação durante sua execução, como imagens, ícones, FXML e CSS. Documentos de consulta pertencem a support/. |
| 💻 [src/](src/README.md) | Código-fonte da aplicação, organizado em pacotes e classes. |
| 🧰 [support/](support/README.md) | Materiais externos e auxiliares usados como apoio ao desenvolvimento. |

### Arquivos da raiz

| Arquivo | Finalidade |
| :--- | :--- |
| [.gitignore](.gitignore) | Arquivos locais e gerados que o Git deve ignorar. |
| [LICENSE](LICENSE) | Condições de uso e distribuição sob a licença MIT. |
<!-- estrutura-guia:fim -->
