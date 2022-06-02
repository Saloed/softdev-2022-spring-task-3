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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "column")
    private List<CardEntity> cards;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    public ListEntity() {
    }


    public ListEntity(Long id) {
        this.id = id;
    }

    public ListEntity(String title) {
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

    public List<CardEntity> getCards() {
        return cards;
    }

    public void setCards(List<CardEntity> cards) {
        this.cards = cards;
    }
}
