package kanban.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lists")
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CardEntity> cards;

    public ListEntity() {
    }

    public ListEntity(Long id) {
        this.id = id;
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

    public BoardEntity getBoard() {
        return board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public List<CardEntity> getCards() {
        return cards;
    }

    public void addCard(CardEntity card) {
        this.cards.add(card);
    }
}
