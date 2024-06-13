## Como rodar projeto
Observação: Para rodar o projeto é necessário:
- Uma IDE (integrated development environment);
- Uma ferramenta de envio de requisições *HTTP* como o *Insomnia*;
- Java versão 17 ou superior;
- Lombok instalado na usa máquina (opcional).<br/>
#### Atenção: caso não tenha o Lombok instalado, é provavél que algumas atividades não funcionem corretamente, caso ocorra defina os métodos GET e SET das classes de entidades!.

- Crie uma nova pasta, acesse o CMD na nova pasta e digite o seguinte comando:
  
```
  git clone https://github.com/GaSiqueira/Autobots.git .
```
- Acesse a branch da atividade 4:

```
  git checkout Atv4
```
- Abra a pasta do projeto com sua IDE e rode a aplicação;
- Caso esteja usando o Eclipse, clique no botão File no canto superior direito e selecione *Open projects from file system*
- Escolha o diretorio do projeto (automanager) e clique em *finish*
- Após o projeto ser contruído, acesse o caminho de pastas: *automanager\src\main\java\com\autobots\automanager*
- Clique duas vezes em *AutomanagerApplication.java* e clique no botão verde de *play* na barra superior
- Se tudo ocorreu bem, a aplicação agora está funcionando! :)

## Autenticação e autorização
- No insomnia, utilize a requisição POST na rota [localhost:8080/login]()
- No Body da requisição use o seguinte JSON:
  ```
  {
    "nomeUsuario": "admin",
    "senha": "123456"
  }
  ```
  ![image](https://github.com/GaSiqueira/Autobots/assets/125694331/e4364e46-724e-4ca6-ba63-2480ea831da5)

- Após enviar a requisição e ele retornar 200, Vá na aba headers e verifique o campo *Authorization*:
![image](https://github.com/GaSiqueira/Autobots/assets/125694331/3ab76863-aff4-4d72-98b6-a249a796f65f)

- Para enviar requisições para as outras rotas basta colocar o código do Bearer em um novo Header da requisição da seguinte forma:
![image](https://github.com/GaSiqueira/Autobots/assets/125694331/f0dd02ca-04d2-4885-9dab-bfe5a7c4b97d)

#### *OBS: caso o insomnia apresente erro de forbidden, crie uma nova requisição no insomnia e tente novamente!*.
