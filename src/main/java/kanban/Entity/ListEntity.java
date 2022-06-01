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

    private String boardTitle;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CardEntity> cards;

    public ListEntity() {
    }


    public ListEntity(Long id) {
        this.id = id;
    }

    public ListEntity(String title, String boardTitle) {
        this.title = title;
        this.boardTitle = boardTitle;
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

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public List<CardEntity> getCards() {
        return cards;
    }

    public void setCards(List<CardEntity> cards) {
        this.cards = cards;
    }

    public void addCard(CardEntity card) {
        this.cards.add(card);
    }
}
