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
$ mvn integration-test -P integration-test
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