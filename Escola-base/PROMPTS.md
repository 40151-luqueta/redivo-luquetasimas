# PROMPTS: 

**Integrantes:**

1. Simioni
2. Oliveira


**Prova:** API em MVC (Reserva de Salas)

**Usamos IA?** (X) Sim  ( ) Não

**Qual ferramenta e modelo (se usaram):** ChatGPT

> Quem não usou IA: troque "prompt" por "passo". Em cada bloco abaixo, descreva o que vocês fizeram naquele passo, na ordem em que aconteceu, e preencha os outros campos do mesmo jeito.

## Registro, na ordem

Copie o bloco abaixo para cada prompt. Escreva o prompt exatamente como foi enviado.

### Prompt 1

**O prompt (cópia exata):**

> Chat, necessito realizar uma prova, vou te informar exatamente qual foi a prova que o professor passou, mas antes, quero te entregar alguns conceitos que devem ser seguidos antes de tudo. Iremos trabalhar com Model, que é a camada que Representa o dado. É a entidade (@Entity) com @Id, campos, construtor vazio, getters e setters, e as restrições de validação (@NotBlank, @Positive, ...) ; Repository que é a camada que Conversa com o banco. É uma interface que estende JpaRepository. Não tem regra de negócio ; Service, a camada que tem as regras da instituição. Decide se pode ou não pode fazer algo. Se não pode, lança uma exceção ; Controller que traduz o mundo web: recebe a rota e o JSON, chama a Service, devolve o status HTTP. Não acessa o Repository e não tem "if" de regra. Um caminho do pedido, será cliente -> Controller -> Service -> Repository -> banco e a resposta volta pelo mesmo caminho. Pergunta-teste para saber onde um código vai: "Isso é regra da instituição ou detalhe de HTTP?" Regra da instituição -> Service. Rota, JSON, status -> Controller. Além disso, defina muito bem os status http 200  deu certo e devolve algo (buscar, listar, mudar estado),201  criou um recurso novo, 204  deu certo e não há nada para devolver (excluir) , 400  o DADO ENVIADO é inválido (campo vazio, número negativo), 404  o recurso pedido não existe e 409  o dado está certo, mas o ESTADO ATUAL do sistema não permite a operação. EXEMPLO RESOLVIDO (tema: clínica de consultas)

  Regras: a consulta tem protocolo (único), paciente e valor. Nasce
  AGENDADA. Pode ser cancelada ou realizada, mas só enquanto estiver
  agendada. Só pode ser excluída se estiver cancelada.

  Pensando em cada regra, decida a camada e o status:

    Regra                                  Camada     Status ao quebrar
    -------------------------------------  ---------  ------------------
    Paciente vazio                         Model +    400
                                           @Valid
    Valor menor ou igual a zero            Model +    400
                                           @Valid
    Protocolo repetido                     Service    409
    Cancelar consulta que não está         Service    409
      agendada
    Excluir consulta que não está          Service    409
      cancelada
    Qualquer operação num id inexistente   Service    404

  Um método da Service, seguindo o padrão (buscar, conferir a regra,
  mudar o estado, salvar):

    public Consulta cancelar(Long id) {
        Consulta c = buscar(id);          // lança "não encontrada" (404)
        if (c.getStatus() != Status.AGENDADA) {
            throw new RegraDeNegocioException(
                "Só é possível cancelar uma consulta agendada");   // 409
        }
        c.setStatus(Status.CANCELADA);
        return consultaRepository.save(c);
    }

  No Controller, a rota só repassa e o tratamento de exceção traduz
  para o status:

    @PostMapping("/{id}/cancelar")
    public Consulta cancelar(@PathVariable Long id) {
        return consultaService.cancelar(id);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<String> conflito(RegraDeNegocioException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(e.getMessage());
    }

  Repare que o Controller não tem "if" e a Service não conhece HTTP.
  A Service só lança a exceção. Quem a transforma em 409 é o Controller.
A partir disso, que se resume à boas práticas de programação, clean code e código desacoplado, devemos fazer o seguinte (SUA MISSÃO PRINCIPAL): Prova prática de API em MVC: Reserva de Salas da Escola, a escola precisa de um sistema (api) que controle a reserva de salas, considerando que existam, além das salas de aula normais, laboratórios e auditórios, cada sala tem um código, um nome e uma capacidade especifica, a escola necessita saber exatamente quais foram reservadas e quais estão livres. A api precisa realizar 3 coisas principais: - Cadastrar uma sala, listar todas, buscar uma pelo id e excluir, - Reservar uma sala e liberar uma sala, - Responder cada situação com o status HTTP certo: sucesso, criado, sem conteúdo, não encontrado, conflito e dados inválidos. Mas a escola possui regras, e a api deve estar construída com base nelas, toda sala cadastrada começa livre, o código de sala é único, portanto não pode haver duas salas com o mesmo código, TODA SALA DEVE ter um código e nome (obrigatórios) e a capacidade PRECISA SER maior que zero (uma validação CLARA necessária, você deve buscar durante o desenvolvimento todas as validações necessárias), uma sala já reservada, não pode ser reservada novamente, só pode ser liberada uma sala que está reservada (validação, porque não dá para liberar uma sala que não está ocupada *já estava livre*), uma sala RESERVADA não pode ser excluída e pedir, reservar, liberar ou excluir uma sala que não existe é um caso de "não encontrado". O projeto terá a pasta raiz com o nome `Escola-base` o professor entregou uma base para nós, Ele já traz Spring Web, Spring Data JPA, validação e banco **H2 em memória**, e já sobe sem nenhuma classe de vocês. Não precisa criar projeto. O arquivo `README.md` dele explica como rodar. Na verdade é o que o "spring initializer" gera automaticamente, portanto, acredito que o necessário seja apenas as pastas da aplicação em si, que sua entrega DEVE SER em um .zip com TODAS ELAS COMPLETAS. O projeto PRECISA TER Organização em camadas, cada uma no seu pacote, criado por você dentro de `src/main/java/escola` (ou seja, esse caminho já existe na pasta enviada pelo professor, depois dos ":" são aqueles que não existem): `model`, `repository`, `service` e `controller`. O projeto base não traz essas pastas prontas. As regras da escola ficam na Service. O Controller só recebe a requisição, chama a Service e devolve a resposta HTTP. O Controller não conversa direto com o Repository. Ou seja, é exatamente aquilo que já te informei acima sobre boas praticas de programação e clean code, As rotas seguem o padrão REST. Você escolhe os nomes. O `README.md` do projeto base traz essa tabela: | Passo | O que fazer | Status esperado | Rota que usamos | Status que deu |
| --- | --- | --- | --- | --- |
| 1 | Cadastrar a sala de código LAB-01, nome Lab Informatica, capacidade 30 | 201 | | |
| 2 | Cadastrar outra sala com o mesmo código LAB-01 | 409 | | |
| 3 | Cadastrar uma sala sem nome | 400 | | |
| 4 | Reservar a sala 1 | 200 | | |
| 5 | Reservar a sala 1 de novo | 409 | | |
| 6 | Excluir a sala 1 (que está reservada) | 409 | | |
| 7 | Liberar a sala 1 | 200 | | |
| 8 | Liberar a sala 1 de novo | 409 | | |
| 9 | Excluir a sala 1 | 204 | | |
| 10 | Buscar a sala 1 | 404 | | |

Se algum passo não bateu, escrevam aqui o que aconteceu: , e o professor pede para preencher o readme com algumas coisas especificas, ou seja, você pode copiar o atual e adicionar o que foi pedido, o pedido foi basicamente a parte de se algum não bateu, escreva o que aconteceu. ## Perguntas finais
1. Quando chega um pedido de reservar uma sala, qual é o caminho até a resposta? Passe por cada camada (Controller, Service, Repository e Model).
2. Por que a regra "uma sala reservada não pode ser reservada de novo" fica na Service e não no Controller?
3. Se a escola trocar o H2 por outro banco de dados, quais classes precisam mudar? Por quê? Essas perguntas você precisa me enviar as respostas curtas e diretas aqui no chat. Novamente: sua entrega em um .zip com o código pronto e organizado nas pastas corretamente, você NÃO FARÁ o spring boot initializer e as pastas do professor novamente, apenas o que falta para complementar (ou seja, praticamente o projeto inteiro, /model, /controllers etc...) eu mesmo insiro na pasta do professor corretamente. O readme não vai ser enviado para você, então considere que você só deve me entregar com a tabela e a pergunta respondida (se necessario) que eu insiro no readme do professor

**O que a IA devolveu (uma ou duas linhas):**

**O que fizemos com a resposta:** (X) usamos como veio mas analisamos a resposta  ( ) alteramos  ( ) descartamos

**Por quê (o que conferimos, o que mudamos):** 
Conferimos os testes, validações, e tudo o que inclui ou refere-se a estrutura do projeto.