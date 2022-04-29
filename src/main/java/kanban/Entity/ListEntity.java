package kanban.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lists")
@Getter
@Setter
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CardEntity> cards;

    public ListEntity() {
    }


}
