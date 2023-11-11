package com.challenge.todolist;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.challenge.todolist.model.Todo;




@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TodolistApplicationTests {

	@Autowired
	private WebTestClient webTestClient;






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


}
