# Bureau Sistema.

### Montando ambiente

 1. Clonar o repositório.
 2. Pré requisitos: OpenJDK 8 64Bits,  Maven, Docker
 3. Subindo ambiente
 `Linux: executar  arquivo monta_ambiente.sh`
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
