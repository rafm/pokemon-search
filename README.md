# Pokemon Search

## Tecnologias utilizadas:

### Back-end
* Java 8
* Spring Boot
* Spring Framework
* Spring WebFlux
* JUnit (testes unitários)
* Mockito (testes unitários)

### Front-end
* HTML
* CSS
* Javascript

### Build, gerenciamento de dependências e deploy
* Maven
* Docker

## Como rodar o projeto localmente
Existem duas maneiras de você executar esta aplicação no seu computador localmente: rodando o uber jar (jar com todas as dependências necessárias para rodar a aplicação web utilizando apenas uma JRE) ou gerando uma imagem local do Docker e criando um container para o projeto.

### Como gerar o uber jar
Existem duas maneiras:
1. Você pode baixar o uber jar da release/tag que você deseja executar diretamente na aba [releases](https://github.com/rafm/pokemon-search/releases) na página do GitHub deste projeto.
2. Caso deseja gerar o uber jar a partir de uma branch específica, você pode clonar este repositório no seu computador local e gerar o uber jar manualmente a partir dele. Obs: para isso, você também precisará ter instalado localmente a JDK 8 e o Maven.
- 1. Após clonar o repositório, vá até a pasta do repositório pelo prompt de comando/bash e digite o comando: **_mvn clean package_**
- 2. Após terminar o comando, o Maven terá gerado o uber jar dentro da pasta _target_ do repositório com o nome _pokemon-search.jar_.

### Como rodar o uber jar
1. Primeiramente, você precisará ter a JRE ou JDK 8 instalado na sua máquina e uma conta no OpenWeather API (mais especificamente, do seu app id do OpenWeather).
2. Pelo prompt de comando/bash, vá até o local onde está o uber jar.
3. Digite o comando: _java -jar pokemon-search.jar --weatherApi.appId={OpenWeatherAppId}_
- * Você deve substituir a parte _{OpenWeatherAppId}_ pela app id da sua conta do OpenWeather para que a aplicação funcione.
4. A aplicação vai estar rodando na sua máquina localmente através da porta **8080**. Ex: http://localhost:8080/
- * Para encerrar a aplicação, basta apertar o conjunto de botões do seu teclado _CTRL+C_.

### Como gerar uma imagem do Docker
1. Primeiramente, além de você também precisar ter a JDK 8 e o Maven, você terá obviamente que ter o Docker instalado na sua máquina.
2. Siga as instruções da seção **Como gerar o uber jar a partir de uma branch específica** citadas anteriormente.
3. Pelo prompt de comando/bash, vá até o local onde está o repositório que você clonou.
4. Digite o comando: _docker build -t prefix/pokemon-search:x.x ._
5. A imagem do Docker será criada localmente com o nome _prefix/pokemon-search: x:x_, onde _x:x_ é a versão da imagem que você criou.

### Como criar e rodar um container do Docker a partir da imagem criada deste projeto
1. Abra o seu prompt de comando/bash.
2. Digite o comando: _docker run --name pokemon-search -p 8080:8080 -e OPEN_WEATHER_APP_ID={OpenWeatherAppId} -d prefix/pokemon-search_
- * Você deve substituir a parte _{OpenWeatherAppId}_ pela app id da sua conta do OpenWeather para que a aplicação funcione.
3. O Docker irá criar e rodar o container **em background** a partir da imagem **prefix/pokemon-search** com o nome **pokemon-search** e você poderá acessar a aplicação a partir da sua máquina através da porta **8080**. Ex: http://localhost:8080/

### Como criar e rodar um container do Docker a partir da imagem do Docker Hub deste projeto
1. Abra o seu prompt de comando/bash.
2. Digite o comando: _docker run --name pokemon-search -p 8080:8080 -e OPEN_WEATHER_APP_ID={OpenWeatherAppId} -d rafm/pokemon-search:1.0_
- * Você deve substituir a parte _{OpenWeatherAppId}_ pela app id da sua conta do OpenWeather para que a aplicação funcione.
3. O Docker irá criar e rodar o container **em background** a partir da imagem **rafm/pokemon-search** na versão **1.0** com o nome **pokemon-search** e você poderá acessar a aplicação a partir da sua máquina através da porta **8080**. Ex: http://localhost:8080/

### Como usar a aplicação
1. Após iniciar a aplicação, acesse-a através do seu navegador. Ex: http://localhost:8080/
2. Irá aparecer o campo de texto para você digitar o nome de uma cidade. Digite o nome de uma cidade e clique no botão **Procurar Pokemon**.
- * Caso você digite um nome de cidade inválido, a aplicação irá te alertar.
3. Uma mensagem irá aparecer informando qual pokemon apareceu para você, junto com uma informação sobre a temperatura da cidade e, caso esteja chovendo, ele também irá lhe informar por meio da mensagem.

### Comandos utéis do Maven para desenvolvedores
* _mvn test_ = roda todos os testes da aplicação. Útil quando deseja garantir que todos os testes estão passando no momento.
* _mvn spring-boot:run_ = compila o seu projeto e roda a sua aplicação localmente. Útil para não ter que ficar gerando o uber jar e rodando ele logo em seguida em mais de um comando, fazendo todo o processo em um comando só. Dica: você pode configurar o OpenWeather app id usando este comando também, passando o app id como variável de sistema do Maven/Java através do parâmetro -D. Ex: _mvn spring-boot:run -D"weatherApi.appId={OpenWeatherAppId}"_
