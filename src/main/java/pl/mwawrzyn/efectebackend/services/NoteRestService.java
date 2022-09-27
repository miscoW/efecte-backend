package pl.mwawrzyn.efectebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.mwawrzyn.efectebackend.daos.NoteDao;
import pl.mwawrzyn.efectebackend.daos.NoteQueriesDao;
import pl.mwawrzyn.efectebackend.mapper.NoteMapper;
import pl.mwawrzyn.efectebackend.models.dto.NoteDto;
import pl.mwawrzyn.efectebackend.models.entity.Note;
import pl.mwawrzyn.efectebackend.models.exception.ElementNotFoundException;
import pl.mwawrzyn.efectebackend.models.exception.TooLongNoteException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NoteRestService {

    private final NoteDao noteCrudDao;
    private final NoteQueriesDao noteQueriesDao;
    private final NoteMapper noteMapper;

    @Autowired
    public NoteRestService(NoteDao noteDao, NoteQueriesDao noteQueriesDao, NoteMapper noteMapper) {
        this.noteCrudDao = noteDao;
        this.noteQueriesDao = noteQueriesDao;
        this.noteMapper = noteMapper;
    }

    public NoteDto saveNote(NoteDto noteDto) throws TooLongNoteException {
        if(noteDto.getContent().length() >= 200) {
            throw new TooLongNoteException("New note too long, max size 200");
        }
        Note entity = noteMapper.noteDtoToNote(noteDto);
        entity =  noteCrudDao.save(entity);
        return noteMapper.noteToNoteDto(entity);
    }

    public List<NoteDto> getAllNotes() {
        return noteMapper.noteListToNoteDtoList(
                (List<Note>) noteCrudDao.findAll());
    }

    public NoteDto getNoteById(Long id) throws ElementNotFoundException {
       Optional<Note> optional = noteCrudDao.findById(id);
       if(optional.isPresent()) {
           return noteMapper.noteToNoteDto(optional.get());
       } else {
           throw new ElementNotFoundException("Element with id " + id + " not found");
       }
    }

    public NoteDto edit(NoteDto note) throws ElementNotFoundException {
        if(noteCrudDao.findById(note.getId()).isPresent()) {
            Note entity = noteMapper.noteDtoToNote(note);
            return noteMapper.noteToNoteDto(noteCrudDao.save(entity));
        } else {
            throw new ElementNotFoundException("Element with id " + note.getId() + " not found");
        }
    }

    public NoteDto deleteNote(NoteDto note) throws ElementNotFoundException {
        Optional<Note> optional =  noteCrudDao.findById(note.getId());
        if(optional.isPresent()) {
            Note entity = optional.get();
            if(Objects.equals(note.getVersion(), entity.getVersion())) {
                noteCrudDao.delete(entity);
                return noteMapper.noteToNoteDto(entity);
            } else {
                throw new OptimisticLockingFailureException("Note already changed");
            }
        } else {
            throw new ElementNotFoundException("Element with id " + note.getId() + " not found");
        }
    }

    public List<Note> searchByString(String text) {
        return noteQueriesDao.findByPartOfContent(text);
    }
}
