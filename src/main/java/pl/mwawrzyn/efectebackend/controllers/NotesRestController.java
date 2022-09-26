package pl.mwawrzyn.efectebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwawrzyn.efectebackend.models.entity.Note;
import pl.mwawrzyn.efectebackend.services.NoteRestService;

import java.util.List;


@RestController
@RequestMapping("/api/note")
public class NotesRestController {

    @Autowired
    public NotesRestController(NoteRestService noteRestService) {
        this.noteRestService = noteRestService;
    }

    NoteRestService noteRestService;
    @GetMapping("/demo")
    public String getDemoNote() {
        return "aaa";
    }

    @GetMapping("")
    public List<Note> getAllNotes() {
        return noteRestService.getAllNotes();
    }

    @PostMapping("")
    public Note saveNote(@RequestBody Note note) {
        return noteRestService.saveNote(note);
    }
}
