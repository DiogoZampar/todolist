package com.challenge.todolist;



import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.challenge.todolist.model.Todo;
import com.challenge.todolist.repositories.TodoRepository;




@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TodolistApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private TodoRepository todoRepository;




	@Test
	void testCreateTodoSuccess() {
		var createdTodo = new Todo("Todo name 1", "todo 1's description", false, 3);


		webTestClient.post().uri("/todo").bodyValue(createdTodo).exchange()
			.expectStatus().isCreated()
			.expectBody()
			.jsonPath("$.name").isEqualTo(createdTodo.getName())
			.jsonPath("$.description").isEqualTo(createdTodo.getDescription())
			.jsonPath("$.completed").isEqualTo(createdTodo.isCompleted())
			.jsonPath("$.priority").isEqualTo(createdTodo.getPriority());

	}


	@Test
	void testCreateTodoFailure() {

		//empty name
		webTestClient.post().uri("/todo").bodyValue(new Todo("", "todo 1's description", false, 3)).exchange()
			.expectStatus().isBadRequest();


		//empty description
		webTestClient.post().uri("/todo").bodyValue(new Todo("Todo name 1", "", false, 3)).exchange()
			.expectStatus().isBadRequest();

		//negative priority
		webTestClient.post().uri("/todo").bodyValue(new Todo("Todo name 1", "todo 1's description", false, -2)).exchange()
			.expectStatus().isBadRequest();

	}

	@Test
	void testGetTodoSuccess() {
		var createdTodo = new Todo("Todo name 1", "todo 1's description", false, 3);
		todoRepository.save(createdTodo);	//saving directly to repository to avoid another POST


		webTestClient.get().uri(new String("/todo/" + createdTodo.getTodoId().toString())).exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.todoId").isEqualTo(createdTodo.getTodoId().toString())
			.jsonPath("$.name").isEqualTo(createdTodo.getName())
			.jsonPath("$.description").isEqualTo(createdTodo.getDescription())
			.jsonPath("$.completed").isEqualTo(createdTodo.isCompleted())
			.jsonPath("$.priority").isEqualTo(createdTodo.getPriority());
	}


	@Test
	void testUpdateTodoSuccess() {
		var createdTodo = new Todo("Todo name 1", "todo 1's description", false, 3);
		createdTodo.setCreatedAt(LocalDateTime.now());
		todoRepository.save(createdTodo);	//saving directly to repository to avoid another POST


		createdTodo.setName("new name");
		createdTodo.setDescription("new description");
		createdTodo.setCompleted(true);
		createdTodo.setPriority(5);


		webTestClient.put().uri(new String("/todo")).bodyValue(createdTodo).exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.name").isEqualTo(createdTodo.getName())
			.jsonPath("$.description").isEqualTo(createdTodo.getDescription())
			.jsonPath("$.completed").isEqualTo(createdTodo.isCompleted())
			.jsonPath("$.priority").isEqualTo(createdTodo.getPriority())
			.jsonPath("$.createdAt").isNotEmpty()
			.jsonPath("$.updatedAt").isNotEmpty();
			
	}


	@Test
	void testPutTodoFailure() {
		var createdTodo = new Todo("Todo name 1", "todo 1's description", false, 3);
		createdTodo.setCreatedAt(LocalDateTime.now());
		createdTodo = todoRepository.save(createdTodo);	//saving directly to repository to avoid another POST



		//empty name
		createdTodo.setName("");
		webTestClient.put().uri("/todo").bodyValue(createdTodo).exchange()
			.expectStatus().isBadRequest();
		createdTodo.setName("Todo name 1");

		//empty description
		createdTodo.setDescription("");
		webTestClient.post().uri("/todo").bodyValue(createdTodo).exchange()
			.expectStatus().isBadRequest();
		createdTodo.setDescription("todo 1's description");

		//negative priority
		createdTodo.setPriority(-2);
		webTestClient.post().uri("/todo").bodyValue(new Todo("Todo name 1", "todo 1's description", false, -2)).exchange()
			.expectStatus().isBadRequest();
		createdTodo.setPriority(3);


	}



}


