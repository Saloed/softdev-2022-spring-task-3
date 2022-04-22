package kanban.Service;

import kanban.Entity.BoardEntity;
import kanban.Entity.CardEntity;
import kanban.Entity.ListEntity;
import kanban.Repository.BoardRepo;
import kanban.Repository.ListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {
    @Autowired
    private ListRepo listRepo;
    @Autowired
    private BoardRepo boardRepo;

    public ListEntity create(ListEntity list, Long boardId){
        BoardEntity board = boardRepo.findById(boardId).get();
        list.setBoard(board);
        return listRepo.save(list);
    }

    public ListEntity getOne(Long id){
        ListEntity list = listRepo.findById(id).get();
        return list;
    }
    public ListEntity changeTitle(String title, Long id){
        ListEntity list = listRepo.findById(id).get();
        list.setTitle(title);
        return listRepo.save(list);
    }
    public ListEntity changeCards(List<CardEntity> cards, Long id){
        ListEntity list = listRepo.findById(id).get();
        list.setCards(cards);
        return listRepo.save(list);
    }
    public Long delete(Long id){
        listRepo.findById(id);
        return id;
    }
}
