package kanban.Service;

import kanban.Entity.BoardEntity;
import kanban.Entity.CardEntity;
import kanban.Entity.UserEntity;
import kanban.Exception.BoardNotFoundException;
import kanban.Repository.BoardRepo;
import kanban.Repository.CardRepo;
import kanban.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private BoardRepo boardRepo;

    public CardEntity create(CardEntity card, Long boardId){
        BoardEntity board = boardRepo.findById(boardId).get();
        card.setBoard(board);
        return cardRepo.save(card);
    }

    public CardEntity getOne(Long id){
        CardEntity card = cardRepo.findById(id).get();
        return card;
    }
    public List<UserEntity> getUsers(Long id) {
        CardEntity card = cardRepo.findById(id).get();
        return card.getUsers();
    }

    public CardEntity changeUsers(List<UserEntity> users, Long id){
        CardEntity card = cardRepo.findById(id).get();
        card.setUsers(users);
        return cardRepo.save(card);
    }

    public CardEntity changeTitle(String title, Long id){
        CardEntity card = cardRepo.findById(id).get();
        card.setTitle(title);
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
