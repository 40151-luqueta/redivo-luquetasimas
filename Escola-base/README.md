# Reserva de Salas da Escola: API em MVC

**Grupo:** 

**Integrantes:**

1. Gabriel Simioni
2. Lucas Oliveira


## Como rodar o projeto

O projeto já vem pronto: Spring Boot, Spring Web, Spring Data JPA, validação e banco H2 em memória. Ele sobe sem nenhuma classe sua, e quando sobe com sucesso a API fica em `http://localhost:8080`.

Escolha um jeito:

**Pelo terminal, dentro desta pasta (não precisa ter o Maven instalado)**

No Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux ou Mac:

```bash
./mvnw spring-boot:run
```

**Pela IDE (IntelliJ, Eclipse, VS Code)**

Abra esta pasta como projeto Maven e execute a classe `EscolaApplication`.

Para parar, use `Ctrl+C` no terminal (ou o botão de parar da IDE).

> **Atenção.** Na primeira vez, o Maven baixa as dependências e isso leva alguns minutos, então precisa de internet. Rodem uma vez antes de começar a prova, para não perder tempo.

## Onde ficam as classes

A classe principal, `EscolaApplication`, está em `src/main/java/escola`. As pastas das camadas (pacotes) **não vêm prontas: vocês criam**, uma para cada camada, dentro de `src/main/java/escola`. Elas precisam ficar dentro do pacote `escola` (ou em subpacotes dele) para o Spring encontrar tudo sozinho.

O banco é H2 em memória: ele começa **vazio** toda vez que a aplicação sobe. Pare e suba de novo para recomeçar o roteiro do zero.

## Roteiro de conferência

Façam os pedidos na ordem, com o banco vazio. Preencham as duas últimas colunas com o que a API realmente fez.

| Passo | O que fazer | Status esperado | Rota que usamos | Status que deu |
| --- | --- | --- | --- | --- |
| 1 | Cadastrar a sala de código LAB-01, nome Lab Informatica, capacidade 30 | 201 | POST /api/salas | 201 |
| 2 | Cadastrar outra sala com o mesmo código LAB-01 | 409 | POST /api/salas | 409 |
| 3 | Cadastrar uma sala sem nome | 400 | POST /api/salas | 400 |
| 4 | Reservar a sala 1 | 200 | POST /api/salas/1/reservar | 200|
| 5 | Reservar a sala 1 de novo | 409 | POST /api/salas/1/reservar | 409|
| 6 | Excluir a sala 1 (que está reservada) | 409 | DELETE /api/salas/1 |409 |
| 7 | Liberar a sala 1 | 200 | POST /api/salas/1/liberar | 200 |
| 8 | Liberar a sala 1 de novo | 409 | POST /api/salas/1/liberar | 409|
| 9 | Excluir a sala 1 | 204 | DELETE /api/salas/1 | 204|
| 10 | Buscar a sala 1 | 404 | GET /api/salas/1 |404 |

Se algum passo não bateu, escrevam aqui o que aconteceu: Os resultados dos testes devem ser registrados nesta seção caso algum status HTTP retornado seja diferente do esperado. Devem ser descritos o passo em que ocorreu o problema, o resultado obtido e o comportamento apresentado pela API.

## Arquivo do processo

O arquivo `PROMPTS.md`, nesta mesma pasta, é o registro do processo. Preencham durante a prova.

1. Quando chega um pedido de reservar uma sala, qual é o caminho até a resposta? Passe por cada camada (Controller, Service, Repository e Model).

O Controller recebe a requisição e chama a Service. A Service solicita ao Repository que busque a sala no banco. O Repository utiliza o Model Sala para representar os dados. A Service verifica se ela está livre, altera o status para reservada e solicita o salvamento ao Repository. Por fim, o Controller devolve a resposta HTTP 200 com os dados atualizados.

2. Por que a regra "uma sala reservada não pode ser reservada de novo" fica na Service e não no Controller?

Porque é uma regra de negócio da instituição. A Service concentra as regras e decisões do sistema, enquanto o Controller deve apenas receber requisições e devolver respostas HTTP.

3. Se a escola trocar o H2 por outro banco de dados, quais classes precisam mudar? Por quê?

Principalmente as configurações do banco, como URL, driver, usuário e senha. As classes Model e Repository normalmente não precisam mudar, pois o Spring Data JPA abstrai a comunicação com o banco. Consultas específicas de determinado banco podem exigir alterações no Repository.