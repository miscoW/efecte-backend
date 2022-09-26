package pl.mwawrzyn.efectebackend.controllers;

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
                .when().get("/api")
                .then()
                .statusCode(200)
                .body(is("aaa"));
    }
}