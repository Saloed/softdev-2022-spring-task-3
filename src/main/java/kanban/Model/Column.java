package kanban.Model;

import java.util.List;

public class Column {
    private Long id;
    private String title;
    private String boardTitle;
    private List<Card> cards;

    public Column() {
    }
    public Column(String title, String boardTitle) {
        this.title = title;
        this.boardTitle = boardTitle;
    }
    public Column(Long id, String title, List<Card> cards) {
        this.id = id;
        this.title = title;
        this.cards = cards;
    }

    public Column(Long id, String title, String boardTitle, List<Card> cards) {
        this.id = id;
        this.title = title;
        this.boardTitle = boardTitle;
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

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
