package kanban.Repository;

import kanban.Entity.BoardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BoardRepo extends CrudRepository<BoardEntity, Long> {
    @Query("SELECT t FROM BoardEntity t WHERE t.title=:title")
    BoardEntity findByTitle(@Param("title")String t);

    @Override
    <S extends BoardEntity> S save(S entity);
}
