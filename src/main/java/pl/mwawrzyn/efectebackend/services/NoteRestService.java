package pl.mwawrzyn.efectebackend.services;

import org.springframework.stereotype.Service;
import pl.mwawrzyn.efectebackend.models.entity.Note;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteRestService {

    private final List<Note> noteList = new ArrayList<>();

    public Note saveNote(Note note) {
        note.setId((long) noteList.size());
        noteList.add(note);
        return note;
    }
}
