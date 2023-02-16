package kanban.Repository;

import kanban.Entity.CardEntity;
import org.springframework.data.repository.CrudRepository;

public interface CardRepo extends CrudRepository<CardEntity, Long> {
}
