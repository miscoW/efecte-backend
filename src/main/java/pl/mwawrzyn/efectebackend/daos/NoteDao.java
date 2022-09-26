package pl.mwawrzyn.efectebackend.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mwawrzyn.efectebackend.models.entity.Note;
@Repository
public interface NoteDao extends CrudRepository<Note, Long> {

}
