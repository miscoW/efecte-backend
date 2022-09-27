package pl.mwawrzyn.efectebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwawrzyn.efectebackend.models.entity.Note;
import pl.mwawrzyn.efectebackend.models.exception.ElementNotFoundException;
import pl.mwawrzyn.efectebackend.models.exception.TooLongNoteException;
import pl.mwawrzyn.efectebackend.services.NoteRestService;

import java.util.List;


@RestController
@RequestMapping("/api/note")
public class NotesRestController {

    NoteRestService noteRestService;

    @Autowired
    public NotesRestController(NoteRestService noteRestService) {
        this.noteRestService = noteRestService;
    }

    @GetMapping("")
    public List<Note> getAllNotes() {
        return noteRestService.getAllNotes();
    }

    @PostMapping("")
    public Note saveNote(@RequestBody Note note) throws TooLongNoteException {
        return noteRestService.saveNote(note);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable("id") Long id) throws ElementNotFoundException {
        return noteRestService.getNoteById(id);
    }

    @PostMapping("/edit")
    public Note editNote(@RequestBody Note note) throws ElementNotFoundException {
           return noteRestService.edit(note);
    }

    @GetMapping("/search")
    public List<Note> searchNotes(@RequestParam(name = "text") String text) {
        return noteRestService.searchByString(text);
    }

    @PostMapping("/delete")
    public Note removeNote(@RequestBody Note note) {
        return noteRestService.deleteNote(note);
    }
}
