package kanban.Service;

import kanban.Entity.CardEntity;
import kanban.Entity.ListEntity;
import kanban.Model.Card;
import kanban.Model.Column;
import kanban.Repository.BoardRepo;
import kanban.Repository.CardRepo;
import kanban.Repository.ListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {
    @Autowired
    private ListRepo listRepo;
    @Autowired
    private BoardRepo boardRepo;
    @Autowired
    private CardRepo cardRepo;

    public Column create(ListEntity list, Long boardID){
        list.setBoard(boardRepo.findById(boardID).get());
        ListEntity entity = listRepo.save(list);
        return new Column(entity.getId(),entity.getTitle());
    }

    public Column getOne(Long id){
        ListEntity list = listRepo.findById(id).get();
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < list.getCards().toArray().length; i++){
            CardEntity entity = list.getCards().get(i);
            cards.add(new Card(entity.getId(), entity.getTitle()));
        }
        return new Column(list.getId(), list.getTitle(), cards);
    }

    public ListEntity addCard(Long cardID, Long id){
        CardEntity card = cardRepo.findById(cardID).get();
        ListEntity list = listRepo.findById(id).get();
        List<CardEntity> cards = list.getCards();
        if(cards == null) cards = new ArrayList<>();
        cards.add(card);
        list.setCards(cards);
        card.setColumn(list);
        cardRepo.save(card);
        return listRepo.save(list);
    }

    public ListEntity deleteCard(Long cardID, Long id){
        CardEntity card = cardRepo.findById(cardID).get();
        ListEntity list = listRepo.findById(id).get();
        List<CardEntity> cards = list.getCards();
        if(cards == null) cards = new ArrayList<>();
        cards.remove(card);
        list.setCards(cards);
        card.setColumn(null);
        cardRepo.save(card);
        return listRepo.save(list);
    }

    public Long delete(Long id){
        listRepo.findById(id);
        return id;
    }
}
