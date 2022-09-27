package pl.mwawrzyn.efectebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwawrzyn.efectebackend.daos.NoteDao;
import pl.mwawrzyn.efectebackend.daos.NoteQueriesDao;
import pl.mwawrzyn.efectebackend.models.entity.Note;
import pl.mwawrzyn.efectebackend.models.exception.ElementNotFoundException;
import pl.mwawrzyn.efectebackend.models.exception.TooLongNoteException;

import java.util.List;
import java.util.Optional;

@Service
public class NoteRestService {

    private final NoteDao noteCrudDao;
    private final NoteQueriesDao noteQueriesDao;

    @Autowired
    public NoteRestService(NoteDao noteDao, NoteQueriesDao noteQueriesDao) {
        this.noteCrudDao = noteDao;
        this.noteQueriesDao = noteQueriesDao;
    }

    public Note saveNote(Note note) throws TooLongNoteException {
        if(note.getContent().length() >= 200) {
            throw new TooLongNoteException();
        }
        return noteCrudDao.save(note);
    }

    public List<Note> getAllNotes() {
        return (List<Note>) noteCrudDao.findAll();
    }

    public Note getNoteById(Long id) throws ElementNotFoundException {
       Optional<Note> optional = noteCrudDao.findById(id);
       if(optional.isPresent()) {
           return optional.get();
       } else {
           throw new ElementNotFoundException();
       }
    }

    public Note edit(Note note) throws ElementNotFoundException {
        if(noteCrudDao.findById(note.getId()).isPresent()) {
            return noteCrudDao.save(note);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public List<Note> searchByString(String text) {
        return noteQueriesDao.findByPartOfContent(text);
    }
}
