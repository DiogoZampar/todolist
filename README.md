


# Todo List API

- CRUD task management API.
- Inspired by Simplify's Jr Dev Challenge.


## Tools

- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Validation I/O
- PostgreSQL
- H2 Database (testing)

## Practices

- REST API
- Dependency Injection
- Error message handling
- Automatic Swagger by OpenAPI3


## Instructions

- Clone GIT repository

```
git clone https://github.com/DiogoZampar/todolist
```
- Build
```
./mvnw clean package
```
- Execute
```
java -jar target/todolist-0.0.1-SNAPSHOT.jar
```
- API can now be accessed at localhost:8080.


## Endpoints

- ### GET https://localhost:8080/todo
    Returns a complete list of Todos in application/json


- ### GET https://localhost:8080/todo/{id}
    Returns Todo with given UUID, if found.

- ### POST https://localhost:8080/todo
    
    Expected request body(application/json):
```
{
    "name": "Todo 1",
    "description": "Todo 1's Description",
    "priority": 3,
    "completed": false
}
```

- ### PUT https://localhost:8080/todo
    Updates Todo with given UUID, if found.
    
    Expected request body(application/json):
```
{
    "todoId": "48894b5b-a9f4-4374-9da6-b9c152b6b75a",
    "name": "Todo 1",
    "description": "Todo 1's Description",
    "priority": 3,
    "completed": false
}
```

- ### DELETE https://localhost:8080/todo/{id}
    Deletes Todo with given UUID, if found.

