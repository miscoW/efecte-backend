package pl.mwawrzyn.efectebackend.controllers;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwawrzyn.efectebackend.models.dto.NoteDto;


import java.util.List;

import static io.restassured.RestAssured.given;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class NotesRestControllerTest {

    @BeforeEach
    public void cleanDatabaseAndAddTwoNotes() throws JSONException {
        NoteDto[] allNotesBefore = given()
                .when().get("/api/note")
                .then()
                .statusCode(200)
                .extract().as(NoteDto[].class);

        for(NoteDto note : allNotesBefore) {
            removeNote(note);
        }

        List<String> contents = List.of("test content1", "test content2");
        for(String content : contents) {
            addNote(content);
        }
    }
    @Test
    public void saveNote() throws JSONException {
        String content = "test content";
        JSONObject requestParams = new JSONObject();
        requestParams.put("content", content);

        NoteDto expectedNote = new NoteDto();
        expectedNote.setId(3L);
        expectedNote.setContent(content);
        expectedNote.setVersion(0);

        NoteDto response = given()
                .when()
                .body(requestParams.toString())
                .contentType(ContentType.JSON)
                .post("/api/note")
                .then()
                .statusCode(200)
                .extract().as(NoteDto.class);


        assertEquals(expectedNote.getId(), response.getId());
        assertEquals(expectedNote.getVersion(), response.getVersion());
        assertEquals(expectedNote.getContent(), response.getContent());
    }

    @Test
    public void saveTooLongNote() throws JSONException {
        String content = "tooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTexttooLongTe Total 201 long";
        JSONObject requestParams = new JSONObject();
        requestParams.put("content", content);

        given()
                .when()
                .body(requestParams.toString())
                .contentType(ContentType.JSON)
                .post("/api/note")
                .then()
                .statusCode(400);


    }

    @Test
    public void getAllNotes() {
        //given

        //when
        NoteDto[] response = given()
                .when().get("/api/note")
                .then()
                .statusCode(200)
                .extract().as(NoteDto[].class);

        //then
        assertEquals(2, response.length);

    }

    @Test
    public void getOneNote() {
        //given
        NoteDto[] data = given()
                .when().get("/api/note")
                .then()
                .statusCode(200)
                .extract().as(NoteDto[].class);

        Long id = data[0].getId();

        NoteDto expectedNote = new NoteDto();
        expectedNote.setId(id);
        expectedNote.setContent("test content2");
        expectedNote.setVersion(0);

        //when
        NoteDto response = given()
                .when().get("/api/note/" + id)
                .then()
                .statusCode(200)
                .extract().as(NoteDto.class);

        //then
        assertEquals(expectedNote.getId(), response.getId());
        assertEquals(expectedNote.getVersion(), response.getVersion());
        assertEquals(expectedNote.getContent(), response.getContent());
    }

    @Test
    public void getOneNotes404() {
        //given
        //when
        given()
                .when().get("/api/note/101")
                .then()
                .statusCode(404);
        //then

    }

    @Test
    public void editNote() throws JSONException {
        //given
        NoteDto[] data = given()
                .when().get("/api/note")
                .then()
                .statusCode(200)
                .extract().as(NoteDto[].class);

        Long id = data[0].getId();

        NoteDto expectedNote = new NoteDto();
        expectedNote.setId(id);
        expectedNote.setContent("test content");
        expectedNote.setVersion(1);
        JSONObject requestParams = new JSONObject();
        requestParams.put("content", "test content");
        requestParams.put("id", id);
        requestParams.put("version", 0);
        //given


        //when
        given()
                .when()
                .body(requestParams.toString())
                .contentType(ContentType.JSON)
                .post("/api/note/edit")
                .then()
                .statusCode(200);

        //then
        NoteDto result = given()
                .when().get("/api/note/" + id)
                .then()
                .statusCode(200)
                .extract().as(NoteDto.class);
        assertEquals(expectedNote.getId(), result.getId());
        assertEquals(expectedNote.getVersion(), result.getVersion());
        assertEquals(expectedNote.getContent(), result.getContent());

    }
    @Test
    public void editOptiomisticLocking() throws JSONException {
        NoteDto[] data = given()
                .when().get("/api/note")
                .then()
                .statusCode(200)
                .extract().as(NoteDto[].class);

        Long id = data[0].getId();
        String content = "test content";
        int version = 10;
        JSONObject requestParams = new JSONObject();
        requestParams.put("content", content);
        requestParams.put("id", id);
        requestParams.put("version", version);

        given()
                .when()
                .body(requestParams.toString())
                .contentType(ContentType.JSON)
                .post("/api/note/edit")
                .then()
                .statusCode(400);
    }

    private void removeNote(NoteDto note) throws JSONException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("content", note.getContent());
        requestParams.put("id", note.getId());
        requestParams.put("version", note.getVersion());

        given()
                .when()
                .body(requestParams.toString())
                .contentType(ContentType.JSON)
                .post("/api/note/delete")
                .then()
                .statusCode(200);
    }

    private void addNote(String content) throws JSONException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("content", content);

        given()
                .when()
                .body(requestParams.toString())
                .contentType(ContentType.JSON)
                .post("/api/note")
                .then()
                .statusCode(200);
    }
}