package pl.mwawrzyn.efectebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwawrzyn.efectebackend.models.dto.NoteContent;
import pl.mwawrzyn.efectebackend.models.dto.NoteDto;
import pl.mwawrzyn.efectebackend.models.exception.BlankNoteException;
import pl.mwawrzyn.efectebackend.models.exception.ElementNotFoundException;
import pl.mwawrzyn.efectebackend.models.exception.TooLongNoteException;
import pl.mwawrzyn.efectebackend.services.NoteRestService;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/note")
public class NotesRestController {

    private final NoteRestService noteRestService;

    @Autowired
    public NotesRestController(NoteRestService noteRestService) {
        this.noteRestService = noteRestService;
    }

    @GetMapping("")
    public List<NoteDto> getAllNotes() {
        return noteRestService.getAllNotes();
    }

    @PostMapping("")
    public NoteDto saveNote(@RequestBody NoteContent note) throws TooLongNoteException, BlankNoteException {
        return noteRestService.saveNote(note);
    }

    @GetMapping("/{id}")
    public NoteDto getNoteById(@PathVariable("id") Long id) throws ElementNotFoundException {
        return noteRestService.getNoteById(id);
    }

    @PostMapping("/edit")
    public NoteDto editNote(@RequestBody NoteDto note) throws ElementNotFoundException {
           return noteRestService.edit(note);
    }

    @GetMapping("/search")
    public List<NoteDto> searchNotes(@RequestParam(name = "text") String text) {
        return noteRestService.searchByString(text);
    }

    @PostMapping("/delete")
    public NoteDto removeNote(@RequestBody NoteDto note) throws ElementNotFoundException {
        return noteRestService.deleteNote(note);
    }
}
