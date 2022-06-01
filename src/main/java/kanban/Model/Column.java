package kanban.Model;

import java.util.List;

public class Column {
    private Long id;
    private String title;
    private List<Card> cards;

    public Column() {
    }

    public Column(String title) {
        this.title = title;
    }

    public Column(String title, List<Card> cards) {
        this.title = title;
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
