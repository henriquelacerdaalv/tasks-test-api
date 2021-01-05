package hlacerda.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APItest {
	
	@BeforeClass
	public static void setup(){
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornar_Tarefas() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
	;
	}
	
	@Test
	public void deveAdicionar_Tarefa() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2021-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdicionar_TarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
}