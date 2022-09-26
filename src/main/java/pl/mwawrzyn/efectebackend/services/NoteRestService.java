package pl.mwawrzyn.efectebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwawrzyn.efectebackend.daos.NoteDao;
import pl.mwawrzyn.efectebackend.models.entity.Note;

import java.util.List;

@Service
public class NoteRestService {

    private final NoteDao noteDao;

    @Autowired
    public NoteRestService(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public Note saveNote(Note note) {
        return noteDao.save(note);
    }

    public List<Note> getAllNotes() {
        return (List<Note>) noteDao.findAll();
    }
}
