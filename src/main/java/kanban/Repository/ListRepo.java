package kanban.Repository;

import kanban.Entity.ListEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ListRepo extends CrudRepository<ListEntity, Long> {
    @Query("SELECT b FROM ListEntity b WHERE b.boardTitle=:boardTitle")
    Iterable<ListEntity> findByBoard(@Param("boardTitle") String b);
}
