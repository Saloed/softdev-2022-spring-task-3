package kanban.Service;

import kanban.Entity.CardEntity;
import kanban.Entity.UserEntity;
import kanban.Model.Card;
import kanban.Model.User;
import kanban.Repository.CardRepo;
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

    public Card create(CardEntity card){
        CardEntity entity = cardRepo.save(card);
        return new Card(entity.getId(),entity.getTitle(),entity.getDescription(), new ListService().getOne(card.getColumn().getId()));
    }

    public Card getOne(Long id){
        CardEntity card = cardRepo.findById(id).get();
        List<User> users = new ArrayList<>();
        for(int i = 0; i < card.getUsers().toArray().length; i++){
            UserEntity user = card.getUsers().get(i);
            users.add(new User(user.getId(), user.getUsername()));
        }
        return new Card(card.getId(), card.getTitle(), users, card.getDescription(),new ListService().getOne(card.getColumn().getId()));
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
