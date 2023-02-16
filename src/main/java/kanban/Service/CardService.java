package kanban.Service;

import kanban.Entity.CardEntity;
import kanban.Entity.UserEntity;
import kanban.Model.Card;
import kanban.Model.Column;
import kanban.Model.User;
import kanban.Repository.CardRepo;
import kanban.Repository.ListRepo;
import kanban.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ListRepo listRepo;

    public Card create(CardEntity card, Long ColumnID){
        card.setColumn(listRepo.findById(ColumnID).get());
        CardEntity entity = cardRepo.save(card);
        return new Card(entity.getId(),entity.getTitle(),entity.getDescription());
    }

    public Card getOne(Long id){
        CardEntity card = cardRepo.findById(id).get();
        List<User> users = new ArrayList<>();
        if(card.getUsers() != null) {
            for (int i = 0; i < card.getUsers().toArray().length; i++) {
                UserEntity entity = card.getUsers().get(i);
                users.add(new User(entity.getId(), entity.getUsername()));
            }
        }
        return new Card(card.getId(), card.getTitle(), users, card.getDescription(),new Column(card.getColumn().getId(), card.getColumn().getTitle()));
    }

    public CardEntity changeUsers(List<User> users, Long id){
        CardEntity card = cardRepo.findById(id).get();
        List<UserEntity> u = new ArrayList<>();
        for(int i = 0; i < users.toArray().length; i++){
            u.add(userRepo.findById(users.get(i).getId()).get());
        }
        card.setUsers(u);
        return cardRepo.save(card);
    }

    public CardEntity changeDescription(String desc, Long id){
        CardEntity card = cardRepo.findById(id).get();
        card.setDescription(desc);
        return cardRepo.save(card);
    }

    public Long delete(Long id){
        cardRepo.findById(id);
        return id;
    }
}
