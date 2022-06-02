package javafx.Model;

import javafx.scene.control.TextField;
import kanban.Model.Card;

public class Note {
    TextField textField;
    Card card;

    public Note() {
    }

    public Note(TextField textField, Card card) {
        this.textField = textField;
        this.card = card;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
