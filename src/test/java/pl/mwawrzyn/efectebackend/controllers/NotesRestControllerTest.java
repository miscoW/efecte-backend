package pl.mwawrzyn.efectebackend.controllers;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class NotesRestControllerTest {

    @Test
    public void saveNote() throws JSONException {
        String content = "test content";
        JSONObject requestParams = new JSONObject();
        requestParams.put("content", content);

        JSONObject expectedResponse = new JSONObject();
        expectedResponse.put("content", content);
        expectedResponse.put("id", 1);

        given()
                .when()
                .body(requestParams.toString())
                .contentType(ContentType.JSON)
                .post("/api/note")
                .then()
                .statusCode(200)
                .body(is(expectedResponse.toString()));
    }

    @Test
    public void getAllNotes() throws JSONException {
        String content = "test content";
        JSONObject expectedResponse = new JSONObject();
        expectedResponse.put("content", content);
        expectedResponse.put("id", 1);

        given()
                .when().get("/api/note")
                .then()
                .statusCode(200)
                .body(is("[{\"id\":1,\"content\":\"test content\"}]")); // todo make tests independent
    }
}