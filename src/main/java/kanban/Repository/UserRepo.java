package kanban.Repository;

import kanban.Entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username=:username")
    UserEntity findByUsername(@Param("username")String u);
}
