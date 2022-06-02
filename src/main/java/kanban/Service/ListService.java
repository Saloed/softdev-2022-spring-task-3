package kanban.Service;

import kanban.Entity.CardEntity;
import kanban.Entity.ListEntity;
import kanban.Model.Card;
import kanban.Model.Column;
import kanban.Repository.ListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {
    @Autowired
    private ListRepo listRepo;

    public Column create(ListEntity list){
        ListEntity entity = listRepo.save(list);
        return new Column(entity.getId(),entity.getTitle());
    }

    public Column getOne(Long id){
        ListEntity list = listRepo.findById(id).get();
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < list.getCards().toArray().length; i++){
            cards.add(new CardService().getOne(list.getCards().get(i).getId()));
        }
        return new Column(list.getId(), list.getTitle(), cards);
    }

    public ListEntity addCard(CardEntity card, Long id){
        ListEntity list = listRepo.findById(id).get();
        List<CardEntity> cards = list.getCards();
        cards.add(card);
        list.setCards(cards);
        return listRepo.save(list);
    }

    public ListEntity deleteCard(CardEntity card, Long id){
        ListEntity list = listRepo.findById(id).get();
        list.getCards().remove(card);
        return listRepo.save(list);
    }

    public Long delete(Long id){
        listRepo.findById(id);
        return id;
    }
}
