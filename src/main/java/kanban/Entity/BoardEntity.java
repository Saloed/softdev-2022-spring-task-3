package kanban.Entity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;

    @ManyToMany(mappedBy = "boards")
    private List<UserEntity> users;

    @OneToMany
    private List<ListEntity> columns;

    public BoardEntity() {
    }

    public BoardEntity(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public List<ListEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ListEntity> columns) {
        this.columns = columns;
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
    }

    public void addColumn(ListEntity column) {
        this.columns.add(column);
    }
}
