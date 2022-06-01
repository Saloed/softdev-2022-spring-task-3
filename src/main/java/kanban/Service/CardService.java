package kanban.Service;

import kanban.Entity.CardEntity;
import kanban.Entity.UserEntity;
import kanban.Repository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepo cardRepo;

    public CardEntity create(CardEntity card){
        return cardRepo.save(card);
    }

    public CardEntity getOne(Long id){
        CardEntity card = cardRepo.findById(id).get();
        return card;
    }

    public CardEntity changeUsers(List<UserEntity> users, Long id){
        CardEntity card = cardRepo.findById(id).get();
        card.setUsers(users);
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
