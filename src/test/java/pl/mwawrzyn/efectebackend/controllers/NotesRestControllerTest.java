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
import pl.mwawrzyn.efectebackend.models.entity.Note;


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


        assertEquals(expectedNote, response);
    }

    @Test
    public void getAllNotes() throws JSONException {
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
    public void optiomisticLocking() throws JSONException {
        String content = "test content";
        Long id = 1L;
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