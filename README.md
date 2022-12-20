# Como executar

Recomendo que abra um terminal para cada um das aplicações abaixo:

1. Inicia o docker

```
docker-compose up
```

2. Inicie o front
```
cd person-crud-frontend
npx vite dev --host
```

3. Inicie o backend pelo F5 ou então pelos comandos:
```
cd person-crud-backend
mvn clean package
java -jar target/person-crud-backend-0.0.1.jar
```
