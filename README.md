## Requirements

* Java 8
* Maven 3.3.9
* Docker 17.03.1-ce

## Setup Database

### Run PostgreSQL

```bash
$ docker run --rm --name postgres-spring -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```

### Create contact schema

```bash
$ docker run -it --rm --link postgres-spring:postgres postgres psql -h postgres -U postgres
```

```bash
postgres=# create database contacts;
```

## Run Application

```bash
$ mvn spring-boot:run
```

### Save a contact

```bash
$ curl -v localhost:9000/contacts  -H "Content-Type: application/json" -d '{ "firstName": "matteo", "lastName": "pierro", "phoneNumber": "+39 329 654321"}'
```

### Retrieve a contact

```bash
$ curl -v localhost:9000/contacts/<id>
```

### Retrieve all contacts

```bash
$ curl -v localhost:9000/contacts
```

## Run Test

### Run Unit Test

```bash
$ mvn clean test
```

### Run Integration Test

#### Create test schema

```bash
postgres=# create database contacts_test;
```

#### Run
```bash
$ mvn test-compile failsafe:integration-test
```

## Test Coverage


### Only Unit Test
```bash
$ mvn cobertura:cobertura
```

### Unit and Integration Test 
```bash
$ mvn cobertura:cobertura-integration-test
```

Maven will generate the Cobertura code coverage report at `${project}/target/site/cobertura/index.html` 