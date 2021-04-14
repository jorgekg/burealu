
## Montando ambiente

 1. Clonar o repositório.
 2. Pré requisitos: OpenJDK 8 64Bits,  Maven, Docker
 3. Subindo ambiente
 `Mac e Linux: executar  arquivo monta_ambiente.sh`
 `Windows: alterar os arquivos Dockerfile de todos o microserviços, alterando o valor de {ip} para o ip da sua maquina (Não vale localhost)`
 `Eclipse: importar os projetos como Maven project, startar o main ou startar os projetos como um projeto Spring Boot.`
 
 4. Estrutura dos endpoints
 
|Microservice|Host with gateway|Host microservice|Swagger
|--|--|--|--|
|Eureka discovery|http://localhost:8023|-|-
|Gateway|http://localhost:8025|-|http://localhost:8025/swagger-ui.html
|Tracking|http://localhost:8025/tracking-api|http://localhost:8027|http://localhost:8025/traking-api/swagger-ui.html
|Earnings|http://localhost:8025/earlings-api|http://localhost:8028| http://localhost:8025/earlings-api/swagger-ui.html
|Details|http://localhost:8025/details-api|http://localhost:8026|http://localhost:8025/details-api/swagger-ui.html

A coleção do Postman, com todos os endpoints com base no swagger pode se baixada aqui: [Postman Collection Bureau](https://github.com/jorgekg/bureau/blob/master/Bureau.json)

## Arquitetura
![Arquitetura](https://github.com/jorgekg/bureau/blob/master/images/Bureau.jpg)

Como podemos ver na image, foi montado um estrutura com 3 microserviços, (Traking, Earnings, Details) um serviço para cada base. Alem dos serviços foram desenvolvidos um Gateway utilizando o Zuul Proxy e um Discovery utilizando Eureka.

### Como funciona

 - Gateway: ao chegar uma nova request, a mesma é autenticada e redirecionada ao microserviço que possa atende-lá.
 - Microserviço: é possível chamar diretamente o microserviço, porem ocorre o processo inverso, o serviço vai até o gateway e recebe a autenticação via RabbitMQ.
 - Discovery: apenas  mapeia a relação dos microserviços, oferecendo load balance e escalabilidade. 

## Base de dados

![Base de dados](https://github.com/jorgekg/bureau/blob/master/images/Database.jpg)


 - Base A, que recebeu o nome de Tracking, possui as tabelas de pessoa, endereço e débitos. Como está base é bastante sensível todos os campos os campos são texto para salva-los criptografados, porem devido ao tempo não foi realizado. A base também possível validação no Backend para modificação não autorizadas direto na base de dados, caso editado manualmente sempre que feito um retrieve dos dados será retornado um erro de DataIntegrityException;
 Foi levado para dentro desta base a informação sobre idade, para deixar centralizado todas as informação da pessoa e alterado o tipo idade para data de nascimento, porem é possível chama-lá pelo serviço de Earnings mas será integrada a Base A via RabbitMQ.
 Para acessar esta base é necessário ter um papel no cadastro de usuário como TRACKING.
 - Base B, que recebeu o nome de Earnings, possui a tabela de bens e renda, nesta base existe apenas uma validação se ouve modificação indevidas.
 As informações de endereço e idade, forem centralizadas dentro da tabela A e integradas via RabbitMQ.
 Para acessar esta base é necessário ter um papel no cadastro de usuário como EARNINGS.
 - Base C, que recebeu o nome de details, possui as tabelas de última compra, última busca e movimentação, não possui nenhum tipo de criptografia, validação de integridade e chaves estrangeiras para serem rápidas ao inserir e buscar seu dados.
 Para acessar esta base é necessário ter um papel no cadastro de usuário como DETAILS.

## Observações
Ao subir o ambiente será criado um usuário administrador com todos os papeis 
`e-mail: admin@admin` e `senha: 12345`.
É possível criar novos usuários, atribuir papeis e trocar a senha, poucas funcionalidades devido a este não ser o foco do sistema.

### Possíveis problemas
O sistema está retornando erro 500 e com a exceção `ZuulException`, o sistema está com o microserviço parado ou ainda não ocorreu o shovel do Discovery. Ocorre em media a cada 30 segundos.
O Sistema está retornando erro 500 ou 504 sem motivo aparente, verificar se as filas foram criadas no RabbitMQ ou se não há filas travadas.

### Tecnologias utilizadas
Java OpenJDK 8 64bits, Maven, Database H2 em memória, RabbitMQ.

 - Java com OpenJDK para poder comercializar sistemas sem a necessidades de royalties a Oracle.
 - Maven  como gerenciador de dependencias.
 - Base H2 em memória, para ser faço de testa-lá, para não haver necessidade de horas de configurações para a pessoa que fará o teste do sistema.
 - RabbitMQ como serviço de mensageira entre os microserviços e o Gateway.
