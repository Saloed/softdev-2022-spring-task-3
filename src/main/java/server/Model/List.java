package server.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class List {
    private Long id;
    private String title;
    private Board board;
    private java.util.List<Card> Cards;

    public List() {
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public java.util.List<Card> getCards() {
        return Cards;
    }

    public void setCards(java.util.List<Card> cards) {
        Cards = cards;
    }
}
