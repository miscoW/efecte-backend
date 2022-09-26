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
    public void testDemoEndpoint() {
        given()
                .when().get("/api/note/demo")
                .then()
                .statusCode(200)
                .body(is("aaa"));
    }

    @Test
    public void saveNote() throws JSONException {
        // given
        String content = "test content";
        JSONObject requestParams = new JSONObject();
        requestParams.put("content", content);

        JSONObject expectedResponse = new JSONObject();
        expectedResponse.put("content", content);
        expectedResponse.put("id", 0);

        given()
                .when()
                .body(requestParams.toString())
                .contentType(ContentType.JSON)
                .post("/api/note")
                .then()
                .statusCode(200)
                .body(is(expectedResponse.toString()));
    }
}