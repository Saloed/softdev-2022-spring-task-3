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

    public Column(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Column(Long id, String title, List<Card> cards) {
        this.id = id;
        this.title = title;
        this.cards = cards;
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
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "id: " + id + ", title: " + title;
    }
}
