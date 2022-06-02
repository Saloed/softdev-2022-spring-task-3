package kanban.Repository;

import kanban.Entity.BoardEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepo extends CrudRepository<BoardEntity, Long> {
}
