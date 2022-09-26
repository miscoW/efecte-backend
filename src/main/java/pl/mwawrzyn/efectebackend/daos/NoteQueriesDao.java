package pl.mwawrzyn.efectebackend.daos;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import pl.mwawrzyn.efectebackend.models.entity.Note;
import pl.mwawrzyn.efectebackend.models.entity.QNote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class NoteQueriesDao {

    @PersistenceContext
    private EntityManager entityManager;
    private QNote note = QNote.note;

    public List<Note> findByPartOfContent(String searchedText) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory
                .selectFrom(note)
                .where(note.content.containsIgnoreCase(searchedText))
                .fetch();
    }
}
