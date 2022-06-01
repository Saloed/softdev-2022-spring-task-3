package kanban.Service;

import kanban.Entity.CardEntity;
import kanban.Entity.ListEntity;
import kanban.Repository.ListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    @Autowired
    private ListRepo listRepo;

    public ListEntity create(ListEntity list){
        return listRepo.save(list);
    }

    public ListEntity getOne(Long id){
        ListEntity list = listRepo.findById(id).get();
        return list;
    }

    public Iterable<ListEntity> findByTitle(String boardTitle) {
        return listRepo.findByBoard(boardTitle);
    }

    public ListEntity addCard(CardEntity card, Long id){
        ListEntity list = listRepo.findById(id).get();
        list.addCard(card);
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
