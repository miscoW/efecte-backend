package pl.mwawrzyn.efectebackend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.mwawrzyn.efectebackend.models.dto.NoteDto;
import pl.mwawrzyn.efectebackend.models.entity.Note;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public NoteDto noteToNoteDto(Note note) {
        return modelMapper.map(note, NoteDto.class);
    }

    public Note noteDtoToNote(NoteDto note) {
        return modelMapper.map(note, Note.class);
    }

    public List<NoteDto> noteListToNoteDtoList(List<Note> notes) {
        return notes.stream().map(this::noteToNoteDto).collect(Collectors.toList());
    }

    public List<Note> noteDtoListToNoteList(List<NoteDto> notes) {
        return notes.stream().map(this::noteDtoToNote).collect(Collectors.toList());
    }
}
