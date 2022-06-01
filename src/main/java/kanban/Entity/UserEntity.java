package kanban.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String password;

    @OneToMany
    private List<BoardEntity> boards;

    public UserEntity(){
    }

    public UserEntity(Long id) {
        this.id = id;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BoardEntity> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardEntity> boards) {
        this.boards = boards;
    }

    public void addBoard(BoardEntity boards) {
        this.boards.add(boards);
    }
}
