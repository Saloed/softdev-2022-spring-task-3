package kanban.Repository;

import kanban.Entity.ListEntity;
import org.springframework.data.repository.CrudRepository;

public interface ListRepo extends CrudRepository<ListEntity, Long> {
}
