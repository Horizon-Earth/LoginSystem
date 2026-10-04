# 🔐 LoginSystem — Horizon Earth

Módulo de autenticação desenvolvido em JavaFX. Após um login válido, abre um painel com boas-vindas, data e hora da sessão e opção de sair.

## Funcionalidades atuais

- Entrada de usuário e senha.
- Validação de campos vazios e credenciais incorretas.
- Autenticação de demonstração com `root` / `toor`.

As credenciais são fixas no código. Cadastro, banco de dados e abertura dos módulos da agenda e do planeta são etapas futuras.

## Estrutura

| Caminho | Responsabilidade |
| --- | --- |
| `src/loginsystem/Main.java` | Tela de login e inicialização |
| `src/loginsystem/Autenticacao.java` | Validação das credenciais |
| `src/loginsystem/PainelPrincipal.java` | Painel da sessão |
| `resources/icons/` | Ícones da interface |
| `docs/` | Diagramas e documentação |
| `support/` | Materiais de apoio |

## Como executar

O repositório não contém `pom.xml`. Configure um projeto Java na sua IDE, use `src/` como pasta de fontes e adicione um JavaFX SDK compatível com o JDK escolhido.

1. Clone `https://github.com/Horizon-Earth/LoginSystem.git`.
2. Adicione os JARs de `javafx-sdk/lib` às bibliotecas do projeto.
3. Configure os argumentos da VM abaixo, substituindo o caminho pelo seu SDK.
4. Execute a classe `loginsystem.Main` com a pasta de trabalho na raiz do repositório.

```text
--module-path "/caminho/javafx-sdk/lib" --add-modules javafx.controls
```

Entre com usuário `root` e senha `toor`. Os ícones são carregados de `resources/icons/`, relativo à pasta de trabalho.

## Próximas etapas

Persistir usuários, substituir a autenticação fixa e conectar o painel aos módulos do Horizon Earth.

## Equipe

Projeto acadêmico de Programação Orientada a Objetos — IFCE, Campus Maranguape, 2026.2.

| Integrante | Área | GitHub |
| --- | --- | --- |
| CaioStack | Full Stack | [CaioStack](https://github.com/CaioStack) |
| BryanStack | Backend | [Bryan9895](https://github.com/Bryan9895) |
| MiguelStack | Design | [MiguelStack](https://github.com/MiguelStack) |

## Licença

Código e documentação próprios da Horizon Earth distribuídos sob a [licença MIT](LICENSE). Materiais externos, imagens, texturas e dados de APIs mantêm suas licenças e atribuições originais.
