# KotlinTechnicalChallenge

- ./mvnw clean package -DskipTests (para gerar o JAR) Deve ter gerado TechnicalChallengeK-0.0.1-SNAPSHOT.jar
- Logo em seguida executar o comando: (Certifique-se de possuir o docker instalado em sua máquina)
  - docker-compose build (será feito todo o build do projeto local utilizando o Dockerfile)
  - docker-compose up -d
- Da forma descrita acima, a aplicação local estará rodando no docker, e o Kafka também estará rodando no docker

# Notas de Desenvolvimento
- Diferente da aplicação em Java, dessa vez, foi possível identificar o erro que estava presente no docker-compose.yml e subir tanto a aplicação quanto o Kafka no docker. 
- Foi usado a distribuição do confluent do Kafka para rodar no docker
